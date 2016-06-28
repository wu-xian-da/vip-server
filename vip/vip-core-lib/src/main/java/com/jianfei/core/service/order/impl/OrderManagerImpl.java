package com.jianfei.core.service.order.impl;

import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateContentBuilder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.*;
import com.jianfei.core.common.enu.*;
import com.jianfei.core.common.pay.PayQueryResult;
import com.jianfei.core.common.pay.PreCreateResult;
import com.jianfei.core.common.utils.*;
import com.jianfei.core.dto.*;
import com.jianfei.core.mapper.AppCardBackMapper;
import com.jianfei.core.mapper.AppConsumeMapper;
import com.jianfei.core.mapper.AppOrderCardMapper;
import com.jianfei.core.mapper.AppOrdersMapper;
import com.jianfei.core.service.base.VipCardManager;
import com.jianfei.core.service.base.impl.AppInvoiceManagerImpl;
import com.jianfei.core.service.base.impl.ValidateCodeManagerImpl;
import com.jianfei.core.service.base.impl.VipCardManagerImpl;
import com.jianfei.core.service.order.OrderManager;
import com.jianfei.core.service.thirdpart.QueueManager;
import com.jianfei.core.service.thirdpart.ThirdPayManager;
import com.jianfei.core.service.user.impl.VipUserManagerImpl;
import com.tencent.protocol.native_protocol.NativePayReqData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 16:31
 */
@Service
@Transactional
public class OrderManagerImpl implements OrderManager {

    @Autowired
    private AppOrdersMapper appOrdersMapper;
    @Autowired
    private AppOrderCardMapper appOrderCardMapper;
    @Autowired
    private AppConsumeMapper appConsumeMapper;
    @Autowired
    private AppCardBackMapper appCardBackMapper;
    @Autowired
    private VipUserManagerImpl vipUserManager;
    @Autowired
    private VipCardManager vipCardManager;
    @Autowired
    private AppInvoiceManagerImpl invoiceManager;
    @Autowired
    private ValidateCodeManagerImpl validateCodeManager;
    @Autowired
    private ConsumeManagerImpl consumeManager;
    @Autowired
    private ThirdPayManager aliPayManager;
    @Autowired
    private ThirdPayManager wechatiPayManager;
    @Autowired
    private ThirdPayManager yeepayManager;
    @Autowired
    private QueueManager queueManager;

    /**
     * 添加订单信息
     *
     * @param addInfoDto
     * @return
     * @author dsliu
     */
    @Override
    public BaseMsgInfo addOrderAndUserInfo(OrderAddInfoDto addInfoDto) throws InvocationTargetException, IllegalAccessException {
       //重复提交问题
        if(StringUtils.isNotBlank(addInfoDto.getOrderId())){
            AppOrders appOrders=appOrdersMapper.selectByPrimaryKey(addInfoDto.getOrderId());
            addInfoDto.setMoney(appOrders.getPayMoney());
            return BaseMsgInfo.success(addInfoDto);
        }
        //1、校验用户和手机验证码
        boolean flag = validateCodeManager.validateSendCode(addInfoDto.getPhone(), MsgType.REGISTER, addInfoDto.getCode());
        if (!flag) {
            return new BaseMsgInfo().setCode(-1).setMsg("手机验证码验证失败");
        }

        //2、根据查询VIP号查询卡片信息
        AppVipcard vipCard = vipCardManager.getVipCardByNo(addInfoDto.getVipCardNo());
        if (vipCard == null) {
            return new BaseMsgInfo().setCode(-1).setMsg("VIP卡号错误");
        } else if (VipCardState.ACTIVE.getName() == vipCard.getCardState()) {
            return BaseMsgInfo.msgFail("VIP卡已使用");
        }

        //3、添加或修改用户信息
        AppCustomer customer = new AppCustomer();
        BeanUtils.copyProperties(customer, addInfoDto);
        vipUserManager.addORUpdateUser(customer);


        //4、添加订单信息
        AppOrders orders = new AppOrders();
        BeanUtils.copyProperties(orders, addInfoDto);
        orders.setSaleNo(addInfoDto.getUno());
        orders.setCustomerId(customer.getCustomerId());
        orders.setPayMoney(vipCard.getInitMoney());
        orders.setOrderId(IdGen.uuid());
        orders.setOrderTime(new Date());
        orders.setRemark1("亿出行VIP卡");
        orders.setOrderState(VipOrderState.NOT_PAY.getName());
        orders.setDtflag(StateType.EXIST.getName());
        orders.setInvoiceFlag(0);
        appOrdersMapper.insertSelective(orders);

        //5、卡表及订单卡表
        vipCard.setCustomerId(customer.getCustomerId());
        vipCardManager.updateVipCard(vipCard);
        AppOrderCard appOrderCard = new AppOrderCard();
        appOrderCard.setId(IdGen.uuid());
        appOrderCard.setOrderId(orders.getOrderId());
        appOrderCard.setCardNo(addInfoDto.getVipCardNo());
        appOrderCard.setCardNum(1);
        appOrderCard.setInitMoney(vipCard.getInitMoney());
        appOrderCard.setCardType(vipCard.getCardType());
        appOrderCardMapper.insert(appOrderCard);

        addInfoDto.setOrderId(orders.getOrderId());
        addInfoDto.setMoney(vipCard.getInitMoney());
        return BaseMsgInfo.success(addInfoDto);

    }


