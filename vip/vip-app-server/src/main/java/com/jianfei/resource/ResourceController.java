package com.jianfei.resource;

import com.github.pagehelper.PageInfo;
import com.jianfei.common.BaseController;
import com.jianfei.common.BaseMsgInfo;
import com.jianfei.core.bean.AriPort;
import com.jianfei.core.bean.SysAirport;
import com.jianfei.core.bean.SysViproom;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.base.impl.AriPortServiceImpl;
import com.jianfei.core.service.base.impl.VipRoomManager;
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

	@Autowired
	private VipRoomManager vipRoomManager;
	/**
	 * 获取支持的省份列表
	 * @param
	 * @return List<AirportVo>
	 */
	@RequestMapping(value = "getAirportProvince", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo airportProvince(	) {
		List<String> stringList=ariPortService.getAriPortProvince();
		return BaseMsgInfo.success(stringList);
	}


	/**
	 * 根据省份ID获取机场列表
	 * @param province 省份
	 * @return List<AirportVo>
     */
	@RequestMapping(value = "/getAirportList", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo airportList(	@RequestParam(value = "province", required = false) String province ) {

		List<SysAirport> list=ariPortService.getAirPortByProvince(province);
		return BaseMsgInfo.success(list);
	}

	/**
	 * 根据用户坐标或省份或机场信息获取VipRoom列表
	 *
	 * @param coordinate 坐标
	 * @return VipRoomList
	 */
	@RequestMapping(value = "/getVipRoomList")
	@ResponseBody
	public BaseMsgInfo vipRoomList(PageDto pageDto,
								   SysAirport airport, @RequestParam(value = "coordinate", required = false) String coordinate
	) {
		PageInfo<SysViproom> pageInfo = vipRoomManager.pageVipRoom(pageDto, airport, coordinate);
		return BaseMsgInfo.success(pageInfo);
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
