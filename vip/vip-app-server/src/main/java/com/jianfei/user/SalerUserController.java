package com.jianfei.user;

import com.jianfei.common.BaseMsgInfo;
import com.jianfei.core.bean.User;
import com.jianfei.core.service.user.impl.SaleUserManagerImpl;
import com.jianfei.dto.VipTestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 销售人员Controller
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/17 15:53
 */
@Controller
@RequestMapping(value = "saler")
public class SalerUserController {

    @Autowired
    private SaleUserManagerImpl saleUserManager;


    /**
     * 用户登录
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public BaseMsgInfo vipRoomInfo(@RequestParam(value = "uno", required = true) String uno,
                                   @RequestParam(value = "password", required = true) String password
    ) {
        boolean validate= saleUserManager.validatePassword(uno,password);
        if (validate){

            VipTestVo vipTestVo=new VipTestVo("4219a91f-45d5-4a07-9e8e-3acbadd0c23e","d41df9fd-3d36-4a20-b0b7-1a1883c7439d",
                    "read write trust","bearer",43199);
            return BaseMsgInfo.success(vipTestVo);
        }else {
            return BaseMsgInfo.fail("");
        }
    }

    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping(value = "/getUser")
    @ResponseBody
    public BaseMsgInfo getUser(@RequestParam(value = "uno", required = true) String uno
    ) {
        User saleUser= saleUserManager.getSaleUser(uno);
        return BaseMsgInfo.success(saleUser);
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    @ResponseBody
    public BaseMsgInfo update(@RequestParam(value = "uno", required = false) String uno,
                             @RequestParam(value = "password", required = false) String password,
                             @RequestParam(value = "newpassword", required = false) String newPassword) {
        boolean flag = saleUserManager.updatePassword(uno, password, newPassword);
        return BaseMsgInfo.success(flag);
    }


}
