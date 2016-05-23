package com.jianfei.core.dto;

/**
 * 用户提交订单信息
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 15:59
 */
public class OrderAddInfoDto {
    /**
     * 订单编号
     */
    private String orderId;
    /**
     *金额
     */
    private float money;
    /**
     * 销售人员工号
     */
    private String uno;

    /**
     * VIP卡卡号
     */
    private String vipCardNo;

    /**
     * 顾客名称
     */
    private String customerName;

    /**
     * 顾客身份证号
     */
    private String customerIdenti;

    /**
     * 顾客手机号
     */
    private String phone;

    /**
     * 顾客验证码
     */
    private String code;

    /**
     * 顾客性别
     */
    private Integer sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 省份
     */
    private String province;

    /**
     * 市
     */
    private String city;

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }

    public String getVipCardNo() {
        return vipCardNo;
    }

    public void setVipCardNo(String vipCardNo) {
        this.vipCardNo = vipCardNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerIdenti() {
        return customerIdenti;
    }

    public void setCustomerIdenti(String customerIdenti) {
        this.customerIdenti = customerIdenti;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
