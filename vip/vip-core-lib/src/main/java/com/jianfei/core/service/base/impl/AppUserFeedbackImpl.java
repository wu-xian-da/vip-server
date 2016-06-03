package com.jianfei.core.service.base.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.common.enu.StateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppUserFeedback;
import com.jianfei.core.mapper.AppUserFeedbackMapper;
import com.jianfei.core.service.base.AppUserFeedbackManager;

@Service
@Transactional
public class AppUserFeedbackImpl implements AppUserFeedbackManager{
	@Autowired
	private AppUserFeedbackMapper appUserFeedbackMapper;
	/**
	 * 分页查询返回的反馈信息
	 */
	@Override
	public PageInfo<AppUserFeedback> pageList(int pageNo, int pageSize,Map<String, Object> params) {
		// TODO Auto-generated method stub
		// 显示第几页
		PageHelper.startPage(pageNo, pageSize);
		List<AppUserFeedback> list = appUserFeedbackMapper.pageList(params);
		PageInfo<AppUserFeedback> page = new PageInfo(list);
		return page;
	}
	
	/**
	 * 逻辑删除反馈信息
	 */
	@Override
	public int delFeedbackInfoById(String feedbackId) {
		// TODO Auto-generated method stub
		return appUserFeedbackMapper.delFeedbackInfoById(feedbackId);
	}
	
	/**
	 * 更新反馈信息的状态
	 */
	@Override
	public int updateFeedbackInfoById(String feedbackId) {
		// TODO Auto-generated method stub
		return appUserFeedbackMapper.updateFeedbackInfoById(feedbackId);
	}

	/**
	 * 插入反馈记录
	 *
	 * @param customer
	 * @param content
	 * @return
	 */
	@Override
	public int addFeedbackInfo(AppCustomer customer, String content) {
		AppUserFeedback userFeedback=new AppUserFeedback();
		userFeedback.setUserId(customer.getCustomerId());
		userFeedback.setCustomerName(customer.getCustomerName());
		userFeedback.setCustomerPhone(customer.getPhone());
		userFeedback.setDtflag(StateType.EXIST.getName());
		userFeedback.setFeedbackContent(content);
		userFeedback.setFeedbackTime(new Date());
		userFeedback.setFeedbackState(0);//// TODO: 2016/6/2 状态 ASK 郭建
		return appUserFeedbackMapper.insertSelective(userFeedback);
	}
}
