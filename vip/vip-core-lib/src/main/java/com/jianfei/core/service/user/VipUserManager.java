package com.jianfei.core.service.user;

import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.common.enu.VipUserSate;
import com.jianfei.core.dto.BaseMsgInfo;

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
     * 根据手机号获取用户信息
     * @param phone 手机号
     * @return
     */
    AppCustomer getUser(String phone);

    /**
     * 根据手机号获取用户信息
     * @param phone 手机号
     * @return
     */
    AppCustomer getUserDetail(String phone);


    /**
     * 验证用户登录验证码
     * @param phone 用户手机号
     * @param code 验证码
     * @return
     */
    boolean validateLoginCode(String phone,String code);


    /**
     * VIP用户反馈
     * @param phone 手机号
     * @param content 反馈内容
     * @return
     */
    BaseMsgInfo sendFeedBackInfo(String phone,String content);
}
