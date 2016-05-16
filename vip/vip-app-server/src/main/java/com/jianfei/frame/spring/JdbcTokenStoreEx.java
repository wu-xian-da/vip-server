package com.jianfei.frame.spring;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * Created by XiaFan on 15-8-26.
 * Catch IncorrectResultSizeDataAccessException and delete conflictive data
 */

public class JdbcTokenStoreEx extends JdbcTokenStore {

    private static final Log LOG_EX = LogFactory.getLog(JdbcTokenStoreEx.class);

    private static final String DEFAULT_ACCESS_TOKEN_DELETE_FROM_AUTHENTICATION_SELECT_STATEMENT = "delete from oauth_access_token where authentication_id = ?";

    private String deleteAccessTokenFromAuthenticationSql = DEFAULT_ACCESS_TOKEN_DELETE_FROM_AUTHENTICATION_SELECT_STATEMENT;

    private AuthenticationKeyGenerator authenticationKeyGeneratorEx = new DefaultAuthenticationKeyGenerator();

    private final JdbcTemplate jdbcTemplateEx;

    public JdbcTokenStoreEx(DataSource dataSource) {
        super(dataSource);
        this.jdbcTemplateEx = new JdbcTemplate(dataSource);
    }

    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken accessToken = null;

        try {
            accessToken = super.getAccessToken(authentication);
        }
        catch (IncorrectResultSizeDataAccessException e) {
            LOG_EX.warn("Delete conflictive access token for authentication" + authentication);
            jdbcTemplateEx.update(deleteAccessTokenFromAuthenticationSql, authenticationKeyGeneratorEx.extractKey(authentication));
        }

        return accessToken;
    }

    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken accessToken = null;

        try {
            accessToken = super.readAccessToken(tokenValue);
        }
        catch (IncorrectResultSizeDataAccessException e) {
            LOG_EX.warn("Delete conflictive access token for token" + tokenValue);
            this.removeAccessToken (tokenValue);
        }

        return accessToken;
    }

    public OAuth2Authentication readAuthentication(String token) {
        OAuth2Authentication authentication = null;

        try {
            authentication = super.readAuthentication(token);
        }
        catch (IncorrectResultSizeDataAccessException e) {
            LOG_EX.warn("Delete conflictive access token for token" + token);
            removeAccessToken(token);
        }

        return authentication;
    }

    public OAuth2RefreshToken readRefreshToken(String token) {
        OAuth2RefreshToken refreshToken = null;

        try {
            refreshToken = super.readRefreshToken(token);
        }
        catch (IncorrectResultSizeDataAccessException e) {
            LOG_EX.warn("Delete conflictive refresh token for token" + token);
            removeRefreshToken(token);
        }

        return refreshToken;
    }

    public OAuth2Authentication readAuthenticationForRefreshToken(String value) {
        OAuth2Authentication authentication = null;

        try {
            authentication = super.readAuthenticationForRefreshToken(value);
        }
        catch (IncorrectResultSizeDataAccessException e) {
            LOG_EX.warn("Delete conflictive refresh token for token" + value);
            removeRefreshToken(value);
        }

        return authentication;
    }

}

