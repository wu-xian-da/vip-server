package com.jianfei.core.service.user.impl;

import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.mapper.UserMapper;
import com.jianfei.core.service.user.SaleUserManager;
import com.tencent.common.MD5;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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
        List<User> users=userMapper.getUserByUno(userNo);
        if (users == null ||users.isEmpty()||users.get(0) == null || StringUtils.isBlank(users.get(0).getExtraPasswd())) {
            return false;
        }
        String md5Password = MD5.MD5Encode(password);
        return users.get(0).getExtraPasswd().equals(md5Password);
    }

    /**
     * 根据用户工号获取用户信息
     *
     * @param userNo
     * @return
     */
    @Override
    public User getSaleUser(String userNo) {
        List<User> list=userMapper.getUserByUno(userNo);
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    /**
     * 根据用户工号获取用户信息
     *
     * @param userNo
     * @return
     */
    @Override
    public User getSaleUserDetail(String userNo) {
        HashMap map = new HashMap();
        map.put("uno", userNo);
        map.put("state", 0);
        List<User> list = userMapper.get(map);
        return list == null || list.isEmpty() ? null : list.get(0);
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
    public BaseMsgInfo updatePassword(String userNo, String password, String newPassword) {
        List<User> users = userMapper.getUserByUno(userNo);
        if (users == null || users.isEmpty() || StringUtils.isBlank(users.get(0).getPassword())) {
            return BaseMsgInfo.msgFail("用户不存在或已被禁用");
        }
      String passwordMd5 = MD5.MD5Encode(password);
        SimpleHash nweHash = new SimpleHash("md5", newPassword, users.get(0).getSalt());
        String md5Password = MD5.MD5Encode(newPassword);
        int num = userMapper.updatePasswordByUno(userNo,passwordMd5, nweHash.toString(), md5Password);
        return num == 1 ? BaseMsgInfo.success(true) : BaseMsgInfo.msgFail("原密码输入错误，请重新输入");
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
        int num = userMapper.updatePhotoPath(userNo, photoPath);
        return num == 1 ? true : false;
    }

}
