package com.jianfei.core.service.order.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppConsume;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.mapper.AppConsumeMapper;
import com.jianfei.core.service.order.ConsumeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 消费记录查询
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/26 8:49
 */
@Service
public class ConsumeManagerImpl implements ConsumeManager {

    @Autowired
     private AppConsumeMapper consumeMapper;
    /**
     * 添加消费记录
     *
     * @param appConsume
     * @return
     */
    @Override
    public boolean addConsume(AppConsume appConsume) {
      int num=  consumeMapper.insertSelective(appConsume);
        return num == 1 ? true : false;
    }


    /**
     * 分页获取用户使用记录
     *
     * @param pageNo
     *            页数
     * @param pageSize
     *            每页大小
     * @param vipCardNo
     *            Vip卡号
     * @return
     */
    @Override
    public PageInfo<AppConsume> pageVipUseInfo(int pageNo, int pageSize, String vipCardNo) {
        // 显示第几页
        PageHelper.startPage(pageNo, pageSize);
        // 查询条件
        List<AppConsume> list =consumeMapper.selectByVipCardNo(vipCardNo);
        PageInfo<AppConsume> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 根据vip卡No获取所有使用记录
     *
     * @param vipCardNo
     *            vipCardNo
     * @return List<AppCustomer>
     */
    @Override
    public List<AppConsume> getConsumesByVipNo(String vipCardNo) {
        return consumeMapper.selectByVipCardNo(vipCardNo);
    }


}
