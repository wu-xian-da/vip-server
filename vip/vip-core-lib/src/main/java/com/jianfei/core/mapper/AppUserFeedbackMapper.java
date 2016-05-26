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
	//分页查询所有的反馈信息
	List<AppUserFeedback> pageList(Map<String,Object> params);
	
	//删除反馈信息-逻辑删除
	int delFeedbackInfoById(String feedbackId);
	
	//更新反馈状态
	int updateFeedbackInfoById(String feedbackId);
	
}
