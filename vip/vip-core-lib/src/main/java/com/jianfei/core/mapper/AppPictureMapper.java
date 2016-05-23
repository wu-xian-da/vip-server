package com.jianfei.core.mapper;

import com.jianfei.core.bean.AppPicture;
import com.jianfei.core.common.persistence.MyBatisDao;

import java.util.List;

@MyBatisDao
public interface AppPictureMapper {
    /**
     * 根据查询条件获取查询数据
     * @param appPicture
     * @return
     */
    List<AppPicture> getPicture(AppPicture appPicture);

    int deleteByPrimaryKey(Integer pictureId);

    int insert(AppPicture record);

    int insertSelective(AppPicture record);

    AppPicture selectByPrimaryKey(Integer pictureId);

    int updateByPrimaryKeySelective(AppPicture record);

    int updateByPrimaryKey(AppPicture record);
}