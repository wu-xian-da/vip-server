package com.jianfei.user;

import com.jianfei.common.BaseController;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.StringUtils;
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
public class SalerUserController extends BaseController{


    /**
     *
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public MessageDto update(@RequestParam(value = "provinceId", required = false) String provinceId,
                             @RequestParam(value = "password", required = false) String airportId,
                             @RequestParam(value = "newpassword", required = false) String airportName) {

        return buildDtoMsg(true).setMsgBody("更新成功...");
    }
}
