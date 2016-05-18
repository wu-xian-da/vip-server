package com.jianfei.core.service.order;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.dto.OrderAddInfoDto;

import java.util.Map;

/**
 * 订单管理
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 15:55
 */
public interface OrderManager {
    /**
     * 添加订单信息
     *
     * @return
     */
    boolean addOrderAndUserInfo(OrderAddInfoDto addInfoDto);

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
    PageInfo<AppOrders> simplePage(int pageNo, int pageSize,
                                   Map<String, Object> params);

}
