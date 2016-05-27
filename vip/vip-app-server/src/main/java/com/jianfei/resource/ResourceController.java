package com.jianfei.resource;

import java.util.ArrayList;
import java.util.List;

import com.jianfei.core.service.base.impl.AppPictureManagerImpl;
import com.jianfei.core.service.base.impl.AriPortManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jianfei.common.BaseMsgInfo;
import com.jianfei.core.bean.AppPicture;
import com.jianfei.core.bean.SysAirport;
import com.jianfei.core.bean.SysViproom;
import com.jianfei.core.common.enu.PictureType;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.service.base.impl.VipRoomManagerImpl;
import com.jianfei.dto.VipCardInfoVo;
import com.jianfei.dto.VipRightInfoVo;

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
public class ResourceController  {

	@Autowired
	private AriPortManagerImpl ariPortService;

	@Autowired
	private VipRoomManagerImpl vipRoomManager;

	@Autowired
	private AppPictureManagerImpl appPictureService;
	/**
	 * 获取支持的省份列表
	 * @param
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
	 * @return VipRoomList
	 */
	@RequestMapping(value = "/getVipRoomInfo", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo vipRoomInfo(@RequestParam(value = "vipRoomId", required = true) String vipRoomId
	) {
		 SysViproom vipRoom=vipRoomManager.getVipRoomInfo(vipRoomId);
  		 return BaseMsgInfo.success(vipRoom);
	}


	/**
	 * VIPAPP首页轮播图
	 */
	@RequestMapping(value = "/getVipHomeImages", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo vipHomeImages() {
		List<AppPicture> list=appPictureService.getPicture(PictureType.VIP_APP_HOME);
		return BaseMsgInfo.success(list);
	}

	/**
	 * VIPAPP首页模块信息
	 */
	@RequestMapping(value = "/getVipHomeModules", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo vipHomeModules() {
		List<AppPicture> list=appPictureService.getPicture(PictureType.VIP_APP_HOME_MODULE);
		return BaseMsgInfo.success(list);
	}

	/**
	 * 销售 APP首页模块信息
	 */
	@RequestMapping(value = "/getSaleHomeImages", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo saleHomeModules() {
		List<AppPicture> list=appPictureService.getPicture(PictureType.SALE_APP_HOME);
		return BaseMsgInfo.success(list);
	}

	/**
	 * VIP卡权益信息
	 */
	@RequestMapping(value = "/getVipCardRight", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo getVipCardRight() {
		List<AppPicture> list=appPictureService.getPicture(PictureType.VIP_APP_HOME);
		List<VipRightInfoVo> vipRightInfoVos=new ArrayList<>();
		vipRightInfoVos.add(new VipRightInfoVo(1,"1.Vip室使用权益","可能因座位限制入内"));
		vipRightInfoVos.add(new VipRightInfoVo(2,"2.全年保险","可能因座位限制入内"));
		vipRightInfoVos.add(new VipRightInfoVo(3,"3.赠送哈哈哈","可能因座位限制入内"));
		VipCardInfoVo vipCardInfoVo=new VipCardInfoVo();
		vipCardInfoVo.setRight(vipRightInfoVos);
		vipCardInfoVo.setImages(list);
		return BaseMsgInfo.success(vipCardInfoVo);
	}

}