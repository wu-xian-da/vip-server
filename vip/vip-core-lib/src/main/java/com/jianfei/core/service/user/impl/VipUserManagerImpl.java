package com.jianfei.core.service.user.impl;

import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.common.enu.VipUserSate;
import com.jianfei.core.common.utils.IdGen;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.mapper.AppCustomerMapper;
import com.jianfei.core.service.base.ValidateCodeManager;
import com.jianfei.core.service.base.impl.AppUserFeedbackImpl;
import com.jianfei.core.service.order.impl.OrderManagerImpl;
import com.jianfei.core.service.user.VipUserManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * 验证码管理实现类
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 15:50
 */
@Service
public class VipUserManagerImpl implements VipUserManager {
    private static Log log = LogFactory.getLog(VipUserManagerImpl.class);
    @Autowired
   private ValidateCodeManager validateCodeManager;
    @Autowired
   private AppCustomerMapper customerMapper;
    @Autowired
    private AppUserFeedbackImpl userFeedback;
    /**
     * 添加Vip用户
     *
     * @param vipUser
     */
    @Override
    public boolean addORUpdateUser(AppCustomer vipUser) throws InvocationTargetException, IllegalAccessException {
        //查询是否存在相应的顾客
        AppCustomer appCustomer = getUser(vipUser.getPhone());
        if (StringUtils.isNotBlank(appCustomer.getCustomerId())) {
            //用户状态未未激活状态
            vipUser.setDtflag(VipUserSate.NOT_ACTIVE.getName());
            //用户ID关联上
            vipUser.setCustomerId(appCustomer.getCustomerId());
            log.info("更新VIP用户状态为初始化状态:手机号:" + vipUser.getPhone() + "，姓名:" + vipUser.getCustomerName());
            return updateUser(vipUser);
        } else {
            vipUser.setCustomerId(IdGen.uuid());
            vipUser.setCreateTime(new Date());
            vipUser.setDtflag(VipUserSate.NOT_ACTIVE.getName());
            int num = customerMapper.insertSelective(vipUser);
            log.info("添加VIP用户:手机号:" + vipUser.getPhone() + "，姓名:" + vipUser.getCustomerName());
            return num == 1 ? true : false;
        }
    }

    /**
     * 修改Vip用户信息
     *
     * @param vipUser
     */
    @Override
    public boolean updateUser(AppCustomer vipUser) {
        int num = customerMapper.updateByPrimaryKeySelective(vipUser);
        return num == 1 ? true : false;
    }

    /**
     * 根据手机号获取用户信息
     *
     * @param phone 手机号
     * @return
     */
    @Override
    public AppCustomer getUser(String phone) {
        List<AppCustomer> list = customerMapper.selectByPhone(phone);
        return list == null || list.isEmpty() ? new AppCustomer() : list.get(0);
    }

    /**
     * 根据手机号获取用户信息
     *
     * @param phone 手机号
     * @return
     */
    @Override
    public AppCustomer getUserDetail(String phone) {
        List<AppCustomer> list = customerMapper.selectCustomerDetailByPhone(phone);
        return list == null || list.isEmpty() ? new AppCustomer() : list.get(0);
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
        return  validateCodeManager.validateSendCode(phone, MsgType.LOGIN,code);
    }



    /**
     * VIP用户反馈
     *
     * @param phone   手机号
     * @param content 反馈内容
     * @return
     */
    @Override
    public BaseMsgInfo sendFeedBackInfo(String phone, String content) {
        AppCustomer customer=getUser(phone);
        int num=userFeedback.addFeedbackInfo(customer,content);
        if (num == 1) {
            return BaseMsgInfo.success(true);
        }
        return new BaseMsgInfo().setCode(-1).setMsg("反馈内容提交失败");
    }

    /**
     * 更新用户状态
     *
     * @param phone
     * @param vipUserSate
     * @return
     */
    @Override
    public boolean updateUserSate(String phone, VipUserSate vipUserSate) {
        AppCustomer appCustomer=new AppCustomer();
        appCustomer.setPhone(phone);
        appCustomer.setDtflag(vipUserSate.getName());
        return updateUser(appCustomer);
    }
}
