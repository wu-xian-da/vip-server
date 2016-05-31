package com.jianfei.user;

import com.jianfei.common.BaseMsgInfo;
import com.jianfei.core.bean.User;
import com.jianfei.core.service.user.impl.SaleUserManagerImpl;
import com.jianfei.dto.VipTestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 销售人员Controller
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/17 15:53
 */
@Controller
@RequestMapping(value = "saler")
public class SalerUserController {

    @Autowired
    private SaleUserManagerImpl saleUserManager;


    /**
     * 用户登录
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public BaseMsgInfo vipRoomInfo(@RequestParam(value = "uno", required = true) String uno,
                                   @RequestParam(value = "password", required = true) String password
    ) {
        boolean validate= saleUserManager.validatePassword(uno,password);
        if (validate){

            VipTestVo vipTestVo=new VipTestVo("4219a91f-45d5-4a07-9e8e-3acbadd0c23e","d41df9fd-3d36-4a20-b0b7-1a1883c7439d",
                    "read write trust","bearer",43199);
            return BaseMsgInfo.success(vipTestVo);
        }else {
            return BaseMsgInfo.fail("");
        }
    }

    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping(value = "/getUser")
    @ResponseBody
    public BaseMsgInfo getUser(@RequestParam(value = "uno", required = true) String uno
    ) {
        User saleUser= saleUserManager.getSaleUser(uno);
        return BaseMsgInfo.success(saleUser);
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping(value = "changePassword")
    @ResponseBody
    public BaseMsgInfo update(@RequestParam(value = "uno", required = false) String uno,
                             @RequestParam(value = "password", required = false) String password,
                             @RequestParam(value = "newPassword", required = false) String newPassword) {
        boolean flag = saleUserManager.updatePassword(uno, password, newPassword);
        return BaseMsgInfo.success(flag);
    }

    /**
     * 用户登录
     * @param xmlObj 请求格式如下
     * <?xml version="1.0" encoding="utf-8"?>
		<COD-MS> 
		  <SessionHead> 
		    <Version>V1</Version>  
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
    @RequestMapping(value = "/poslogin")
    @ResponseBody
    public BaseMsgInfo yeepayLogin(@RequestParam(value = "uno", required = true) String xmlObj) {
    	
        boolean validate= saleUserManager.validatePassword("","");
        if (validate){

            VipTestVo vipTestVo=new VipTestVo("4219a91f-45d5-4a07-9e8e-3acbadd0c23e","d41df9fd-3d36-4a20-b0b7-1a1883c7439d",
                    "read write trust","bearer",43199);
            return BaseMsgInfo.success(vipTestVo);
        }else {
            return BaseMsgInfo.fail("");
        }
    }

}
