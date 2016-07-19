package com.jianfei.core.service.base;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppVipcard;


import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Vip使用记录
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/20 10:05
 */
public interface VipCardManager {
	/**
	 * 添加VipCard
	 * 
	 * @param vipcard
	 * @return
	 */
	boolean addVipCard(AppVipcard vipcard);

	/**
	 * 更新VipCard
	 * 
	 * @param vipcard
	 * @return
	 */
	boolean updateVipCard(AppVipcard vipcard);

	/**
	 * 分页显示vip卡列表信息 showCardListPage
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return PageInfo<OrderShowInfoDto>
	 * @version 1.0.0
	 */
	PageInfo<AppVipcard> showCardListPage(int pageNo, int pageSize,
			Map<String, Object> params);
	
	/**
	 * 不分页查询
	 * @param params
	 * @return
	 */
	List<AppVipcard> showCardListNotPage(Map<String, Object> params);
	/**
	 * 根据vip卡逻辑删除vip卡信息 deleteVipCardByCardNo
	 * 
	 * @param cardNo
	 * @return int
	 * @version 1.0.0
	 */
	int deleteVipCardByCardNo(String cardNo);

	/**
	 * 导入excel表格数据到数据表中 importExcelData
	 * 
	 * @param appVipcard
	 * @return int
	 * @version 1.0.0
	 */
	int importExcelData(InputStream in);

	/**
	 * 根据vip card no 获取VIP卡片信息
	 * 
	 * @param vipCardNo
	 *            VIP卡号
	 * @return
	 */
	AppVipcard getVipCardByNo(String vipCardNo);

	boolean activeAppCard(Map<String, Object> map);
	
	//更新卡记录
	int updateByPrimaryKeySelective(AppVipcard record);
	
	

}
