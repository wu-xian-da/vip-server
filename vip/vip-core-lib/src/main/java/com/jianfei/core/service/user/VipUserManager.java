package com.jianfei.core.service.user;

import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.common.enu.VipUserSate;

/**
 * Vip用户管理
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 10:58
 */
public interface VipUserManager {
    /**
     * 添加Vip用户
     * @param vipUser
     */
    boolean addUser(AppCustomer vipUser);

    /**
     * 修改Vip用户信息
     * @param vipUser
     */
    boolean updateUser(AppCustomer vipUser);

    /**
     * 更改用户状态
     * @param sate
     */
    void addUserState(VipUserSate sate);

    /**
     * 验证用户登录验证码
     * @param phone 用户手机号
     * @param code 验证码
     * @return
     */
    boolean validateLoginCode(String phone,String code);


    /**
     * 更新用户头像位置
     * @param userNo 用户唯一标示
     * @param photoPath 用户头像位置
     * @return
     */
    boolean updatePhotoPath(String userNo,String photoPath);

    /**
     * 验证用户退卡验证码
     * @param phone 用户手机号
     * @param code 验证码
     * @return
     */
    boolean validateBackCardCode(String phone,String code);
}
