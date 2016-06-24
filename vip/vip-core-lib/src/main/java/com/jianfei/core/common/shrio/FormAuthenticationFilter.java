package com.jianfei.core.common.shrio;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfei.core.bean.AriPort;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.security.shiro.ShiroUtils;
import com.jianfei.core.common.shrio.ShiroDbRealm.Principal;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.base.AriPortManager;
import com.jianfei.core.service.sys.RoleManager;
import com.jianfei.core.service.sys.UserManaer;

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
	public static final String CAPTCHA_ERROR_MESSAGE = "captcha_error_message";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
	private String messageParam = DEFAULT_MESSAGE_PARAM;

	@Autowired
	private UserManaer<User> userManaer;

	@Autowired
	private RoleManager roleManager;

	@Autowired
	private AriPortManager<AriPort> ariPortManager;

	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		if (password == null) {
			password = "";
		}
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr(servletRequest);
		String captcha = getCaptcha(request);
		// 校验登录验证码
		Session session = ShiroUtils.getSession();
		session.setAttribute(CAPTCHA_ERROR_MESSAGE, "");
		String code = (String) session
				.getAttribute(FormAuthenticationFilter.DEFAULT_CAPTCHA_PARAM);
		code = code == null ? "" : code;
		if (captcha == null || !code.equalsIgnoreCase(captcha)) {

			// session.setAttribute(CAPTCHA_ERROR_MESSAGE, "randomCodeError");
		}
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
		User user = userManaer.getUserByName(principal.getLoginName());
		// 存放角色
		user.setRoles(roleManager.selectRoleByUserId(user.getId()));
		// 存放机场
		MessageDto<List<AriPort>> messageDto = ariPortManager
				.selectAriportByUserId(user.getId());
		if (messageDto.isOk()) {
			user.setAripors(messageDto.getData());
		}
		ShiroUtils.getSession().setAttribute(GloabConfig.SESSION_USER, user);
		ShiroUtils.getSession().setAttribute(GloabConfig.GLOAB_NAME,
				GloabConfig.getConfig("gloabName.name"));
		return super.onLoginSuccess(token, subject, request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginFailure
	 * (org.apache.shiro.authc.AuthenticationToken,
	 * org.apache.shiro.authc.AuthenticationException,
	 * javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */

	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		String errorCode = (String) ShiroUtils.getSession().getAttribute(
				CAPTCHA_ERROR_MESSAGE);
		String className = e.getClass().getName(), message = "";
		if (!StringUtils.isEmpty(errorCode)) {
			message = "验证码错误...";
			className = "ErrorCaptcha";
		} else if (IncorrectCredentialsException.class.getName().equals(
				className)
				|| UnknownAccountException.class.getName().equals(className)) {
			message = "用户或密码错误, 请重试.";
		} else if (e.getMessage() != null
				&& StringUtils.startsWith(e.getMessage(), "msg:")) {
			message = StringUtils.replace(e.getMessage(), "msg:", "");
		} else {
			message = "系统出现点问题，请稍后再试！";
		}
		request.setAttribute(getFailureKeyAttribute(), className);
		request.setAttribute(DEFAULT_ERROR_MESSAGE, message);
		return true;
	}
}