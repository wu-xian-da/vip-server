package com.jianfei.core.service.order.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.dto.OrderAddInfoDto;
import com.jianfei.core.dto.OrderShowInfoDto;
import com.jianfei.core.mapper.AppOrdersMapper;
import com.jianfei.core.service.order.OrderManager;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 16:31
 */
@Service
public class OrderManagerImpl implements OrderManager {

    @Autowired
    private AppOrdersMapper appOrdersMapper;

    /**
     * 添加订单信息
     *
     * @param addInfoDto
     * @return
     */
    @Override
    public boolean addOrderAndUserInfo(OrderAddInfoDto addInfoDto) {
        //TODO 1、添加用户信息

        //TODO 2、根据查询VIP号查询卡片信息

        //TODO 3、添加订单信息
        return false;
    }


    /**
     * queryPage(分页查询)
     *
     * @param pageNo
     * @param pageSize
     * @param params
     *            封装查询参数
     * @return Page<Role>
     * @version 1.0.0
     */

    public PageInfo<OrderShowInfoDto> simplePage(int pageNo, int pageSize,
                                          Map<String, Object> params) {
        PageHelper.startPage(pageNo, pageSize);
        List<OrderShowInfoDto> list = appOrdersMapper.get(params);
        PageInfo<OrderShowInfoDto> pageInfo = new PageInfo(list);
        return pageInfo;
    }


	/**
	 * 改变订单状态
	 * 0 未支付
	 * 1 已支付
	 * 2 正在审核
	 * 3 审核通过
	 * 4 退款成功
	 */
	@Override
	public void updateOrderState(String orderId, int operationType) {
		int orderState =1;
		if(operationType == 0){//退款申请 
			orderState = 2;
		}else if(operationType == 1){//审核通过
			orderState = 3;
		}else if(operationType == 2){//审核不通过
			orderState = 1;
		}else if(operationType == 3){//退款
			orderState = 4;
		}
	}
}
