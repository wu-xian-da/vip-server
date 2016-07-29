package com.jianfei.user;

import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.bean.AppAirportTrans;
import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.service.base.AirportTransManager;
import com.jianfei.core.service.base.AppConfigManager;
import com.jianfei.core.service.base.impl.AppConfigManagerImpl;
import com.jianfei.core.service.order.OrderPayManager;
import com.jianfei.core.service.order.impl.ConsumeManagerImpl;
import com.jianfei.core.service.user.impl.VipUserManagerImpl;
import com.jianfei.dto.VipTestVo;
import com.jianfei.resource.ResourceController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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
    private static Log log = LogFactory.getLog(VipUserController.class);
    @Autowired
    private VipUserManagerImpl vipUserManager;

    @Autowired
    private ConsumeManagerImpl consumeManager;

    @Autowired
    private AirportTransManager transManager;


    /**
     * VIP 获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "/getUser")
    @ResponseBody
    public BaseMsgInfo getUser(@RequestParam(value = "phone", required = true) String phone
    ) {
        AppCustomer appCustomer = vipUserManager.getUserDetail(phone);
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

    /**
     * VIP 用户信息更新
     *
     * @return
     */
    @RequestMapping(value = "/userFeedbackInfo")
    @ResponseBody
    public BaseMsgInfo userFeedbackInfo(@RequestParam(value = "phone", required = true) String phone,
                                        @RequestParam(value = "content", required = true) String content
    ) {
        return vipUserManager.sendFeedBackInfo(phone, content);
    }

    /**
     * VIP 退出接口
     *
     * @return
     */
    @RequestMapping(value = "/loginOut")
    @ResponseBody
    public BaseMsgInfo loginOut(@RequestParam(value = "token ", required = false) String token
    ) {
        return BaseMsgInfo.success(true);
    }

    /**
     * 接送机信息添加
     *
     * @return
     */
    @RequestMapping(value = "/airportTransInfo")
    @ResponseBody
    public BaseMsgInfo addAppAirportTrans(AppAirportTrans airportTrans
    ) {
        try {
            return transManager.addAirportTransInfo(airportTrans);
        }catch (Exception e){
            log.error("添加接送机信息失败",e);
            return BaseMsgInfo.msgFail("接送机信息添加失败");
        }
    }

    /**
     *  接送机次数统计
     *
     * @return
     */
    @RequestMapping(value = "/airportTransNum")
    @ResponseBody
    public BaseMsgInfo airportTransNum(@RequestParam(value = "phone", required = true) String phone
    ) {
        try {
            return transManager.getAirportTransNum(phone);
        }catch (Exception e){
            log.error("接送机次数统计错误",e);
            return BaseMsgInfo.msgFail("接送机次数统计失败");
        }
    }

    /**
     * 接送机城市列表
     *
     * @return
     */
    @RequestMapping(value = "/cityList")
    @ResponseBody
    public BaseMsgInfo cityList(
    ) {
        try {
            return transManager.getTransCityList();
        }catch (Exception e){
            log.error("接送机城市列表失败",e);
            return BaseMsgInfo.msgFail("接送机城市列表失败");
        }
    }

    /**
     * 接送机机场列表信息
     *
     * @return
     */
    @RequestMapping(value = "/airportList")
    @ResponseBody
    public BaseMsgInfo airportList(@RequestParam(value = "city", required = false) String city
    ) {
        try {
            return transManager.getTransAirportList(city);
        }catch (Exception e){
            log.error("接送机机场列表失败",e);
            return BaseMsgInfo.msgFail("接送机机场列表失败");
        }
    }


    /**
     * 获取用户二维码
     *
     * @return
     */
    @RequestMapping(value = "/qrCode")
    @ResponseBody
    public BaseMsgInfo getUserQRCode(@RequestParam(value = "phone", required = true) String phone
    ) {
        try {
            return vipUserManager.getQRCode(phone);
        }catch (Exception e){
            log.error("获取易行二维码异常",e);
            return BaseMsgInfo.msgFail("获取用户二维码失败");
        }
    }
}
