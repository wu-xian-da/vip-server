package com.jianfei.core.service.order.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateContentBuilder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.*;
import com.jianfei.core.common.enu.*;
import com.jianfei.core.common.pay.PayQueryResult;
import com.jianfei.core.common.pay.PreCreateResult;
import com.jianfei.core.common.utils.*;
import com.jianfei.core.dto.*;
import com.jianfei.core.mapper.AppCardBackMapper;
import com.jianfei.core.mapper.AppConsumeMapper;
import com.jianfei.core.mapper.AppOrderCardMapper;
import com.jianfei.core.mapper.AppOrdersMapper;
import com.jianfei.core.service.base.AppCustomerManager;
import com.jianfei.core.service.base.AppInvoiceManager;
import com.jianfei.core.service.base.ValidateCodeManager;
import com.jianfei.core.service.base.VipCardManager;
import com.jianfei.core.service.base.impl.AppInvoiceManagerImpl;
import com.jianfei.core.service.base.impl.ValidateCodeManagerImpl;
import com.jianfei.core.service.base.impl.VipCardManagerImpl;
import com.jianfei.core.service.order.CardBackManager;
import com.jianfei.core.service.order.OrderManager;
import com.jianfei.core.service.thirdpart.QueueManager;
import com.jianfei.core.service.thirdpart.ThirdPayManager;
import com.jianfei.core.service.user.SaleUserManager;
import com.jianfei.core.service.user.VipUserManager;
import com.jianfei.core.service.user.impl.VipUserManagerImpl;
import com.tencent.protocol.native_protocol.NativePayReqData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 16:31
 */
@Service
@Transactional
public class OrderManagerImpl implements OrderManager {
    private static Log log = LogFactory.getLog(OrderManagerImpl.class);
    @Autowired
    private AppOrdersMapper appOrdersMapper;
    @Autowired
    private AppOrderCardMapper appOrderCardMapper;
    @Autowired
    private AppConsumeMapper appConsumeMapper;
    @Autowired
    private AppCardBackMapper appCardBackMapper;
    @Autowired
    private VipCardManager vipCardManager;
    @Autowired
    private AppInvoiceManager invoiceManager;
    @Autowired
    private ValidateCodeManager validateCodeManager;
    @Autowired
    private ConsumeManagerImpl consumeManager;

    @Autowired
    private QueueManager queueManager;
    @Autowired
    private VipUserManager vipUserManager;
    @Autowired
    private CardBackManager cardBackManager;
    @Autowired
    private SaleUserManager saleUserManager;

