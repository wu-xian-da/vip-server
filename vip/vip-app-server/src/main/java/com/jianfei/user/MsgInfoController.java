package com.jianfei.user;

import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.service.base.impl.ValidateCodeManagerImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 信息相关
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/17 15:51
 */
@Controller
@RequestMapping(value = "msg")
public class MsgInfoController {

    private static Log log = LogFactory.getLog(MsgInfoController.class);
    @Autowired
    private ValidateCodeManagerImpl validateCodeManager;

    /**
     * 给用户发送登录验证码
     *
     */
    @RequestMapping(value = "/sendTelCode")
    @ResponseBody
    public BaseMsgInfo sendTelCode(@RequestParam(value = "phone", required = true) String phone,
                                   @RequestParam(value = "type", required = true) String type
    ) {

        try {
            MsgType msgType = null;
            if (MsgType.REGISTER.getName().equals(type)) {
                msgType = MsgType.REGISTER;
            } else if (MsgType.LOGIN.getName().equals(type)) {
                msgType = MsgType.LOGIN;
            } else if (MsgType.SELECT.getName().equals(type)) {
                msgType = MsgType.SELECT;
            }
            if (msgType == null)
                return new BaseMsgInfo().setCode(-1).setMsg("消息类型有误");

           return validateCodeManager.sendValidateCode(phone, msgType);
        }catch (Exception e){
            log.error("给用户发送登录验证码",e);
            return BaseMsgInfo.msgFail("发送登录验证码失败");
        }
    }

    /**
     * 给用户发送登录验证码
     *
     */
    @RequestMapping(value = "/getTelCode")
    @ResponseBody
    public BaseMsgInfo getTelCode(@RequestParam(value = "phone", required = true) String phone,
                                   @RequestParam(value = "type", required = true) String type
    ) {
        try {
            MsgType msgType = null;
            if (MsgType.REGISTER.getName().equals(type)) {
                msgType = MsgType.REGISTER;
            } else if (MsgType.LOGIN.getName().equals(type)) {
                msgType = MsgType.LOGIN;
            } else if (MsgType.SELECT.getName().equals(type)) {
                msgType = MsgType.SELECT;
            }
            if (msgType == null)
                return new BaseMsgInfo().setCode(-1).setMsg("消息类型有误");

            String code = validateCodeManager.getSendValidateCode(phone, msgType);
            return BaseMsgInfo.success("手机号:"+phone+"的校验码为"+code+",请于5分钟内输入,请勿向任何人泄露。[亿出行]");
        }catch (Exception e){
            log.error("获取发送的验证码失败",e);
            return BaseMsgInfo.msgFail("获取发送的验证码失败");
        }

    }


}
