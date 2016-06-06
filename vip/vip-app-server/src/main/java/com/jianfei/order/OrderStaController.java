package com.jianfei.order;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppOrderArchive;
import com.jianfei.core.bean.SysAirport;
import com.jianfei.core.bean.SysViproom;
import com.jianfei.core.common.enu.PayType;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.service.order.impl.OrderManagerImpl;
import com.jianfei.core.service.stat.impl.StatManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/27 11:10
 */
@Controller
@RequestMapping(value = "orderSta")
public class OrderStaController {

    @Autowired
    private StatManagerImpl statManager;

    /**
     * 分页获取
     * @param pageDto
     * @param uno
     * @return
     */
    @RequestMapping(value = "/pageSaleVipCards")
    @ResponseBody
    public BaseMsgInfo pageSaleVipCards(PageDto pageDto, @RequestParam(value = "uno", required = true) String uno
    ) {
        PageInfo<AppOrderArchive> pageInfo= statManager.pageOrderStatByUserId(pageDto,uno);
        return BaseMsgInfo.success(pageInfo);
    }


    /**
     * 个人销售记录
     * @param uno
     * @param date
     * @return
     */
    @RequestMapping(value = "/saleVipCardDetail")
    @ResponseBody
    public BaseMsgInfo listOrderByUserId(@RequestParam(value = "uno", required = true) String uno,
                                         @RequestParam(value = "date", required = true) String date
    ) {
        return BaseMsgInfo.success(statManager.listOrderByUserId(uno,date));
    }

}


