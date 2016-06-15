package com.jianfei.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.bean.AppUserFeedback;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.service.base.AppCustomerManager;
import com.jianfei.core.service.base.impl.AppUserFeedbackImpl;
/**
 * 用户反馈管理controller
 * @author guojian
 *
 */
@Controller
@RequestMapping("feedback")
public class AppUserFeedbackController {
	@Autowired
	private AppUserFeedbackImpl appUserFeedbackImpl;
	@Autowired
	private AppCustomerManager appCustomerManager;
	/**
	 * 跳转到用户反馈管理页面
	 * @return
	 */
	@RequestMapping("goFeedbackView")
	public String goFeedbackView(){
		return "feedback/feedbackManagement";
	}
	
	
	/**
	 * 
	 * 跳转到用户反馈详情页面
	 * @param model
	 * @return
	 */
	@RequestMapping("gotoFeedBackDetailView")
	public String gotoFeedBackDetailView(@RequestParam(value="userId") String userId,@RequestParam(value="feedBackId") String feedBackId,Model model){
		MessageDto<AppCustomer> messageDto = appCustomerManager
				.selectByPrimaryKey(userId);
		if (messageDto.isOk()) {
			model.addAttribute("customer", messageDto.getData());
		}
		//根据反馈id号获取该条反馈信息
		AppUserFeedback appUserFeedbackInfo = appUserFeedbackImpl.getFeedBackInfoById(feedBackId);
		model.addAttribute("appUserFeedbackInfo", appUserFeedbackInfo);
		appCustomerManager.batchDealMsg(userId, model);
		return "feedback/feedBackDetail";
	}
	
	
	/**
	 * 跳转到用户反馈处理页面
	 * @param userId
	 * @param feedbackId
	 * @param model
	 * @return
	 */
	@RequestMapping("goHandFeedbackView")
	public String goHandFeedbackView(String userId,String feedbackId,String feedbackContent,Model model){
		MessageDto<AppCustomer> messageDto = appCustomerManager
				.selectByPrimaryKey(userId);
		if (messageDto.isOk()) {
			model.addAttribute("customer", messageDto.getData());
		}
		model.addAttribute("feedbackContent", feedbackContent);
		model.addAttribute("feedbackId", feedbackId);
		appCustomerManager.batchDealMsg(userId, model);
		return "feedback/handfeedbackView";
	}
	
	/**
	 * 用户反馈信息分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param feedbackState
	 * @return
	 */
	@RequestMapping("feedbackList")
	@ResponseBody
	public Map<String,Object> pageList(@RequestParam(value="page",defaultValue="1") Integer pageNo,
			@RequestParam(value="rows",defaultValue="10") Integer pageSize, 
			@RequestParam(value="feedbackState",defaultValue="2") Integer feedbackState ){
		//设置刷选条件
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("feedbackState", feedbackState);
		
		PageInfo<AppUserFeedback> page = appUserFeedbackImpl.pageList(pageNo, pageSize, paramsMap);
		//出参
		Map<String,Object> resMap =  new HashMap<String,Object>();
		//设置操作选项
		List<AppUserFeedback> feedbackList = page.getList();
		for(AppUserFeedback appUserFeedback : feedbackList){
			if(appUserFeedback.getFeedbackState() == 0){//反馈信息未处理
				appUserFeedback.setFeedbackStateName("未处理");
				appUserFeedback.setOpr("<a href='goHandFeedbackView?userId="+appUserFeedback.getUserId()+"&feedbackId="+appUserFeedback.getId()+"&feedbackContent="+appUserFeedback.getFeedbackContent()+"'><button class='btn'>处理</button></a>");
			}else if(appUserFeedback.getFeedbackState() == 1){//反馈信息已处理
				appUserFeedback.setFeedbackStateName("已处理");
				appUserFeedback.setOpr("<a href='gotoFeedBackDetailView?userId="+appUserFeedback.getUserId()+"&feedBackId="+appUserFeedback.getId()+"'><button class='btn'>查看</button></a><button class='btn' style='background-color:red' onclick='delFeedBackInfo("+appUserFeedback.getId()+")'>删除</button>");
			}
		}
		resMap.put("total", page.getTotal());
		resMap.put("rows", feedbackList);
		return resMap;
		
	}
	
	/**
	 * 逻辑删除用户反馈信息
	 * @param feedbackId
	 * @return
	 */
	@RequestMapping("delFeedBackInfo")
	@ResponseBody
	public Map<String,Object> delFeedBackInfo(@RequestParam(value="id",required=true) String feedbackId){
		Map<String,Object> resMap = new HashMap<String,Object>();
		try {
			appUserFeedbackImpl.delFeedbackInfoById(feedbackId);
			resMap.put("result", 1);//1代表成功
		} catch (Exception e) {
			// TODO: handle exception
			resMap.put("result", 0);
			e.printStackTrace();
		}
		return resMap;
	}
	
	/**
	 * 更新用户反馈的状态
	 * @param appUserFeedback
	 * @return
	 */
	@RequestMapping(value="updateFeedbackState",method=RequestMethod.POST)
	public String updateFeedbackState(AppUserFeedback appUserFeedback){
		System.out.println("feedbackid="+appUserFeedback.getId());
		try {
			appUserFeedbackImpl.updateFeedbackInfoById(appUserFeedback.getId()+"");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return "redirect:goFeedbackView";
	}

	
}
