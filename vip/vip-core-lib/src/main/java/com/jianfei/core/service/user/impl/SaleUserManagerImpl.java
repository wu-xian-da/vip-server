package com.jianfei.core.service.user.impl;

import com.jianfei.core.bean.User;
import com.jianfei.core.service.user.SaleUserManager;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 15:48
 */
public class SaleUserManagerImpl implements SaleUserManager {
    /**
     * 验证用户密码
     *
     * @param userNo   用户唯一标示
     * @param password 密码
     * @return
     */
    @Override
    public boolean validatePassword(String userNo, String password) {
        return false;
    }

    /**
     * 根据用户工号获取用户信息
     *
     * @param userNo
     * @return
     */
    @Override
    public User getSaleUser(String userNo) {
        return null;
    }

    /**
     * 更新用户密码
     *
     * @param userNo      用户唯一标示
     * @param password    密码
     * @param newPassword 新密码
     * @return
     */
    @Override
    public boolean updatePassword(String userNo, String password, String newPassword) {
        return false;
    }

    /**
     * 更新用户头像位置
     *
     * @param userNo    用户唯一标示
     * @param photoPath 用户头像位置
     * @return
     */
    @Override
    public boolean updatePhotoPath(String userNo, String photoPath) {
        return false;
    }
}
