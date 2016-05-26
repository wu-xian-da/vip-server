package com.jianfei.dto;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/26 9:40
 */
public class VipTestVo {

    /**
     * access_token : 4219a91f-45d5-4a07-9e8e-3acbadd0c23e
     * refresh_token : d41df9fd-3d36-4a20-b0b7-1a1883c7439d
     * scope : read write trust
     * token_type : bearer
     * expires_in : 43199
     */
    private String access_token;
    private String refresh_token;
    private String scope;
    private String token_type;
    private int expires_in;

    public VipTestVo(String access_token, String refresh_token, String scope, String token_type, int expires_in) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.scope = scope;
        this.token_type = token_type;
        this.expires_in = expires_in;
    }

    public VipTestVo() {
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public String getToken_type() {
        return token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }
}
