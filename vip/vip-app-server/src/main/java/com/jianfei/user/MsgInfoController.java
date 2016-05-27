package com.jianfei.user;

import com.jianfei.common.BaseMsgInfo;
import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.service.thirdpart.impl.MsgInfoManagerImpl;
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


    @Autowired
    private MsgInfoManagerImpl msgInfoManager;

    /**
     * 给用户发送登录验证码
     *
     */
    @RequestMapping(value = "/sendTelCode")
    @ResponseBody
    public BaseMsgInfo sendTelCode(@RequestParam(value = "phone", required = true) String phone,
                                   @RequestParam(value = "type", required = true) String type
    ) {
        MsgType msgType = null;
        if (MsgType.REGISTER.getName().equals(type)) {
            msgType = MsgType.REGISTER;
        } else if (MsgType.LOGIN.getName().equals(type)) {
            msgType = MsgType.LOGIN;
        } else if (MsgType.BACK_CARD.getName().equals(type)) {
            msgType = MsgType.BACK_CARD;
        }
        if (msgType == null)
            return BaseMsgInfo.fail("");

        boolean flag = msgInfoManager.sendValidateCode(phone, msgType);
        return BaseMsgInfo.success(flag);
    }


}
