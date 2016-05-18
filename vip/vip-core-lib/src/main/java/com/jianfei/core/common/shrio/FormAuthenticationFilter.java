/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jianfei.core.common.shrio;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import com.jianfei.core.common.security.shiro.ShiroUtils;
import com.jianfei.core.common.shrio.ShiroDbRealm.Principal;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.SpringContextHolder;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.sys.SystemService;

/**
 * 表单验证（包含验证码）过滤类
 * 
 * @author ThinkGem
 * @version 2014-5-19
 */
@Service
public class FormAuthenticationFilter extends
		org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_PARAM = "message";

	public static final String DEFAULT_ERROR_MESSAGE = "shrio_error_message";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
	private String messageParam = DEFAULT_MESSAGE_PARAM;
	public SystemService systemService = SpringContextHolder
			.getBean(SystemService.class);

	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		if (password == null) {
			password = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr((HttpServletRequest) request);
		String captcha = getCaptcha(request);
		boolean mobile = isMobileLogin(request);
		return new UsernamePasswordToken(username, password.toCharArray(),
				rememberMe, host, captcha, mobile);
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	public String getMobileLoginParam() {
		return mobileLoginParam;
	}

	protected boolean isMobileLogin(ServletRequest request) {
		return WebUtils.isTrue(request, getMobileLoginParam());
	}

	public String getMessageParam() {
		return messageParam;
	}

	/**
	 * 登录成功之后跳转URL
	 */
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}

	@Override
	protected void issueSuccessRedirect(ServletRequest request,
			ServletResponse response) throws Exception {
		WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginSuccess
	 * (org.apache.shiro.authc.AuthenticationToken,
	 * org.apache.shiro.subject.Subject, javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		Principal principal = (Principal) SecurityUtils.getSubject()
				.getPrincipal();
		ShiroUtils.getSession().setAttribute(
				GloabConfig.SESSION_USER,
				systemService.getUserMapper().getUserByName(
						principal.getLoginName()));
		return super.onLoginSuccess(token, subject, request, response);
	}

	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		String className = e.getClass().getName(), message = "";
		if (IncorrectCredentialsException.class.getName().equals(className)
				|| UnknownAccountException.class.getName().equals(className)) {
			message = ShiroUtils.getSessionAttribute(DEFAULT_ERROR_MESSAGE)
					.toString();
		} else if (e.getMessage() != null
				&& StringUtils.startsWith(e.getMessage(), "msg:")) {
			message = StringUtils.replace(e.getMessage(), "msg:", "");
		} else {
			message = "系统出现点问题，请稍后再试！";
			e.printStackTrace(); // 输出到控制台
		}
		request.setAttribute(getFailureKeyAttribute(), className);
		request.setAttribute(getMessageParam(), message);
		return true;
	}

}