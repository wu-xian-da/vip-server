package com.tencent.business;

import com.tencent.common.Configure;
import com.tencent.common.Log;
import com.tencent.common.Signature;
import com.tencent.common.Util;
import com.tencent.common.report.ReporterFactory;
import com.tencent.common.report.protocol.ReportReqData;
import com.tencent.common.report.service.ReportService;
import com.tencent.protocol.native_protocol.NativePayReqData;
import com.tencent.protocol.native_protocol.NativePayResData;
import com.tencent.protocol.native_query_protocol.NativePayQueryReqData;
import com.tencent.protocol.native_query_protocol.NativePayQueryResData;
import com.tencent.protocol.pay_protocol.ScanPayReqData;
import com.tencent.protocol.pay_protocol.ScanPayResData;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryReqData;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryResData;
import com.tencent.protocol.reverse_protocol.ReverseReqData;
import com.tencent.protocol.reverse_protocol.ReverseResData;
import com.tencent.service.NativePayQueryService;
import com.tencent.service.NativePayService;
import com.tencent.service.ReverseService;
import com.tencent.service.ScanPayQueryService;
import com.tencent.service.ScanPayService;

import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;

/**
 * User: liu.lei@jianfeitech.com
 * Date: 2016/05/25
 * Time: 17:05
 */
public class NativePayBusiness {

