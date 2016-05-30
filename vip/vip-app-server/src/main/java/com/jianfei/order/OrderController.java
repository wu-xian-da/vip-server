package com.jianfei.order;

import com.jianfei.common.BaseMsgInfo;
import com.jianfei.core.bean.AppInvoice;
import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.dto.OrderAddInfoDto;
import com.jianfei.core.service.order.PayManager;
import com.jianfei.core.service.order.impl.OrderManagerImpl;
import com.jianfei.core.service.order.impl.PayClass;
import com.jianfei.core.service.thirdpart.impl.MsgInfoManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单相关接口
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/17 15:51
 */
@Controller
@RequestMapping(value = "order")
public class OrderController {

    @Autowired
    private OrderManagerImpl orderManager;


    /**
     * 添加订单信息
     * @param addInfoDto
     * @return
     */
    @RequestMapping(value = "/addOrder")
    @ResponseBody
    public BaseMsgInfo addOrder(OrderAddInfoDto addInfoDto) {
      boolean flag=  orderManager.addOrderAndUserInfo(addInfoDto);
        if(flag){
            return BaseMsgInfo.success(addInfoDto);
        }else {
            return BaseMsgInfo.fail(addInfoDto);
        }
    }


    /**
     * 生成支付URL接口 传入支付类型
     * @param orderId 订单号
     * @param payType  支付类型
     * @return
     */
    @RequestMapping(value = "/getPayUrl")
    @ResponseBody
    public BaseMsgInfo getPayUrl(@RequestParam(value = "orderId", required = true) String orderId,
                                 @RequestParam(value = "payType ", required = true) String payType
    ) {
       //TODO
        return new BaseMsgInfo();
    }

    /**
     * 第三方支付确认收款接口
     * @param orderId 订单号
     * @param payType  支付类型
     * @return
     */
    @RequestMapping(value = "/checkThirdPay")
    @ResponseBody
    public BaseMsgInfo checkThirdPay(@RequestParam(value = "orderId", required = true) String orderId,
                                 @RequestParam(value = "payType ", required = true) String payType
    ) {
        //TODO
        return new BaseMsgInfo();
    }

    /**
     * 顾客现金刷卡确认接口
     * @param orderId 订单号
     * @param payType  支付类型
     * @return
     */
    @RequestMapping(value = "/checkBuyerPay")
    @ResponseBody
    public BaseMsgInfo checkBuyerPay(@RequestParam(value = "orderId", required = true) String orderId,
                                     @RequestParam(value = "payType ", required = true) String payType
    ) {
        //TODO
        return new BaseMsgInfo();
    }

    /**
     * 邮寄信息保存
     * @param appInvoice 邮寄信息
     * @return
     */
    @RequestMapping(value = "/addOrderMail")
    @ResponseBody
    public BaseMsgInfo addOrderMail(AppInvoice appInvoice
    ) {
        boolean flag=  orderManager.addOrderMailInfo(appInvoice);
        if(flag){
            return BaseMsgInfo.success(flag);
        }else {
            return BaseMsgInfo.fail(flag);
        }
    }


    @RequestMapping(value = "/getVipReturnInfo")
    @ResponseBody
    public BaseMsgInfo getVipReturnInfo(@RequestParam(value = "phone", required = true) String phone,
                                        @RequestParam(value = "code", required = true) String code
    ) {
      //TODO 验证 获取消费记录List
        return BaseMsgInfo.success("");

    }


}
