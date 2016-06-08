package com.jianfei.order;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppOrderArchive;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.dto.BaseDto;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.dto.ReturnCardDto;
import com.jianfei.core.dto.UserProvince;
import com.jianfei.core.service.base.impl.AriPortManagerImpl;
import com.jianfei.core.service.base.impl.BusizzManagerImpl;
import com.jianfei.core.service.stat.impl.ArchiveManagerImpl;
import com.jianfei.core.service.stat.impl.StatManagerImpl;
import com.jianfei.core.service.user.impl.SaleUserManagerImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @Autowired
    private ArchiveManagerImpl archiveManager;
    @Autowired
    private SaleUserManagerImpl saleUserManager;
    @Autowired
    private StatManagerImpl statManagerImpl;
    @Autowired
	private BusizzManagerImpl busizzManagerImpl;

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
     * 根据省份id查询该省份下所有的机场
     * @param provinceId
     * @return
     */
    @RequestMapping("getAriPortListByProvinceId")
    @ResponseBody
    public BaseMsgInfo getAriPortListByProvinceId(@RequestParam(value="provinceId",required=true)String provinceId){
    	try {
    		Map<String,Object> map = new HashMap<String,Object>();
    		//用户选择全国时，省份id号格式如下："provinceId1,provinceId2"
			List<Map<String,Object>> airPortList = archiveManager.selectAirportByProvinceIds(map);
			return BaseMsgInfo.success(airPortList);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("根据省份id号查询该省份下所有的机场 异常",e);
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
    public BaseMsgInfo getSaleCurveByUserId(@RequestParam(value="uno",required=true) String uno,
    		@RequestParam(value="begin",required=true) String begin,
    		@RequestParam(value="end",required=true) String end){
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	try {
    		//1、从归档表中查询该业务员某个时间段内的销售业绩
    		Map<String,Object> paraMap = new HashMap<String,Object>();
    		paraMap.put("saleNo", uno);
    		paraMap.put("begintime", begin);
    		paraMap.put("endTime", end);
    		List<AppOrderArchive> listBycustomer = statManagerImpl.selectCharDataByUserId(paraMap);
    		Map<String,Object> customerMap = new HashMap<String,Object>();
    		customerMap.put("customer", listBycustomer);
    		list.add(customerMap);
    		
    		//2、业务人员所属省份该时间段内的平均开卡人数
    		//2.1根据销售人员id获取该用户所属的省份id
    		//***？？有点问题*****
    		List<UserProvince> userProvinceList = busizzManagerImpl.getProvinceIdByUserId(Integer.parseInt(uno));
    		List<Map<String,Object>> provinceList = statManager.getSaleCurveByUserId(userProvinceList,begin,end);
    		Map<String,Object> provinceMap = new HashMap<String,Object>();
    		provinceMap.put("province", provinceList);
    		list.add(provinceMap);
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
    public BaseMsgInfo getSticCardData(@RequestParam(value="uno",required=true) String uno,
    		@RequestParam(value="areaId",required=false) String areaId,
    		@RequestParam(value="begin",required=true) String begin,
    		@RequestParam(value="end",required=true) String end,
    		@RequestParam(value="airportId",required=false) String airportId){
    	
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	try {
    		//日期-所管辖的省份当日开卡总数
    		List<UserProvince> userProvinceList = busizzManagerImpl.getProvinceIdByUserId(Integer.parseInt(uno));
    		List<Map<String,Object>> provinceList = statManager.getSaleCurveByUserId(userProvinceList,begin,end);
    		Map<String,Object> provinceMap = new HashMap<String,Object>();
    		provinceMap.put("province", provinceList);
    		list.add(provinceMap);
    		
    		//场站-当日开卡总数
    		//组装一个省份+场站id列表
    		List<Map<String,Object>> proIdApIdList = null;
    		List<Map<String,Object>> airPortList = statManager.getSticCardData(proIdApIdList,begin,end);
    		Map<String,Object> airPortMap = new HashMap<String,Object>();
    		airPortMap.put("airPort", airPortList);
    		list.add(airPortMap);
    		
    		return BaseMsgInfo.success(list);
		} catch (Exception e) {
			// TODO: handle exception
			return new BaseMsgInfo().setCode(-1).setMsg("查询失败");
		}
    }

}


