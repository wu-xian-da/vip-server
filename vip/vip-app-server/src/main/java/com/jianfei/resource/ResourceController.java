package com.jianfei.resource;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppPicture;
import com.jianfei.core.bean.AppVersion;
import com.jianfei.core.bean.SysAirport;
import com.jianfei.core.bean.SysViproom;
import com.jianfei.core.common.enu.PictureType;
import com.jianfei.core.common.enu.RightType;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.common.utils.UUIDUtils;
import com.jianfei.core.dto.BaseDto;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.service.base.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
public class ResourceController  {
	private static Log log = LogFactory.getLog(ResourceController.class);
	@Autowired
	private AriPortManager ariPortService;

	@Autowired
	private VipRoomManager vipRoomManager;

	@Autowired
	private AppPictureManager appPictureService;

	@Autowired
	private AppVersionManager appVersionManager;
	@Autowired
	private AppConfigManager appConfigManager;
	/**
	 * 获取支持的省份列表
	 * @param
	 */
	@RequestMapping(value = "/getAirportProvince", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo airportProvince(	) {
		try {
			List<BaseDto> stringList=ariPortService.getAriPortProvince();
			stringList.add(0,new BaseDto("","所有省份"));
			return BaseMsgInfo.success(stringList);
		}catch (Exception e){
			log.error("获取支持的省份列表失败",e);
			return BaseMsgInfo.msgFail("获取支持的省份列表失败");
		}
	}

	/**
	 * 根据省份ID获取机场列表
	 * @param province 省份
     */
	@RequestMapping(value = "/getAirportList", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo airportList(	@RequestParam(value = "province", required = false) String province ) {
		try {
			List<SysAirport> list=ariPortService.getAirPortByProvince(province);
			return BaseMsgInfo.success(list);
		}catch (Exception e){
			log.error("根据省份ID获取机场列表失败",e);
			return BaseMsgInfo.msgFail("根据省份ID获取机场列表失败");
		}

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
		try {
			PageInfo<SysViproom> pageInfo = vipRoomManager.pageVipRoom(pageDto, airport, coordinate);
			return BaseMsgInfo.success(pageInfo);
		}catch (Exception e){
			log.error("根据用户坐标或省份或机场信息获取VipRoom列表失败",e);
			return BaseMsgInfo.msgFail("获取Vip室列表失败");
		}
	}

	/**
	 * 根据VIP室ID获取VIP室详细信息
	 * @param vipRoomId vip室信息
	 * @return VipRoomList
	 */
	@RequestMapping(value = "/getVipRoomInfo", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo vipRoomInfo(@RequestParam(value = "vipRoomId", required = true) String vipRoomId
	) {
		try {
			SysViproom vipRoom=vipRoomManager.getVipRoomInfo(vipRoomId);
			return BaseMsgInfo.success(vipRoom);
		}catch (Exception e){
			log.error("根据VIP室ID获取VIP室详细信息失败",e);
			return BaseMsgInfo.msgFail("获取VIP室详细信息失败");
		}
	}


	/**
	 * VIPAPP首页轮播图
	 */
	@RequestMapping(value = "/getVipHomeImages", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo vipHomeImages() {
		try {
			List<AppPicture> list=appPictureService.getPicture(PictureType.VIP_APP_HOME);
			return BaseMsgInfo.success(list);
		}catch (Exception e){
			log.error("获取VIP—APP首页轮播图失败",e);
			return BaseMsgInfo.msgFail("获取APP首页轮播图失败");
		}
	}

	/**
	 * VIPAPP首页模块信息
	 */
	@RequestMapping(value = "/getVipHomeModules", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo vipHomeModules() {
		try {
			List<AppPicture> list=appPictureService.getPicture(PictureType.VIP_APP_HOME_MODULE);
			return BaseMsgInfo.success(list);
		}catch (Exception e){
			log.error("获取VIP—APP首页模块信息失败",e);
			return BaseMsgInfo.msgFail("获取APP首页模块信息失败");
		}
	}

	/**
	 * 销售 APP首页轮播图
	 */
	@RequestMapping(value = "/getSaleHomeImages", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo saleHomeModules() {
		try {
			List<AppPicture> list=appPictureService.getPicture(PictureType.SALE_APP_HOME);
			return BaseMsgInfo.success(list);
		}catch (Exception e){
			log.error("销售 APP首页轮播图失败",e);
			return BaseMsgInfo.msgFail("获取APP首页轮播图失败");
		}
	}

	/**
	 * 销售VIP卡权益信息
	 */
	@RequestMapping(value = "/saleVipCardRight", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo getVipCardRight() {
		try {
			return BaseMsgInfo.success(appConfigManager.getVipCardInfo());
		}catch (Exception e){
			log.error("获取VIP卡权益信息失败",e);
			return BaseMsgInfo.msgFail("获取VIP卡权益信息失败");
		}
	}

	/**
	 * VIP卡权益信息
	 */
	@RequestMapping(value = "/getVipCardRight", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo getVipCardRight(@RequestParam(value = "phone", required = false) String phone) {
		try {
			return BaseMsgInfo.success(appConfigManager.getVipCardInfo(phone));
		}catch (Exception e){
			log.error("获取VIP卡权益信息失败",e);
			return BaseMsgInfo.msgFail("获取VIP卡权益信息失败");
		}
	}

	/**
	 * VIP卡常见问题
	 */
	@RequestMapping(value = "/getVipCardQA", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo getVipCardQA() {
		try {
			return BaseMsgInfo.success(appConfigManager.getAppConfig(RightType.PROBLEM));
		}catch (Exception e){
			log.error("获取VIP卡权益信息失败",e);
			return BaseMsgInfo.msgFail("获取VIP卡权益信息失败");
		}
	}

	/**
	 *  APP版本更新接口
	 */
	@RequestMapping(value = "/lastAppVersion", method = RequestMethod.GET)
	@ResponseBody
	public BaseMsgInfo getLastAppVersion(@RequestParam(value = "channel", required = true) String channel) {
		try {
			AppVersion appVersion=appVersionManager.getLastVersion(channel);
			return BaseMsgInfo.success(appVersion);
		}catch (Exception e){
			log.error("APP版本更新接口",e);
			return BaseMsgInfo.msgFail("获取APP版本失败");
		}
	}

	/**
	 *  APP版本修改接口
	 *  发布后删除
	 */
	@RequestMapping(value = "/appVersion")
	@ResponseBody
	public BaseMsgInfo updateAppVersion(AppVersion version) {
		try {
			return appVersionManager.updateVersion(version);
		}catch (Exception e){
			log.error("修改APP版本接口失败",e);
			return BaseMsgInfo.msgFail("修改APP版本失败");
		}
	}

	/**
	 * 上传图片
	 * @param file
	 * @return
     */
	@RequestMapping(value = "/photoUpdate")
	@ResponseBody
	public BaseMsgInfo salePhotoUpdate(@RequestParam(value = "file", required = false) MultipartFile file) {
		if (!file.isEmpty()) {
			String path = GloabConfig.getInstance().getConfig("upload.home.dir") + "/salesPhoto";
			String fileName = file.getOriginalFilename();
			String newFileName = UUIDUtils.returnNewFileName(fileName);
			File targetFile = new File(path, newFileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}

			try {
				file.transferTo(targetFile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return BaseMsgInfo.success( "/salesPhoto/" + newFileName);

		} else {
			return  BaseMsgInfo.fail("失败",false);
		}
	}

}
