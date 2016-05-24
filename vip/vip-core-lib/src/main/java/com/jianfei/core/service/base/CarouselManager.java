package com.jianfei.core.service.base;

import com.jianfei.core.bean.AppPicture;
import com.jianfei.core.common.enu.PictureType;

import java.util.List;

/**
 * 轮播图管理
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/20 16:23
 */
public interface CarouselManager {

    /**
     * 查询某个类型下的轮播图数据
     * @param pictureType
     * @return
     */
    List<AppPicture> getPicture(PictureType pictureType);


    /**
     * 查询Vip室的轮播图数据
     * @param vipRoomId
     * @return
     */
    List<AppPicture> getPicture(String vipRoomId);
}