    /**
     * 添加订单信息
     *
     * @param addInfoDto
     * @return
     * @author dsliu
     */
    @Override
    public BaseMsgInfo addOrderAndUserInfo(OrderAddInfoDto addInfoDto) throws InvocationTargetException, IllegalAccessException {
       //重复提交问题
        if(StringUtils.isNotBlank(addInfoDto.getOrderId())){
            AppOrders appOrders=appOrdersMapper.selectByPrimaryKey(addInfoDto.getOrderId());
            addInfoDto.setMoney(appOrders.getPayMoney());
            return BaseMsgInfo.success(addInfoDto);
        }
        //1、校验用户和手机验证码
        boolean flag = validateCodeManager.validateSendCode(addInfoDto.getPhone(), MsgType.REGISTER, addInfoDto.getCode());
        if (!flag) {
            return new BaseMsgInfo().setCode(-1).setMsg("手机验证码验证失败");
        }

        //2、根据查询VIP号查询卡片信息
        AppVipcard vipCard = vipCardManager.getVipCardByNo(addInfoDto.getVipCardNo());
        if (vipCard == null) {
            return new BaseMsgInfo().setCode(-1).setMsg("VIP卡号错误");
        } else if (VipCardState.ACTIVE.getName() == vipCard.getCardState()) {
            return BaseMsgInfo.msgFail("VIP卡已使用");
        }

        //3、添加或修改用户信息
        AppCustomer customer = new AppCustomer();
        BeanUtils.copyProperties(customer, addInfoDto);
        vipUserManager.addORUpdateUser(customer);


        //4、添加订单信息
        AppOrders orders = new AppOrders();
        BeanUtils.copyProperties(orders, addInfoDto);
        orders.setSaleNo(addInfoDto.getUno());
        orders.setCustomerId(customer.getCustomerId());
        orders.setPayMoney(vipCard.getInitMoney());
        orders.setOrderId(IdGen.uuid());
        orders.setOrderTime(new Date());
        orders.setRemark1("亿出行VIP卡");
        orders.setOrderState(VipOrderState.NOT_PAY.getName());
        orders.setDtflag(StateType.EXIST.getName());
        orders.setInvoiceFlag(0);
        appOrdersMapper.insertSelective(orders);

        //5、卡表及订单卡表
        vipCard.setCustomerId(customer.getCustomerId());
        vipCardManager.updateVipCard(vipCard);
        AppOrderCard appOrderCard = new AppOrderCard();
        appOrderCard.setId(IdGen.uuid());
        appOrderCard.setOrderId(orders.getOrderId());
        appOrderCard.setCardNo(addInfoDto.getVipCardNo());
        appOrderCard.setCardNum(1);
        appOrderCard.setInitMoney(vipCard.getInitMoney());
        appOrderCard.setCardType(vipCard.getCardType());
        appOrderCardMapper.insert(appOrderCard);

        addInfoDto.setOrderId(orders.getOrderId());
        addInfoDto.setMoney(vipCard.getInitMoney());
        return BaseMsgInfo.success(addInfoDto);

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
    public boolean updateOrderInfo(AppOrders addInfoDto) {
        return appOrdersMapper.updateByPrimaryKeySelective(addInfoDto) < 0 ? false : true;
    }


    /**
     * 订单发票信息
     *
     * @param appInvoice 发票信息
     * @return
     */
    @Override
    public BaseMsgInfo addOrderMailInfo(AppInvoice appInvoice) {
        AppOrders orders = appOrdersMapper.selectByPrimaryKey(appInvoice.getOrderId());
        if (orders == null || StringUtils.isBlank(orders.getOrderId())) {
            return BaseMsgInfo.msgFail("订单不存在");
        }
        invoiceManager.insert(appInvoice);
        orders.setInvoiceFlag(InvoiceState.NEED_INVOICE.getName());
        int flag = appOrdersMapper.updateByPrimaryKeySelective(orders);
        if (flag < 0)
            return BaseMsgInfo.msgFail("订单邮寄信息状态更新失败");
        return BaseMsgInfo.success(true);
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

        int orderState = 1;
        if (operationType == 0) {//退款申请
            orderState = 2;
        } else if (operationType == 1) {//审核通过
            orderState = 2;
        } else if (operationType == 2) {//审核不通过
            orderState = 1;
        } else if (operationType == 3) {//录入退卡人账号
            orderState = 3;
        } else if (operationType == 4) {//最终退款
            orderState = 4;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderState", orderState);
        params.put("orderId", orderId);
        //审核通过
        if(operationType == 3){
        	params.put("applyType", ApplyBackCardMethod.CUSTOMER_SERVICE_APPLY.getName());
        }

        return appOrdersMapper.updateOrderState(params);

    }


    /**
     * 根据订单号返回用户vip卡可退金额
     */
    @Override
    public double remainMoney(String orderId) {
        double remainMoney = 0.00;
        //1、app_order_card表中返回卡号，用户初始金额
        AppOrderCard appOrderCard = appOrderCardMapper.getAppOrderCard(orderId);
        if (appOrderCard != null) {
            //2、app_consume表中返回vip消费次数
            int count = appConsumeMapper.getCountCosume(appOrderCard.getCardNo());
            //3、计算用户vip卡剩余金额
            remainMoney = (float) (appOrderCard.getInitMoney() - count * 150 - 100);
            if (remainMoney < 0) {
                remainMoney = 0.00;
            }

        }
        System.out.println("float remainMoney=" + remainMoney);
        return remainMoney;
    }


    /**
     * 记录退卡流水号
     */
    @Override
    public int insertBackCardInfo(AppCardBack appCardBack) {
        // TODO Auto-generated method stub
    	int flag =0;
    	try {
    		appCardBackMapper.insertBackCard(appCardBack);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			flag =1;
		}
        
        return flag;
    }


    /**
     * 通过订单号在流水表中查询用户的退款账户
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
     * 根据订单ID获取订单金额
     *
     * @param orderId
     * @return
     */
    @Override
    public AppOrders getOrderDetailByOrderId(String orderId) {
        return appOrdersMapper.getOrderDetailByOrderId(orderId);
    }



    /**
     * 根据手机号查询用户VIP卡使用信息和订单详细信息
     *
     * @param phone 手机号
     * @param code
     * @return
     */
    @Override
    public BaseMsgInfo getVipCardUseAndOrder(String phone, String code, String vipCardNo) {
        //1、校验用户和手机验证码
        boolean flag = validateCodeManager.validateSendCode(phone, MsgType.SELECT, code);
        if (!flag)
            return new BaseMsgInfo().setCode(-1).setMsg("验证码校验失败");
        //2、查询用户信息和订单信息
        List<VipCardUseDetailInfo> vipCardUseDetailInfoList = appOrderCardMapper.getVipCardUseDetailInfo(phone, vipCardNo);
        VipCardUseDetailInfo vipCardUseDetailInfo = vipCardUseDetailInfoList == null || vipCardUseDetailInfoList.isEmpty() ? new VipCardUseDetailInfo()
                : vipCardUseDetailInfoList.get(0);
        if (vipCardUseDetailInfo == null || StringUtils.isBlank(vipCardUseDetailInfo.getVipCardNo())) {
            return BaseMsgInfo.msgFail("暂未查询到此VIP卡相关信息");
        }
        getVipCardUseInfo(vipCardUseDetailInfo);
        return BaseMsgInfo.success(vipCardUseDetailInfo);
    }

    /**
     * 计算退款相关信息
     *
     * @param vipCardUseDetailInfo
     */
    private void getVipCardUseInfo(VipCardUseDetailInfo vipCardUseDetailInfo) {
        //3、查询VIP使用信息
        List<AppConsume> list = consumeManager.getConsumesByVipNo(vipCardUseDetailInfo.getVipCardNo());
        float usedMoney = 0;
        if (list != null && list.isEmpty()) {
            for (AppConsume appConsume : list) {
                usedMoney = usedMoney + appConsume.getConsumeMoney();
            }
        }
        float realMoney = (float) (usedMoney * 0.8);
        float remainMoney = vipCardUseDetailInfo.getOrderMoney() - realMoney - 100;
        if (remainMoney < 0) {
            remainMoney = 0;
        }
        vipCardUseDetailInfo.setReturnMoney(remainMoney);
        vipCardUseDetailInfo.setRealMoney(realMoney);
        vipCardUseDetailInfo.setReturnInfo("您已免费享受了价值" + usedMoney + "元的VIP室服务,若退卡需扣除该费用。亿出行仅收取该费用的80%作为服务费。");
        vipCardUseDetailInfo.setUsedMoney(usedMoney);
        vipCardUseDetailInfo.setCardUseList(list);
        vipCardUseDetailInfo.setSaleRate("80%");
    }


    /* (non-Javadoc)
     * @see com.jianfei.core.service.order.OrderManager#selectOrder(java.util.Map)
     */
    @Override
    public List<Map<String, Object>> selectOrder(Map<String, Object> map) {
        return appOrdersMapper.selectOrder(map);
    }

    /**
     * 更新用户付款状态
     * 并发送激活卡消息
     *
     * @return
     */
    @Override
    public BaseMsgInfo updatePayState(AppOrders appOrders) {
        log.info("更新订单状态:"+appOrders.getOrderId());
        if (StringUtils.isBlank(appOrders.getOrderId())){
            return BaseMsgInfo.msgFail("订单号不存在");
        }
        //1、查询订单是否支付
        AppOrders order=appOrdersMapper.getOrderDetailByOrderId(appOrders.getOrderId());
        if (VipOrderState.ALREADY_PAY.getName() == order.getOrderState()) {
            return BaseMsgInfo.success(true);
        }
        if (!(VipOrderState.NOT_PAY.getName() == order.getOrderState())){
            return BaseMsgInfo.msgFail("订单状态已更改");
        }
        //2、选择性更新订单信息
        appOrders.setOrderState(VipOrderState.ALREADY_PAY.getName());
       // appOrders.setPayTime(new Date());
        int num = appOrdersMapper.updateByPrimaryKeySelective(appOrders);

        //更新VIP用户激活
        vipUserManager.updateUserSate(order.getCustomer().getPhone(),VipUserSate.ACTIVE);
        //更新VIP卡状态为待激活
        AppVipcard vipcard=new AppVipcard();
        vipcard.setCardNo(order.getVipCards().get(0).getCardNo());
        vipcard.setCardState(VipCardState.TO_ACTIVATE.getName());
        vipCardManager.updateVipCard(vipcard);
        //构建消息体 并放入消息队列
        ServiceMsgBuilder msgBuilder=new ServiceMsgBuilder().setUserPhone(order.getCustomer().getPhone()).setMsgType(MsgType.ACTIVE_CARD.getName()).
                setVipCardNo(order.getVipCards().get(0).getCardNo()).setUserName(order.getCustomer().getCustomerName());
        //放入消息队列
        log.info(msgBuilder);
         queueManager.sendMessage(msgBuilder);
        if (num > 0) {
            return BaseMsgInfo.success(true);
        } else {
            return BaseMsgInfo.msgFail("确认付款失败");
        }
    }

    /**
     * APP端添加退卡信息
     *
     * @param appCardBack 退卡信息
     * @return
     */
    @Override
    public synchronized BaseMsgInfo addBackCardInfo(AppCardBack appCardBack) {
        log.info("提交退卡信息");
        log.info(appCardBack);
        //1、根据订单号查询订单信息
        AppOrders orders = getOrderDetailByOrderId(appCardBack.getOrderId());
        if (orders == null || StringUtils.isBlank(orders.getOrderId())) {
            return BaseMsgInfo.msgFail("订单不存在");
        }

        //2、查询用户信息和订单信息
        if (VipOrderState.NOT_PAY.getName() == orders.getOrderState()) {
            return BaseMsgInfo.msgFail("订单未付款");
        }else if (!(VipOrderState.ALREADY_PAY.getName() == orders.getOrderState())){
            return BaseMsgInfo.msgFail("订单已经申请退款");
        }

        // 2、重新计算可退余额 校验是否正确
        log.info("重新计算可退余额 校验是否正确");
        VipCardUseDetailInfo useDetailInfo=new VipCardUseDetailInfo();
        useDetailInfo.setOrderMoney(orders.getPayMoney());
        useDetailInfo.setVipCardNo(appCardBack.getCardNo());
        getVipCardUseInfo(useDetailInfo);
        appCardBack.setMoney(useDetailInfo.getReturnMoney());
        appCardBack.setServiceMoney(useDetailInfo.getUsedMoney());
        appCardBack.setSafeMoney(100);
        User user=saleUserManager.getSaleUser(appCardBack.getCreaterId());
        appCardBack.setCustomerName(user.getName());

        //3、插入数据库
       boolean temp= cardBackManager.addOrUpdateCardBackInfo(appCardBack);
        ServiceMsgBuilder msgBuilder=new ServiceMsgBuilder().setUserPhone(orders.getCustomer().getPhone()).
                setVipCardNo(orders.getVipCards().get(0).getCardNo()).setUserName(orders.getCustomer().getCustomerName());
        JSONObject object=new JSONObject();
        object.put("returnMoney",useDetailInfo.getRealMoney());
        msgBuilder.setMsgBody(object.toJSONString());
        //添加订单状态为已退款
        if (StringUtils.isNotBlank(appCardBack.getAgreementUrl())) {
            //紧急退卡 更改订单状态为已退款 和申请方式为紧急
            orders.setOrderState(VipOrderState.ALREADY_REFUND.getName());
            msgBuilder.setMsgType(MsgType.RIGHT_BACK_CARD.getName());
        } else {
            //审核通过
            orders.setOrderState(VipOrderState.AUDIT_PASS.getName());
            msgBuilder.setMsgType(MsgType.BACK_CARD_APPLY.getName());
        }
        log.info("更改VIP卡状态");
        AppVipcard vipcard=new AppVipcard();
        vipcard.setCardNo(orders.getVipCards().get(0).getCardNo());
        vipcard.setCardState(VipCardState.BACK_CARD.getName());
        log.info(vipcard);
        vipUserManager.updateUserSate(orders.getCustomer().getPhone(),VipUserSate.NOT_ACTIVE);
        log.info("更改用户状态为不可用");
        vipCardManager.updateVipCard(vipcard);
        //APP申请
        orders.setApplyType(ApplyBackCardMethod.SCENE_APPLY.getName());
        appOrdersMapper.updateByPrimaryKeySelective(orders);

        log.info("发送消息");
        log.info(msgBuilder);
        queueManager.sendMessage(msgBuilder);
        return temp ? BaseMsgInfo.success(true) : BaseMsgInfo.fail("退卡信息添加失败");
    }

    /**
     * 根据订单号返回订单基本信息
     */
    @Override
    public AppOrders getOrderInfoByOrderId(String orderId) {
        // TODO Auto-generated method stub
        return appOrdersMapper.selectByPrimaryKey(orderId);
    }

	/**
	 * 查询需要开发票的订单信息
	 */
	@Override
	public PageInfo<OrderShowInfoDto> invoicePageList(int pageNo, int pageSize,Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNo, pageSize);
		List<OrderShowInfoDto> list = appOrdersMapper.invoicePageList(map);
		PageInfo<OrderShowInfoDto> pageInfo = new PageInfo(list);
		return pageInfo;
	}

	/**
	 * 根据order_id返回订单基本信息
	 */
	@Override
	public AppOrders selectByPrimaryKey(String orderId) {
		// TODO Auto-generated method stub
		return appOrdersMapper.selectByPrimaryKey(orderId);
	}

	/**
	 * 根据ordrId查询订单卡表信息
	 */
	@Override
	public AppOrderCard selectByOrderId(String orderId) {
		// TODO Auto-generated method stub
		return appOrderCardMapper.selectByOrderId(orderId);
	}

	/**
	 * 根据卡号返回所有的消费记录
	 */
	@Override
	public List<AppConsume> selectByVipCardNo(String cardNo) {
		// TODO Auto-generated method stub
		return appConsumeMapper.selectByVipCardNo(cardNo);
	}

	/**
	 * 不分页，返回所有满足条件的数据
	 */
	@Override
	public List<OrderShowInfoDto> simplePage(Map<String, Object> params) {
		// TODO Auto-generated method stub
		List<OrderShowInfoDto> list = appOrdersMapper.get(params);
		return list;
	}
	
	/**
	 * 发送短信（录入退款信息、完成退款信息）
	 * @param msgBuilder
	 */
	public void sendMessageOfOrder(ServiceMsgBuilder msgBuilder){
		 queueManager.sendMessage(msgBuilder);
	}

    /**
     * 取消VIP卡退卡记录
     *
     * @param phone     手机号
     * @param code      验证码
     * @param vipCardNo
     * @return
     */
    @Override
    public BaseMsgInfo removeBackCard(String phone, String code, String vipCardNo,String orderId) {
        //1、校验用户和手机验证码
        boolean flag = validateCodeManager.validateSendCode(phone, MsgType.SELECT, code);
        if (!flag)
            return new BaseMsgInfo().setCode(-1).setMsg("验证码校验失败");
        //2、查询卡状态及卡对应的订单状态
        AppOrders orders=getOrderDetailByOrderId(orderId);

        //3、如果订单状态时已退款 则提示用户退卡申请失败 已退卡
        if (orders == null || orders.getOrderState() == VipOrderState.ALREADY_REFUND.getName()) {
            return BaseMsgInfo.msgFail("订单不存在 或已退款");
        }

        //4、判断订单状态是否在可取消退卡范围内
        if (!(orders.getOrderState() == VipOrderState.BEING_AUDITED.getName() ||
                orders.getOrderState() == VipOrderState.AUDIT_PASS.getName())){
            return BaseMsgInfo.msgFail("订单无法取消退卡");
        }

        //5、更改订单状态为已付款 逻辑删除退卡信息
        orders.setOrderState(VipOrderState.ALREADY_PAY.getName());
        appOrdersMapper.updateByPrimaryKeySelective(orders);
        cardBackManager.deleteCardBackInfo(orderId);

        //6、给用户发送取消退卡成功短信
        ServiceMsgBuilder msgBuilder=new ServiceMsgBuilder().setUserPhone(orders.getCustomer().getPhone()).
                setVipCardNo(vipCardNo).setUserName(orders.getCustomer().getCustomerName());
        msgBuilder.setMsgType(MsgType.REMOVE_BACK_CARD.getName());
        log.info("发送消息");
        log.info(msgBuilder);
        queueManager.sendMessage(msgBuilder);
        return BaseMsgInfo.success(true);
    }

    /**
     * 重新激活VIP卡
     *
     * @param phone     手机号
     * @param vipCardNo
     * @param orderId   订单ID
     */
    @Override
    public BaseMsgInfo activeCard(String phone, String vipCardNo, String orderId) {
        //TODO 查询订单详细信息 订单是否付款
        //TODO 查询卡状态是否为激活失败
        //TODO 重新发送激活消息
        return BaseMsgInfo.success(true);
    }
}
