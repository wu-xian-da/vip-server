package com.jianfei.core.service.order;

import com.jianfei.core.bean.AppCardBack;

/**
 * 退卡相关管理类
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/7/8 16:29
 */
public interface CardBackManager {

    /**
     * 添加或者更新退卡信息
     * @param cardBack
     * @return
     */
   boolean addOrUpdateCardBackInfo(AppCardBack cardBack);

    /**
     * 逻辑删除退卡信息
     * @param orderId
     * @return
     */
    boolean deleteCardBackInfo(String orderId);
}
