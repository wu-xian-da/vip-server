package com.jianfei.order;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppOrderArchive;
import com.jianfei.core.bean.SysAirport;
import com.jianfei.core.bean.SysViproom;
import com.jianfei.core.common.enu.PayType;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.dto.BaseDto;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.dto.ReturnCardDto;
import com.jianfei.core.service.base.impl.AriPortManagerImpl;
import com.jianfei.core.service.order.impl.OrderManagerImpl;
import com.jianfei.core.service.stat.impl.StatManagerImpl;
import com.jianfei.yeepay.PayController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static Log log = LogFactory.getLog(OrderStaController.class);

    @Autowired
    private StatManagerImpl statManager;
    @Autowired
    private AriPortManagerImpl ariPortService;

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

    /**
     * 分页获取退卡数据
     * @param pageDto
     * @param uno
     * @return
     */
    @RequestMapping(value = "/pageReturnVipCards")
    @ResponseBody
    public BaseMsgInfo pageReturnVipCards(PageDto pageDto, @RequestParam(value = "uno", required = true) String uno
    ) {
        PageInfo<ReturnCardDto> pageInfo= statManager.pageReturnVipCardsByUserId(pageDto,uno);
        return BaseMsgInfo.success(pageInfo);
    }

    /**
     * 根据工号查询某人能查询哪些省份的机场
     * @param uno
     * @return
     */
    @RequestMapping(value = "/airportProvince")
    @ResponseBody
    public BaseMsgInfo getAirportProvinceByUser(@RequestParam(value = "uno", required = true) String uno
    ) {
        try {
            //TODO 需要根据工号查询用户信息 然后查询管辖的省份 List里面
            List<BaseDto> stringList=ariPortService.getAriPortProvince();
            return BaseMsgInfo.success(stringList);
        }catch (Exception e){
            log.error("根据工号查询某人能查询哪些省份的机场 异常",e);
            return new BaseMsgInfo().setCode(-1).setMsg("查询失败");
        }

    }
    
    /**
     * 个人中心销售榜单获取接口
     * @param uno 用户编号
     * @param begin 开始时间
     * @param end 结束时间
     * @return
     */
    @RequestMapping(value="getSaleCurveByUserId")
    @ResponseBody
    public BaseMsgInfo getSaleCurveByUserId(@RequestParam(value="uno") String uno,
    		@RequestParam(value="begin") String begin,
    		@RequestParam(value="end") String end){
    	try {
    		//接口入参
    		Map<String,Object> reqMap = new HashMap<String,Object>();
    		reqMap.put("uno",uno);
    		reqMap.put("begin",begin);
    		reqMap.put("end",end);
    		
    		List<Map<String,Object>> list = statManager.getSaleCurveByUserId(reqMap);
            return BaseMsgInfo.success(list);
		} catch (Exception e) {
			return new BaseMsgInfo().setCode(-1).setMsg("查询失败");
		}
    }
    
    /**
     * 销售榜单-详细图表接口
     * @param uno
     * @param areaId
     * @param begin
     * @param end
     * @param airportId
     * @return
     */
    @RequestMapping(value="getSticCardData")
    @ResponseBody
    public BaseMsgInfo getSticCardData(@RequestParam(value="uno") String uno,
    		@RequestParam(value="areaId") String areaId,
    		@RequestParam(value="begin") String begin,
    		@RequestParam(value="end") String end,
    		@RequestParam(value="airportId") String airportId){
    	
    	Map<String,Object> reqMap = new HashMap<String,Object>();
    	try {
    		//接口入参
    		reqMap.put("uno",uno);
    		reqMap.put("begin",begin);
    		reqMap.put("end",end);
    		
    		List<Map<String,Object>> list = statManager.getSticCardData(reqMap);
    		return BaseMsgInfo.success(list);
		} catch (Exception e) {
			// TODO: handle exception
			return new BaseMsgInfo().setCode(-1).setMsg("查询失败");
		}
    }

}