    public NativePayBusiness() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        nativePayService = new NativePayService();
        nativePayQueryService = new NativePayQueryService();
        reverseService = new ReverseService();
    }

    public interface ResultListener {

        //API返回ReturnCode不合法，支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问
        void onFailByReturnCodeError(NativePayResData nativePayResData);

        //API返回ReturnCode为FAIL，支付API系统返回失败，请检测Post给API的数据是否规范合法
        void onFailByReturnCodeFail(NativePayResData nativePayResData);

        //支付请求API返回的数据签名验证失败，有可能数据被篡改了
        void onFailBySignInvalid(NativePayResData nativePayResData);



        //授权码无效，提示用户刷新一维码/二维码，之后重新扫码支付"
//        void onFailByAuthCodeInvalid(NativePayResData nativePayResData);

        //用户余额不足，换其他卡支付或是用现金支付
        void onFailByMoneyNotEnough(NativePayResData nativePayResData);

        //支付失败
        void onFail(NativePayResData nativePayResData);

        //支付成功
        void onSuccess(NativePayResData nativePayResData);

    }

    //打log用
    private static Log log = new Log(LoggerFactory.getLogger(NativePayBusiness.class));

    //每次调用订单查询API时的等待时间，因为当出现支付失败的时候，如果马上发起查询不一定就能查到结果，所以这里建议先等待一定时间再发起查询

    private int waitingTimeBeforePayQueryServiceInvoked = 5000;

    //循环调用订单查询API的次数
    private int payQueryLoopInvokedCount = 3;

    //每次调用撤销API的等待时间
    private int waitingTimeBeforeReverseServiceInvoked = 5000;

    private NativePayService nativePayService;

    private NativePayQueryService nativePayQueryService;

    private ReverseService reverseService;

    /**
     * 直接执行扫码支付业务逻辑（包含最佳实践流程）
     *
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws Exception
     */
    public NativePayResData run(NativePayReqData nativePayReqData, ResultListener resultListener) throws Exception {

        //--------------------------------------------------------------------
        //构造请求“被扫支付API”所需要提交的数据
        //--------------------------------------------------------------------

        String outTradeNo = nativePayReqData.getOut_trade_no();

        //接受API返回
        String payServiceResponseString;

        long costTimeStart = System.currentTimeMillis();


        log.i("支付API返回的数据如下：");
        payServiceResponseString = nativePayService.request(nativePayReqData);

        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        log.i("api请求总耗时：" + totalTimeCost + "ms");

        //打印回包数据
        log.i(payServiceResponseString);

        //将从API返回的XML数据映射到Java对象
        NativePayResData nativePayResData = (NativePayResData) Util.getObjectFromXML(payServiceResponseString, NativePayResData.class);

     

        if (nativePayResData == null || nativePayResData.getReturn_code() == null) {
            log.e("【支付失败】支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问");
            resultListener.onFailByReturnCodeError(nativePayResData);
            return nativePayResData;
        }

        if (nativePayResData.getReturn_code().equals("FAIL")) {
            //注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
            log.e("【支付失败】支付API系统返回失败，请检测Post给API的数据是否规范合法");
            resultListener.onFailByReturnCodeFail(nativePayResData);
            return nativePayResData;
        } else {
            log.i("支付API系统成功返回数据");
            //--------------------------------------------------------------------
            //收到API的返回数据的时候得先验证一下数据有没有被第三方篡改，确保安全
            //--------------------------------------------------------------------
            if (!Signature.checkIsSignValidFromResponseString(payServiceResponseString)) {
                log.e("【支付失败】支付请求API返回的数据签名验证失败，有可能数据被篡改了");
                resultListener.onFailBySignInvalid(nativePayResData);
                return nativePayResData;
            }

            //获取错误码
            String errorCode = nativePayResData.getErr_code();
            //获取错误描述
            String errorCodeDes = nativePayResData.getErr_code_des();

            if (nativePayResData.getResult_code().equals("SUCCESS")) {
                //--------------------------------------------------------------------
                //1)直接扣款成功
                //--------------------------------------------------------------------
                log.i("【一次性支付成功】");
                resultListener.onSuccess(nativePayResData);
                return nativePayResData;
            }else{

                //出现业务错误
                log.i("业务返回失败");
                log.i("err_code:" + errorCode);
                log.i("err_code_des:" + errorCodeDes);

                //业务错误时错误码有好几种，商户重点提示以下几种
                if (errorCode.equals("NOTENOUGH")) {
                    //--------------------------------------------------------------------
                    //2)扣款明确失败
                    //--------------------------------------------------------------------
                    //对于扣款明确失败的情况直接走撤销逻辑
                    doReverseLoop(outTradeNo);
                    //提示用户余额不足，换其他卡支付或是用现金支付
                    log.w("【支付扣款明确失败】原因是：" + errorCodeDes);
                    resultListener.onFailByMoneyNotEnough(nativePayResData);
                    
                } else {
                    //--------------------------------------------------------------------
                    //4)扣款未知失败
                    //--------------------------------------------------------------------
                    if (doPayQueryLoop(payQueryLoopInvokedCount, outTradeNo)) {
                        log.i("【支付扣款未知失败、查询到支付成功】");
                        resultListener.onSuccess(nativePayResData);
                    } else {
                        log.i("【支付扣款未知失败、在一定时间内没有查询到支付成功、走撤销流程】");
                        doReverseLoop(outTradeNo);
                        resultListener.onFail(nativePayResData);
                    }
                }
                return nativePayResData;
            }
        }
    }

    /**
     * 进行一次支付订单查询操作
     *
     * @param outTradeNo    商户系统内部的订单号,32个字符内可包含字母, [确保在商户系统唯一]
     * @return 该订单是否支付成功
     * @throws Exception
     */
    private boolean doOnePayQuery(String outTradeNo) throws Exception {

        sleep(waitingTimeBeforePayQueryServiceInvoked);//等待一定时间再进行查询，避免状态还没来得及被更新

        String payQueryServiceResponseString;

        NativePayQueryReqData nativePayQueryReqData = new NativePayQueryReqData("",outTradeNo);
        payQueryServiceResponseString = nativePayQueryService.request(nativePayQueryReqData);

        log.i("支付订单查询API返回的数据如下：");
        log.i(payQueryServiceResponseString);

        //将从API返回的XML数据映射到Java对象
        NativePayQueryResData nativePayQueryResData = (NativePayQueryResData) Util.getObjectFromXML(payQueryServiceResponseString, NativePayQueryResData.class);
        if (nativePayQueryResData == null || nativePayQueryResData.getReturn_code() == null) {
            log.i("支付订单查询请求逻辑错误，请仔细检测传过去的每一个参数是否合法");
            return false;
        }

        if (nativePayQueryResData.getReturn_code().equals("FAIL")) {
            //注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
            log.i("支付订单查询API系统返回失败，失败信息为：" + nativePayQueryResData.getReturn_msg());
            return false;
        } else {
            if (nativePayQueryResData.getResult_code().equals("SUCCESS")) {//业务层成功
                if (nativePayQueryResData.getTrade_state().equals("SUCCESS")) {
                    //表示查单结果为“支付成功”
                    log.i("查询到订单支付成功");
                    return true;
                } else {
                    //支付不成功
                    log.i("查询到订单支付不成功");
                    return false;
                }
            } else {
                log.i("查询出错，错误码：" + nativePayQueryResData.getErr_code() + "     错误信息：" + nativePayQueryResData.getErr_code_des());
                return false;
            }
        }
    }

    /**
     * 由于有的时候是因为服务延时，所以需要商户每隔一段时间（建议5秒）后再进行查询操作，多试几次（建议3次）
     *
     * @param loopCount     循环次数，至少一次
     * @param outTradeNo    商户系统内部的订单号,32个字符内可包含字母, [确保在商户系统唯一]
     * @return 该订单是否支付成功
     * @throws InterruptedException
     */
    private boolean doPayQueryLoop(int loopCount, String outTradeNo) throws Exception {
        //至少查询一次
        if (loopCount == 0) {
            loopCount = 1;
        }
        //进行循环查询
        for (int i = 0; i < loopCount; i++) {
            if (doOnePayQuery(outTradeNo)) {
                return true;
            }
        }
        return false;
    }

    //是否需要再调一次撤销，这个值由撤销API回包的recall字段决定
    private boolean needRecallReverse = false;

    /**
     * 进行一次撤销操作
     *
     * @param outTradeNo    商户系统内部的订单号,32个字符内可包含字母, [确保在商户系统唯一]
     * @return 该订单是否支付成功
     * @throws Exception
     */
    private boolean doOneReverse(String outTradeNo) throws Exception {

        sleep(waitingTimeBeforeReverseServiceInvoked);//等待一定时间再进行查询，避免状态还没来得及被更新

        String reverseResponseString;

        ReverseReqData reverseReqData = new ReverseReqData("",outTradeNo);
        reverseResponseString = reverseService.request(reverseReqData);

        log.i("撤销API返回的数据如下：");
        log.i(reverseResponseString);
        //将从API返回的XML数据映射到Java对象
        ReverseResData reverseResData = (ReverseResData) Util.getObjectFromXML(reverseResponseString, ReverseResData.class);
        if (reverseResData == null) {
            log.i("支付订单撤销请求逻辑错误，请仔细检测传过去的每一个参数是否合法");
            return false;
        }
        if (reverseResData.getReturn_code().equals("FAIL")) {
            //注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
            log.i("支付订单撤销API系统返回失败，失败信息为：" + reverseResData.getReturn_msg());
            return false;
        } else {
            if (reverseResData.getResult_code().equals("FAIL")) {
                log.i("撤销出错，错误码：" + reverseResData.getErr_code() + "     错误信息：" + reverseResData.getErr_code_des());
                if (reverseResData.getRecall().equals("Y")) {
                    //表示需要重试
                    needRecallReverse = true;
                    return false;
                } else {
                    //表示不需要重试，也可以当作是撤销成功
                    needRecallReverse = false;
                    return true;
                }
            } else {
                //查询成功，打印交易状态
                log.i("支付订单撤销成功");
                return true;
            }
        }
    }


    /**
     * 由于有的时候是因为服务延时，所以需要商户每隔一段时间（建议5秒）后再进行查询操作，是否需要继续循环调用撤销API由撤销API回包里面的recall字段决定。
     *
     * @param outTradeNo    商户系统内部的订单号,32个字符内可包含字母, [确保在商户系统唯一]
     * @throws InterruptedException
     */
    private void doReverseLoop(String outTradeNo) throws Exception {
        //初始化这个标记
        needRecallReverse = true;
        //进行循环撤销，直到撤销成功，或是API返回recall字段为"Y"
        while (needRecallReverse) {
            if (doOneReverse(outTradeNo)) {
                return;
            }
        }
    }

    /**
     * 设置循环多次调用订单查询API的时间间隔
     *
     * @param duration 时间间隔，默认为10秒
     */
    public void setWaitingTimeBeforePayQueryServiceInvoked(int duration) {
        waitingTimeBeforePayQueryServiceInvoked = duration;
    }

    /**
     * 设置循环多次调用订单查询API的次数
     *
     * @param count 调用次数，默认为三次
     */
    public void setPayQueryLoopInvokedCount(int count) {
        payQueryLoopInvokedCount = count;
    }

    /**
     * 设置循环多次调用撤销API的时间间隔
     *
     * @param duration 时间间隔，默认为5秒
     */
    public void setWaitingTimeBeforeReverseServiceInvoked(int duration) {
        waitingTimeBeforeReverseServiceInvoked = duration;
    }

    public void setNativePayService(NativePayService service) {
        nativePayService = service;
    }

    public void setNativePayQueryService(NativePayQueryService service) {
        nativePayQueryService = service;
    }

    public void setReverseService(ReverseService service) {
        reverseService = service;
    }

}
