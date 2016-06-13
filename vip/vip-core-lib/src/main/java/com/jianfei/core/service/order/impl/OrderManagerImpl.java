package com.jianfei.core.service.order.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateContentBuilder;
import com.jianfei.core.bean.*;
import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.common.enu.PayType;
import com.jianfei.core.common.pay.PayQueryResult;
import com.jianfei.core.common.pay.PreCreateResult;
import com.jianfei.core.common.utils.BeanUtils;
import com.jianfei.core.common.utils.IdGen;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.common.enu.*;
import com.jianfei.core.common.utils.*;
import com.jianfei.core.dto.*;
import com.jianfei.core.service.base.impl.AppInvoiceManagerImpl;
import com.jianfei.core.service.base.impl.VipCardManagerImpl;
import com.jianfei.core.service.thirdpart.ThirdPayManager;
import com.jianfei.core.service.thirdpart.impl.MsgInfoManagerImpl;
import com.jianfei.core.service.user.impl.VipUserManagerImpl;

import com.tencent.protocol.native_protocol.NativePayReqData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.mapper.AppCardBackMapper;
import com.jianfei.core.mapper.AppConsumeMapper;
import com.jianfei.core.mapper.AppOrderCardMapper;
import com.jianfei.core.mapper.AppOrdersMapper;
import com.jianfei.core.service.order.OrderManager;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 16:31
 */
