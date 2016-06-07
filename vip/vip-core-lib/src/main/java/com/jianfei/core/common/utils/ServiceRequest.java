package com.jianfei.core.common.utils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

public interface ServiceRequest {
	 public String sendPost(String url, Map<String,String> sendParam) throws UnrecoverableKeyException, 
	 									KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException;
	 
	 public String sendGet(String url);
}
