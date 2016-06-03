package com.jianfei.core.mapper;

import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.AppPicture;
import com.jianfei.core.common.persistence.MyBatisDao;

@MyBatisDao
public interface AppPictureMapper {
	/**
	 * 根据查询条件获取查询数据
	 * 
	 * @param appPicture
	 * @return
	 */
	List<AppPicture> getPicture(AppPicture appPicture);

	int insert(AppPicture record);

	int deleteByPrimaryKey(Integer pictureId);

	int insertSelective(AppPicture record);

	AppPicture selectByPrimaryKey(Integer pictureId);

	int updateByPrimaryKeySelective(AppPicture record);

	int updateByPrimaryKey(AppPicture record);

	List<AppPicture> get(Map<String, Object> map);
	
	int updateByVipRoomId(Map<String,Object> map);
	
	List<AppPicture> selByVipRoomId(String viproomId);
}