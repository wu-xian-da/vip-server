package com.jianfei.core.service.order.impl;

import com.jianfei.core.bean.AppCardBack;
import com.jianfei.core.common.utils.IdGen;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.mapper.AppCardBackMapper;
import com.jianfei.core.service.order.CardBackManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 退卡信息
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/7/8 16:31
 */
@Service
public class CardBackManagerImpl implements CardBackManager {
    @Autowired
    private AppCardBackMapper appCardBackMapper;
    /**
     * 添加或者更新退卡信息
     *
     * @param cardBack
     * @return
     */
    @Override
    public boolean addOrUpdateCardBackInfo(AppCardBack cardBack) {
        if (cardBack ==null || StringUtils.isBlank(cardBack.getOrderId())){
            return false;
        }
        AppCardBack appCardBack=appCardBackMapper.selectByOrderId(cardBack.getOrderId());
        int i;
        if (appCardBack==null){
            cardBack.setBackId(IdGen.uuid());
            cardBack.setCreateTime(new Date());
            cardBack.setDtflag(0);
             i= appCardBackMapper.insertSelective(cardBack);
        }else {
            i= appCardBackMapper.updateByPrimaryKeySelective(cardBack);
        }
        return i == 1;
    }

    /**
     * 逻辑删除退卡信息
     *
     * @param orderId
     * @return
     */
    @Override
    public boolean deleteCardBackInfo(String orderId) {
        int i= appCardBackMapper.deleteByOrderId(orderId);
        return 1 == i ;
    }
}
