package com.jianfei.core.service.order.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.*;
import com.jianfei.core.common.enu.*;
import com.jianfei.core.common.utils.*;
import com.jianfei.core.common.utils.bean.BeanUtil;
import com.jianfei.core.common.utils.export.ExcelUtils;
import com.jianfei.core.dto.*;
import com.jianfei.core.mapper.AppCardBackMapper;
import com.jianfei.core.mapper.AppConsumeMapper;
import com.jianfei.core.mapper.AppOrderCardMapper;
import com.jianfei.core.mapper.AppOrdersMapper;
import com.jianfei.core.service.base.AppInvoiceManager;
import com.jianfei.core.service.base.ValidateCodeManager;
import com.jianfei.core.service.base.VipCardManager;
import com.jianfei.core.service.order.CardBackManager;
import com.jianfei.core.service.order.OrderManager;
import com.jianfei.core.service.thirdpart.AirportEasyManager;
import com.jianfei.core.service.thirdpart.MsgInfoManager;
import com.jianfei.core.service.thirdpart.QueueManager;
import com.jianfei.core.service.user.SaleUserManager;
import com.jianfei.core.service.user.VipUserManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	private static Log log = LogFactory.getLog(OrderManagerImpl.class);
	@Autowired
	private AppOrdersMapper appOrdersMapper;
	@Autowired
	private AppOrderCardMapper appOrderCardMapper;
	@Autowired
	private AppConsumeMapper appConsumeMapper;
	@Autowired
	private AppCardBackMapper appCardBackMapper;
	@Autowired
	private VipCardManager vipCardManager;
	@Autowired
	private AppInvoiceManager invoiceManager;
	@Autowired
	private ValidateCodeManager validateCodeManager;
	@Autowired
	private ConsumeManagerImpl consumeManager;

	@Autowired
	private QueueManager queueManager;
	@Autowired
	private VipUserManager vipUserManager;
	@Autowired
	private CardBackManager cardBackManager;
	@Autowired
	private SaleUserManager saleUserManager;

	@Autowired
	private AirportEasyManager airportEasyManager;

	@Autowired
	private MsgInfoManager msgInfoManager;

	/**
	 * 添加订单信息
	 *
	 * @param addInfoDto
	 * @return
	 * @author dsliu
	 */
	@Override
	public BaseMsgInfo addOrderAndUserInfo(OrderAddInfoDto addInfoDto)
			throws InvocationTargetException, IllegalAccessException {
		// 重复提交问题
		if (StringUtils.isNotBlank(addInfoDto.getOrderId())) {
			AppOrders appOrders = appOrdersMapper.selectByPrimaryKey(addInfoDto.getOrderId());
			addInfoDto.setMoney(appOrders.getPayMoney());
			return BaseMsgInfo.success(addInfoDto);
		}

		boolean flag = true;
		// 2、校验空港易行的姓名接口及VIP卡
		flag = airportEasyManager.vipuserStatus(addInfoDto.getCustomerName(), addInfoDto.getPhone());
		log.info("验证空港易行用户名:" + addInfoDto.getCustomerName() + ",手机号:" + addInfoDto.getPhone() + "验证结果:" + flag);
		if (!flag) {
			return BaseMsgInfo.msgFail("请输入正确的用户姓名");
		}
		flag = airportEasyManager.cardBindStatus(addInfoDto.getVipCardNo());
		log.info("验证空港易行VIP卡号是否绑定:" + addInfoDto.getVipCardNo() + "验证结果:" + flag);
		if (!flag) {
			return BaseMsgInfo.msgFail("卡号无效请更换");
		}

		// 3、根据查询VIP号查询卡片信息
		AppVipcard vipCard = vipCardManager.getVipCardByNo(addInfoDto.getVipCardNo());
		if (vipCard == null) {
			return new BaseMsgInfo().setCode(-1).setMsg("VIP卡号错误");
		} else if (VipCardState.NOT_ACTIVE.getName() != vipCard.getCardState()) {
			return BaseMsgInfo.msgFail("此VIP卡已使用或其他状态");
		}
		User user = saleUserManager.getSaleUser(addInfoDto.getUno());
		if (user == null || StringUtils.isBlank(user.getName())) {
			return BaseMsgInfo.msgFail("人员工号不存在");
		}
		// 1、校验用户和手机验证码
		flag = validateCodeManager.validateSendCode(addInfoDto.getPhone(), MsgType.REGISTER, addInfoDto.getCode());
		if (!flag) {
			return new BaseMsgInfo().setCode(-1).setMsg("手机验证码验证失败");
		}
		// 3、添加或修改用户信息
		AppCustomer customer = new AppCustomer();
		BeanUtils.copyProperties(customer, addInfoDto);
		vipUserManager.addORUpdateUser(customer);

		// 4、添加订单信息
		AppOrders orders = new AppOrders();
		BeanUtils.copyProperties(orders, addInfoDto);
		orders.setSaleNo(addInfoDto.getUno());
		orders.setSaleName(user.getName());
		orders.setCustomerId(customer.getCustomerId());
		orders.setPayMoney(vipCard.getInitMoney());
		orders.setOrderId(IdGen.uuid());
		orders.setOrderTime(new Date());
		orders.setRemark1("VIP卡");
		orders.setOrderState(VipOrderState.NOT_PAY.getName());
		orders.setDtflag(StateType.EXIST.getName());
		orders.setInvoiceFlag(0);
		appOrdersMapper.insertSelective(orders);

		// 5、卡表及订单卡表
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
		// **日志记录（正常）
		SmartLog.info(ModuleType.ORDER_MODULE.getName(), customer.getPhone(),
				"【订单模块-订单添加】，订单编号：" + orders.getOrderId() + "，用户手机号：" + customer.getPhone() + "，用户姓名："
						+ customer.getCustomerName() + "，操作员编号：" + user.getId() + "，操作员姓名：" + user.getName()
						+ "，操作内容：【订单状态:未支付】，操作结果：【成功】");
		return BaseMsgInfo.success(addInfoDto);

	}
	
	/**
	 * 自定义方法，用于后期补录订单信息，不走后台
	 * @param addInfoDto
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws ParseException 
	 */
	public BaseMsgInfo addOrderAndUserInfoByOutLine(OrderAddInfoDto addInfoDto)
			throws InvocationTargetException, IllegalAccessException, ParseException {
		// 根据查询VIP号查询卡片信息
		AppVipcard vipCard = vipCardManager.getVipCardByNo(addInfoDto.getVipCardNo());
		if (vipCard == null) {
			return new BaseMsgInfo().setCode(-1).setMsg("VIP卡号错误");
		} 
		// 销售员信息
		User user = saleUserManager.getSaleUser(addInfoDto.getUno());
		if (user == null || StringUtils.isBlank(user.getName())) {
			return BaseMsgInfo.msgFail("人员工号不存在");
		}
		
		// 1、添加或修改用户信息
		AppCustomer customer = new AppCustomer();
		
		BeanUtils.copyProperties(customer, addInfoDto);
		customer.setSex(1);
		vipUserManager.addORUpdateUser(customer);

		// 2、添加订单信息
		AppOrders orders = new AppOrders();
		BeanUtils.copyProperties(orders, addInfoDto);
		orders.setSaleNo(addInfoDto.getUno());
		orders.setSaleName(user.getName());
		orders.setCustomerId(customer.getCustomerId());
		orders.setPayMoney(vipCard.getInitMoney());
		orders.setOrderId(IdGen.uuid());
		orders.setOrderTime(new Date());
		orders.setRemark1("VIP卡");
		orders.setOrderState(VipOrderState.ALREADY_PAY.getName());//订单状态-已支付
		orders.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-16 10:00:00"));
		orders.setPayTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-16 10:01:00"));//支付时间
		orders.setPayType(PayType.CASHPAY.getName());//支付方式-现金
		orders.setDtflag(StateType.EXIST.getName());
		orders.setInvoiceFlag(0);
		appOrdersMapper.insertSelective(orders);

		// 3、卡表及订单卡表
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
		
		//4 将卡的状态改为绑定成功未激活1
		AppVipcard vipcard = new AppVipcard();
		vipcard.setCardState(VipCardState.ACTIVE.getName());
		vipCardManager.updateByPrimaryKeySelective(vipcard);
		
		
		
		addInfoDto.setOrderId(orders.getOrderId());
		addInfoDto.setMoney(vipCard.getInitMoney());
		// **日志记录（正常）
		SmartLog.info(ModuleType.ORDER_MODULE.getName(), customer.getPhone(),
				"【订单模块-订单添加】，订单编号：" + orders.getOrderId() + "，用户手机号：" + customer.getPhone() + "，用户姓名："
						+ customer.getCustomerName() + "，操作员编号：" + user.getId() + "，操作员姓名：" + user.getName()
						+ "，操作内容：【订单状态:未支付】，操作结果：【成功】");
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

	public PageInfo<OrderShowInfoDto> simplePage(int pageNo, int pageSize, Map<String, Object> params) {
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
	public boolean updateOrderInfo(AppOrders addInfoDto) {
		return appOrdersMapper.updateByPrimaryKeySelective(addInfoDto) < 0 ? false : true;
	}

	/**
	 * 订单发票信息
	 *
	 * @param appInvoice
	 *            发票信息
	 * @return
	 */
	@Override
	public BaseMsgInfo addOrderMailInfo(AppInvoice appInvoice) {
		AppOrders orders = appOrdersMapper.getOrderDetailByOrderId(appInvoice.getOrderId());
		if (orders == null || StringUtils.isBlank(orders.getOrderId())) {
			return BaseMsgInfo.msgFail("订单不存在");
		}
		AppInvoice invoice = invoiceManager.selInvoiceInfoByOrderId(appInvoice.getOrderId());
		if (invoice != null && StringUtils.isNotBlank(invoice.getInvoiceId())) {
			return BaseMsgInfo.msgFail("发票信息已经添加过");
		}
		invoiceManager.insert(appInvoice);
		orders.setInvoiceFlag(InvoiceState.NEED_INVOICE.getName());
		AppCustomer customer = orders.getCustomer();
		customer.setDtflag(null);
		customer.setAddress(
				appInvoice.getProvince() + appInvoice.getCity() + appInvoice.getCountry() + appInvoice.getAddress());
		vipUserManager.updateUser(customer);
		int flag = appOrdersMapper.updateByPrimaryKeySelective(orders);
		SmartLog.info(ModuleType.ORDER_MODULE.getName(), customer.getPhone(),
				"【订单模块-订单发票信息添加】，订单编号：" + orders.getOrderId() + "，用户手机号：" + customer.getPhone() + "，用户姓名："
						+ customer.getCustomerName() + "，操作员编号：" + orders.getSaleNo() + "，操作员姓名：" + orders.getSaleName()
						+ "，操作内容：【订单发票信息添加】，操作结果：【成功】");
		if (flag < 0)
			return BaseMsgInfo.msgFail("订单邮寄信息状态更新失败");
		return BaseMsgInfo.success(true);
	}

	/**
	 * 更新订单状态 0 未支付 1 已支付 2 正在审核 3 审核通过 4 退款成功
	 */
	@Override
	public int updateOrderStateByOrderId(String orderId, int operationType) {
		// TODO Auto-generated method stub

		int orderState = 1;
		if (operationType == 0) {// 退款申请
			orderState = 2;
		} else if (operationType == 1) {// 审核通过
			orderState = 2;
		} else if (operationType == 2) {// 审核不通过
			orderState = 1;
		} else if (operationType == 3) {// 录入退卡人账号
			orderState = 3;
		} else if (operationType == 4) {// 最终退款
			orderState = 4;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderState", orderState);
		params.put("orderId", orderId);
		// 审核通过
		if (operationType == 3) {
			params.put("applyType", ApplyBackCardMethod.CUSTOMER_SERVICE_APPLY.getName());
		}

		return appOrdersMapper.updateOrderState(params);

	}

	/**
	 * 根据订单号返回用户vip卡可退金额
	 */
	@Override
	public double remainMoney(String orderId) {
		double remainMoney = 0.00;
		int count = 0;
		// 1、app_order_card表中返回卡号，用户初始金额
		AppOrderCard appOrderCard = appOrderCardMapper.getAppOrderCard(orderId);
		if (appOrderCard != null) {
			// >>>>>>>>>>2、app_consume表中返回vip消费次数-->改为从第三方接口获取
			// int count =
			// appConsumeMapper.getCountCosume(appOrderCard.getCardNo());
			AirportEasyUseInfo airportEasyUseInfo = airportEasyManager.readDisCodeData(appOrderCard.getCardNo());
			if (airportEasyUseInfo != null) {
				List<AppConsume> list = airportEasyUseInfo.getConsumeList();
				if (list != null && list.size() > 0) {
					count = list.size();
				}
			}
			// 3、计算用户vip卡剩余金额
			remainMoney = (double) (appOrderCard.getInitMoney() - count * 200);
			if (remainMoney < 0) {
				remainMoney = 0.00;
			}

		}
		return remainMoney;
	}

	/**
	 * 根据订单号计算对应卡号的服务费
	 */
	@Override
	public double calculateServiceMoney(String orderId) {
		double serviceMoney = 0.00;
		int count = 0;
		AppOrderCard appOrderCard = appOrderCardMapper.getAppOrderCard(orderId);
		if (appOrderCard != null) {
			AirportEasyUseInfo airportEasyUseInfo = airportEasyManager.readDisCodeData(appOrderCard.getCardNo());
			if (airportEasyUseInfo != null) {
				List<AppConsume> list = airportEasyUseInfo.getConsumeList();
				if (list != null && list.size() > 0) {
					count = list.size();
				}
			}
			// 计算对应卡号的服务费
			serviceMoney = count * 150.00;
		}
		return serviceMoney;
	}

	/**
	 * 记录退卡流水号
	 */
	@Override
	public int insertBackCardInfo(AppCardBack appCardBack) {
		// TODO Auto-generated method stub
		int flag = 0;
		try {
			appCardBackMapper.insertBackCard(appCardBack);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			flag = 1;
		}

		return flag;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.order.OrderManager#backCardPage(int, int,
	 * java.util.Map)
	 */
	@Override
	public PageInfo<OrderShowInfoDto> backCardPage(int pageNo, int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<OrderShowInfoDto> list = appOrdersMapper.page(params);
		PageInfo<OrderShowInfoDto> pageInfo = new PageInfo(list);
		return pageInfo;
	}

	/**
	 * 根据订单ID获取订单金额
	 *
	 * @param orderId
	 * @return
	 */
	@Override
	public AppOrders getOrderDetailByOrderId(String orderId) {
		AppOrders orders = appOrdersMapper.getOrderDetailByOrderId(orderId);
		/*
		 * if (!orders.getVipCards().isEmpty()) { List<AppVipcard> list = new
		 * ArrayList<>(); AppVipcard vipcard = orders.getVipCards().get(0);
		 * AirportEasyUseInfo airportEasyUseInfo =
		 * airportEasyManager.readDisCodeData(vipcard.getCardNo()); if
		 * (airportEasyUseInfo != null &&
		 * !airportEasyUseInfo.getConsumeList().isEmpty()) {
		 * vipcard.setActiveTime(airportEasyUseInfo.getConsumeList().get(0).
		 * getConsumeTime()); } list.add(vipcard); orders.setVipCards(list); }
		 */
		return orders;
	}

	/**
	 * 根据手机号查询用户VIP卡使用信息和订单详细信息
	 *
	 * @param phone
	 *            手机号
	 * @param code
	 * @return
	 */
	@Override
	public BaseMsgInfo getVipCardUseAndOrder(String phone, String code, String vipCardNo) {
		// 1、校验用户和手机验证码
		/*
		 * boolean flag = validateCodeManager.validateSendCode(phone,
		 * MsgType.SELECT, code); if (!flag) return new
		 * BaseMsgInfo().setCode(-1).setMsg("验证码校验失败");
		 */
		// 2、查询用户信息和订单信息
		if (StringUtils.isBlank(vipCardNo)) {
			vipCardNo = null;
		}
		List<VipCardUseDetailInfo> vipCardUseDetailInfoList = appOrderCardMapper.getVipCardUseDetailInfo(phone,
				vipCardNo);
		VipCardUseDetailInfo vipCardUseDetailInfo = vipCardUseDetailInfoList == null
				|| vipCardUseDetailInfoList.isEmpty() ? new VipCardUseDetailInfo() : vipCardUseDetailInfoList.get(0);
		if (vipCardUseDetailInfo == null || StringUtils.isBlank(vipCardUseDetailInfo.getVipCardNo())) {
			return BaseMsgInfo.msgFail("暂未查询到此VIP卡相关信息");
		}
		vipCardUseDetailInfo.setReturnDate(airportEasyManager.checkone(vipCardUseDetailInfo.getVipCardNo()).getDatas());
		getVipCardUseInfo(vipCardUseDetailInfo);
		return BaseMsgInfo.success(vipCardUseDetailInfo);
	}

	/**
	 * 计算退款相关信息
	 *
	 * @param vipCardUseDetailInfo
	 */
	private void getVipCardUseInfo(VipCardUseDetailInfo vipCardUseDetailInfo) {
		// 3、查询VIP使用信息
		AirportEasyUseInfo easyUseInfo = airportEasyManager.readDisCodeData(vipCardUseDetailInfo.getVipCardNo());
		List<AppConsume> list = new ArrayList<>();
		if (easyUseInfo != null && !easyUseInfo.getConsumeList().isEmpty()) {
			list = easyUseInfo.getConsumeList();
			vipCardUseDetailInfo.setActiveTime(list.get(0).getConsumeTime());
		}
		int num = list.size();
		float usedMoney = num * 200;
		float realMoney = num * 200;
		float remainMoney = vipCardUseDetailInfo.getOrderMoney() - realMoney;
		if (remainMoney < 0)
			remainMoney = 0;
		vipCardUseDetailInfo.setSafeMoney(0);
		vipCardUseDetailInfo.setReturnMoney(remainMoney);
		vipCardUseDetailInfo.setRealMoney(realMoney);
		vipCardUseDetailInfo.setReturnInfo("由于开卡当日亿出行已为您投保，保费100元，若退卡，需扣除保费。" + "您已免费享受了价值" + usedMoney
				+ "元的VIP室服务，若退卡需扣除该费用，亿出行仅收取150元/次的服务费，总计" + realMoney + "元。" + "现在申请退款，可以退给用户" + remainMoney + " 元");
		vipCardUseDetailInfo.setUsedMoney(usedMoney);
		vipCardUseDetailInfo.setCardUseList(list);
		vipCardUseDetailInfo.setSaleRate("80%");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.order.OrderManager#selectOrder(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> selectOrder(Map<String, Object> map) {
		return appOrdersMapper.selectOrder(map);
	}

	/**
	 * 更新用户付款状态 并发送激活卡消息
	 *
	 * @return
	 */
	@Override
	public BaseMsgInfo updatePayState(AppOrders appOrders) {
		if (StringUtils.isBlank(appOrders.getOrderId())) {
			return BaseMsgInfo.msgFail("订单号不存在");
		}
		// 1、查询订单是否支付
		AppOrders order = appOrdersMapper.getOrderDetailByOrderId(appOrders.getOrderId());
		if (VipOrderState.ALREADY_PAY.getName() == order.getOrderState()) {
			return BaseMsgInfo.success(true);
		}
		if (!(VipOrderState.NOT_PAY.getName() == order.getOrderState())) {
			return BaseMsgInfo.msgFail("订单状态已更改");
		}
		// 2、选择性更新订单信息
		appOrders.setOrderState(VipOrderState.ALREADY_PAY.getName());
		int num = appOrdersMapper.updateByPrimaryKeySelective(appOrders);
		// **日志记录（正常）
		SmartLog.info(ModuleType.ORDER_MODULE.getName(), order.getCustomer().getPhone(),
				"【订单模块-订单支付】，订单编号：" + order.getOrderId() + "，用户手机号：" + order.getCustomer().getPhone() + "，用户姓名："
						+ order.getCustomer().getCustomerName() + "，操作员编号：" + appOrders.getPayUserId() + "，操作员姓名："
						+ appOrders.getPayUserId() + "，操作内容：【订单状态:未支付-->已支付】，操作结果：【成功】");
		// 更新VIP用户激活
		vipUserManager.updateUserSate(order.getCustomer().getPhone(), VipUserSate.ACTIVE);
		// 更新VIP卡状态为待激活
		AppVipcard vipcard = new AppVipcard();
		vipcard.setCardNo(order.getVipCards().get(0).getCardNo());
		vipcard.setCardState(VipCardState.TO_ACTIVATE.getName());
		vipcard.setActiveTime(new Date());
		vipCardManager.updateVipCard(vipcard);

		SmartLog.info(ModuleType.VIPCARD_MODULE.getName(), vipcard.getCardNo(),
				"【订单模块-支付成功-更改卡状态】，订单编号：" + order.getOrderId() + "，用户手机号：" + order.getCustomer().getPhone() + "，用户姓名："
						+ order.getCustomer().getCustomerName() + "，操作员编号：" + appOrders.getPayUserId() + "，操作员姓名："
						+ appOrders.getPayUserId() + "，操作内容：【将卡状态变为-待绑定】，操作结果：【成功】");
		// 构建消息体 并放入消息队列
		ServiceMsgBuilder msgBuilder = new ServiceMsgBuilder().setUserPhone(order.getCustomer().getPhone())
				.setMsgType(MsgType.ACTIVE_CARD.getName()).setVipCardNo(order.getVipCards().get(0).getCardNo())
				.setUserName(order.getCustomer().getCustomerName());
		// 放入消息队列
		SmartLog.info(ModuleType.MESSAGE_MODULE.getName(), msgBuilder.getUserPhone(),
				"给用户手机号:" + msgBuilder.getUserPhone() + "发送购卡成功短信");
		queueManager.sendMessage(msgBuilder);
		if (num > 0) {
			return BaseMsgInfo.success(true);
		} else {
			SmartLog.error(ModuleType.ORDER_MODULE.getName(), order.getCustomer().getPhone(),
					"【订单模块-订单支付】，订单编号：" + order.getOrderId() + "，用户手机号：" + order.getCustomer().getPhone() + "，用户姓名："
							+ order.getCustomer().getCustomerName() + "，操作员编号：" + appOrders.getPayUserId() + "，操作员姓名："
							+ appOrders.getPayUserId() + "，操作内容：【订单状态:未支付-->已支付】，操作结果：【失败】");
			return BaseMsgInfo.msgFail("确认付款失败");
		}
	}

	/**
	 * APP端添加退卡信息
	 *
	 * @param appCardBack
	 *            退卡信息
	 * @return
	 */
	@Override
	public synchronized BaseMsgInfo addBackCardInfo(AppCardBack appCardBack) {

		// 1、根据订单号查询订单信息和用户信息
		AppOrders orders = getOrderDetailByOrderId(appCardBack.getOrderId());
		if (orders == null || StringUtils.isBlank(orders.getOrderId())) {
			return BaseMsgInfo.msgFail("订单不存在");
		}

		// 判断订单状态是否合法 只能是已付款的订单才能退款
		if (VipOrderState.NOT_PAY.getName() == orders.getOrderState()) {
			return BaseMsgInfo.msgFail("订单未付款");
		} else if (!(VipOrderState.ALREADY_PAY.getName() == orders.getOrderState())) {
			return BaseMsgInfo.msgFail("订单已经申请退款");
		}

		String vipCardNo;
		if (StringUtils.isBlank(appCardBack.getCardNo())) {
			vipCardNo = orders.getVipCards().get(0).getCardNo();
		} else {
			vipCardNo = appCardBack.getCardNo();
		}
		// 2、重新计算可退余额 校验是否正确
		VipCardUseDetailInfo useDetailInfo = new VipCardUseDetailInfo();
		useDetailInfo.setOrderMoney(orders.getPayMoney());
		useDetailInfo.setVipCardNo(vipCardNo);
		getVipCardUseInfo(useDetailInfo);
		appCardBack.setMoney(useDetailInfo.getReturnMoney());
		appCardBack.setServiceMoney(useDetailInfo.getRealMoney());
		appCardBack.setSafeMoney(0);
		User user = saleUserManager.getSaleUser(appCardBack.getCreaterId());
		appCardBack.setCreateName(user.getName());
		appCardBack.setCustomerName(orders.getCustomer().getCustomerName());

		// 3、封装消息体 及更改订单状态 和 卡状态
		ServiceMsgBuilder msgBuilder = new ServiceMsgBuilder().setUserPhone(orders.getCustomer().getPhone())
				.setVipCardNo(vipCardNo).setUserName(orders.getCustomer().getCustomerName());
		JSONObject object = new JSONObject();
		object.put("returnMoney", useDetailInfo.getRealMoney());
		msgBuilder.setMsgBody(object.toJSONString());

		if (StringUtils.isNotBlank(appCardBack.getAgreementUrl())) {

			// 紧急退卡 更改订单状态为已退款 \申请方式为紧急
			orders.setOrderState(VipOrderState.ALREADY_REFUND.getName());
			appCardBack.setFinishTime(new Date());
			orders.setApplyType(ApplyBackCardMethod.SCENE_EMERGENT_APPLY.getName());

			// 紧急通道退卡 更改卡状态为已退卡
			AppVipcard vipcard = new AppVipcard();
			vipcard.setCardNo(vipCardNo);
			vipcard.setCardState(VipCardState.BACK_CARD.getName());
			vipCardManager.updateVipCard(vipcard);
			SmartLog.info(ModuleType.VIPCARD_MODULE.getName(), vipcard.getCardNo(),
					"【订单模块-现场紧急退卡-更改卡状态】，订单编号：" + orders.getOrderId() + "，用户手机号：" + orders.getCustomer().getPhone()
							+ "，用户姓名：" + orders.getCustomer().getCustomerName() + "，操作员编号：" + user.getCode() + "，操作员姓名："
							+ user.getName() + "，操作内容：【将卡状态变为-已退卡】，操作结果：【成功】");

			// 消息体为紧急退卡
			msgBuilder.setMsgType(MsgType.RIGHT_BACK_CARD.getName());

		} else {
			// 其他APP端申请退卡 更改订单状态为退卡审核已通过 \申请方式为APP端正常申请
			orders.setOrderState(VipOrderState.AUDIT_PASS.getName());
			orders.setApplyType(ApplyBackCardMethod.SCENE_APPLY.getName());

			// 根据付款方式 封装给用户退卡短信 消息体 现金和其他付款方式不同
			if (PayType.CASHPAY.getName() == orders.getPayType()) {
				msgBuilder.setMsgType(MsgType.BACK_CARD_APPLY.getName());
			} else {
				msgBuilder.setMsgType(MsgType.QT_BACK_CARD_APPLY.getName());
			}
		}
		appOrdersMapper.updateByPrimaryKeySelective(orders);
		// 记录日志
		SmartLog.info(ModuleType.ORDER_MODULE.getName(), orders.getCustomer().getPhone(),
				"【订单模块-订单现场申请退卡或紧急退卡】，订单编号：" + orders.getOrderId() + "，用户手机号：" + orders.getCustomer().getPhone()
						+ "，用户姓名：" + orders.getCustomer().getCustomerName() + "，操作员编号：" + user.getCode() + "，操作员姓名："
						+ user.getName() + "，操作内容：【订单状态:已支付-->退款审核通过 或 已退款】，操作结果：【成功】");

		boolean temp = cardBackManager.addOrUpdateCardBackInfo(appCardBack);
		// 更改用户状态为禁用状态
		vipUserManager.updateUserSate(orders.getCustomer().getPhone(), VipUserSate.NOT_ACTIVE);
		queueManager.sendMessage(msgBuilder);
		SmartLog.info(ModuleType.MESSAGE_MODULE.getName(), msgBuilder.getUserPhone(),
				"给用户手机号：" + msgBuilder.getUserPhone() + "发送退卡申请成功或紧急退卡相关短信");
		return temp ? BaseMsgInfo.success(true) : BaseMsgInfo.fail("退卡信息添加失败");
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
	public PageInfo<OrderShowInfoDto> invoicePageList(int pageNo, int pageSize, Map<String, Object> map) {
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
	 * 发送短信（录入退款信息、完成退款信息）
	 * 
	 * @param msgBuilder
	 */
	public void sendMessageOfOrder(ServiceMsgBuilder msgBuilder) {
		queueManager.sendMessage(msgBuilder);
	}

	/**
	 * 取消VIP卡退卡记录
	 *
	 * @param phone
	 *            手机号
	 * @param code
	 *            验证码
	 * @param vipCardNo
	 * @return
	 */
	@Override
	public BaseMsgInfo removeBackCard(String phone, String code, String vipCardNo, String orderId) {
		// 1、校验用户和手机验证码
		boolean flag = true; // validateCodeManager.validateSendCode(phone,
								// MsgType.SELECT, code);
		if (!flag)
			return new BaseMsgInfo().setCode(-1).setMsg("验证码校验失败");
		// 2、查询卡状态及卡对应的订单状态
		AppOrders orders = getOrderDetailByOrderId(orderId);

		// 3、如果订单状态时已退款 则提示用户退卡申请失败 已退卡
		if (orders == null || orders.getOrderState() == VipOrderState.ALREADY_REFUND.getName()) {
			return BaseMsgInfo.msgFail("订单不存在 或已退款");
		}
		if (!phone.equals(orders.getCustomer().getPhone())) {
			return BaseMsgInfo.msgFail("订单对应的下单用户和本手机号不一致");
		}
		// 4、判断订单状态是否在可取消退卡范围内
		if (!(orders.getOrderState() == VipOrderState.BEING_AUDITED.getName()
				|| orders.getOrderState() == VipOrderState.AUDIT_PASS.getName())) {
			return BaseMsgInfo.msgFail("订单无法取消退卡");
		}

		// 5、更改订单状态为已付款 逻辑删除退卡信息
		orders.setOrderState(VipOrderState.ALREADY_PAY.getName());
		appOrdersMapper.updateByPrimaryKeySelective(orders);
		SmartLog.info(ModuleType.ORDER_MODULE.getName(), orders.getCustomer().getPhone(),
				"【订单模块-取消退卡】，订单编号：" + orders.getOrderId() + "，用户手机号：" + orders.getCustomer().getPhone() + "，用户姓名："
						+ orders.getCustomer().getCustomerName() + "，操作员编号：" + "，操作员姓名："
						+ "，操作内容：【订单状态:退款已审核通过-->已支付】，操作结果：【成功】");
		cardBackManager.deleteCardBackInfo(orderId);

		// 用户状态为已激活
		vipUserManager.updateUserSate(phone, VipUserSate.ACTIVE);

		// 6、给用户发送取消退卡成功短信
		ServiceMsgBuilder msgBuilder = new ServiceMsgBuilder().setUserPhone(orders.getCustomer().getPhone())
				.setVipCardNo(vipCardNo).setUserName(orders.getCustomer().getCustomerName());
		msgBuilder.setMsgType(MsgType.REMOVE_BACK_CARD.getName());
		SmartLog.info(ModuleType.MESSAGE_MODULE.getName(), msgBuilder.getUserPhone(),
				"给用户手机号：" + msgBuilder.getUserPhone() + "发送取消退卡申请相关短信");
		queueManager.sendMessage(msgBuilder);
		return BaseMsgInfo.success(true);
	}

	/**
	 * 重新激活VIP卡
	 *
	 * @param phone
	 *            手机号
	 * @param vipCardNo
	 * @param orderId
	 *            订单ID
	 */
	@Override
	public BaseMsgInfo activeCard(String phone, String vipCardNo, String orderId) throws UnrecoverableKeyException,
			IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		// 查询订单详细信息 订单是否付款
		AppOrders orders = getOrderDetailByOrderId(orderId);
		// 如果订单状态时不是已付款 则提示
		if (orders == null || !(orders.getOrderState() == VipOrderState.ALREADY_PAY.getName())) {
			return BaseMsgInfo.msgFail("订单不存在或未付款");
		}
		if (!(vipCardNo.equals(orders.getVipCards().get(0).getCardNo()))) {
			return BaseMsgInfo.msgFail("订单号和VIP卡号不对应");
		}
		// 查询卡状态是否为激活失败
		AppVipcard vipcard = vipCardManager.getVipCardByNo(vipCardNo);
		if (vipcard == null) {
			return BaseMsgInfo.msgFail("VIP卡号不存在");
		}
		if (vipcard.getCardState() != VipCardState.ACTIVATE_FAIL.getName()) {
			return BaseMsgInfo.msgFail("本VIP卡不是激活失败状态");
		}
		// 调取易行接口激活
		if (airportEasyManager.activeVipCard(vipCardNo, orders.getCustomer().getPhone(),
				orders.getCustomer().getCustomerName())) {
			// 如果激活成功 发送激活成功短信
			vipcard.setCardState(VipCardState.ACTIVE.getName());
			vipCardManager.updateVipCard(vipcard);
			Map map = new HashMap();
			map.put("userPhone", orders.getCustomer().getPhone());
			map.put("userName", orders.getCustomer().getCustomerName());
			map.put("vipCardNo", vipCardNo);
			String msgBody = MsgAuxiliary.buildMsgBody(map, "005");
			if (!(msgInfoManager.sendMsgInfo(orders.getCustomer().getPhone(), msgBody))) {
				return BaseMsgInfo.msgFail("激活成功，给用户发送激活短信失败");
			}
			return BaseMsgInfo.success(true);
		} else {
			return BaseMsgInfo.msgFail("绑定失败");
		}
	}

	/**
	 * 将符合筛选条件的订单信息导出到excel表格中
	 * 
	 * @param map
	 * @param request
	 * @param response
	 */
	public void exportOrderInfo(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
		List<OrderShowInfoDto> list = appOrdersMapper.get(map);
		// 3 生成提示信息，
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			// 进行转码，使其支持中文文件名
			codedFileName = java.net.URLEncoder.encode("订单信息", "UTF-8");
			response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
			// 产生工作簿对象
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 产生工作表对象
			HSSFSheet sheet = workbook.createSheet();
			// 设置表头
			HSSFRow head = sheet.createRow((int) 0);
			HSSFCell idCell = head.createCell((int) 0);
			idCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			idCell.setCellValue("序号");

			HSSFCell orderIdCell = head.createCell((int) 1);
			orderIdCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			orderIdCell.setCellValue("订单编号");

			HSSFCell orderTimeCell = head.createCell((int) 2);
			orderTimeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			orderTimeCell.setCellValue("订单日期");

			HSSFCell airportStateCell = head.createCell((int) 3);
			airportStateCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			airportStateCell.setCellValue("所属场站");

			HSSFCell activeStateCell = head.createCell((int) 4);
			activeStateCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			activeStateCell.setCellValue("业务员");

			HSSFCell userNameCell = head.createCell((int) 5);
			userNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			userNameCell.setCellValue("用户姓名");

			HSSFCell userPhoneCell = head.createCell((int) 6);
			userPhoneCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			userPhoneCell.setCellValue("用户手机");

			HSSFCell invoiceStateCell = head.createCell((int) 7);
			invoiceStateCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			invoiceStateCell.setCellValue("发票状态");

			HSSFCell orderStateCell = head.createCell((int) 8);
			orderStateCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			orderStateCell.setCellValue("订单状态");

			// 返回表中所有的数据
			int index = 1;
			for (OrderShowInfoDto orderShowInfoDto : list) {
				// 创建一行
				HSSFRow row = sheet.createRow((int) index);

				// 序号
				HSSFCell id = row.createCell((int) 0);
				id.setCellType(HSSFCell.CELL_TYPE_STRING);
				id.setCellValue(index);

				// 订单号
				HSSFCell orderIdCells = row.createCell((int) 1);
				orderIdCells.setCellType(HSSFCell.CELL_TYPE_STRING);
				orderIdCells.setCellValue(orderShowInfoDto.getOrderId());

				// 订单日期
				HSSFCell orderTimesCell = row.createCell((int) 2);
				orderTimesCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				orderTimesCell.setCellValue(DateUtil.formatterDate(orderShowInfoDto.getOrderTime()));

				// 场站
				HSSFCell apNamesCell = row.createCell((int) 3);
				apNamesCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				apNamesCell.setCellValue(orderShowInfoDto.getAirportName());

				// 业务员
				HSSFCell agentNameCell = row.createCell((int) 4);
				agentNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				agentNameCell.setCellValue(orderShowInfoDto.getAgentName());

				// 用户名
				HSSFCell userNamesCell = row.createCell((int) 5);
				userNamesCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				userNamesCell.setCellValue(orderShowInfoDto.getCustomerName());

				// 用户手机号码
				HSSFCell userPhonesCell = row.createCell((int) 6);
				userPhonesCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				userPhonesCell.setCellValue(orderShowInfoDto.getCustomerPhone());

				// 发票状态
				HSSFCell invoiceStatesCell = row.createCell((int) 7);
				invoiceStatesCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				invoiceStatesCell
						.setCellValue(StateChangeUtils.returnInvoiceFlagName(orderShowInfoDto.getInvoiceFlag()));

				// 订单状态
				HSSFCell orderStatesCell = row.createCell((int) 8);
				orderStatesCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				orderStatesCell.setCellValue(StateChangeUtils.returnOrderStateName(orderShowInfoDto.getOrderState()));
				index++;

			}
			fOut = response.getOutputStream();
			workbook.write(fOut);
		} catch (UnsupportedEncodingException e1) {
		} catch (Exception e) {
		} finally {
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
			}

		}
	}

	/**
	 * 日志信息
	 */
	@Override
	public OrderDetailInfo selLogInfoByOrderId(String orderId) {
		// TODO Auto-generated method stub
		return appOrdersMapper.selLogInfoByOrderId(orderId);
	}

	/**
	 * 导出核销记录
	 * 
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@Override
	public void exportCounsumeInfoOfVipCard(HttpServletResponse response) throws InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		List<Map<Object, Object>> basicConsumeInfoGroup = appOrdersMapper.selBasicConsumeInfo();
		List<VipCardConsumeDto> vipCardConsumeDtoGroup = new ArrayList<>();

		for (Map<Object, Object> map : basicConsumeInfoGroup) {
			// 组装核销记录基本信息
			VipCardConsumeDto consumeInfo = (VipCardConsumeDto) BeanUtil.covertMap(VipCardConsumeDto.class, map);
			consumeInfo.setOrderStateName(StateChangeUtils.returnOrderStateName((Integer) map.get("order_state")));
			vipCardConsumeDtoGroup.add(consumeInfo);

			// 组装核销记录消费信息
			AirportEasyUseInfo airportEasyUseInfo = airportEasyManager.readDisCodeData(consumeInfo.getCardNo());
			if (airportEasyUseInfo == null) {
				consumeInfo.setConsumeNum(0);
				consumeInfo.setConsumeInfo(StringUtils.EMPTY);

			} else {
				List<AppConsume> consumeList = airportEasyUseInfo.getConsumeList();
				consumeInfo.setConsumeNum(consumeList == null ? 0 : consumeList.size());
				JSONArray jsonArray = new JSONArray();
				for (AppConsume appConsume : consumeList) {
					StringBuilder strBuilder = new StringBuilder();
					strBuilder.append("消费卡号:" + appConsume.getCardNo() + " 地点:" + appConsume.getViproomName() + " 金额:"
							+ appConsume.getConsumeMoney() + " 时间:" + appConsume.getConsumeTime());
					jsonArray.add(strBuilder.toString());
				}
				consumeInfo.setConsumeInfo(jsonArray.toJSONString());
			}
		}
		ExcelUtils.fillDataToExcel(vipCardConsumeDtoGroup, response, VipCardConsumeDto.class);

	}
}
