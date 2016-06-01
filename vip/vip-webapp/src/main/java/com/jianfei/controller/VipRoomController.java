/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月18日-上午10:09:28
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.bean.AriPort;
import com.jianfei.core.bean.SysViproom;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.service.base.AriPortManager;
import com.jianfei.core.service.base.impl.AriPortManagerImpl;
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
	private AriPortManagerImpl ariPortManagerImpl;
	
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
		//返回所有的场站信息
		MessageDto<List<AriPort>> list= ariPortService.get(null);
		Map<String,Object> reqMap = new HashMap<String,Object>();
		reqMap.put("pid", 0);
		List<Map<String,Object>> provinceList = ariPortManagerImpl.selectCityById(reqMap);
		//List<AriPort> airportList = list.getData();
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
	public String addVipRoom(@RequestParam( value = "file", required = false) MultipartFile file,HttpServletRequest request,SysViproom room){
		//文件上传
        String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = file.getOriginalFilename(); 
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        System.out.println("文件的相对路径："+request.getContextPath()+"/upload/"+fileName);
		room.setViproomId(UUID.randomUUID().toString());
		room.setDtflag(0);//0表示不删除
		vipRoomManagerImp.addVipRoom(room);
		//保存图片url
		
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
		System.out.println("viproomId="+viproomId);
		//返回所有的场站信息
		MessageDto<List<AriPort>> list= ariPortService.get(null);
		List<AriPort> airportList = list.getData();
		model.addAttribute("airportList", airportList);
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
	public String editVipRoomById(SysViproom room){
		room.setDtflag(0);
		vipRoomManagerImp.updateVipRoom(room);
		return "redirect:gotoVipRoomView";
	}
	
	/**
	 * 根据省id获取所有的场站列表
	 * @param provinceId
	 * @return
	 */
	@RequestMapping("getAirPortList")
	@ResponseBody
	public List<AriPort> getAirPortList(@RequestParam(value="provinceId") String provinceId){
		Map<String,Object> reqMap = new HashMap<String,Object>();
		reqMap.put("province", provinceId);
		MessageDto<List<AriPort>> list= ariPortService.get(reqMap);
		List<AriPort> airportList = list.getData();
		return airportList;
	}
}
