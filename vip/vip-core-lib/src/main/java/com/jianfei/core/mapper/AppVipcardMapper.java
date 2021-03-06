package com.jianfei.core.mapper;

import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.common.persistence.MyBatisDao;

@MyBatisDao
public interface AppVipcardMapper {
	// 分页查询
	List<AppVipcard> pageList(Map<String, Object> params);

	// 根据vip卡号逻辑删除vip卡信息
	int delVipCard(String cardNo);

	// 查询所有的vip卡信息
	List<AppVipcard> selAllVipCard();

	// 将excel表格中的数据到到数据表中
	int importExcelToDB(AppVipcard record);
	
	//根据订单号查询对应的卡片信息
	AppVipcard selVipCardInfoByOrderId(String orderId);

	int deleteByPrimaryKey(String cardNo);

	int insert(AppVipcard record);

	int insertSelective(AppVipcard record);

	AppVipcard selectByPrimaryKey(String cardNo);

	int updateByPrimaryKeySelective(AppVipcard record);

	int updateByPrimaryKey(AppVipcard record);

	/**
	 * 激活卡信息，更新卡的有效时间
	 * 
	 * @param map
	 * @return
	 */
	int activeAppCard(Map<String, Object> map);

}