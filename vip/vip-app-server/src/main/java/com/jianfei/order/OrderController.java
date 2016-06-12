package com.jianfei.order;

import com.jianfei.core.bean.AppCardBack;
import com.jianfei.core.bean.AppInvoice;
import com.jianfei.core.common.enu.PayType;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.dto.OrderAddInfoDto;
import com.jianfei.core.service.order.impl.OrderManagerImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	private static Log log = LogFactory.getLog(OrderController.class);
	@Autowired
	private OrderManagerImpl orderManager;

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
			return  orderManager.getPayUrl(orderId,type);
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

			return orderManager.checkThirdPay(orderId, type);
		}catch (Exception e){
			log.error("第三方支付确认收款接口失败",e);
			return BaseMsgInfo.msgFail("第三方支付确认收款接口失败");
		}

	}

	/**
	 * 顾客现金刷卡确认接口
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
			return orderManager.updatePayState(orderId, type);
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
			@RequestParam(value = "code", required = true) String code) {
		try {
			return orderManager.getVipCardUseAndOrder(phone, code);
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

		return BaseMsgInfo.success(true);
	}
}
