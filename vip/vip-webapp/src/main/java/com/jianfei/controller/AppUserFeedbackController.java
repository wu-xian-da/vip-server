package com.jianfei.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppUserFeedback;
import com.jianfei.core.service.base.impl.AppUserFeedbackImpl;
/**
 * 用户反馈管理controller
 * @author guojian
 *
 */
@Controller
@RequestMapping("feedback")
public class AppUserFeedbackController {
	private AppUserFeedbackImpl appUserFeedbackImpl;
	/**
	 * 跳转到用户反馈管理页面
	 * @return
	 */
	@RequestMapping("goFeedbackView")
	public String goFeedbackView(){
		return "feedback/feedbackManagement";
	}
	
	
	
	
	/**
	 * 用户反馈信息分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param feedbackState
	 * @return
	 */
	@RequestMapping("feedbackList")
	public Map<String,Object> pageList(@RequestParam(value="page",defaultValue="1") Integer pageNo,
			@RequestParam(value="rows",defaultValue="10") Integer pageSize, 
			@RequestParam(value="feedbackState",defaultValue="2") Integer feedbackState ){
		//设置刷选条件
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("feedbackState", feedbackState);
		//出参
		Map<String,Object> resMap =  new HashMap<String,Object>();
		PageInfo<AppUserFeedback> page = appUserFeedbackImpl.pageList(pageNo, pageSize, paramsMap);
		//设置操作选项
		List<AppUserFeedback> feedbackList = page.getList();
		for(AppUserFeedback appUserFeedback : feedbackList){
			if(appUserFeedback.getFeedbackState() == 0){//反馈信息未处理
				appUserFeedback.setOpr("<button class='btn'><a href='returnOrderDetailInfoByOrderId?orderId=1001'>查看</a></button><button class='btn btn-close' onclick='onError()'>✕</button>");
			}else if(appUserFeedback.getFeedbackState() == 1){//反馈信息已处理
				appUserFeedback.setOpr("<button class='btn'><a href='returnOrderDetailInfoByOrderId?orderId=1001'>处理</a></button>");
			}
		}
		resMap.put("total", page.getTotal());
		resMap.put("rows", feedbackList);
		return resMap;
		
	}
	
	
}
