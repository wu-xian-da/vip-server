package com.jianfei.core.mapper;

import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.AppUserFeedback;
import com.jianfei.core.common.persistence.MyBatisDao;

/**
 * 用户反馈
 * @author guojian
 *
 */
@MyBatisDao
public interface AppUserFeedbackMapper {
	//根据反馈id号查询反馈信息
	AppUserFeedback getFeedBackInfoById(String feedBackId);
	//分页查询所有的反馈信息
	List<AppUserFeedback> pageList(Map<String,Object> params);
	
	//删除反馈信息-逻辑删除
	int delFeedbackInfoById(String feedbackId);
	
	//更新反馈状态
	int updateFeedbackInfoById(String feedbackId);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table app_user_feedback
	 *
	 * @mbggenerated Thu Jun 02 21:05:33 CST 2016
	 */
	int insertSelective(AppUserFeedback record);
	
	//根据用户id返回所有的反馈信息
	List<AppUserFeedback> getFeedBackInfoListByUserId(String userId);
	
}
