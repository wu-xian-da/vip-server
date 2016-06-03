package com.jianfei.core.common.utils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

public interface ServiceRequest {
	 public String sendPost(String url, String sendParam) throws UnrecoverableKeyException, 
	 									KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException;
}
