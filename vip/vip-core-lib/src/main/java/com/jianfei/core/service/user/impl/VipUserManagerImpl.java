package com.jianfei.core.service.user.impl;

import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.common.enu.VipUserSate;
import com.jianfei.core.common.utils.IdGen;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.dto.CheckOneDto;
import com.jianfei.core.dto.exception.GetQrcodeException;
import com.jianfei.core.mapper.AppCustomerMapper;
import com.jianfei.core.mapper.AppOrdersMapper;
import com.jianfei.core.service.base.ValidateCodeManager;
import com.jianfei.core.service.base.impl.AppUserFeedbackImpl;
import com.jianfei.core.service.thirdpart.AirportEasyManager;
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
    @Autowired
    private AirportEasyManager airportEasyManager;
    @Autowired
    private AppOrdersMapper appOrdersMapper;
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
            //用户ID关联上
            vipUser.setCustomerId(appCustomer.getCustomerId());
            log.info("关联已存在用户:手机号:" + vipUser.getPhone() + "，姓名:" + vipUser.getCustomerName());
            return true;
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

    /**
     * 获取用户二维码
     *
     * @param phone
     * @return
     */
    @Override
    public BaseMsgInfo getQRCode(String phone) throws GetQrcodeException {
        AppCustomer appCustomer=getUserDetail(phone);
        if (appCustomer==null || appCustomer.getVipCards()==null || appCustomer.getVipCards().isEmpty()){
            return BaseMsgInfo.msgFail("此用户暂无可用VIP卡");
        }
        AppVipcard card=appCustomer.getVipCards().get(0);
        if (card==null || StringUtils.isBlank(card.getCardNo())){
            return BaseMsgInfo.msgFail("此用户暂无可用VIP卡");
        }
        String code=  airportEasyManager.getQrcode(card.getCardNo());
        return BaseMsgInfo.success(code);
    }

    /**
     * 根据手机号查询是否有下订单的权利
     *
     * @param phone 用户手机号
     * @return true 有
     */
    @Override
    public boolean haveAddOrderRight(String phone) {
        List<AppOrders> list = appOrdersMapper.orderListByPhone(phone);
        return list.isEmpty();
    }

    /**
     * 根据vipCard卡号查询卡状态
     *
     * @param vipCardNo
     * @return
     */
    @Override
    public BaseMsgInfo checkCardState(String vipCardNo) {
        CheckOneDto checkOneDto= airportEasyManager.checkone(vipCardNo);
        return BaseMsgInfo.success(checkOneDto.getStatus());
    }
}
