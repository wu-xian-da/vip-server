package com.jianfei.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppConfig;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.UUIDUtils;
import com.jianfei.core.service.base.impl.AppConfigManagerImpl;

/**
 * 权益管理
 * 
 * @Description: TODO
 * @author guo.jian
 * @Title: RightController.java
 * @date 2016年6月14日 上午9:24:53
 * @Version 1.0.0
 */
@Controller
@RequestMapping("right")
public class RightController extends BaseController {
	@Autowired
	private AppConfigManagerImpl appConfigManager;
	
	@RequestMapping("/gotoRightManagerView")
	public String gotoRightManagerView() {
		return "cardRights/RightsManagement";
	}
	
	/**
	 * 分页显示权益列表
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequestMapping("showRightByPage")
	@ResponseBody
	public Grid showRightByPage(@RequestParam(value = "page", defaultValue = "1") Integer pageNo,
			@RequestParam(value = "rows", defaultValue = "20") Integer pageSize, HttpServletRequest request) {
		// 查询条件
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "_");
		PageInfo<AppConfig> page = appConfigManager.page(pageNo, pageSize, searchParams);
		intiWebContentEnv();
		return appConfigManager.bindAppConfigGridData(page);
	}
	
	/**
	 * 跳转到添加素材页面
	 * @return
	 */
	@RequestMapping("gotoAddRightView")
	public String gotoAddRightView(){
		return "cardRights/addRight";
	}
	
	/**
	 * 添加权益
	 * @param appConfig
	 * @return
	 */
	@RequestMapping("addRight")
	public String addRight(AppConfig appConfig){
		appConfig.setId(UUIDUtils.getPrimaryKey());
		appConfigManager.addRight(appConfig);
		return "redirect:gotoRightManagerView";
	}
	
	/**
	 * 逻辑删除权益
	 * @param appConfig
	 * @return
	 */
	@RequestMapping(value="delRightById", method = RequestMethod.POST)
	@ResponseBody
	public MessageDto<AppConfig> delRightById(AppConfig appConfig){
		appConfigManager.delRight(appConfig.getId());
		return new MessageDto<AppConfig>().setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}
	
	/**
	 * 跳转到权益编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("gotoUpdateRightView")
	public String  gotoUpdateRightView(String id,Model model){
		//根据id查询权益信息
		AppConfig appConfig = appConfigManager.selectByPrimaryKey(id);
		model.addAttribute("appConfig", appConfig);
		return "cardRights/editRight";
	}
	
	/**
	 * 更新权益信息
	 * @param appConfig
	 * @return
	 */
	@RequestMapping("editRight")
	public String editRight(AppConfig appConfig){
		appConfigManager.updateByPrimaryKeySelective(appConfig);
		return "redirect:gotoRightManagerView";
	}
}
