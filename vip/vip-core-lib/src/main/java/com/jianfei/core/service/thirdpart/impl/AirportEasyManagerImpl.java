package com.jianfei.core.service.thirdpart.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.bean.AppConsume;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.impl.HttpServiceRequest;
import com.jianfei.core.dto.AirportEasyUseInfo;
import com.jianfei.core.dto.exception.GetQrcodeException;
import com.jianfei.core.service.thirdpart.AirportEasyManager;
import com.tencent.common.MD5;

/**
 * 空港易行相关接口实现
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 18:05
 */
@Service
public class AirportEasyManagerImpl implements AirportEasyManager{
	protected Logger logger = LoggerFactory.getLogger(getClass());
//	public static final String KONGGANG_URL = "http://apitest.airportcip.com:89/API/shanglvditui/ShanglvdituiPD.svc/";
//	public static final String KONGGANG_PARTNER = "20160607001";
//	public static final String KONGGANG_KEY = "S1-L6-D0-T1-G2-S7";
	
    /**
     * 激活VIP卡
     *
     * @param vipCardNo VIP卡号
     * @param userPhone VIP用户手机号
     * @param userName  VIP用户名
     * @return
     * @throws IOException 
     * @throws KeyStoreException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     * @throws UnrecoverableKeyException 
     */
    @Override
    public boolean activeVipCard(String vipCardNo, String userPhone, String userName) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
    	GloabConfig.getInstance();
		String sign = sign("partner="+GloabConfig.getConfig("konggang.partner")+"&verify_code="+vipCardNo+"&mobile="+userPhone+GloabConfig.getConfig("konggang.key"));
    	String result = HttpServiceRequest.getInstance().sendGet(GloabConfig.getConfig("konggang.url")+"confirmcardphone?"+
    															"partner="+ GloabConfig.getConfig("konggang.partner") + 
    															"&verify_code="+vipCardNo + 
    															"&mobile="+userPhone + 
    															"&name="+ userName + 
    															"&sign=" + sign);
    	
