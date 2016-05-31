package com.jianfei.yeepay;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jianfei.core.common.utils.YeepayUtils;



/**
 
protolType:http
url:http://172.17.103.121:8081/codcustomer/hkexchange.xhtml
secKey:DFE23HLAW198820SQWE1224SDAQQ3319203945
customerNo:10040011593
employeeID:123456
type:COD201
posSn:11
orderNo:
password:222333
bankCardNo:
sendxml:<?xml version="1.0" encoding="UTF-8"?><COD-MS><SessionHead><Version>V1.0</Version><ServiceCode>COD201</ServiceCode><TransactionID>CODTESTCOD201201605300565242747</TransactionID><SrcSysID>yeepay</SrcSysID><DstSysID>CODTEST</DstSysID><ReqTime>20160530162755</ReqTime><ExtendAtt></ExtendAtt><HMAC>f116dba503648a80ca500db4cf99a256</HMAC></SessionHead><SessionBody><Employee_ID>123456</Employee_ID><Password>731982a033a5cc815ac03c8504abb748</Password><PosSn>11</PosSn><CustomerNo>10040011593</CustomerNo></SessionBody></COD-MS> 
 
 * @author leoliu
 *
 */
@Controller
@RequestMapping(value = "yeepay")
public class YeepayController {
	
