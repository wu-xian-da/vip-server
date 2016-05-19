package com.jianfei.core.service.user.impl;

import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.common.enu.VipUserSate;
import com.jianfei.core.service.user.VipUserManager;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 15:50
 */
public class VipUserManagerImpl implements VipUserManager {

    /**
     * 添加Vip用户
     *
     * @param vipUser
     */
    @Override
    public boolean addUser(AppCustomer vipUser) {
        return false;
    }

    /**
     * 修改Vip用户信息
     *
     * @param vipUser
     */
    @Override
    public boolean updateUser(AppCustomer vipUser) {
        return false;
    }

    /**
     * 根据手机号获取用户信息
     *
     * @param phone 手机号
     * @return
     */
    @Override
    public AppCustomer getUser(String phone) {
        return null;
    }

    /**
     * 更改用户状态
     *
     * @param sate
     */
    @Override
    public void addUserState(VipUserSate sate) {

    }

    /**
     * 验证用户登录验证码
     *
     * @param phone 用户手机号
     * @param code  验证码
     * @return
     */
    @Override
    public boolean validateLoginCode(String phone, String code) {
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

    /**
     * 验证用户退卡验证码
     *
     * @param phone 用户手机号
     * @param code  验证码
     * @return
     */
    @Override
    public boolean validateBackCardCode(String phone, String code) {
        return false;
    }
}
