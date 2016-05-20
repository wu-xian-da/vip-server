package com.jianfei.core.service.base.impl;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.service.base.VipCardManager;

import java.util.List;

/**
 *  Vip卡管理
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/20 10:45
 */
public class VipCardManagerImpl implements VipCardManager {

    /**
     * 添加VipCard
     *
     * @param vipcard
     * @return
     */
    @Override
    public boolean addVipCard(AppVipcard vipcard) {
        return false;
    }

    /**
     * 更新VipCard
     *
     * @param vipcard
     * @return
     */
    @Override
    public boolean updateVipCard(AppVipcard vipcard) {
        return false;
    }

    /**
     * 添加VipCard使用记录
     *
     * @param customer
     * @return
     */
    @Override
    public boolean addVipUseInfo(AppCustomer customer) {
        return false;
    }

    /**
     * 分页获取用户使用记录
     *
     * @param pageNo    页数
     * @param pageSize  每页大小
     * @param vipCardNo Vip卡号
     * @return
     */
    @Override
    public PageInfo<AppCustomer> pageVipUseInfo(int pageNo, int pageSize, String vipCardNo) {
        return null;
    }

    /**
     * 根据vip卡No获取所有使用记录
     *
     * @param vipCardNo vipCardNo
     * @return List<AppCustomer>
     */
    @Override
    public List<AppCustomer> listAllVipUse(String vipCardNo) {
        return null;
    }
}
