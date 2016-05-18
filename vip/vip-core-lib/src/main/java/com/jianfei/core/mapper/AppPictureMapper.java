package com.jianfei.core.mapper;

import com.jianfei.core.bean.AppPicture;

public interface AppPictureMapper {
    int deleteByPrimaryKey(Integer pictureId);

    int insert(AppPicture record);

    int insertSelective(AppPicture record);

    AppPicture selectByPrimaryKey(Integer pictureId);

    int updateByPrimaryKeySelective(AppPicture record);

    int updateByPrimaryKey(AppPicture record);
}