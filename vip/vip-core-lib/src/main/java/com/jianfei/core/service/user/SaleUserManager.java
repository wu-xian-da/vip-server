package com.jianfei.core.service.user;

import com.jianfei.core.bean.User;

/**
 * 销售用户管理
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 10:47
 */
public interface SaleUserManager {

    /**
     * 验证用户密码
     * @param userNo 用户唯一标示
     * @param password 密码
     * @return
     */
    boolean validatePassword(String userNo,String password);

    /**
     * 根据用户工号获取用户信息
     * @param userNo
     * @return
     */
    User getSaleUser(String userNo);

    /**
     * 更新用户密码
     * @param userNo 用户唯一标示
     * @param password 密码
     * @param newPassword 新密码
     * @return
     */
    boolean updatePassword(String userNo,String password,String newPassword);

    /**
     * 更新用户头像位置
     * @param userNo 用户唯一标示
     * @param photoPath 用户头像位置
     * @return
     */
    boolean updatePhotoPath(String userNo,String photoPath);
    
    /**
     * 易宝pos机登录（销售员）
     * @param userNo
     * @param password
     * @return
     */
    public int yeepayLogin(String userNo,String password);

}
