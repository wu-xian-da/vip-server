package com.jianfei.order;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppOrderArchive;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.dto.*;
import com.jianfei.core.service.base.impl.AriPortManagerImpl;
import com.jianfei.core.service.base.impl.BusizzManagerImpl;
import com.jianfei.core.service.stat.impl.ArchiveManagerImpl;
import com.jianfei.core.service.stat.impl.StatManagerImpl;
import com.jianfei.core.service.user.impl.SaleUserManagerImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public BaseMsgInfo getAriPortListByProvinceId(@RequestParam(value="provinceId",defaultValue="",required=false)String provinceId){
    	try {
    		Map<String,Object> map = new HashMap<String,Object>();
    		if(!provinceId.equals("")){
    			map.put("pids", provinceId);
    		}
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
    		paraMap.put("beginTime", begin);
    		paraMap.put("endTime", end);
    		List<AppOrderArchive> listBycustomer = statManager.selectCharDataByUserId(paraMap);
    		Map<String,Object> customerMap = new HashMap<String,Object>();
    		customerMap.put("customer", listBycustomer);
    		list.add(customerMap);
    		
    		//2、业务人员所属省份该时间段内的平均开卡人数
    		//2.1根据销售人员id获取该用户所属的省份id
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
    		@RequestParam(value="areaId",defaultValue="", required=false) String areaId,
    		@RequestParam(value="begin",required=true) String begin,
    		@RequestParam(value="end",required=true) String end,
    		@RequestParam(value="airportId",required=false,defaultValue="") String airportId){
    	
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	try {
    		//1日期-所管辖的省份当日开卡总数
    		//1.1省份列表
    		List<UserProvince> userProvinceList=new ArrayList<UserProvince>();
    		//全国
    		if(!areaId.equals("")){
    			UserProvince userProvince = new UserProvince();
    			userProvince.setProvinceId(areaId);
    			userProvince.setUserId(uno);
    			userProvinceList.add(userProvince);
    		}else{
    			userProvinceList = busizzManagerImpl.getProvinceIdByUserId(Integer.parseInt(uno));
    		}
    		
    		List<Map<String,Object>> provinceList = statManager.getTotalSaleCurveByProvinceId(userProvinceList,begin,end);
    		Map<String,Object> provinceMap = new HashMap<String,Object>();
    		provinceMap.put("province", provinceList);
    		list.add(provinceMap);
    		
    		//2场站-当日开卡总数
    		//2.1组装一个省份+场站id列表
    		List<Map<String,Object>> proIdApIdList = new ArrayList<Map<String,Object>>();
    		if(!airportId.equals("")){
    			Map<String,Object> map = new HashMap<String,Object>();
    			map.put("pid", areaId);
    			map.put("airportId", airportId);
    			proIdApIdList.add(provinceMap);
    		}else{
    			if(!areaId.equals("")){//某个省下的所有的机场
    				Map<String,Object> map = new HashMap<String,Object>();
    				map.put("pids", areaId);
    				List<Map<String,Object>> airPortList = archiveManager.selectAirportByProvinceIds(map);
    				for(Map<String,Object> maps : airPortList){
    					Map<String,Object> mapItem = new HashMap<String,Object>();
    					//获取某个省份下所有的机场列表
    					List<Map<String,Object>> airPortMaps = (List<Map<String, Object>>) maps.get("airPortList");
    					for(Map<String,Object> airPortMap:airPortMaps){
    						mapItem.put("pid", areaId);
    						mapItem.put("anames", airPortMap.get("anames"));
    						mapItem.put("airportId", airPortMap.get("aids"));
    						proIdApIdList.add(mapItem);
    					}
    				}
    			}else{//该业务人员管辖下的省份所有的机场
    				Map<String,Object> map = new HashMap<String,Object>();
    				List<Map<String,Object>> airPortList = archiveManager.selectAirportByProvinceIds(map);
    				for(Map<String,Object> maps : airPortList){
    					Map<String,Object> mapItem = new HashMap<String,Object>();
    					List<Map<String,Object>> airPortMaps = (List<Map<String, Object>>) maps.get("airPortList");
    					for(Map<String,Object> airPortMap:airPortMaps){
    						mapItem.put("pid", maps.get("pid"));
    						mapItem.put("anames", airPortMap.get("anames"));
    						mapItem.put("airportId", airPortMap.get("aids"));
    						proIdApIdList.add(mapItem);
    					}
    	    			
    				}
    			}
    		}
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
    
    /**
     * 销售榜单分页&TOP10
     * @param uno
     * @param pid
     * @param begin
     * @param end
     * @param airportId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value="salesRanking")
    @ResponseBody
    public BaseMsgInfo salesRanking(@RequestParam(value="uno", required = false) String uno,
    		@RequestParam(value="pid", required = false) String pid,
    		@RequestParam(value="begin") String begin,
    		@RequestParam(value="end") String end,
    		@RequestParam(value="airportId", required = false) String airportId,
    		@RequestParam(value="pageNo") int pageNo,
    		@RequestParam(value="pageSize") int pageSize){
    	List <SalesRankingDto> result = statManager.salesRanking(uno, pid, airportId, begin, end, pageNo, pageSize);
    	return BaseMsgInfo.success(result);
    }
    
    /**
     * 个人中心销售榜单获取接口
     * @param uno 用户编号
     * @param begin 开始时间
     * @param end 结束时间
     * @return
     */
    @RequestMapping(value="sticSaleCardByUserId")
    @ResponseBody
    public BaseMsgInfo sticSaleCardByUserId(@RequestParam(value="uno",required=true) String uno,
    		@RequestParam(value="begin",required=true) String begin,
    		@RequestParam(value="end",required=true) String end){
    		Map<String,Object> paraMap = new HashMap<String,Object>();
    		paraMap.put("saleNo", uno);
    		paraMap.put("beginTime", begin);
    		paraMap.put("endTime", end);
    		List<AppOrderArchive> listBycustomer = statManager.selectCharDataByUserId(paraMap);
    		return BaseMsgInfo.success(listBycustomer);
    }
    
    
}