    	logger.info("active_vip_card:"+result);
    	System.out.println("active_vip_card1:"+result);
		JSONObject obj = JSON.parseObject(result);
		return obj.get("code").equals("00")?true:false;
    }

    /**
     * 禁用VIP卡
     *
     * @param vipCardNo vip卡号
     * @return
     * @throws IOException 
     * @throws KeyStoreException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     * @throws UnrecoverableKeyException 
     */
    @Override
    public boolean disabledVipCard(String vipCardNo) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
    	String result = null;
    	String sign = sign("partner="+GloabConfig.getConfig("konggang.partner")+"&verify_code="+vipCardNo+GloabConfig.getConfig("konggang.key"));
    	result = HttpServiceRequest.getInstance().sendGet(GloabConfig.getConfig("konggang.url")+"enableDis?"+
    													"partner="+GloabConfig.getConfig("konggang.partner")+
    													"&verify_code="+vipCardNo +
    													"&sign=" + sign);
    	
    	logger.info("disable_vip_card:"+result);
    	System.out.println("disable_vip_card1:"+result);
		JSONObject obj = JSON.parseObject(result);
		return obj.get("code").equals("00")?true:false;

    }

    /**
     * 获取所有Vip卡使用记录
     *
     * @return
     */
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//小写的mm表示的是分钟  
    
    @Override
    public AirportEasyUseInfo getVipCardUseInfo() throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
    	String result = null;
    	String randomStr = getRandomStringByLength(18);
    	AirportEasyUseInfo aeInfo = null;
    	String sign = sign("random="+randomStr+"&partner="+GloabConfig.getConfig("konggang.partner")+GloabConfig.getConfig("konggang.key"));
    	
    	result = HttpServiceRequest.getInstance().sendGet(GloabConfig.getConfig("konggang.url")+"readcheckindata?"+
    													"random="+randomStr+
    													"&partner="+GloabConfig.getConfig("konggang.partner")+
    													"&sign=" + sign);

   		JSONObject obj = JSON.parseObject(result);
		int return_result = (Integer)obj.get("result");
		if (return_result == 0){
		    aeInfo = new AirportEasyUseInfo();
			
			JSONObject return_data = (JSONObject)obj.get("data");
			int countNo = (Integer)return_data.get("CountNo");
			int recordNo = (Integer)return_data.get("RecordNo");
			String batchNo = (String)return_data.get("BatchNo");
			aeInfo.setCountNo(countNo);
			aeInfo.setRecordNo(recordNo);
			aeInfo.setBatchNo(batchNo);
			List<AppConsume> cosumeList = new ArrayList<AppConsume>();
			
			JSONArray dataArr = return_data.getJSONArray("data");
			int dataArrSize = dataArr.size();
			for(int i=0;i<dataArrSize;i++){
				JSONObject data = dataArr.getJSONObject(i);
    			String code = (String)data.get("code");//核销码
    			int count = (Integer)data.get("count");//核销次数
    			String info = (String)data.get("info");//核销地点
    			String checkintime = (String)data.get("checkintime");//核销时间
    			AppConsume ac = new AppConsume();
    			ac.setCardNo(code);
    			ac.setConsumeMoney(200f);
    			Date checkinDate = null;
    			try {
    				checkinDate = sdf.parse(checkintime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
    			
    			ac.setConsumeTime(checkinDate);
    			ac.setViproomName(info);
    			cosumeList.add(ac);
			}
			aeInfo.setConsumeList(cosumeList);
		}
		return aeInfo;
    }

    /**
     * 确认已收到VIP卡使用记录
     *
     * @param batchNo 批次号
     * @return
     * @throws IOException 
     * @throws KeyStoreException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     * @throws UnrecoverableKeyException 
     */
    @Override
    public boolean sendConfirmInfo(String batchNo) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
    	String ack = null;
    	Map<String,String> ackParam = new HashMap<String,String>();
    	ackParam.put("BatchNo", batchNo);
    	ackParam.put("partner ", GloabConfig.getConfig("konggang.partner"));
    	String ackSign = sign("BatchNo="+batchNo+"&partner="+GloabConfig.getConfig("konggang.partner")+GloabConfig.getConfig("konggang.key"));
    	ackParam.put("sign", ackSign);
		ack = HttpServiceRequest.getInstance().sendGet(GloabConfig.getConfig("konggang.url")+"DataConfirm?"+
													"BatchNo="+batchNo+
													"&partner="+GloabConfig.getConfig("konggang.partner")+
													"&sign=" + ackSign);
		JSONObject obj = JSON.parseObject(ack);
		return obj.get("code").equals("00")?true:false;
    }
    
    /**
     * 判定手机号和姓名状态
     * @param userName 姓名
     * @param userPhone 手机号
     * @return
     */
	@Override
	public boolean vipuserStatus(String userName, String userPhone) {
    	GloabConfig.getInstance();
		String sign = sign("partner="+GloabConfig.getConfig("konggang.partner")+"&mobile="+userPhone + GloabConfig.getConfig("konggang.key"));
    	String result = HttpServiceRequest.getInstance().sendGet(GloabConfig.getConfig("konggang.url")+"VerifyUser?"+
    															"partner="+ GloabConfig.getConfig("konggang.partner") + 
    															"&mobile="+userPhone + 
    															"&name="+userName + 
    															"&sign=" + sign);
    	
    	logger.info("vipuserStatus:"+result);
    	System.out.println("vipuserStatus:"+result);
		JSONObject obj = JSON.parseObject(result);
		return obj.get("code").equals("00")?true:false;
	}
	
    /**
     * 判定VIP卡是否已绑定
     * @param vipCardNo 卡号
     * @return
     */
	@Override
	public boolean cardBindStatus(String vipCardNo) {
    	String result = null;
    	String sign = sign("partner="+GloabConfig.getConfig("konggang.partner")+"&verify_code="+vipCardNo+GloabConfig.getConfig("konggang.key"));
    	result = HttpServiceRequest.getInstance().sendGet(GloabConfig.getConfig("konggang.url")+"VerifyCard?"+
    													"partner="+GloabConfig.getConfig("konggang.partner")+
    													"&verify_code="+vipCardNo +
    													"&sign=" + sign);
    	
    	logger.info("cardBindStatus:"+result);
    	System.out.println("cardBindStatus:"+result);
		JSONObject obj = JSON.parseObject(result);
		return obj.get("code").equals("00")?true:false;
	}

    /**
     * 获取核销二维码
     * @param vipCardNo 卡号
     * @return 
     * @throws Exception 
     */
	@Override
	public String getQrcode(String vipCardNo) throws GetQrcodeException {
    	String result = null;
    	String sign = sign("partner="+GloabConfig.getConfig("konggang.partner")+"&verify_code="+vipCardNo+GloabConfig.getConfig("konggang.key"));
    	result = HttpServiceRequest.getInstance().sendGet(GloabConfig.getConfig("konggang.url")+"GetCardUuid?"+
    													"partner="+GloabConfig.getConfig("konggang.partner")+
    													"&verify_code="+vipCardNo +
    													"&sign=" + sign);
    	
    	logger.info("getQrcode:"+result);
    	System.out.println("getQrcode:"+result);
		JSONObject obj = JSON.parseObject(result);
		if (obj.get("code").equals("00"))
			return obj.get("uuid").toString();
		else
			throw new GetQrcodeException("获取核销码失败");
	}
    
    public String sign(String str){
    	return MD5.MD5Encode(str).toUpperCase();
    }
    
    /**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public  String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取激活码，每次返回一个码
     */
	@Override
	public boolean getCardCode(String tradeNo)
			throws UnrecoverableKeyException, KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException, IOException {
    	String sign = sign("out_trade_no="+tradeNo + "&partner="+ GloabConfig.getConfig("konggang.partner")+"&sku="+"0125"+GloabConfig.getConfig("konggang.key"));
    	String result = HttpServiceRequest.getInstance().sendGet(GloabConfig.getConfig("konggang.url")+"add?"+
    															"out_trade_no="+ tradeNo + 
    															"&partner="+GloabConfig.getConfig("konggang.partner") + 
    															"&sku="+"0125" + 
    															"&sign=" + sign);
		JSONObject obj = JSON.parseObject(result);
		System.out.println(obj.get("verify_code").toString());
		return obj.get("code").equals("00")?true:false;
//		if (obj.get("code").equals("00")){
//			return true;
//		}
//		else
//			return false;
	}
	

}
