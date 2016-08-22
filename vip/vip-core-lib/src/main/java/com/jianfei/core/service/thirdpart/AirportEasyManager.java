package com.jianfei.core.service.thirdpart;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import com.jianfei.core.dto.AirportEasyUseInfo;
import com.jianfei.core.dto.exception.GetQrcodeException;


/**
 * 空港易行相关接口封装
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 17:43
 */
public interface AirportEasyManager {
	
	public boolean getCardCode(String tradeNo) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException;

    /**
     * 激活VIP卡
     *
     * @param vipCardNo VIP卡号
     * @param userPhone VIP用户手机号
     * @param userName  VIP用户名
     * @return
     * @throws IOException 
     * @throws KeyStoreException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     * @throws UnrecoverableKeyException 
     */
    boolean activeVipCard(String vipCardNo, String userPhone, String userName) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException;

    /**
     * 禁用VIP卡
     *
     * @param vipCardNo vip卡号
     * @return
     */
    boolean disabledVipCard(String vipCardNo) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException;

    /**
     * 获取所有Vip卡使用记录
     *
     * @return
     */
    AirportEasyUseInfo getVipCardUseInfo() throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException;

    /**
     * 确认已收到VIP卡使用记录
     *
     * @param batchNo 批次号
     * @return
     */
    boolean sendConfirmInfo(String batchNo) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException;

    /**
     * 判定手机号和姓名状态
     * @param userName 姓名
     * @param userPhone 手机号
     * @return
     */
    boolean vipuserStatus(String userName,String userPhone);
    
    /**
     * 判定VIP卡是否已绑定
     * @param vipCardNo 卡号
     * @return
     */
    boolean cardBindStatus(String vipCardNo);
    
    /**
     * 获取核销二维码
     * @param vipCardNo 卡号
     * @return 
     * @throws Exception 
     */
    String getQrcode(String vipCardNo) throws GetQrcodeException;
    
    AirportEasyUseInfo readDisCodeData(String vipCardNo);
}
