/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年6月1日-下午4:50:49
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

import com.jianfei.core.common.enu.CardType;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年6月1日 下午4:50:49
 * 
 * @version 1.0.0
 *
 */
public class ExportAip {

	private Object name;

	private Object phone;

	private Object sex;

	private Object cardType;

	private Object identity;
	private Object birthday;
	private Object orderId;
	private Object isTb;
	private Object orderstate;

	/**
	 * 创建一个新的实例 ExportAip.
	 *
	 */
	public ExportAip() {
	}

	public ExportAip(Object name, Object phone, Object sex, Object cardType,
			Object identity, Object birthday, Object orderId, Object isTb,
			Object orderstate) {
		super();
		this.name = StringUtils.isEmpty(StringUtils.obj2String(name)) ? StringUtils.EMPTY
				: name;
		this.phone = StringUtils.isEmpty(StringUtils.obj2String(phone)) ? StringUtils.EMPTY
				: phone;
		String sexParams = StringUtils.obj2String(sex);
		if (!StringUtils.isEmpty(sexParams)) {
			if ("0".equals(sexParams)) {
				this.sex = "女";
			} else {
				this.sex = "男";
			}
		} else {
			this.sex = '女';
		}
		String cardTypeParams = StringUtils.obj2String(cardType);
		if (!StringUtils.isEmpty(cardTypeParams)) {
			if (CardType.SFZ.getName().toString().equals(cardTypeParams)) {
				this.cardType = "身份证";
			} else if (CardType.HZ.getName().toString().equals(cardTypeParams)) {
				this.cardType = "护照";
			} else if (CardType.JGZ.getName().toString().equals(cardTypeParams)) {
				this.cardType = "军官证";
			} else if (CardType.HXZ.getName().toString().equals(cardTypeParams)) {
				this.cardType = "回乡证";
			} else {
				this.cardType = StringUtils.EMPTY;
			}
		} else {
			this.cardType = StringUtils.EMPTY;
		}
		this.identity = StringUtils.isEmpty(StringUtils.obj2String(identity)) ? StringUtils.EMPTY
				: identity;
		this.birthday = StringUtils.isEmpty(StringUtils.obj2String(birthday)) ? StringUtils.EMPTY
				: birthday;
		String isTbParams = StringUtils.obj2String(isTb);
		if (!StringUtils.isEmpty(isTbParams)) {
			if ("0".equals(isTbParams)) {
				this.isTb = "未投保";
			} else if ("1".equals(isTbParams)) {
				this.isTb = "已投保";
			} else {
				this.isTb = StringUtils.EMPTY;
			}
		} else {
			this.isTb = StringUtils.EMPTY;
		}
		this.orderstate = StringUtils.isEmpty(StringUtils
				.obj2String(orderstate)) ? StringUtils.EMPTY : orderstate;
		this.orderId = StringUtils.isEmpty(StringUtils.obj2String(orderId)) ? StringUtils.EMPTY
				: orderId;
	}

	/**
	 * name
	 *
	 * @return the name
	 * @version 1.0.0
	 */

	public Object getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(Object name) {
		this.name = name;
	}

	/**
	 * phone
	 *
	 * @return the phone
	 * @version 1.0.0
	 */

	public Object getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(Object phone) {
		this.phone = phone;
	}

	/**
	 * sex
	 *
	 * @return the sex
	 * @version 1.0.0
	 */

	public Object getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(Object sex) {
		this.sex = sex;
	}

	/**
	 * cardType
	 *
	 * @return the cardType
	 * @version 1.0.0
	 */

	public Object getCardType() {
		return cardType;
	}

	/**
	 * @param cardType
	 *            the cardType to set
	 */
	public void setCardType(Object cardType) {
		this.cardType = cardType;
	}

	/**
	 * identity
	 *
	 * @return the identity
	 * @version 1.0.0
	 */

	public Object getIdentity() {
		return identity;
	}

	/**
	 * @param identity
	 *            the identity to set
	 */
	public void setIdentity(Object identity) {
		this.identity = identity;
	}

	/**
	 * birthday
	 *
	 * @return the birthday
	 * @version 1.0.0
	 */

	public Object getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday
	 *            the birthday to set
	 */
	public void setBirthday(Object birthday) {
		this.birthday = birthday;
	}

	/**
	 * isTb
	 *
	 * @return the isTb
	 * @version 1.0.0
	 */

	public Object getIsTb() {
		return isTb;
	}

	/**
	 * @param isTb
	 *            the isTb to set
	 */
	public void setIsTb(Object isTb) {
		this.isTb = isTb;
	}

	/**
	 * orderstate
	 *
	 * @return the orderstate
	 * @version 1.0.0
	 */

	public Object getOrderstate() {
		return orderstate;
	}

	/**
	 * @param orderstate
	 *            the orderstate to set
	 */
	public void setOrderstate(Object orderstate) {
		this.orderstate = orderstate;
	}

	/**
	 * orderId
	 *
	 * @return the orderId
	 * @version 1.0.0
	 */

	public Object getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(Object orderId) {
		this.orderId = orderId;
	}

}