@Service
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
	private VipCardManagerImpl vipCardManager;
	@Autowired
	private AppInvoiceManagerImpl invoiceManager;
	@Autowired
	private MsgInfoManagerImpl msgInfoManager;
	@Autowired
	private ConsumeManagerImpl consumeManager;
	@Autowired
	private ThirdPayManager aliPayManager;
	@Autowired
	private ThirdPayManager wechatiPayManager;

    /**
     * 添加订单信息
     *
     * @param addInfoDto
     * @return
     */
    @Override
    public BaseMsgInfo addOrderAndUserInfo(OrderAddInfoDto addInfoDto) {

		try {
			//1、校验用户和手机验证码
			boolean flag=msgInfoManager.validateSendCode(addInfoDto.getPhone(), MsgType.REGISTER,addInfoDto.getCode());
			if (!flag){
				return new BaseMsgInfo().setCode(-1).setMsg("手机验证码验证失败");
			}
			//2、添加用户信息
			AppCustomer customer= new AppCustomer();
			BeanUtils.copyProperties(customer,addInfoDto);
			vipUserManager.addUser(customer);
			//3、根据查询VIP号查询卡片信息
			AppVipcard vipCard=vipCardManager.getVipCardByNo(addInfoDto.getVipCardNo());
			if (vipCard == null) {
				return new BaseMsgInfo().setCode(-1).setMsg("VIP卡号错误");
			}
			vipCard.setCustomerId(customer.getCustomerId());
			vipCardManager.updateVipCard(vipCard);

			//4、添加订单信息
			AppOrders orders=new AppOrders();
			BeanUtils.copyProperties(orders,addInfoDto);
			orders.setSaleNo(addInfoDto.getUno());
			orders.setCustomerId(customer.getCustomerId());
			orders.setPayMoney(vipCard.getInitMoney());
			orders.setOrderId(IdGen.uuid());
			orders.setOrderTime(new Date());
			orders.setRemark1(vipCard.getCardName());
			orders.setOrderState(VipOrderState.NOT_PAY.getName());
			orders.setDtflag(StateType.EXIST.getName());
			orders.setInvoiceFlag(0);
			appOrdersMapper.insertSelective(orders);

            //5、订单卡表
			AppOrderCard appOrderCard=new AppOrderCard();
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
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();

		}
		return new BaseMsgInfo().setCode(-1).setMsg("开卡失败");
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
		AppOrders orders=appOrdersMapper.selectByPrimaryKey(appInvoice.getOrderId());
		if (orders == null || StringUtils.isBlank(orders.getOrderId())) {
			return BaseMsgInfo.msgFail("订单不存在");
		}
		orders.setInvoiceFlag(InvoiceState.NEED_INVOICE.getName());
		int flag = appOrdersMapper.updateByPrimaryKeySelective(orders);
		if (flag < 0)
			return BaseMsgInfo.msgFail("订单邮寄信息状态更新失败");
		invoiceManager.insert(appInvoice);
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
		
		int orderState =1;
		if(operationType == 0){//退款申请 
			orderState = 2;
		}else if(operationType == 1){//审核通过
			orderState = 2;
		}else if(operationType == 2){//审核不通过
			orderState = 1;
		}else if(operationType == 3){//录入退卡人账号
			orderState = 3;
		}else if(operationType == 4){//最终退款
			orderState = 4;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderState", orderState);
		params.put("orderId", orderId);
		
		return appOrdersMapper.updateOrderState(params);
		
	}


	/**
	 * 根据订单号返回用户vip卡可退金额
	 */
	@Override
	public float remainMoney(String orderId) {
		float remainMoney = 0;
		//1、app_order_card表中返回卡号，用户初始金额
		AppOrderCard appOrderCard = appOrderCardMapper.getAppOrderCard(orderId);
		if(appOrderCard!= null){
			//2、app_consume表中返回vip消费次数
			int count = appConsumeMapper.getCountCosume(appOrderCard.getCardNo());
			//3、计算用户vip卡剩余金额
			remainMoney = (float) (appOrderCard.getInitMoney()-count*200*0.8);
			
		}
		System.out.println("float remainMoney="+remainMoney);
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
	 *  通过订单号在流水表中查询用户的退款账户
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
		AppOrders appOrders=getOrderInfo(orderId);
		PreCreateResult preCreateResult=null;
		 if(PayType.WXPAY.equals(payType)){
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
			 NativePayReqData nativePayReqData=new NativePayReqData("",appOrders.getRemark1(),"",orderId,(int)(appOrders.getPayMoney()*100),
					 "","192.168.199.200","","","", "","http://121.42.199.169/pay/wechat_notify","NATIVE",appOrders.getAirportId(),"","");
			 preCreateResult=wechatiPayManager.tradePrecreate(nativePayReqData);
		 }else if (PayType.ALIPAY.equals(payType)){
			 GoodsDetail goodsDetail = GoodsDetail.newInstance(appOrders.getOrderId(), appOrders.getRemark1(), 1, 1);
			 List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
			 goodsDetailList.add(goodsDetail);
			 AlipayTradePrecreateContentBuilder builder = new
					 AlipayTradePrecreateContentBuilder() .setSubject(appOrders.getRemark1())
					 .setTotalAmount(appOrders.getPayMoney().toString()).setOutTradeNo(appOrders.getOrderId())
					 .setGoodsDetailList(goodsDetailList).setStoreId("test");

			 preCreateResult= aliPayManager.tradePrecreate(builder);
		 }
         if ("0".equals(preCreateResult.getCode())){
			 return BaseMsgInfo.success(preCreateResult.getQrUrl());
		 }else {
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
		Map<String,Object> params = new HashMap<String,Object>();
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
		PayQueryResult result=null;
		if(PayType.WXPAY.equals(payType)){
			result=wechatiPayManager.tradeQuery(orderId);
		}
		else if (PayType.ALIPAY.equals(payType)){
			result=aliPayManager.tradeQuery(orderId);
		}
		if ("0".equals(result.getCode())){
			return BaseMsgInfo.success(true);
		}else {
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
	public BaseMsgInfo getVipCardUseAndOrder(String phone, String code) {
		//1、校验用户和手机验证码
		boolean flag = msgInfoManager.validateSendCode(phone, MsgType.BACK_CARD, code);
		if (!flag)
			return new BaseMsgInfo().setCode(-1).setMsg("验证码校验失败");
		//2、查询用户信息和订单信息
		VipCardUseDetailInfo vipCardUseDetailInfo = appOrderCardMapper.getVipCardUseDetailInfo(phone);
		if (vipCardUseDetailInfo == null || StringUtils.isBlank(vipCardUseDetailInfo.getVipCardNo())) {
			return BaseMsgInfo.success(vipCardUseDetailInfo);
		}

		//3、查询VIP使用信息
		List<AppConsume> list = consumeManager.getConsumesByVipNo(vipCardUseDetailInfo.getVipCardNo());
		if (list == null)
			return BaseMsgInfo.success(vipCardUseDetailInfo);
		float usedMoney = 0;
		for (AppConsume appConsume : list) {
			usedMoney = usedMoney + appConsume.getConsumeMoney();
		}
		vipCardUseDetailInfo.setUsedMoney(usedMoney);
		vipCardUseDetailInfo.setCardUseList(list);
		//TODO 可配置优惠信息多少
		vipCardUseDetailInfo.setSaleRate("80%");
		return BaseMsgInfo.success(vipCardUseDetailInfo);
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
	 *
	 * @param orderId
	 * @param payType
	 * @return
	 */
	@Override
	public BaseMsgInfo updatePayState(String orderId, PayType payType) {
		AppOrders orders = appOrdersMapper.selectByPrimaryKey(orderId);
		if (orders == null || StringUtils.isBlank(orders.getOrderId())) {
			return BaseMsgInfo.msgFail("订单不存在");
		}
		orders.setPayType(payType.getName());
		orders.setOrderState(VipOrderState.ALREADY_PAY.getName());
		int num = appOrdersMapper.updateByPrimaryKeySelective(orders);
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
		AppOrders orders = appOrdersMapper.selectByPrimaryKey(appCardBack.getOrderId());
		if (orders == null || StringUtils.isBlank(orders.getOrderId())) {
			return BaseMsgInfo.msgFail("订单不存在");
		}
		//TODO 2、重新计算可退余额 校验是否正确
		//TODO 3、插入数据库

		return null;
	}
}