    /**
     * 用户登录
     * @param xmlObj 请求格式如下
     * <?xml version="1.0" encoding="utf-8"?>
		<COD-MS> 
		  <SessionHead> 
		    <Version>V1.0</Version>  
		    <ServiceCode>COD201</ServiceCode>  
		    <TransactionID>SOUFANCOD201201507056500288432</TransactionID>  
		    <SrcSysID>yeepay</SrcSysID>  
		    <DstSysID>SOUFAN</DstSysID>  
		    <ReqTime>20150705182331</ReqTime>  
		    <ExtendAtt>12</ExtendAtt>  
		    <HMAC>7cd9b45ac13d6b27e55e3cc566f70d9b</HMAC> 
		  </SessionHead>  
		  <SessionBody> 
		    <Employee_ID>123456</Employee_ID>  
		    <Password>e10adc3949ba59abbe56e057f20f883e</Password>  
		    <PosSn>H7NL00119897</PosSn>  
		    <CustomerNo>10012075953</CustomerNo> 
		  </SessionBody> 
		</COD-MS>

     * @return 响应格式如下
     * <?xml version="1.0" encoding="utf-8"?>
		<COD-MS>
		  <SessionHead>
		    <Version>V1.0</Version>
		    <ServiceCode>COD201</ServiceCode>
		    <TransactionID>SOUFANCOD201201507056500288432</TransactionID>
		    <SrcSysID>yeepay</SrcSysID>
		    <DstSysID>SOUFAN</DstSysID>
		    <Result_Code>2</Result_Code>  
		    <Result_Msg>成 功</Result_Msg>  
		    <Resp_Time>20150705182330</Resp_Time>  
		    <ExtendAtt>
		      <Employee_ID>123456</Employee_ID>
		    </ExtendAtt>  
		    <HMAC>3d78b90547a0e08c5fbc2abe61f4afb0</HMAC> 
		  </SessionHead>  
		  <SessionBody>
		    <ExtendAtt> 
		      <Employee_Name>搜房电商</Employee_Name>  
		      <Company_Code>SOUFUN</Company_Code>  
		      <Company_Name>搜房网</Company_Name>  
		      <Company_Addr>北京</Company_Addr>  
		      <Company_Tel>00000000</Company_Tel> 
		    </ExtendAtt>
		  </SessionBody> 
		</COD-MS>
     */
    @RequestMapping(value = "/action")
    public void yeepayLogin(HttpServletRequest request,HttpServletResponse response) {
    	String send = "";//获取请求的报文
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	ServletInputStream inStream;
    	String result = "";//商户返回给易宝的报文 
    	try{
		    inStream = request.getInputStream(); //int length = 0;
	    	int len = -1;
	    	byte[] buffer = new byte[1024]; 
	    	while ((len = inStream.read(buffer)) != -1) {
	    		outputStream.write(buffer, 0, len);
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	    try {
		  send = new String(outputStream.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    
	    
	    try {
			Document document = DocumentHelper.parseText(send);
			Element root = document.getRootElement();
			Element sessionHead = root.element("SessionHead");
			Element sessionBody = root.element("SessionBody");
			String serviceCode = sessionHead.elementText("ServiceCode");
			String hmac = sessionHead.elementText("HMAC");
			String hmacresult = YeepayUtils.hmacSign(send, "DFE23HLAW198820SQWE1224SDAQQ3319203945");
	
			
			if (serviceCode.equals("COD201")){
				String eid = sessionBody.elementText("Employee_ID");
				String password = sessionBody.elementText("Password");
				//数据库验证用户
				result = YeePayResponseBuilder.buildLoginResponse(sessionHead, sessionBody, "2");
				
			}else if (serviceCode.equals("COD402")){
				String orderNo = sessionBody.elementText("OrderNo");
				//数据库获取订单
				result = YeePayResponseBuilder.buildOrderQueryResponse(sessionHead, sessionBody, "2", "20", "刘大大", "13988889999", "1980");
			}else if (serviceCode.equals("COD403")){
				String orderNo = sessionBody.elementText("OrderNo");
				//数据库获取订单
				result = YeePayResponseBuilder.buildOrderQueryResponse(sessionHead, sessionBody, "2", "20", "刘大大", "13988889999", "1980");
			}
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
	    
	    response.setContentType("text/xml; charset=utf-8");
	    response.setCharacterEncoding("utf-8");
	    try { 

	    	response.getWriter().write(result);
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    
    }
    
    
    public static class YeePayResponseBuilder{
    	public static final String hmac_key = "DFE23HLAW198820SQWE1224SDAQQ3319203945";
    	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	
    	public static String buildLoginResponse(Element sessionHead,Element sessionBody,String resultCode){
    		
			String eid = sessionBody.elementText("Employee_ID");
			String transId = sessionHead.elementText("TransactionID");
			String srcSysID = sessionHead.elementText("SrcSysID");
			String dstSysID = sessionHead.elementText("DstSysID");
    		
	    	Document   document   = DocumentHelper.createDocument(); 
	    	Element codmsEle =  document.addElement("COD-MS");
	    	Element headEle = codmsEle.addElement("SessionHead");
	    	Element verEle = headEle.addElement("Version");
	    	verEle.setText("V1.0");
	    	Element serviceCodeEle = headEle.addElement("ServiceCode");
	    	serviceCodeEle.setText("COD201");
	    	Element tranIdEle = headEle.addElement("TransactionID");
	    	tranIdEle.setText(transId);
	    	Element srcSysIDEle = headEle.addElement("SrcSysID");
	    	srcSysIDEle.setText(srcSysID);
	    	Element dstSysIDEle = headEle.addElement("DstSysID");
	    	dstSysIDEle.setText(dstSysID);
	    	Element resultCodeEle = headEle.addElement("Result_Code");
	    	resultCodeEle.setText(resultCode);
	    	Element resultMsgEle = headEle.addElement("Result_Msg");
	    	resultMsgEle.setText("成功");
	    	Element respTimeEle = headEle.addElement("Resp_Time");
	    	respTimeEle.setText(sdf.format(new Date()));
	    	Element extendAttEle = headEle.addElement("ExtendAtt");
	    	Element eidEle = extendAttEle.addElement("Employee_ID");
	    	eidEle.setText(eid);
	    	Element hmacEle = headEle.addElement("HMAC");
	    	hmacEle.setText("hmac");
	    	
	    	Element bodyEle = codmsEle.addElement("SessionBody");
	    	Element extendAtt2Ele = bodyEle.addElement("ExtendAtt");
	    	Element eid2Ele = extendAtt2Ele.addElement("Employee_ID");
	    	eid2Ele.setText(eid);
	    	Element companyCodeEle = extendAtt2Ele.addElement("Company_Code");
	    	companyCodeEle.setText("opsmart");
	    	
	    	return YeepayUtils.hmacSign(document.asXML(), hmac_key);
    	}
    	
    	public static String buildOrderQueryResponse(Element sessionHead,Element sessionBody,String resultCode,
    			String orderStatus,String receiverName,String receiverTel,String amout){
			String eid = sessionBody.elementText("EmployeeID");
			String orderNo = sessionBody.elementText("OrderNo");
			String transId = sessionHead.elementText("TransactionID");
			String srcSysID = sessionHead.elementText("SrcSysID");
			String dstSysID = sessionHead.elementText("DstSysID");
    		
	    	Document   document   = DocumentHelper.createDocument(); 
	    	Element codmsEle =  document.addElement("COD-MS");
	    	Element headEle = codmsEle.addElement("SessionHead");
	    	Element verEle = headEle.addElement("Version");
	    	verEle.setText("V1.0");
	    	Element serviceCodeEle = headEle.addElement("ServiceCode");
	    	serviceCodeEle.setText("COD402");
	    	Element tranIdEle = headEle.addElement("TransactionID");
	    	tranIdEle.setText(transId);
	    	Element srcSysIDEle = headEle.addElement("SrcSysID");
	    	srcSysIDEle.setText(srcSysID);
	    	Element dstSysIDEle = headEle.addElement("DstSysID");
	    	dstSysIDEle.setText(dstSysID);
	    	Element resultCodeEle = headEle.addElement("Result_Code");
	    	resultCodeEle.setText(resultCode);
	    	Element resultMsgEle = headEle.addElement("Result_Msg");
	    	resultMsgEle.setText("成功");
	    	Element respTimeEle = headEle.addElement("Resp_Time");
	    	respTimeEle.setText(sdf.format(new Date()));
	    
	    	Element hmacEle = headEle.addElement("HMAC");
	    	hmacEle.setText("hmac");
	    	
	    	Element bodyEle = codmsEle.addElement("SessionBody");
	    	Element itemEle = bodyEle.addElement("Item");
	    	Element eid2Ele = itemEle.addElement("EmployeeID");
	    	eid2Ele.setText(eid);
	    	Element orderEle = itemEle.addElement("OrderNo");
	    	orderEle.setText(orderNo);
	    	Element receiverOrderNoEle = itemEle.addElement("ReceiverOrderNo");
	    	receiverOrderNoEle.setText(orderNo);
	    	Element receiverNameNoEle = itemEle.addElement("ReceiverName");
	    	receiverNameNoEle.setText(receiverName);
	    	Element rceiverTelEle = itemEle.addElement("RceiverTel");
	    	rceiverTelEle.setText(receiverTel);
	    	Element amoutEle = itemEle.addElement("Amount");
	    	amoutEle.setText(amout);
	    	Element orderStatusNoEle = itemEle.addElement("OrderStatus");
	    	orderStatusNoEle.setText(orderStatus);
	    	
	    	return YeepayUtils.hmacSign(document.asXML(), hmac_key);
    	}
    	
    	public static String buildHead(Element sessionHead){
    		return "";
    	}
    	
    	public static String buildPayResponse(Element sessionHead,Element sessionBody){
    		return "";
    	}
    }
    
    
    /**
     * 订单号查询请求
     * <?xml version="1.0" encoding="utf-8"?>

		<COD-MS>
		  <SessionHead>
		    <Version>1.0</Version>
		    <ServiceCode>COD402</ServiceCode>
		    <TransactionID>XIAOMICOD402201507061825935159</TransactionID>
		    <SrcSysID>yeepay</SrcSysID>
		    <DstSysID>XIAOMI</DstSysID>
		    <ReqTime>20150706105602</ReqTime>
		    <HMAC>89ae6c731efa6a 2a064efbd4c22ce6d2</HMAC>
		  </SessionHead>
		  <SessionBody>
		    <EmployeeID>123456</EmployeeID>
		    <PosSn>Q1NL00050479</PosSn>
		    <OrderNo>201507061043001</OrderNo>
		    <CustomerNo>10040014015</CustomerNo>
		  </SessionBody>
		</COD-MS>
		
		订单号查询响应
		<?xml version="1.0" encoding="utf-8"?>
			<COD-MS>
			  <SessionHead>
			    <Version>1.0</Version>
			    <ServiceCode>COD402</ServiceCode>
			    <TransactionID>XIAOMICOD402201507061825935159</TransactionID>
			    <SrcSysID>yeepay</SrcSysID>
			    <DstSysID>TEST</DstSysID>
			    <ResultCode>2</ResultCode>
			    <ResultMsg>成功接收</ResultMsg>
			    <RespTime>20150706105603</RespTime>
			    <HMAC>8a93c0b36e1ac076aed06d0 8af292e25</HMAC>
			  </SessionHead>
			  <SessionBody>
			    <Item>
			      <EmployeeID>123456</EmployeeID>
			      <CompanyCode>8888888888</CompanyCode>
			      <CompanyTel>010- 57073725</CompanyTel>
			      <CompanyAddr>北京市朝阳区朝外大街甲 6 号万通中心 D 座 23 层</CompanyAddr>
			      <OrderNo>201507061043001</OrderNo>
			      <TicketNo>T1436150582066</TicketNo>
			      <ReceiverOrderNo>201507061043001</ReceiverOrderNo>
			      <DistributionNo>Y1436150582065</DistributionNo>
			      <ConfirmInfo/>
			      <TravelRemark>行程备注</TravelRemark>
			      <PassengerInfo>李四</PassengerInfo>
			      <ReceiverName>赵六</ReceiverName>
			      <RceiverAddr>北京市海淀区上地十街 10 号</RceiverAddr>
			      <RceiverTel>85266662</RceiverTel>
			      <Amount>0.7</Amount>
			      <BizCode>10102 138234</BizCode>
			      <SubStationCode>10020431236879</SubStationCode>
			      <SubStationName>物 流组</SubStationName>
			      <CheckedItems>行程单 2 张,机票 10 张</CheckedItems>
			      <PaDetails/>
			      <PcAutoSplit>0</PcAutoSplit>
			      <OrderStatus>23</OrderStatus>
			      <OrderStatusMsg>未签收,未签收</OrderStatusMsg>
			    </Item>
			  </SessionBody>
			</COD-MS>
     */
}
