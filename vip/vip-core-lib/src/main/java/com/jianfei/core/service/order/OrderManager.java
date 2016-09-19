package com.jianfei.core.service.order;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppCardBack;
import com.jianfei.core.bean.AppConsume;
import com.jianfei.core.bean.AppInvoice;
import com.jianfei.core.bean.AppOrderCard;
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
    BaseMsgInfo addOrderAndUserInfo(OrderAddInfoDto addInfoDto) throws InvocationTargetException, IllegalAccessException;

    /**
     * 更新订单信息付款
     *
     * @return
     */
    boolean updateOrderInfo(AppOrders addInfoDto);


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
     * guo.jian
     * remainMoney(根据订单号返回用户vip卡剩余金额)
     * @param orderId
     * @return
     *float
     * @version  1.0.0
     */
    double remainMoney(String orderId);
    
    /**
     * 根据订单号计算对应卡号的服务费
     * @param orderId
     * @return
     */
    double calculateServiceMoney(String orderId);
    
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
     * 根据订单ID获取订单金额
     * @param orderId
     * @return
     */
    AppOrders getOrderDetailByOrderId(String orderId);


    /**
     * 根据手机号查询用户VIP卡使用信息和订单详细信息
     * @param phone 手机号
     * @param code
     * @return
     */
    BaseMsgInfo  getVipCardUseAndOrder(String phone,String code,String vipCardNo);
    
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
     * @param appOrders
     * @return
     */
    BaseMsgInfo updatePayState(AppOrders appOrders);

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
    
    /**
	 * 查询需要开发票的订单信息
	 */
    PageInfo<OrderShowInfoDto> invoicePageList(int pageNo, int pageSize,Map<String,Object> map);
    
    /**
     * 根据order_id查询订单基本信息
     * @param orderId
     * @return
     */
    AppOrders selectByPrimaryKey(String orderId);
    

	/**
	 * 根据订单id获取订单卡表信息
	 * @param orderId
	 * @return
	 */
	AppOrderCard selectByOrderId(String orderId);
	
	/**
	 * 根据卡号返回所有消费记录
	 */
	List<AppConsume> selectByVipCardNo(String cardNo);

    /**
     * 取消VIP卡退卡记录
     * @param phone 手机号
     * @param code 验证码
     * @return
     */
    BaseMsgInfo  removeBackCard(String phone,String code,String vipCardNo,String orderId);

    /**
     * 重新激活VIP卡
     * @param phone 手机号
     * @param orderId 订单ID
     * @return
     */
    BaseMsgInfo  activeCard(String phone,String vipCardNo,String orderId) throws UnrecoverableKeyException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException;
    
    /**
     * 日志信息
     * @param orderId
     * @return
     */
    OrderDetailInfo selLogInfoByOrderId(String orderId);
}
