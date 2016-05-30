package com.jianfei.core.service.order.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.*;

import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.common.enu.PayType;
import com.jianfei.core.common.utils.BeanUtils;
import com.jianfei.core.common.utils.IdGen;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.service.base.impl.AppInvoiceManagerImpl;
import com.jianfei.core.service.base.impl.VipCardManagerImpl;
import com.jianfei.core.service.thirdpart.impl.MsgInfoManagerImpl;
import com.jianfei.core.service.user.impl.VipUserManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.dto.OrderAddInfoDto;
import com.jianfei.core.dto.OrderDetailInfo;
import com.jianfei.core.dto.OrderShowInfoDto;
import com.jianfei.core.mapper.AppCardBackMapper;
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
    @Autowired
    private AppCardBackMapper appCardBackMapper;
	@Autowired
	private VipUserManagerImpl vipUserManager;
	@Autowired
	private VipCardManagerImpl vipCardManager;
	@Autowired
	private AppInvoiceManagerImpl invoiceManager;
	@Autowired
	private MsgInfoManagerImpl msgInfoManager;
    /**
     * 添加订单信息
     *
     * @param addInfoDto
     * @return
     */
    @Override
    public boolean addOrderAndUserInfo(OrderAddInfoDto addInfoDto) {

		try {
			//1、校验用户和手机验证码
			boolean flag=msgInfoManager.validateSendCode(addInfoDto.getPhone(), MsgType.REGISTER,addInfoDto.getCode());
			if (!flag){
				return false;
			}
			//2、添加用户信息
			AppCustomer customer= new AppCustomer();
			BeanUtils.copyProperties(customer,addInfoDto);
			vipUserManager.addUser(customer);
			//3、根据查询VIP号查询卡片信息
			AppVipcard vipCard=vipCardManager.getVipCardByNo(addInfoDto.getVipCardNo());
			//4、添加订单信息
			AppOrders orders=new AppOrders();
			BeanUtils.copyProperties(orders,addInfoDto);
			orders.setCustomerId(customer.getCustomerId());
			orders.setPayMoney(vipCard.getInitMoney());
			orders.setOrderId(IdGen.uuid());
			appOrdersMapper.insert(orders);
            //5、TODO 订单卡表
			addInfoDto.setOrderId(orders.getOrderId());
			addInfoDto.setMoney(vipCard.getInitMoney());
			return true;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();

		}
		return false;
    }


    /**
     * queryPage(订单列表分页查询)
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
		//TODO 更新订单为已开发票
		invoiceManager.insert(appInvoice);
        return true;
    }

    /**

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
			orderState = 2;
		}else if(operationType == 2){//审核不通过
			orderState = 1;
		}else if(operationType == 3){//录入退卡人账号
			orderState = 3;
		}else if(operationType == 4){//最终退款
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


	
	

	/* 获取销售某天开卡详细数据
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


	/**
	 * 记录退卡流水号
	 */
	@Override
	public int insertBackCardInfo(AppCardBack appCardBack) {
		// TODO Auto-generated method stub
		appCardBackMapper.insertBackCard(appCardBack);
		return 0;
	}



	/**
	 *  通过订单号在流水表中查询用户的退款账户
	 */
	@Override
	public AppCardBack selCustomerCard(String orderId) {
		// TODO Auto-generated method stub
		return appCardBackMapper.seleConsumeCardId(orderId);
	}


	/**
	 * 更新退卡流水号
	 */
	@Override
	public int updateBackCardByOrderId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return appCardBackMapper.updateBackCard(map);
	}


	/**
	 * 根据订单编号返回订单详细信息
	 */
	@Override
	public OrderDetailInfo returnOrderDetailInfoByOrderId(String orderId) {
		// TODO Auto-generated method stub
		return appOrdersMapper.selOrderDetailInfo(orderId);
	}


	/* (non-Javadoc)
	 * @see com.jianfei.core.service.order.OrderManager#backCardPage(int, int, java.util.Map)
	 */
	@Override
	public PageInfo<OrderShowInfoDto> backCardPage(int pageNo, int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
        List<OrderShowInfoDto> list = appOrdersMapper.page(params);
        PageInfo<OrderShowInfoDto> pageInfo = new PageInfo(list);
        return pageInfo;
	}

	/**
	 * 根据付款方式获取付款URL
	 *
	 * @param orderId 订单ID
	 * @param payType 付款方式
	 * @return
	 */
	@Override
	public String getPayUrl(String orderId, PayType payType) {
		return null;
	}

	/**
	 * 根据订单ID获取订单金额
	 *
	 * @param orderId
	 * @return
	 */
	@Override
	public AppOrders getOrderInfo(String orderId) {
		return appOrdersMapper.selectByPrimaryKey(orderId);
	}
}
