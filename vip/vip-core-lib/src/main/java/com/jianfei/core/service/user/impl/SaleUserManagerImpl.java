package com.jianfei.core.service.user.impl;

import com.jianfei.core.bean.User;
import com.jianfei.core.mapper.UserMapper;
import com.jianfei.core.service.user.SaleUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 15:48
 */
@Service
public class SaleUserManagerImpl implements SaleUserManager {

    @Autowired
   private UserMapper userMapper;

    /**
     * 验证用户密码
     *
     * @param userNo   用户唯一标示
     * @param password 密码
     * @return
     */
    @Override
    public boolean validatePassword(String userNo, String password) {
        //TODO 数据库查询
        return true;
    }

    /**
     * 根据用户工号获取用户信息
     *
     * @param userNo
     * @return
     */
    @Override
    public User getSaleUser(String userNo) {
        return userMapper.getUserByName(userNo);
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
        userMapper.updatePasswordByUno(userNo, password, newPassword);
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
