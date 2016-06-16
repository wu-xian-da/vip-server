package com.jianfei.core.service.order;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppCardBack;
import com.jianfei.core.bean.AppInvoice;
import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.common.enu.PayType;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.dto.*;

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
    BaseMsgInfo addOrderAndUserInfo(OrderAddInfoDto addInfoDto);

    /**
     * 更新订单信息付款
     *
     * @return
     */
    boolean updateOrderPayInfo(AppOrders addInfoDto);


    /**
     * guo.jian
     * queryPage(订单列表分页查询)
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
    
    /**
     * guo.jian
     * queryPage(退款列表分页查询)
     *
     * @param pageNo
     * @param pageSize
     * @param params
     *            封装查询参数
     * @return Page<Role>
     * @version 1.0.0
     */
    PageInfo<OrderShowInfoDto> backCardPage(int pageNo, int pageSize,
                                   Map<String, Object> params);
    /**
     * guo.jian
     * updateOrderStateByOrderId(更新订单状态)
     * @param params
     * @return
     * int
     * @version  1.0.0
     */
    int updateOrderStateByOrderId(String orderId,int optype);
    
    /**
     * liu.lei@jianfeitech.com
     * @param order
     * @param orderState
     * @return
     */
    int updateOrderStateByOrderIdEx(String orderId,int orderState);
    
    /**
     * guo.jian
     * remainMoney(根据订单号返回用户vip卡剩余金额)
     * @param orderId
     * @return
     *float
     * @version  1.0.0
     */
    float remainMoney(String orderId);
    
    /**
     * guo.jian
     * insertBackCardInfo(记录退卡流水号)
     * @param appCardBack
     * @return
     *int
     * @version  1.0.0
     */
    int insertBackCardInfo(AppCardBack appCardBack);
    
    /**
     * 通过订单号在流水表中查询用户的退款账户和退款方式
     */
    AppCardBack selCustomerCard(String orderId);
    
    /**
     * guo.jian
     * 更新退卡流水号
     */
    int updateBackCardByOrderId(Map<String,Object> map);
    
    /**
     * guo.jian
     * returnOrderDetailInfoByOrderId(根据订单编号返回订单详细信息)
     * @param order
     * @return
     *OrderDetailInfo
     * @version  1.0.0
     */
    OrderDetailInfo returnOrderDetailInfoByOrderId(String order);
    /**
     * 订单发票信息
     * @param appInvoice 发票信息
     * @return
     */
    BaseMsgInfo addOrderMailInfo(AppInvoice appInvoice);


    /**
     * 获取销售某天开卡详细数据
     * @param userId 销售Id
     * @param date 日期
     * @return
     */
    List<AppOrders> getOrdersBySaleId(String userId,String date);

    /**
     * 查询某个销售销售卡退卡分页
     * @param userId 销售ID
     * @param pageDto 分页数据
     * @return
     */
    PageInfo<AppOrders> pageReturnOrderBySaleId(String userId, PageDto pageDto);

    /**
     * 根据付款方式获取付款URL
     * @param orderId 订单ID
     * @param payType 付款方式
     * @return
     */
    BaseMsgInfo getPayUrl(String orderId, PayType payType);

    /**
     * 根据订单ID获取订单金额
     * @param orderId
     * @return
     */
    AppOrders getOrderInfo(String orderId);

    /**
     *
     * @param orderId
     * @param payType
     * @return
     */
    BaseMsgInfo checkThirdPay(String orderId, PayType payType);

    /**
     * 根据手机号查询用户VIP卡使用信息和订单详细信息
     * @param phone 手机号
     * @param code
     * @return
     */
    BaseMsgInfo  getVipCardUseAndOrder(String phone,String code);
    
    /**
	 * selectOrder(订单状态查询)
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> selectOrder(Map<String, Object> map);

    /**
     * 更新用户付款状态
     * @param orderId
     * @param payType
     * @return
     */
    BaseMsgInfo updatePayState(String orderId, PayType payType);

    /**
     * APP端添加退卡信息
     * @param appCardBack 退卡信息
     * @return
     */
    BaseMsgInfo addBackCardInfo(AppCardBack appCardBack);
    
    /**
     * 根据订单号返回订单基本信息
     * @param orderId
     * @return
     */
    AppOrders getOrderInfoByOrderId(String orderId);
}
