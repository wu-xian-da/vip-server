package com.jianfei.core.mapper;

import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.AppPicture;
import com.jianfei.core.common.persistence.MyBatisDao;

<<<<<<< HEAD
@MyBatisDao
public interface AppPictureMapper {
	   /**
=======
import java.util.List;

@MyBatisDao
public interface AppPictureMapper {
    /**
>>>>>>> 15b4d4ae28c4140068b60a414326962a95e7c6dd
     * 根据查询条件获取查询数据
     * @param appPicture
     * @return
     */
    List<AppPicture> getPicture(AppPicture appPicture);
<<<<<<< HEAD
    
	int deleteByPrimaryKey(Integer pictureId);

	int insert(AppPicture record);
=======

    int deleteByPrimaryKey(Integer pictureId);
>>>>>>> 15b4d4ae28c4140068b60a414326962a95e7c6dd

	int insertSelective(AppPicture record);

	AppPicture selectByPrimaryKey(Integer pictureId);

	int updateByPrimaryKeySelective(AppPicture record);

	int updateByPrimaryKey(AppPicture record);

	List<AppPicture> get(Map<String, Object> map);
}