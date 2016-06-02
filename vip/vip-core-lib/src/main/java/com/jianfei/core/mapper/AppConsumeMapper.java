package com.jianfei.core.mapper;

import com.jianfei.core.bean.AppConsume;
import com.jianfei.core.common.persistence.MyBatisDao;
import org.apache.ibatis.annotations.Param;

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
     * 获取用户消费记录根据VIPCard号
     * @param vipCardNo
     * @return
     */
    List<AppConsume> selectByVipCardNo(@Param("vipCardNo") String vipCardNo);

}