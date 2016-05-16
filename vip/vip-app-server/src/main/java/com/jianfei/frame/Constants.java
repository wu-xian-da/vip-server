/**
 * Copyright (C) 2013-2014 科大讯飞股份有限公司 - All rights reserved.
 */
package com.jianfei.frame;

/**
 * @author libinsong1204@gmail.com
 */
public abstract class Constants {

    /**
     * 系统级参数key。
     */
    public static final String SYS_PARAM_KEY_METHOD = "method";

    public static final String SYS_PARAM_KEY_FORMAT = "format";

    public static final String SYS_PARAM_KEY_VERSION = "version";

    public static final String SYS_PARAM_KEY_LOCALE = "locale";

    public static final String SYS_PARAM_KEY_APPKEY = "appkey";

    public static final String SYS_PARAM_KEY_APPNAME = "appname";

    public static final String SYS_PARAM_KEY_DEVELOPER_ID = "developerId";

    public static final String SYS_PARAM_KEY_ACCESS_TOKEN = "access_token";

    public static final String SYS_PARAM_KEY_CLIENT_TYPE = "ct";

    public static final String SYS_PARAM_KEY_CLIENT_VERSION = "cver";

    public static final String SYS_PARAM_KEY_CALLBACK = "callback";

    public static final String SYS_PARAM_KEY_NO_AUTH = "no_auth";

    public static final String SYS_PARAM_KEY_OPEN_ID = "openId";

    public static final String SYS_PARAM_KEY_USER_ID = "user_id";

    public static final String SYS_PARAM_KEY_SKIP = "skip";

    public static final String SYS_PARAM_KEY_LIMIT = "limit";

    public static final String SYS_PARAM_KEY_PAGE = "page";

    public static final String MAIN_ERROR_CODE = "MAIN_ERROR_CODE";


    /**
     * 服务框架只支持两个http method：get & post
     */
    public static final String HTTP_METHOD_GET = "GET";

    public static final String HTTP_METHOD_POST = "POST";

    /**
     * 返回数据格式支持：xml & json
     */
    public static final String DATA_FORMAT_XML = "xml";

    public static final String DATA_FORMAT_JSON = "json";

    /**
     * 系统profile：development、test、production
     */
    public static final String PROFILE_DEVELOPMENT = "development";

    public static final String PROFILE_TEST = "test";

    public static final String PROFILE_BUILD = "build";

    public static final String PROFILE_PRODUCTION = "production";

    public static final String PROFILE_OAUTH = "oauth";

    public static final String PROFILE_JDBC = "jdbc";

    public static final String PROFILE_REDIS = "redis";
}
