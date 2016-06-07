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

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.bean.AppConsume;
import com.jianfei.core.common.utils.impl.HttpServiceRequest;
import com.jianfei.core.dto.AirportEasyUseInfo;
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
	
	public static final String KONGGANG_URL = "http://apitest.airportcip.com:89/API/shanglvditui/ShanglvdituiPD.svc/";
	public static final String KONGGANG_PARTNER = "20160607001";
	public static final String KONGGANG_KEY = "S1-L6-D0-T1-G2-S7";
	
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
    	String sign = sign("partner="+KONGGANG_PARTNER+"&verify_code="+vipCardNo+"&mobile="+userPhone+KONGGANG_KEY);
    	String result = HttpServiceRequest.getInstance().sendGet(KONGGANG_URL+"confirmcardphone?"+
    															"partner="+ KONGGANG_PARTNER + 
    															"&verify_code="+vipCardNo + 
    															"&mobile="+userPhone + 
    															"&name="+ userName + 
    															"&sign=" + sign);
		JSONObject obj = JSON.parseObject(result);
		if (obj.get("code").equals("00"))
			return true;
		else
			return false;
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
    	String sign = sign("partner="+KONGGANG_PARTNER+"&verify_code="+vipCardNo+KONGGANG_KEY);
    	result = HttpServiceRequest.getInstance().sendGet(KONGGANG_URL+"enableDis?"+
    													"partner="+KONGGANG_PARTNER+
    													"&verify_code="+vipCardNo +
    													"&sign=" + sign);
		JSONObject obj = JSON.parseObject(result);
		if (obj.get("code").equals("00"))
			return true;
		else
			return false;

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
    	String sign = sign("random="+randomStr+"&partner="+KONGGANG_PARTNER+KONGGANG_KEY);
    	
    	result = HttpServiceRequest.getInstance().sendGet(KONGGANG_URL+"readcheckindata?"+
    													"random="+randomStr+
    													"&partner="+KONGGANG_PARTNER+
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
    	ackParam.put("partner ", KONGGANG_PARTNER);
    	String ackSign = sign("BatchNo="+batchNo+"&partner="+KONGGANG_PARTNER+KONGGANG_KEY);
    	ackParam.put("sign", ackSign);
		ack = HttpServiceRequest.getInstance().sendGet(KONGGANG_URL+"DataConfirm?"+
													"BatchNo="+batchNo+
													"&partner="+KONGGANG_PARTNER+
													"&sign=" + ackSign);
		JSONObject obj = JSON.parseObject(ack);
		if (obj.get("code").equals("00"))
			return true;
		else
			return false;
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

	@Override
	public boolean getCardCode(String tradeNo)
			throws UnrecoverableKeyException, KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException, IOException {
    	String sign = sign("out_trade_no="+tradeNo + "&partner="+ KONGGANG_PARTNER+"&sku="+"0125"+KONGGANG_KEY);
    	String result = HttpServiceRequest.getInstance().sendGet(KONGGANG_URL+"add?"+
    															"out_trade_no="+ tradeNo + 
    															"&partner="+KONGGANG_PARTNER + 
    															"&sku="+"0125" + 
    															"&sign=" + sign);
		JSONObject obj = JSON.parseObject(result);
		if (obj.get("code").equals("00")){
			System.out.println(obj.get("verify_code"));
			return true;
		}
		else
			return false;
	}
}
