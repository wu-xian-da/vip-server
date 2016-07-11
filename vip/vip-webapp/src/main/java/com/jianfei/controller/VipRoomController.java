/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月18日-上午10:09:28
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppPicture;
import com.jianfei.core.bean.SysViproom;
import com.jianfei.core.common.enu.DtFlagType;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.PluploadUtil;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.common.utils.UUIDUtils;
import com.jianfei.core.service.base.AriPortManager;
import com.jianfei.core.service.base.impl.AppPictureManagerImpl;
import com.jianfei.core.service.base.impl.VipRoomManagerImpl;
import com.jianfei.core.bean.Plupload;
/**
 *
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com
 * @date: 2016年5月18日 上午10:09:28
 * 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("viproom")
public class VipRoomController extends BaseController {
	//获取静态服务器的url
	private static String path=  GloabConfig.getInstance().getConfig("upload.home.dir");
	//vip室图片上传的文件夹名 
	public static final String FileDir = "/viproomPhoto"; 
	
	@Autowired
	private VipRoomManagerImpl vipRoomManagerImp;
	@Autowired
	private AriPortManager ariPortService;
	@Autowired
	private AppPictureManagerImpl appPictureManager;

	
	/**
	 * 跳转到vip室管理页面
	 * @return
	 */
	@RequestMapping("/gotoVipRoomView")
	public String test() {
		return "viproom/vipRoomManagement";
	}

	/**
	 * 分页显示vip室信息 showVipRoomList
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return Grid
	 * @version 1.0.0
	 */
	@RequestMapping("showVipRoomList")
	@ResponseBody
	public Grid showVipRoomList(@RequestParam(value = "page", defaultValue = "1") Integer pageNo,
			@RequestParam(value = "rows", defaultValue = "20") Integer pageSize, HttpServletRequest request) {
		// 查询条件
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "_");
		PageInfo<SysViproom> page = vipRoomManagerImp.simplePage(pageNo, pageSize, searchParams);
		intiWebContentEnv();
		return vipRoomManagerImp.bindVipRoomGridData(page);
	}

	/**
	 * 逻辑删除vip室信息 delVipRoomById
	 * 
	 * @param viproom
	 * @return MessageDto<AppVipcard>
	 * @version 1.0.0
	 */
	@RequestMapping(value = "delVipRoomById", method = RequestMethod.POST)
	@ResponseBody
	public MessageDto<SysViproom> delVipRoomById(SysViproom viproom) {
		vipRoomManagerImp.delVipRoom(viproom.getViproomId());
		return new MessageDto<SysViproom>().setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}
	
	/**
	 * 启用vip室功能
	 * @param viproom
	 * @return
	 */
	@RequestMapping(value="startUsingVipRoomById",method = RequestMethod.POST)
	@ResponseBody
	public MessageDto<SysViproom> startUsingVipRoomById(SysViproom viproom) {

		vipRoomManagerImp.startUsingByVipRommId(viproom.getViproomId());
		return new MessageDto<SysViproom>().setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}
	
	/**
	 * 禁用vip室功能
	 * @param viproom
	 * @return
	 */
	@RequestMapping(value="forbiddenUsingVipRoomById",method = RequestMethod.POST)
	@ResponseBody
	public MessageDto<SysViproom> forbiddenUsingVipRoomById(SysViproom viproom) {
		vipRoomManagerImp.forbideenUsingByVipRommId(viproom.getViproomId());
		return new MessageDto<SysViproom>().setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}
	
	/**
	 * 跳转到vip室添加页面 gotoAddVipRoomView
	 * 
	 * @return String
	 * @version 1.0.0
	 */
	@RequestMapping("gotoAddVipRoomView")
	public String gotoAddVipRoomView(Model model) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("pid", 0);
		List<Map<String, Object>> provinceList = ariPortService.selectCityById(reqMap);
		model.addAttribute("provinceList", provinceList);
		return "viproom/addVipRoom";
	}
	
	
	/**
	 * vip室多图片上传
	 * @param plupload
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "dumifileupload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> dumifileupload(Plupload plupload, HttpServletRequest request, HttpServletResponse response) {
		String newFileName = "";
		plupload.setRequest(request);
		// 文件存储路径
		File dir = new File(path + FileDir);
		try {
			// 上传文件
			newFileName = PluploadUtil.upload(plupload, dir);
			// 判断文件是否上传成功（被分成块的文件是否全部上传完成）
			if (PluploadUtil.isUploadFinish(plupload)) {
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,Object> respMap = new HashMap<String,Object>();
	    respMap.put("newFileName", newFileName);
	    return respMap;
	}
	
	/**
	 * 添加vip信息 addVipRoom
	 * 
	 * @return String
	 * @version 1.0.0
	 */
	@RequestMapping(value = "addVipRoomInfo", method = RequestMethod.POST)
	public Map<String,Object> addVipRoom(SysViproom room) {
		//1、生成唯一的vip室编号
		String viproomId = UUIDUtils.getPrimaryKey();
		
		//2、保存图片url
		String[] newFileUrls = room.getNewFileUrl();
		for(String newFileUrl : newFileUrls){
			AppPicture appPicture = new AppPicture();
			appPicture.setViproomId(viproomId);
			appPicture.setPictureUrl(FileDir+"/"+newFileUrl);
			appPicture.setDtflag(DtFlagType.NOT_DELETE.getName());
			appPictureManager.save(appPicture);
		
		}
		//3、保存vip室信息
		room.setViproomId(viproomId);
		room.setDtflag(DtFlagType.NOT_DELETE.getName());
		room.setCreateTime(new Date());
		vipRoomManagerImp.addVipRoom(room);
		
		Map<String,Object> repMap = new HashMap<String,Object>();
		repMap.put("result", 1);
		return repMap;
	}

	/**
	 * 跳转到vip室编辑页面 gotoUpdateVipRoomView
	 * 
	 * @param model
	 * @return String
	 * @version 1.0.0
	 */
	@RequestMapping("gotoUpdateVipRoomView")
	public String gotoUpdateVipRoomView(@RequestParam(value = "viproomId") String viproomId, Model model) {
		//1、返回所有的省列表
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("pid", 0);
		List<Map<String, Object>> provinceList = ariPortService.selectCityById(reqMap);
		model.addAttribute("provinceList", provinceList);
		
		//2、根据vip室编号返回vip室信息
		SysViproom viproom = vipRoomManagerImp.selVipRoomById(viproomId);
		List<AppPicture> pictureList = viproom.getPictures();
		AppPicture.getStaticAdderss(pictureList);
		model.addAttribute("viproom", viproom);
		model.addAttribute("pictureList", pictureList);
		
		//3、根据vip所属省份id返回该省份下机场列表
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("province", viproom.getProvince());
		List<Map<String, Object>> airPortlist = ariPortService.mapList(paramMap);
		model.addAttribute("airPortlist",airPortlist);
		return "viproom/editVipRoom";
	}
	
	/**
	 * 根据图片id删除vip室图片信息
	 * @param pictureId
	 * @return
	 */
	@RequestMapping("delVipPhotoByPhotoId")
	@ResponseBody
	public Map<String,Object> delVipPhotoByPhotoId(@RequestParam(value="pictureId",required=true)String pictureId){
		MessageDto<String> messageDto = appPictureManager.deleteByPrimaryKey(Integer.parseInt(pictureId));
		Map<String,Object> repMap = new HashMap<String,Object>();
		if(messageDto.isOk()){
			repMap.put("status", "1");
		}else{
			repMap.put("status", "0");
		}
		return repMap;
	} 
	
	/**
	 * 编辑vip室信息 editVipRoomById
	 * 
	 * @param room
	 * @return String
	 * @version 1.0.0
	 */
	@RequestMapping(value = "editVipRoomInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> editVipRoomById(SysViproom room) {
		// 更新vip室信息
		room.setDtflag(DtFlagType.NOT_DELETE.getName());
		room.setCreateTime(new Date());
		vipRoomManagerImp.updateVipRoom(room);
		//添加图片信息
		String[] newFileUrls = room.getNewFileUrl();
		if(newFileUrls != null && newFileUrls.length >0){
			for(String newFileUrl : newFileUrls){
				AppPicture appPicture = new AppPicture();
				appPicture.setViproomId(room.getViproomId());
				appPicture.setPictureUrl(FileDir+"/"+newFileUrl);
				appPicture.setDtflag(DtFlagType.NOT_DELETE.getName());
				appPictureManager.save(appPicture);
			}
		}
		
		Map<String,Object> repMap = new HashMap<String,Object>();
		repMap.put("result", "1");
		return repMap;
	}

	/**
	 * 根据省id获取所有的场站列表
	 * 
	 * @param provinceId
	 * @return
	 */
	@RequestMapping("getAirPortList")
	@ResponseBody
	public List<Map<String, Object>> getAirPortList(@RequestParam(value = "provinceId") String provinceId) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("province", provinceId);
		reqMap.put("state", "0");
		List<Map<String, Object>> list = ariPortService.mapList(reqMap);
		return list;
	}
}
