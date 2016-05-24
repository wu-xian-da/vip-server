package com.jianfei.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.base.impl.VipCardManagerImpl;

/**
 * 
 *
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月24日 上午6:51:25 
 * 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("vipCard")
public class VipCardController extends BaseController {
	@Autowired
	private VipCardManagerImpl vipCardManagerImpl;
	
	/**
	 * 跳转到vip卡管理页面
	 * goVipCardManageView
	 * @return
	 * String
	 * @version  1.0.0
	 */
	@RequestMapping(value="goVipCardManageView")
	public String goVipCardManageView(){
		return "vipcard/vipCardManagement";
	}
	
	/**
	 * 分页显示卡列表信息
	 * showVipCardList
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 * Grid
	 * @version  1.0.0
	 */
	@RequestMapping(value="showVipCardList",method = RequestMethod.POST)
	@ResponseBody
	public Grid showVipCardList(@RequestParam(value = "page", defaultValue = "1") Integer pageNo,
			@RequestParam(value = "rows", defaultValue = "10") Integer pageSize,
			HttpServletRequest request){
		System.out.println("");
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(
				request, "_");
		if(searchParams !=null){
			if(searchParams.get("cardState") !=null && searchParams.get("cardState").equals(3)){
				searchParams.remove("cardState");
			}
		}
		PageInfo<AppVipcard> pageInfo = vipCardManagerImpl.showCardListPage(pageNo, pageSize, searchParams);
		intiWebContentEnv();
		return vipCardManagerImpl.bindVipCardGridData(pageInfo);
		
		
	}
	
	/**
	 * 逻辑删除vip卡信息
	 * delVipCard
	 * @param vipCard
	 * @return
	 * MessageDto<AppVipcard>
	 * @version  1.0.0
	 */
	@ResponseBody
	@RequestMapping(value="delVipCard",method=RequestMethod.POST)
	public MessageDto<AppVipcard> delVipCard(AppVipcard vipCard){
		vipCardManagerImpl.deleteVipCardByCardNo(vipCard.getCardNo());
		return new MessageDto<AppVipcard>().setOk(true).setMsgBody(
				MessageDto.MsgFlag.SUCCESS);
	
	}
	
	/**
	 * 将excel表格数据导入到数据库
	 * importExcel
	 * @param filePath
	 * @return
	 * MessageDto<AppVipcard>
	 * @version  1.0.0
	 */
	@RequestMapping(value="importExcel",method=RequestMethod.POST)
	@ResponseBody
	public MessageDto<AppVipcard> importExcel(String filePath){
		vipCardManagerImpl.importExcelData(filePath);
		return new MessageDto<AppVipcard>().setOk(true).setMsgBody(
				MessageDto.MsgFlag.SUCCESS);
		
	}
	
	/**
	 * 将数据表导出到excel
	 * exportExcel
	 * @param filePath
	 * @param response
	 * @return
	 * MessageDto<AppVipcard>
	 * @version  1.0.0
	 */
	@RequestMapping(value="exportExcel")
	@ResponseBody
	public MessageDto<AppVipcard> exportExcel(String filePath,HttpServletResponse response){
		filePath = "f://";
		String fileName = filePath +"vipCard.xlsx";
		vipCardManagerImpl.exportDataToExcel(fileName);
		return new MessageDto<AppVipcard>().setOk(true).setMsgBody(
				MessageDto.MsgFlag.SUCCESS);
	}
	
	
}
