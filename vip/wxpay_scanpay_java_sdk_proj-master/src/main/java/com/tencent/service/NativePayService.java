package com.tencent.service;

import com.tencent.common.*;
import com.tencent.protocol.native_protocol.NativePayReqData;
import com.tencent.protocol.pay_protocol.ScanPayReqData;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 16:03
 */
public class NativePayService extends BaseService{

    public NativePayService() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(Configure.NATIVE_API);
    }

    /**
     * 请求支付服务
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String request(NativePayReqData nativePayReqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(nativePayReqData);

        return responseString;
    }
}
