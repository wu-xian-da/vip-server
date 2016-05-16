
package com.jianfei.frame.support;

/**
 * @author @author libinsong1204@gmail.com
 * @date 2012-11-11 上午9:46:36
 */
public enum GrantType {
	AUTH_CODE("authorization_code"),
	REFRESH_TOKEN("refresh_token"),
	IMPLICIT("implicit"),
	CLIENT("client_credentials"),
	PASSWORD("password");
	
	private String name;
    
	public String getName(){
   	 	return this.name;
    }
    
    private GrantType(String name){
   	 	this.name = name;
    }
}
