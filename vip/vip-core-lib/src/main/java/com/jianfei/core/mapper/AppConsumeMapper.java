package com.jianfei.core.mapper;

import com.jianfei.core.bean.AppConsume;
import com.jianfei.core.common.persistence.MyBatisDao;

import java.util.List;

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

    /**
     * 获取用户消费记录根据手机号
     * @param phone
     * @return
     */
    List<AppConsume> listConsumeByUserPhone(String phone);
}