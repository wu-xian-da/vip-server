package com.jianfei.core.mapper;

import com.jianfei.core.bean.AppConsume;
import com.jianfei.core.common.persistence.MyBatisDao;

@MyBatisDao
public interface AppConsumeMapper {
	//根据卡号返回消费记录数
	int getCountCosume(String cardId);
	
    int deleteByPrimaryKey(String consumeId);

    int insert(AppConsume record);

    int insertSelective(AppConsume record);

    AppConsume selectByPrimaryKey(String consumeId);

    int updateByPrimaryKeySelective(AppConsume record);

    int updateByPrimaryKey(AppConsume record);
}