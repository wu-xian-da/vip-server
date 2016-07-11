package com.jianfei.core.mapper;

import java.util.Map;

import com.jianfei.core.bean.AppCardBack;
import com.jianfei.core.common.persistence.MyBatisDao;
@MyBatisDao
public interface AppCardBackMapper {
	//记录退卡流水号
	int insertBackCard(AppCardBack record);
	
	//通过订单号查询用户退款账户
	AppCardBack seleConsumeCardId(String orderId);
	
	//更新流水信息（最终审核时间、审核人）
	int updateBackCard(Map<String,Object> map);
	
    int deleteByPrimaryKey(String backId);

    int insert(AppCardBack record);

    int insertSelective(AppCardBack record);

    AppCardBack selectByPrimaryKey(String backId);

    int updateByPrimaryKeySelective(AppCardBack record);

    int updateByPrimaryKey(AppCardBack record);

    AppCardBack selectByOrderId(String orderId);

    int deleteByOrderId(String orderId);

}