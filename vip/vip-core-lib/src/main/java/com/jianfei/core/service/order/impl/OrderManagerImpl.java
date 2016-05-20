package com.jianfei.core.service.order.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.AppInvoice;
<<<<<<< HEAD
import com.jianfei.core.bean.AppOrderCard;

=======
import com.jianfei.core.common.utils.PageDto;
>>>>>>> 9e2b0ade970ebbc0b98a556fa6a21fa520ee4b17
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.dto.OrderAddInfoDto;
import com.jianfei.core.dto.OrderShowInfoDto;
import com.jianfei.core.mapper.AppConsumeMapper;
import com.jianfei.core.mapper.AppOrderCardMapper;
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
    @Autowired
    private AppOrderCardMapper appOrderCardMapper;
    @Autowired
    private AppConsumeMapper appConsumeMapper;

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
     * 更新订单信息付款
     *
     * @param addInfoDto
     * @return
     */
    @Override
    public boolean updateOrderPayInfo(AppOrders addInfoDto) {
        return false;
    }

    /**
     * 查询订单是否付款
     *
     * @param orderId 订单ID
     * @return
     */
    @Override
    public boolean checkOrderPay(String orderId) {
        return false;
    }

    /**
     * 订单发票信息
     *
     * @param appInvoice 发票信息
     * @return
     */
    @Override
    public boolean addOrderMailInfo(AppInvoice appInvoice) {
        return false;
    }

    /**
<<<<<<< HEAD
     * 更新订单状态
     * 0 未支付
	 * 1 已支付
	 * 2 正在审核
	 * 3 审核通过
	 * 4 退款成功
     */
	@Override
	public int updateOrderStateByOrderId(String orderId, int operationType) {
		// TODO Auto-generated method stub
		
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
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderState", orderState);
		params.put("orderId", orderId);
		
		return appOrdersMapper.updateOrderState(params);
		
	}


	/**
	 * 根据订单号返回用户vip卡可退金额
	 */
	@Override
	public float remainMoney(String orderId) {
		float remainMoney = 0;
		//1、app_order_card表中返回卡号，用户初始金额
		AppOrderCard appOrderCard = appOrderCardMapper.getAppOrderCard(orderId);
		if(appOrderCard!= null){
			//2、app_consume表中返回vip消费次数
			int count = appConsumeMapper.getCountCosume(appOrderCard.getCardNo());
			//3、计算用户vip卡剩余金额
			remainMoney = (float) ((appOrderCard.getInitMoney()-count*200)*0.8);
			
		}
		System.out.println("float remainMoney="+remainMoney);
		return remainMoney;
	}


	
	

=======
     * 获取销售某天开卡详细数据
     *
     * @param userId 销售Id
     * @param date   日期
     * @return
     */
    @Override
    public List<AppOrders> getOrdersBySaleId(String userId, String date) {
        return null;
    }

    /**
     * 查询某个销售销售卡退卡分页
     *
     * @param userId  销售ID
     * @param pageDto 分页数据
     * @return
     */
    @Override
    public PageInfo<AppOrders> pageReturnOrderBySaleId(String userId, PageDto pageDto) {
        return null;
    }
>>>>>>> 9e2b0ade970ebbc0b98a556fa6a21fa520ee4b17
}
