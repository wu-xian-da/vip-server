package com.jianfei.core.service.base;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.bean.AppUserFeedback;

public interface AppUserFeedbackManager {
	//分页查询所有的反馈信息
	PageInfo<AppUserFeedback> pageList(int pageNo, int pageSize,Map<String,Object> params);
		
	//删除反馈信息-逻辑删除
	int delFeedbackInfoById(String feedbackId);
		
	//更新反馈状态
	int updateFeedbackInfoById(String feedbackId);

	/**
	 *  插入反馈记录
	 * @param customer
	 * @param content
     * @return
     */
	int addFeedbackInfo(AppCustomer customer,String content);

}
