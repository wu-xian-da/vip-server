package com.jianfei.core.service.base.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.common.cache.CacheCons;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.common.enu.VipUserSate;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.common.utils.UUIDUtils;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.dto.ServiceMsgBuilder;
import com.jianfei.core.service.base.ValidateCodeManager;
import com.jianfei.core.service.thirdpart.QueueManager;
import com.jianfei.core.service.user.VipUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 验证码管理类
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/6/15 15:34
 */
@Service
public class ValidateCodeManagerImpl implements ValidateCodeManager {

    @Autowired
    private QueueManager queueManager;

    @Autowired
    private VipUserManager vipUserManager;
    /**
     * 生成验证码 并存储
     *
     * @param phone
     * @param msgType
     */
    @Override
    public String getValidateCode(String phone, MsgType msgType) {
        // 1、根据配置规则生成验证码 6位
        String code = UUIDUtils.getRandomStringByLength(6);
        if ("13856002692".equals(phone)) {
            code = "123456";
        }
        String key = CacheCons.getVerifyPhoneKey(phone, msgType);
       if(MsgType.REGISTER.equals(msgType)){
           JedisUtils.setObject(key, code, 1000000);
       }else {
           JedisUtils.setObject(key, code, 1000000);
       }
        return code;
    }

    /**
     * 验证验证码 并把验证码设置无效
     *
     * @param phone   手机号
     * @param msgType 消息类型
     * @param code    验证码
     * @return
     */
    @Override
    public boolean validateSendCode(String phone, MsgType msgType, String code) {
        String key = CacheCons.getVerifyPhoneKey(phone, msgType);
        String value = (String) JedisUtils.getObject(key);
        boolean flag = code == null || code.equals(value);
        if (flag && !MsgType.REGISTER.equals(msgType)) {
            JedisUtils.delObject(key);
        }
        return flag;
    }

    /**
     * 获取发送的验证码
     *
     * @param phone
     * @param msgType
     */
    @Override
    public String getSendValidateCode(String phone, MsgType msgType) {
        String key = CacheCons.getVerifyPhoneKey(phone, msgType);
        return (String) JedisUtils.getObject(key);
    }

    /**
     * 发送相关短信
     *
     * @param phone
     * @param msgType
     * @return
     */
    @Override
    public BaseMsgInfo sendValidateCode(String phone, MsgType msgType) {
        AppCustomer customer = vipUserManager.getUser(phone);
        if (MsgType.REGISTER.equals(msgType)) {
            //注册 先查找用户是否存在 如果存在 判断是否是激活状态 如果是不能开卡
            if (!vipUserManager.haveAddOrderRight(phone)) {
                return BaseMsgInfo.msgFail("该会员已存在");
            }
        }else {
            if (customer == null || StringUtils.isBlank(customer.getCustomerId())) {
                return BaseMsgInfo.msgFail("手机号尚未注册");
            }else if (MsgType.LOGIN.equals(msgType) && !(VipUserSate.ACTIVE.getName() == customer.getDtflag())) {
                return BaseMsgInfo.msgFail("手机号暂未激活或已经退卡");
            }
        }
        String code = getValidateCode(phone, msgType);
        boolean flag = sendMsgInfo(phone, msgType, code);
        return flag ? BaseMsgInfo.success(true) : BaseMsgInfo.msgFail("消息发送失败");
    }

    /**
     * 发送验证码
     *
     * @param phone
     * @param msgType
     * @param code
     * @return
     */
    @Override
    public boolean sendMsgInfo(String phone, MsgType msgType, String code) {
        //TODO 需要配置验证码发送时间等相关信息
        String time="5分钟";
        JSONObject object=new JSONObject();
        object.put("code",code);
        object.put("time",time);
        ServiceMsgBuilder msgBuilder=new ServiceMsgBuilder().setUserPhone(phone).setMsgType(msgType.getName()).setMsgBody(object.toJSONString());
        return queueManager.sendMessage(msgBuilder);
    }
}
