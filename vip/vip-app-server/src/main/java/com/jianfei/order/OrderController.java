package com.jianfei.order;

import com.jianfei.core.bean.AppCardBack;
import com.jianfei.core.bean.AppInvoice;
import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.common.enu.PayType;
import com.jianfei.core.common.enu.StateType;
import com.jianfei.core.common.enu.VipCardState;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.dto.OrderAddInfoDto;
import com.jianfei.core.service.base.ValidateCodeManager;
import com.jianfei.core.service.base.VipCardManager;
import com.jianfei.core.service.base.impl.ValidateCodeManagerImpl;
import com.jianfei.core.service.order.OrderPayManager;
import com.jianfei.core.service.order.impl.OrderManagerImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

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
	private static Log log = LogFactory.getLog(OrderController.class);
	@Autowired
	private OrderManagerImpl orderManager;

	@Autowired
	private OrderPayManager orderPayManager;

	@Autowired
	private VipCardManager vipCardManager;
	@Autowired
	private ValidateCodeManager validateCodeManager;
	/**
	 * 验证卡片信息及用户手机号
	 * @return
	 */
	@RequestMapping(value = "/validateCard")
	@ResponseBody
	public BaseMsgInfo validateCard(@RequestParam(value = "vipCardNo", required = true) String vipCardNo,
									@RequestParam(value = "phone", required = false) String phone,
									@RequestParam(value = "code", required = false) String code
	) {
		try {
			AppVipcard vipCard = vipCardManager.getVipCardByNo(vipCardNo);
			if (vipCard == null || StringUtils.isBlank(vipCard.getCardNo())) {
				return BaseMsgInfo.msgFail("卡号有误或系统暂未录入此卡信息，请联系相关人员添加此卡信息！");
			} else if (StateType.NOT_EXIST.getName() == vipCard.getDtflag()) {
				return BaseMsgInfo.msgFail("此卡已禁用");
			} else if (VipCardState.ACTIVE.getName().equals(vipCard.getCardState())) {
				return BaseMsgInfo.msgFail("卡号有误，此卡已激活");
			}else if (VipCardState.TO_ACTIVATE.getName().equals(vipCard.getCardState())) {
				return BaseMsgInfo.msgFail("卡号有误，此卡待激活");
			}
			if (StringUtils.isBlank(phone) && StringUtils.isBlank(code)) {
				//1、校验用户和手机验证码
				boolean flag = validateCodeManager.validateSendCode(phone, MsgType.REGISTER, code);
				if (!flag) {
					return new BaseMsgInfo().setCode(-1).setMsg("手机验证码验证失败");
				}
			}
			return BaseMsgInfo.success(true);
		} catch (Exception e) {
			log.error("验证卡片信息失败", e);
			return BaseMsgInfo.msgFail("验证卡片信息失败");
		}
	}

	/**
	 * 添加订单信息
	 * 
	 * @param addInfoDto
	 * @return
	 */
	@RequestMapping(value = "/addOrder")
	@ResponseBody
	public BaseMsgInfo addOrder(OrderAddInfoDto addInfoDto) {
		try {
			return orderManager.addOrderAndUserInfo(addInfoDto);
		}catch (Exception e){
			log.error("添加订单信息失败",e);
			return BaseMsgInfo.msgFail("订单信息添加失败");
		}
	}

	/**
	 * 生成支付URL接口 传入支付类型
	 * 
	 * @param orderId
	 *            订单号
	 * @param payType
	 *            支付类型
	 * @return
	 */
	@RequestMapping(value = "/payUrl", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo getPayUrl(
			@RequestParam(value = "orderId", required = true) String orderId,
			@RequestParam(value = "payType", required = true) int payType) {
		try {
			PayType type = null;
			if (PayType.WXPAY.getName() == payType) {
				type = PayType.WXPAY;
			} else if (PayType.ALIPAY.getName() == payType) {
				type = PayType.ALIPAY;
			} else if (PayType.BANKPAY.getName() == payType) {
				type = PayType.BANKPAY;
			}
			if (type == null)
				return new BaseMsgInfo().setCode(-1).setMsg("付款方式错误");
			return  orderPayManager.getPayUrl(orderId,type);
		}catch (Exception e){
			log.error("生成支付URL失败",e);
			return BaseMsgInfo.msgFail("生成支付URL失败");
		}
	}

	/**
	 * 第三方支付确认收款接口
	 * 
	 * @param orderId
	 *            订单号
	 * @param payType
	 *            支付类型
	 * @return
	 */
	@RequestMapping(value = "/thirdPayState")
	@ResponseBody
	public BaseMsgInfo checkThirdPay(
			@RequestParam(value = "orderId", required = true) String orderId,
			@RequestParam(value = "payType", required = true) int payType) {
		try {
			PayType type = null;
			if (PayType.WXPAY.getName() == payType) {
				type = PayType.WXPAY;
			} else if (PayType.ALIPAY.getName() == payType) {
				type = PayType.ALIPAY;
			} else if (PayType.BANKPAY.getName() == payType) {
				type = PayType.BANKPAY;
			}
			if (type == null)
				return new BaseMsgInfo().setCode(-1).setMsg("付款方式错误");

			return orderPayManager.checkThirdPay(orderId, type);
		}catch (Exception e){
			log.error("第三方支付确认收款接口失败",e);
			return BaseMsgInfo.msgFail("第三方支付确认收款接口失败");
		}

	}

	/**
	 * 顾客现金确认接口
	 * 
	 * @param orderId
	 *            订单号
	 * @param payType
	 *            支付类型
	 * @return
	 */
	@RequestMapping(value = "/payState")
	@ResponseBody
	public BaseMsgInfo updatePayState(
			@RequestParam(value = "orderId", required = true) String orderId,
			@RequestParam(value = "payType", required = false) int payType) {
		try {
			AppOrders appOrders=new AppOrders();
			appOrders.setOrderId(orderId);
			appOrders.setPayTime(new Date());
			appOrders.setPayType(PayType.CASHPAY.getName());
			return orderManager.updatePayState(appOrders);
		}catch (Exception e){
			log.error("顾客现金刷卡确认接口失败",e);
			return BaseMsgInfo.msgFail("顾客现金刷卡确认接口失败");
		}
	}

	/**
	 * 邮寄信息保存
	 * 
	 * @param appInvoice
	 *            邮寄信息
	 * @return
	 */
	@RequestMapping(value = "/orderMail")
	@ResponseBody
	public BaseMsgInfo addOrderMail(AppInvoice appInvoice) {
		try {
			return orderManager.addOrderMailInfo(appInvoice);
		}catch (Exception e){
			log.error("添加邮寄信息异常",e);
			return BaseMsgInfo.msgFail("邮寄信息添加失败");
		}
	}

	/**
	 * 用户使用记录查询接口
	 * 
	 * @param phone
	 *            手机号
	 * @param code
	 *            验证码
	 * @return
	 */
	@RequestMapping(value = "/vipCardUse")
	@ResponseBody
	public BaseMsgInfo VipCardUseAndOrder(
			@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "vipCardNo", required = false) String vipCardNo) {
		try {
			return orderManager.getVipCardUseAndOrder(phone, code, vipCardNo);
		}catch (Exception e){
			log.error("用户使用记录查询接口异常",e);
			return BaseMsgInfo.msgFail("用户使用记录查询失败");
		}
	}

	/**
	 * 保存用户退卡信息
	 * 
	 * @param appCardBack
	 * @return
	 */
	@RequestMapping(value = "/vipReturnInfo")
	@ResponseBody
	public BaseMsgInfo addVipReturnInfo(AppCardBack appCardBack) {
		try {
			return orderManager.addBackCardInfo(appCardBack);
		}catch (Exception e){
			log.error("保存用户退卡信息异常",e);
			return BaseMsgInfo.msgFail("保存用户退卡信息失败");
		}
	}
}