    /**
     * queryPage(订单列表分页查询)
     *
     * @param pageNo
     * @param pageSize
     * @param params
     *            封装查询参数
     * @return Page<Role>
     * @version 1.0.0
     */

    public PageInfo<OrderShowInfoDto> simplePage(int pageNo, int pageSize,
                                                 Map<String, Object> params) {
        PageHelper.startPage(pageNo, pageSize);
        List<OrderShowInfoDto> list = appOrdersMapper.get(params);
        PageInfo<OrderShowInfoDto> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 更新订单信息付款
     *
     * @param addInfoDto
     * @return
     */
    @Override
    public boolean updateOrderPayInfo(AppOrders addInfoDto) {
        return appOrdersMapper.updateByPrimaryKeySelective(addInfoDto) < 0 ? false : true;
    }


    /**
     * 订单发票信息
     *
     * @param appInvoice 发票信息
     * @return
     */
    @Override
    public BaseMsgInfo addOrderMailInfo(AppInvoice appInvoice) {
        AppOrders orders = appOrdersMapper.selectByPrimaryKey(appInvoice.getOrderId());
        if (orders == null || StringUtils.isBlank(orders.getOrderId())) {
            return BaseMsgInfo.msgFail("订单不存在");
        }
        invoiceManager.insert(appInvoice);
        orders.setInvoiceFlag(InvoiceState.NEED_INVOICE.getName());
        int flag = appOrdersMapper.updateByPrimaryKeySelective(orders);
        if (flag < 0)
            return BaseMsgInfo.msgFail("订单邮寄信息状态更新失败");
        return BaseMsgInfo.success(true);
    }

    /**
     * 更新订单状态
     * 0 未支付
     * 1 已支付
     * 2 正在审核
     * 3 审核通过
     * 4 退款成功
     */
    @Override
    public int updateOrderStateByOrderId(String orderId, int operationType) {
        // TODO Auto-generated method stub

        int orderState = 1;
        if (operationType == 0) {//退款申请
            orderState = 2;
        } else if (operationType == 1) {//审核通过
            orderState = 2;
        } else if (operationType == 2) {//审核不通过
            orderState = 1;
        } else if (operationType == 3) {//录入退卡人账号
            orderState = 3;
        } else if (operationType == 4) {//最终退款
            orderState = 4;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderState", orderState);
        params.put("orderId", orderId);

        return appOrdersMapper.updateOrderState(params);

    }


    /**
     * 根据订单号返回用户vip卡可退金额
     */
    @Override
    public double remainMoney(String orderId) {
        double remainMoney = 0.00;
        //1、app_order_card表中返回卡号，用户初始金额
        AppOrderCard appOrderCard = appOrderCardMapper.getAppOrderCard(orderId);
        if (appOrderCard != null) {
            //2、app_consume表中返回vip消费次数
            int count = appConsumeMapper.getCountCosume(appOrderCard.getCardNo());
            //3、计算用户vip卡剩余金额
            remainMoney = (float) (appOrderCard.getInitMoney() - count * 200 * 0.8 - 100);
            if (remainMoney < 0) {
                remainMoney = 0.00;
            }

        }
        System.out.println("float remainMoney=" + remainMoney);
        return remainMoney;
    }


    /* 获取销售某天开卡详细数据
     *
     * @param userId 销售Id
     * @param date   日期
     * @return
     */
    @Override
    public List<AppOrders> getOrdersBySaleId(String userId, String date) {
        return null;
    }

    /**
     * 查询某个销售销售卡退卡分页
     *
     * @param userId  销售ID
     * @param pageDto 分页数据
     * @return
     */
    @Override
    public PageInfo<AppOrders> pageReturnOrderBySaleId(String userId, PageDto pageDto) {
        return null;
    }


    /**
     * 记录退卡流水号
     */
    @Override
    public int insertBackCardInfo(AppCardBack appCardBack) {
        // TODO Auto-generated method stub
        appCardBackMapper.insertBackCard(appCardBack);
        return 0;
    }


    /**
     * 通过订单号在流水表中查询用户的退款账户
     */
    @Override
    public AppCardBack selCustomerCard(String orderId) {
        // TODO Auto-generated method stub
        return appCardBackMapper.seleConsumeCardId(orderId);
    }


    /**
     * 更新退卡流水号
     */
    @Override
    public int updateBackCardByOrderId(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return appCardBackMapper.updateBackCard(map);
    }


    /**
     * 根据订单编号返回订单详细信息
     */
    @Override
    public OrderDetailInfo returnOrderDetailInfoByOrderId(String orderId) {
        // TODO Auto-generated method stub
        return appOrdersMapper.selOrderDetailInfo(orderId);
    }


    /* (non-Javadoc)
     * @see com.jianfei.core.service.order.OrderManager#backCardPage(int, int, java.util.Map)
     */
    @Override
    public PageInfo<OrderShowInfoDto> backCardPage(int pageNo, int pageSize, Map<String, Object> params) {
        PageHelper.startPage(pageNo, pageSize);
        List<OrderShowInfoDto> list = appOrdersMapper.page(params);
        PageInfo<OrderShowInfoDto> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 根据付款方式获取付款URL
     *
     * @param orderId 订单ID
     * @param payType 付款方式
     * @return
     */
    @Override
    public BaseMsgInfo getPayUrl(String orderId, PayType payType) {
        AppOrders appOrders = getOrderInfo(orderId);
        if (appOrders == null || StringUtils.isBlank(appOrders.getOrderId())) {
            return BaseMsgInfo.msgFail("订单不存在");
        }
        PreCreateResult preCreateResult = new PreCreateResult();
        if (PayType.WXPAY.equals(payType)) {
            /**
             * @param authCode 这个是扫码终端设备从用户手机上扫取到的支付授权号，这个号是跟用户用来支付的银行卡绑定的，有效期是1分钟
             * @param body 要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
             * @param attach 支付订单里面可以填的附加数据，API会将提交的这个附加数据原样返回
             * @param outTradeNo 商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
             * @param totalFee 订单总金额，单位为“分”，只能整数
             * @param deviceInfo 商户自己定义的扫码支付终端设备号，方便追溯这笔交易发生在哪台终端设备上
             * @param spBillCreateIP 订单生成的机器IP
             * @param timeStart 订单生成时间， 格式为yyyyMMddHHmmss，如2009年12 月25 日9 点10 分10 秒表示为20091225091010。时区为GMT+8 beijing。该时间取自商户服务器
             * @param timeExpire 订单失效时间，格式同上
             * @param goodsTag 商品标记，微信平台配置的商品标记，用于优惠券或者满减使用
             */
            NativePayReqData nativePayReqData = new NativePayReqData("", appOrders.getRemark1(), "", orderId, (int) (appOrders.getPayMoney() * 100),
                    "", "192.168.199.200", "", "", "", "", GloabConfig.getConfig("pay.notify.address") + "/pay/wechat_notify", "NATIVE", appOrders.getAirportId(), "", "");
            preCreateResult = wechatiPayManager.tradePrecreate(nativePayReqData);
        } else if (PayType.ALIPAY.equals(payType)) {
            GoodsDetail goodsDetail = GoodsDetail.newInstance(appOrders.getOrderId(), appOrders.getRemark1(), 1, 1);
            List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
            goodsDetailList.add(goodsDetail);
            AlipayTradePrecreateContentBuilder builder = new
                    AlipayTradePrecreateContentBuilder().setSubject(appOrders.getRemark1())
                    .setTotalAmount(appOrders.getPayMoney().toString()).setOutTradeNo(appOrders.getOrderId())
                    .setGoodsDetailList(goodsDetailList).setStoreId("test");

            preCreateResult = aliPayManager.tradePrecreate(builder);
        } else if (PayType.BANKPAY.equals(payType)) {
            preCreateResult.setCode("0");
            preCreateResult.setQrUrl(orderId);
        }
        if ("0".equals(preCreateResult.getCode())) {
            return BaseMsgInfo.success(preCreateResult.getQrUrl());
        } else {
            return new BaseMsgInfo().setCode(-1).setMsg(preCreateResult.getMsg());
        }
    }

    /**
     * 根据订单ID获取订单金额
     *
     * @param orderId
     * @return
     */
    @Override
    public AppOrders getOrderInfo(String orderId) {
        return appOrdersMapper.selectByPrimaryKey(orderId);
    }


    @Override
    public int updateOrderStateByOrderIdEx(String orderId, int orderState) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderState", orderState);
        params.put("orderId", orderId);

        return appOrdersMapper.updateOrderState(params);
    }

    /**
     * @param orderId
     * @param payType
     * @return
     */
    @Override
    public BaseMsgInfo checkThirdPay(String orderId, PayType payType) {
        //先查询订单是否存在及合法
        AppOrders appOrders=appOrdersMapper.selectByPrimaryKey(orderId);

        //如果订单存在 并且已付款
        if (appOrders==null || StringUtils.isBlank(appOrders.getOrderId())){
            return BaseMsgInfo.msgFail("订单不存在");
        }
        if (VipOrderState.ALREADY_PAY.getName() == appOrders.getOrderState()) {
            return BaseMsgInfo.success(true);
        }
        //查询第三方支付接口
        PayQueryResult result = new PayQueryResult();
        if (PayType.WXPAY.equals(payType)) {
            result = wechatiPayManager.tradeQuery(orderId);
        } else if (PayType.ALIPAY.equals(payType)) {
            result = aliPayManager.tradeQuery(orderId);
        } else if (PayType.BANKPAY.equals(payType)) {
            result = yeepayManager.tradeQuery(orderId);
        }
        if ("0".equals(result.getCode())) {
            appOrders.setPayUserId(result.getPayUserId());
            appOrders.setSerialId( result.getTradeNo());
            appOrders.setPayType(payType.getName());
            BaseMsgInfo baseMsgInfo = updatePayState(appOrders);
            if (baseMsgInfo.getCode() < 0) {
                return baseMsgInfo;
            }
            return BaseMsgInfo.success(true);
        } else {
            return BaseMsgInfo.success(false);
        }
    }


    /**
     * 根据手机号查询用户VIP卡使用信息和订单详细信息
     *
     * @param phone 手机号
     * @param code
     * @return
     */
    @Override
    public BaseMsgInfo getVipCardUseAndOrder(String phone, String code, String vipCardNo) {
        //1、校验用户和手机验证码
        boolean flag = validateCodeManager.validateSendCode(phone, MsgType.SELECT, code);
        if (!flag)
            return new BaseMsgInfo().setCode(-1).setMsg("验证码校验失败");
        //2、查询用户信息和订单信息
        List<VipCardUseDetailInfo> vipCardUseDetailInfoList = appOrderCardMapper.getVipCardUseDetailInfo(phone, vipCardNo);
        VipCardUseDetailInfo vipCardUseDetailInfo = vipCardUseDetailInfoList == null || vipCardUseDetailInfoList.isEmpty() ? new VipCardUseDetailInfo()
                : vipCardUseDetailInfoList.get(0);
        if (vipCardUseDetailInfo == null || StringUtils.isBlank(vipCardUseDetailInfo.getVipCardNo())) {
            return BaseMsgInfo.msgFail("暂未查询到此VIP卡相关信息");
        }
        getVipCardUseInfo(vipCardUseDetailInfo);
        return BaseMsgInfo.success(vipCardUseDetailInfo);
    }

    /**
     * 计算退款相关信息
     *
     * @param vipCardUseDetailInfo
     */
    private void getVipCardUseInfo(VipCardUseDetailInfo vipCardUseDetailInfo) {
        //3、查询VIP使用信息
        List<AppConsume> list = consumeManager.getConsumesByVipNo(vipCardUseDetailInfo.getVipCardNo());
        float usedMoney = 0;
        if (list != null && list.isEmpty()) {
            for (AppConsume appConsume : list) {
                usedMoney = usedMoney + appConsume.getConsumeMoney();
            }
        }
        float realMoney = (float) (usedMoney * 0.8);
        float remainMoney = vipCardUseDetailInfo.getOrderMoney() - realMoney - 100;
        if (remainMoney < 0) {
            remainMoney = 0;
        }
        vipCardUseDetailInfo.setReturnMoney(remainMoney);
        vipCardUseDetailInfo.setRealMoney(realMoney);
        vipCardUseDetailInfo.setReturnInfo("您已免费享受了价值" + usedMoney + "元的VIP室服务,若退卡需扣除该费用。亿出行仅收取该费用的80%作为服务费。");
        vipCardUseDetailInfo.setUsedMoney(usedMoney);
        vipCardUseDetailInfo.setCardUseList(list);
        vipCardUseDetailInfo.setSaleRate("80%");
    }


    /* (non-Javadoc)
     * @see com.jianfei.core.service.order.OrderManager#selectOrder(java.util.Map)
     */
    @Override
    public List<Map<String, Object>> selectOrder(Map<String, Object> map) {
        return appOrdersMapper.selectOrder(map);
    }

    /**
     * 更新用户付款状态
     * 并发送激活卡消息
     *
     * @return
     */
    @Override
    public BaseMsgInfo updatePayState(AppOrders appOrders) {
        if (StringUtils.isBlank(appOrders.getOrderId())){
            return BaseMsgInfo.msgFail("订单号不存在");
        }
        appOrders.setOrderState(VipOrderState.ALREADY_PAY.getName());
        appOrders.setPayTime(new Date());
        int num = appOrdersMapper.updateByPrimaryKeySelective(appOrders);
        //查询是否存在用户手机号等信息 如果不存在查询
        if (appOrders.getCustomer() == null || StringUtils.isBlank(appOrders.getCustomer().getPhone())) {
            appOrders=appOrdersMapper.getOrderDetailByOrderId(appOrders.getOrderId());
        }
        ServiceMsgBuilder msgBuilder=new ServiceMsgBuilder().setUserPhone(appOrders.getCustomer().getPhone()).setMsgType(MsgType.ACTIVE_CARD.getName()).
                setVipCardNo(appOrders.getVipCards().get(0).getCardNo()).setUserName(appOrders.getCustomer().getCustomerName());
         queueManager.sendMessage(msgBuilder);
        if (num > 0) {
            return BaseMsgInfo.success(true);
        } else {
            return BaseMsgInfo.msgFail("确认付款失败");
        }
    }

    /**
     * APP端添加退卡信息
     *
     * @param appCardBack 退卡信息
     * @return
     */
    @Override
    public BaseMsgInfo addBackCardInfo(AppCardBack appCardBack) {
        //1、根据订单号查询订单信息
        AppOrders orders = appOrdersMapper.getOrderDetailByOrderId(appCardBack.getOrderId());
        if (orders == null || StringUtils.isBlank(orders.getOrderId())) {
            return BaseMsgInfo.msgFail("订单不存在");
        }

        //2、查询用户信息和订单信息
        if (VipOrderState.NOT_PAY.getName() == orders.getOrderState()) {
            return BaseMsgInfo.msgFail("订单未付款");
        }else if (!(VipOrderState.ALREADY_PAY.getName() == orders.getOrderState())){
            return BaseMsgInfo.msgFail("订单已经申请退款");
        }

        // 2、重新计算可退余额 校验是否正确
        VipCardUseDetailInfo useDetailInfo=new VipCardUseDetailInfo();
        useDetailInfo.setOrderMoney(orders.getPayMoney());
        useDetailInfo.setVipCardNo(orders.getVipCards().get(0).getCardNo());
        getVipCardUseInfo(useDetailInfo);
         if(useDetailInfo.getReturnMoney()!=appCardBack.getMoney()){
			return BaseMsgInfo.fail("退款金额有误，请重新查询使用");
		}
        //3、插入数据库
        appCardBack.setBackId(IdGen.uuid());
        appCardBack.setCreateTime(new Date());
        int i = appCardBackMapper.insertBackCard(appCardBack);
        ServiceMsgBuilder msgBuilder=new ServiceMsgBuilder().setUserPhone(orders.getCustomer().getPhone()).
                setVipCardNo(orders.getVipCards().get(0).getCardNo()).setUserName(orders.getCustomer().getCustomerName());
        //添加订单状态为已退款
        if (StringUtils.isNotBlank(appCardBack.getAgreementUrl())) {
            //更改订单状态为已退款 和申请方式为
            orders.setOrderState(VipOrderState.ALREADY_REFUND.getName());
            msgBuilder.setMsgType(MsgType.BACK_CARD_APPLY.getName());
        } else {
            //审核通过
            orders.setOrderState(VipOrderState.AUDIT_PASS.getName());
            msgBuilder.setMsgType(MsgType.RIGHT_BACK_CARD.getName());
        }
        AppVipcard vipcard=new AppVipcard();
        vipcard.setCardNo(orders.getVipCards().get(0).getCardNo());
        vipcard.setCardState(VipCardState.BACK_CARD.getName());
        vipCardManager.updateVipCard(vipcard);
        appOrdersMapper.updateByPrimaryKeySelective(orders);
        queueManager.sendMessage(msgBuilder);
        return i > 0 ? BaseMsgInfo.success(true) : BaseMsgInfo.fail("退卡信息添加失败");
    }

    /**
     * 根据订单号返回订单基本信息
     */
    @Override
    public AppOrders getOrderInfoByOrderId(String orderId) {
        // TODO Auto-generated method stub
        return appOrdersMapper.selectByPrimaryKey(orderId);
    }

	/**
	 * 查询需要开发票的订单信息
	 */
	@Override
	public PageInfo<OrderShowInfoDto> invoicePageList(int pageNo, int pageSize,Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNo, pageSize);
		List<OrderShowInfoDto> list = appOrdersMapper.invoicePageList(map);
		PageInfo<OrderShowInfoDto> pageInfo = new PageInfo(list);
		return pageInfo;
	}

	/**
	 * 根据order_id返回订单基本信息
	 */
	@Override
	public AppOrders selectByPrimaryKey(String orderId) {
		// TODO Auto-generated method stub
		return appOrdersMapper.selectByPrimaryKey(orderId);
	}

	/**
	 * 根据ordrId查询订单卡表信息
	 */
	@Override
	public AppOrderCard selectByOrderId(String orderId) {
		// TODO Auto-generated method stub
		return appOrderCardMapper.selectByOrderId(orderId);
	}

	/**
	 * 根据卡号返回所有的消费记录
	 */
	@Override
	public List<AppConsume> selectByVipCardNo(String cardNo) {
		// TODO Auto-generated method stub
		return appConsumeMapper.selectByVipCardNo(cardNo);
	}

	/**
	 * 不分页，返回所有满足条件的数据
	 */
	@Override
	public List<OrderShowInfoDto> simplePage(Map<String, Object> params) {
		// TODO Auto-generated method stub
		List<OrderShowInfoDto> list = appOrdersMapper.get(params);
		return list;
	}
}
