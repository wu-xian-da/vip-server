/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月18日-上午10:09:28
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.bean.AriPort;
import com.jianfei.core.bean.SysViproom;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.UUIDUtils;
import com.jianfei.core.service.base.AppPictureManager;
import com.jianfei.core.service.base.AriPortManager;
import com.jianfei.core.service.base.impl.AppPictureManagerImpl;
import com.jianfei.core.service.base.impl.VipRoomManagerImpl;

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
	@Autowired
	private VipRoomManagerImpl vipRoomManagerImp;
	@Autowired
	private AriPortManager ariPortService;
	@Autowired
	private AppPictureManager appPictureManager;
	
	@RequestMapping("/gotoVipRoomView")
	public String test(){
		return "viproom/vipRoomManagement";
	}
	
	
	/**
	 * 分页显示vip室信息
	 * showVipRoomList
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 *Grid
	 * @version  1.0.0
	 */
	@RequestMapping("showVipRoomList")
	@ResponseBody
	public Grid showVipRoomList(@RequestParam(value = "page", defaultValue = "1") Integer pageNo,
			@RequestParam(value = "rows", defaultValue = "10") Integer pageSize,
			HttpServletRequest request){
		//查询条件
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(
				request, "_");
		PageInfo<SysViproom> page =  vipRoomManagerImp.simplePage(pageNo, pageSize, searchParams);
		intiWebContentEnv();
		return vipRoomManagerImp.bindVipRoomGridData(page);
	}
	
	/**
	 * 逻辑删除vip室信息
	 * delVipRoomById
	 * @param viproom
	 * @return
	 * MessageDto<AppVipcard>
	 * @version  1.0.0
	 */
	@RequestMapping(value="delVipRoomById",method=RequestMethod.POST)
	@ResponseBody
	public  MessageDto<AppVipcard> delVipRoomById(SysViproom viproom){
		
		vipRoomManagerImp.delVipRoom(viproom.getViproomId());
		return new MessageDto<AppVipcard>().setOk(true).setMsgBody(
				MessageDto.MsgFlag.SUCCESS);
	}
	
	/**
	 * 跳转到vip室添加页面
	 * gotoAddVipRoomView
	 * @return
	 * String
	 * @version  1.0.0
	 */
	@RequestMapping("gotoAddVipRoomView")
	public String gotoAddVipRoomView(Model model){
		Map<String,Object> reqMap = new HashMap<String,Object>();
		reqMap.put("pid", 0);
		List<Map<String,Object>> provinceList = ariPortService.selectCityById(reqMap);
		model.addAttribute("provinceList", provinceList);
		return "viproom/addVipRoom";
	}
	
	/**
	 * 添加vip信息
	 * addVipRoom
	 * @return
	 * String
	 * @version  1.0.0
	 */
	@RequestMapping(value="addVipRoomInfo",method=RequestMethod.POST)
	public String addVipRoom(@RequestParam( value = "file", required = false) MultipartFile file,SysViproom room){
		//文件上传
        String path =  GloabConfig.getInstance().getConfig("upload.home.dir")+"//viproomPhoto";  
        System.out.println("path="+path);
        String fileName = file.getOriginalFilename();
        String newFileName = UUIDUtils.returnNewFileName(fileName);
        File targetFile = new File(path, newFileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        String relativePath = "/viproomPhoto/"+newFileName;
        String viproomId = UUIDUtils.getPrimaryKey();
		room.setViproomId(viproomId);
		room.setDtflag(0);//0表示不删除
		vipRoomManagerImp.addVipRoom(room);
		//保存图片url
		AppPicture appPicture =  new AppPicture();
		appPicture.setViproomId(viproomId);
		appPicture.setPictureUrl(relativePath);
		appPictureManager.save(appPicture);
		
		return "redirect:gotoVipRoomView";
	}
	
	/**
	 * 跳转到vip室编辑页面
	 * gotoUpdateVipRoomView
	 * @param model
	 * @return
	 * String
	 * @version  1.0.0
	 */
	@RequestMapping("gotoUpdateVipRoomView")
	public String gotoUpdateVipRoomView(@RequestParam(value="viproomId") String viproomId,Model model){
		//返回所有的省列表
		Map<String,Object> reqMap = new HashMap<String,Object>();
		reqMap.put("pid", 0);
		List<Map<String,Object>> provinceList = ariPortService.selectCityById(reqMap);
		model.addAttribute("provinceList", provinceList);
		//根据vip室编号返回vip室信息
		SysViproom viproom = vipRoomManagerImp.selVipRoomById(viproomId);
		model.addAttribute("viproom", viproom);
		return "viproom/editVipRoom";
	}
	
	/**
	 * 编辑vip室信息
	 * editVipRoomById
	 * @param room
	 * @return
	 * String
	 * @version  1.0.0
	 */
	@RequestMapping(value="editVipRoomInfo",method=RequestMethod.POST)
	public String editVipRoomById(@RequestParam( value = "file", required = false) MultipartFile file,SysViproom room){
		//文件上传
        String path =  GloabConfig.getInstance().getConfig("upload.home.dir")+"//viproomPhoto";  
        System.out.println("path="+path);
        String fileName = file.getOriginalFilename();
        String newFileName = UUIDUtils.returnNewFileName(fileName);
        File targetFile = new File(path, newFileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        String relativePath = "/viproomPhoto/"+newFileName;
        
		room.setDtflag(0);
		//更新vip室信息
		vipRoomManagerImp.updateVipRoom(room);
		//更新vip室图片信息
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("viproomId", room.getViproomId());
		resMap.put("pictureUrl", relativePath);
		appPictureManager.updateByVipRoomId(resMap);
		return "redirect:gotoVipRoomView";
	}
	
	/**
	 * 根据省id获取所有的场站列表
	 * @param provinceId
	 * @return
	 */
	@RequestMapping("getAirPortList")
	@ResponseBody
	public List<Map<String,Object>> getAirPortList(@RequestParam(value="provinceId") String provinceId){
		Map<String,Object> reqMap = new HashMap<String,Object>();
		reqMap.put("province", provinceId);
		List<Map<String, Object>> list= ariPortService.mapList(reqMap);
		return list;
	}
}
