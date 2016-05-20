package com.jianfei.core.service.order;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppInvoice;
import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.dto.OrderAddInfoDto;
import com.jianfei.core.dto.OrderShowInfoDto;

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
     * 更新订单信息付款
     *
     * @return
     */
    boolean updateOrderPayInfo(AppOrders addInfoDto);

    /**
     * 查询订单是否付款
     * @param orderId 订单ID
     * @return
     */
    boolean checkOrderPay(String orderId);

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
    PageInfo<OrderShowInfoDto> simplePage(int pageNo, int pageSize,
                                   Map<String, Object> params);
<<<<<<< HEAD
    
    /**
     * 
     * 改变订单状态
     * @param orderId 订单编号
     * @param operationType 操作类型
     *void
     * @version  1.0.0
     */
    public void updateOrderState(String orderId,int operationType);
=======

    /**
     * 订单发票信息
     * @param appInvoice 发票信息
     * @return
     */
    boolean addOrderMailInfo(AppInvoice appInvoice);
>>>>>>> 584798f39b4e089b56aa7c2b0f696870ff25a8d7

}
