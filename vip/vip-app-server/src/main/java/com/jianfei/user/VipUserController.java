package com.jianfei.user;

import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.service.order.impl.ConsumeManagerImpl;
import com.jianfei.core.service.user.impl.VipUserManagerImpl;
import com.jianfei.dto.VipTestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Vip用户Controller
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/17 15:51
 */
@Controller
@RequestMapping(value = "vipUser")
public class VipUserController {

    @Autowired
    private VipUserManagerImpl vipUserManager;

    @Autowired
    private ConsumeManagerImpl consumeManager;

    /**
     * VIP 用户登录
     *
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public BaseMsgInfo vipRoomInfo(@RequestParam(value = "phone", required = true) String phone,
                                   @RequestParam(value = "code", required = true) String code
    ) {
        boolean validate = vipUserManager.validateLoginCode(phone, code);
        if (validate) {

            VipTestVo vipTestVo = new VipTestVo("4219a91f-45d5-4a07-9e8e-3acbadd0c23e", "d41df9fd-3d36-4a20-b0b7-1a1883c7439d",
                    "read write trust", "bearer", 43199);
            return BaseMsgInfo.success(vipTestVo);
        } else {
            return BaseMsgInfo.fail("");
        }
    }

    /**
     * VIP 获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "/getUser")
    @ResponseBody
    public BaseMsgInfo getUser(@RequestParam(value = "phone", required = true) String phone
    ) {
        AppCustomer appCustomer = vipUserManager.getUser(phone);
        return BaseMsgInfo.success(appCustomer);
    }

    /**
     * VIP 用户信息更新
     *
     * @return
     */
    @RequestMapping(value = "/updateUserInfo")
    @ResponseBody
    public BaseMsgInfo updateUserInfo(AppCustomer appCustomer
    ) {
        return BaseMsgInfo.success(vipUserManager.updateUser(appCustomer));
    }


    /**
     * VIP 使用记录查询
     *
     * @return
     */
    @RequestMapping(value = "/vipCardUse")
    @ResponseBody
    public BaseMsgInfo getVipCardUse(@RequestParam(value = "vipCardNo", required = true) String vipCardNo
    ) {
        return BaseMsgInfo.success(consumeManager.getConsumesByVipNo(vipCardNo));
    }

}
