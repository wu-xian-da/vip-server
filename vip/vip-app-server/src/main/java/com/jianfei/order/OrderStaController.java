package com.jianfei.order;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppOrderArchive;
import com.jianfei.core.bean.AriPort;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.dto.*;
import com.jianfei.core.service.base.AriPortManager;
import com.jianfei.core.service.base.impl.BusizzManagerImpl;
import com.jianfei.core.service.stat.ArchiveManager;
import com.jianfei.core.service.stat.impl.StatManagerImpl;
import com.jianfei.core.service.user.impl.SaleUserManagerImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

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
    private AriPortManager ariPortService;
    @Autowired
    private ArchiveManager archiveManager;

    @Autowired
	private BusizzManagerImpl busizzManagerImpl; 
    @Autowired
    private SaleUserManagerImpl saleUserManagerImpl;

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
	 * 分页获取
	 * @param pageDto
	 * @param uno
	 * @return
	 */
	@RequestMapping(value = "/pageSaleVipCard")
	@ResponseBody
	public BaseMsgInfo pageSaleVipCard(PageDto pageDto, @RequestParam(value = "uno", required = true) String uno
	) {
		PageInfo<GraphDto> pageInfo= statManager.pageOrderStatByUserUno(pageDto,uno);
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
     * 根据用户工号查询所拥有省份的机场
     * @param provinceId
     * @return
     */
    @RequestMapping("getAriPortListByUserNo")
    @ResponseBody
    public BaseMsgInfo getAriPortListByUserNo(@RequestParam(value="userNo",required=true)String userNo){
    	try {
    		Map<String,Object> map = new HashMap<String,Object>();
    		map.put("code", userNo);
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
	 * 
	 * @param uno
	 *            用户工号
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return
	 */
	@RequestMapping(value = "getSaleCurveByUserId")
	@ResponseBody
	public BaseMsgInfo getSaleCurveByUserId(@RequestParam(value = "uno", required = true) String uno,
			@RequestParam(value = "begin", required = true) String begin,
			@RequestParam(value = "end", required = true) String end) {
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			
			User user = saleUserManagerImpl.getSaleUserDetail(uno);
			//根据用户编号获取用户id
			long  userId = user.getId();
			
			// 1、从归档表中查询该业务员某个时间段内的销售业绩
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("saleNo", userId);
			paraMap.put("beginTime", begin);
			paraMap.put("endTime", end);
			// 1.1业务人员某个时间段内每天的开卡数量、退卡数量
			List<CharData> listBycustomer = statManager.selectCharDataByUserId(paraMap);
			
			// 2、业务人员所属省份该时间段内的平均开卡人数
			// 2.1根据销售人员id获取该用户所属的省份id
			List<UserProvince> userProvinceList = busizzManagerImpl.getProvinceIdByUserId(userId+"");
			List<Map<String, Object>> provinceList = statManager.getSaleCurveByUserId(userProvinceList, begin, end);

			// 3将两个list合并为一个list
			if (listBycustomer != null && listBycustomer.size() > 0) {
				for (Map<String, Object> map : provinceList) {
					String dateStr = (String) map.get("date");
					int listBycustomerSize = listBycustomer.size();
					int flag = 0;// 是否有该天的数据 0 表示没有
					for (int index = 0; index < listBycustomerSize; index++) {
						CharData charData = listBycustomer.get(index);
						// 归档日期
						String fileTime = formatDate(charData.getDate());
						if (fileTime.equals(dateStr)) {
							map.put("total", charData.getTotal() ==null ? "0" : charData.getTotal());
							map.put("back_total", charData.getBack_order_total() == null ? "0" : charData.getBack_order_total());
							flag = 1;
							break;
						}
					}

					if (flag == 0) {
						map.put("total", "0");
						map.put("back_total","0");
					}
				}
				resMap.put("cardNumList", provinceList);
			} else {
				for (Map<String, Object> map : provinceList) {
					map.put("total", "0");
					map.put("back_total","0");
				}
				resMap.put("cardNumList", provinceList);
			}
			
			//4 根据 listBycustomer计算业务员某个月份总的开卡总数和退款总数
			Map<String,Object> totalCardNumMap = new HashMap<String,Object>();
			if (listBycustomer != null && listBycustomer.size() > 0) {
				float saleCardNumTotal = 0;
				float backCardNumTotal = 0;
				for(CharData charData : listBycustomer){
					saleCardNumTotal += Float.parseFloat(charData.getTotal() == null ? "0" : charData.getTotal());
					backCardNumTotal += Float.parseFloat(charData.getBack_order_total() == null ? "0" : charData.getBack_order_total());
				}
				totalCardNumMap.put("saleCardNumTotal", saleCardNumTotal);
				totalCardNumMap.put("backCardNumTotal", backCardNumTotal);
			}else{
				totalCardNumMap.put("saleCardNumTotal", "0");
				totalCardNumMap.put("backCardNumTotal", "0");
			}
			resMap.put("total", totalCardNumMap);
			
			return BaseMsgInfo.success(resMap);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseMsgInfo().setCode(-1).setMsg("查询失败");
		}
	}

	/**
	 * 销售榜单-详细图表接口
	 * 
	 * @param uno
	 * @param areaId
	 * @param begin
	 * @param end
	 * @param airportId
	 * @return
	 */
	@RequestMapping(value = "getSticCardData")
	@ResponseBody
	public BaseMsgInfo getSticCardData(@RequestParam(value = "uno", required = true) String uno,
			@RequestParam(value = "areaId", defaultValue = "", required = false) String areaId,
			@RequestParam(value = "begin", required = true) String begin,
			@RequestParam(value = "end", required = true) String end,
			@RequestParam(value = "airportId", required = false, defaultValue = "") String airportId) {

		List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
		try {
			// 1 构建一个【省份id+机场id】列表
			List<Map<String, Object>> proIdApIdList = new ArrayList<Map<String, Object>>();
			// 具体某个场站
			if (!airportId.equals("")) {
				Map<String, Object> mapItem = new HashMap<String, Object>();
				mapItem.put("pid", areaId);
				//**根据场站id获取场站名称
				AriPort ariPort = ariPortService.selectAirPortInfoById(airportId);
				mapItem.put("anames", ariPort.getName());
				mapItem.put("airportId", airportId);
				proIdApIdList.add(mapItem);

			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("code", uno);
				if (!areaId.equals("")) {// 某个省下的所有的机场
					map.put("cid", areaId);
				}
				List<Map<String, Object>> airPortList = archiveManager.selectAirportByProvinceIds(map);
				for (Map<String, Object> maps : airPortList) {
					List<Map<String, Object>> airPortMaps = (List<Map<String, Object>>) maps.get("airPortList");
					for (Map<String, Object> airPortMap : airPortMaps) {
						Map<String, Object> mapItem = new HashMap<String, Object>();
						mapItem.put("pid", maps.get("pid"));
						mapItem.put("anames", airPortMap.get("anames"));
						mapItem.put("airportId", airPortMap.get("aids"));
						proIdApIdList.add(mapItem);
					}
				}
			}

			// 2 ****** x轴：日期 y轴：当日所有场站的开卡总和
			Map<String, Object> repMap = statManager.returnCardNumByDate(proIdApIdList, begin, end);
			resList.add(repMap);
			// 3 ****** x轴：场站名称  y轴：该场站在所选时间段内所有的开卡总数
			List<Map<String, Object>> cardNumByAirPortList = statManager.getSticCardData(proIdApIdList, begin, end);
			Map<String, Object> airPortMap = new HashMap<String, Object>();
			airPortMap.put("cardNumByAirPort", cardNumByAirPortList);
			resList.add(airPortMap);

			return BaseMsgInfo.success(resList);
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
    		List<CharData> listBycustomer = statManager.selectCharDataByUserId(paraMap);
    		return BaseMsgInfo.success(listBycustomer);
    }
    
    /**
	 * 格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public String formatDate(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(date);
	}


	/**
	 * 个人中心-我的订单统计数据
	 * @param uno 用户工号
	 * @return
	 */
	@RequestMapping(value="sticSaleDataByUno")
	@ResponseBody
	public BaseMsgInfo sticSaleDataByUno(@RequestParam(value="uno",required=true) String uno){

		return BaseMsgInfo.success(statManager.getSaleToDoData(uno));
	}

	/**
	 * 个人中心-我的订单分页数据
	 * @param uno 用户工号
	 * @return
	 */
	@RequestMapping(value="pageOrderInfoBySale")
	@ResponseBody
	public BaseMsgInfo pageOrderByUno(@RequestParam(value="uno",required=true) String uno,@RequestParam(value="state",required=true) String orderState,
									  @RequestParam(value="pageNo") int pageNo,
									  @RequestParam(value="pageSize") int pageSize,
									  @RequestParam(value="key",required=true) String key){
		return BaseMsgInfo.success(statManager.pageOrderInfoBySale(uno,orderState,pageNo,pageSize,key));
	}

}


