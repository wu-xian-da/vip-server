package com.jianfei.core.common.shrio;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.jianfei.core.bean.Resource;
import com.jianfei.core.bean.Role;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.security.shiro.ShiroUtils;
import com.jianfei.core.common.utils.SpringContextHolder;
import com.jianfei.core.service.SystemService;

/**
 *
 * @Description:登入认证，权限拦截
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月17日 下午12:52:25
 * 
 * @version 1.0.0
 *
 */
public class ShiroDbRealm extends AuthorizingRealm {

	public SystemService systemService = SpringContextHolder
			.getBean(SystemService.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache
	 * .shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		ShrioUser shrioUser = (ShrioUser) principals;
		// 创建简单的授权模块
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		List<Role> roles = shrioUser.getUser().getRoles();
		// 添加用户角色
		for (Role role : roles) {
			authorizationInfo.getRoles().add(role.getName());
		}
		// 获取资源菜单
		List<Resource> resources = systemService.getResourceMapper()
				.findResourceByUserId(shrioUser.getUser().getId());
		// 添加资源访问权限
		for (Resource resource : resources) {
			authorizationInfo.getStringPermissions().add(resource.getName());
		}
		return authorizationInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.
	 * apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// token中储存着输入的用户名和密码
		try {
			UsernamePasswordToken authcToken = (UsernamePasswordToken) token;

			// 校验登录验证码
			Session session = ShiroUtils.getSession();
			String code = (String) session
					.getAttribute(FormAuthenticationFilter.DEFAULT_CAPTCHA_PARAM);
			if (authcToken.getCaptcha() == null
					|| !authcToken.getCaptcha().equalsIgnoreCase(code)) {
				ShiroUtils.getSession().setAttribute(
						FormAuthenticationFilter.DEFAULT_ERROR_MESSAGE,
						"验证码错误, 请重试.");
				throw new AuthenticationException("msg:验证码错误, 请重试.");
			}

			// 校验用户名密码
			User user = systemService.getUserMapper().getUserByName(
					authcToken.getUsername());

			if (null == user) {
				ShiroUtils.getSession().setAttribute(
						FormAuthenticationFilter.DEFAULT_ERROR_MESSAGE,
						"验证码错误, 请重试.");
				return null;
			}
			// 判断是否有后台的登入权限
			if (1 == user.getUserType()) {
				ShrioUser shrioUser = new ShrioUser();
				shrioUser.setUser(user);
				return new SimpleAuthenticationInfo(shrioUser,
						"f59cf5692216275b832bd98223516774",
						ByteSource.Util.bytes("refineli"), getName());
			}
			ShiroUtils.getSession()
					.setAttribute(
							FormAuthenticationFilter.DEFAULT_ERROR_MESSAGE,
							"没有后台登入权限。");
			throw new AuthenticationException("msg:验证码错误, 请重试.");
		} catch (Exception e) {
		}
		return null;
	}
}
