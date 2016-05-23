package com.jianfei.resource;

import com.jianfei.common.BaseController;
import com.jianfei.core.service.base.impl.AriPortServiceImpl;
import com.jianfei.dto.AirportVo;
import com.jianfei.dto.VipRoomVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源查询接口
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016年5月11日 上午11:19:41
 * 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping(value = "resource")
public class ResourceController extends BaseController {

	@Autowired
	private AriPortServiceImpl ariPortService;
	/**
	 * 获取支持的省份列表
	 * @param
	 * @return List<AirportVo>
	 */
	@RequestMapping(value = "getAirportProvince", method = RequestMethod.GET)
	@ResponseBody
	public List<String> airportProvince(	) {
		return ariPortService.getAriPortProvince();
	}


	/**
	 * 根据省份ID获取机场列表
	 * @param provinceId 省份ID
	 * @return List<AirportVo>
     */
	@RequestMapping(value = "/getAirportList", method = RequestMethod.POST)
	@ResponseBody
	public List<AirportVo> airportList(	@RequestParam(value = "provinceId", required = false) String provinceId ) {
		//TODO 根据省份ID查询机场信息 如果省份ID为空 查询所有机场List
		List<AirportVo> list=new ArrayList<>();
		list.add(new AirportVo("1","首都机场"));
		return list;
	}

	/**
	 * 根据用户坐标或省份或机场信息获取VipRoom列表
	 * @param provinceId 省份ID
	 * @param airportId 机场ID
	 * @param airportName 机场名称 模糊搜索
	 * @param coordinate 坐标
	 * @return VipRoomList
	 */
	@RequestMapping(value = "/getVipRoomList", method = RequestMethod.POST)
	@ResponseBody
	public List<AirportVo> vipRoomList(@RequestParam(value = "provinceId", required = false) String provinceId,
									   @RequestParam(value = "airportId", required = false) String airportId,
									   @RequestParam(value = "airportName", required = false) String airportName,
									   @RequestParam(value = "coordinate", required = false) String coordinate
	) {
		//TODO 首先根据坐标查城市 转换坐标
		List<AirportVo> list=new ArrayList<>();
		list.add(new AirportVo());
		return list;
	}

	/**
	 * 根据用户坐标或省份或机场信息获取VipRoom列表
	 * @param vipRoomId vip室信息
	 * @param coordinate 坐标
	 * @return VipRoomList
	 */
	@RequestMapping(value = "/getVipRoomInfo", method = RequestMethod.POST)
	@ResponseBody
	public VipRoomVo vipRoomInfo(@RequestParam(value = "vipRoomId", required = false) String vipRoomId,
								 @RequestParam(value = "coordinate", required = false) String coordinate
	) {
		//TODO 首先根据坐标查 转换坐标
		List<AirportVo> list=new ArrayList<>();
		list.add(new AirportVo());
		return new VipRoomVo();
	}

}
