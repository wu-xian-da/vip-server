package com.jianfei.core.service.base.impl;

import com.jianfei.core.bean.AppPicture;
import com.jianfei.core.common.enu.PictureType;
import com.jianfei.core.mapper.AppPictureMapper;
import com.jianfei.core.service.base.CarouselManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮播图管理
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/20 17:27
 */
@Service
public class CarouselManagerImpl implements CarouselManager {
    @Autowired
   private AppPictureMapper appPictureMapper;

    /**
     * 查询某个类型下的轮播图数据
     *
     * @param pictureType
     * @return
     */
    @Override
    public List<AppPicture> getPicture(PictureType pictureType) {
        AppPicture picture=new AppPicture();
        picture.setClickUrl(pictureType.getName());
        return  appPictureMapper.getPicture(picture);
    }

    /**
     * 查询Vip室的轮播图数据
     *
     * @param vipRoomId
     * @return
     */
    @Override
    public List<AppPicture> getPicture(String vipRoomId) {
        return null;
    }
}
