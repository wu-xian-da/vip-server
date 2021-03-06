package com.jianfei.core.common.shrio;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfei.core.bean.Resource;
import com.jianfei.core.bean.Role;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.security.shiro.ShiroUtils;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.sys.ResourceManager;
import com.jianfei.core.service.sys.RoleManager;
import com.jianfei.core.service.sys.UserManaer;

/**
 *
 * @Description:登入认证，权限拦截
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月17日 下午12:52:25
 * 
 * @version 1.0.0
 *
 */
@Service
public class ShiroDbRealm extends AuthorizingRealm {

	@Autowired
	private RoleManager roelManager;

	@Autowired
	private ResourceManager resourceManager;

	@Autowired
	private UserManaer<User> userManaer;

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
		Principal principal = (Principal) getAvailablePrincipal(principals);
		// 创建简单的授权模块
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		if (principal.getUserType() == 2) {
			authorizationInfo.addStringPermission("*");
			authorizationInfo.addRole("*");
			return authorizationInfo;
		}

		Set<String> roles = new HashSet<String>();
		List<Role> roleList = roelManager.selectRoleByUserId(StringUtils
				.toLong(principal.getId()));

		// 添加用户角色
		for (Role role : roleList) {
			if (!StringUtils.isEmpty(role.getName())) {
				roles.add(role.getName());
			}
		}
		authorizationInfo.setRoles(roles);

		// 获取资源菜单
		List<Resource> resources = resourceManager.findResourceByUserId(Long
				.valueOf(principal.getId()));

		// 添加资源访问权限
		for (Resource resource : resources) {
			if (!StringUtils.isEmpty(resource.getPermission())) {
				authorizationInfo.addStringPermission(resource.getPermission());
			}
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

		UsernamePasswordToken authcToken = (UsernamePasswordToken) token;

		// 校验登录验证码
		Session session = ShiroUtils.getSession();
		String codeMsg = (String) session
				.getAttribute(FormAuthenticationFilter.CAPTCHA_ERROR_MESSAGE);
		if (!StringUtils.isEmpty(codeMsg)) {
			return null;
		}
		// 校验用户名密码
		User user = userManaer
				.getUserByName(authcToken.getUsername() == null ? StringUtils.EMPTY
						: authcToken.getUsername().trim());

		if (null == user) {
			return null;
		}
		// 判断是否有后台的登入权限
		if (1 == user.getUserType() || 2 == user.getUserType()) {
			return new SimpleAuthenticationInfo(new Principal(user, false,
					user.getUserType()), user.getPassword(),
					ByteSource.Util.bytes(user.getSalt()), getName());
		}
		ShiroUtils.getSession().setAttribute(
				FormAuthenticationFilter.DEFAULT_ERROR_MESSAGE, "没有后台登入权限。");
		throw new AuthenticationException("msg:验证码错误, 请重试.");

	}

	/**
	 * cleanCache(清除缓存) void
	 * 
	 * @version 1.0.0
	 */
	public void cleanCache() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject()
				.getPrincipals();
		super.clearCache(principalCollection);
	}

	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;

		private String id; // 编号
		private String loginName; // 登录名
		private String name; // 姓名
		private boolean mobileLogin; // 是否手机登录

		private int userType;

		public Principal(User user, boolean mobileLogin, int type) {
			this.id = String.valueOf(user.getId());
			this.loginName = user.getLoginName();
			this.name = user.getName();
			this.mobileLogin = mobileLogin;
			this.userType = user.getUserType();
		}

		public String getId() {
			return id;
		}

		public String getLoginName() {
			return loginName;
		}

		public String getName() {
			return name;
		}

		public boolean isMobileLogin() {
			return mobileLogin;
		}

		/**
		 * userType
		 *
		 * @return the userType
		 * @version 1.0.0
		 */

		public int getUserType() {
			return userType;
		}

		/**
		 * @param userType
		 *            the userType to set
		 */
		public void setUserType(int userType) {
			this.userType = userType;
		}

		/**
		 * 获取SESSIONID
		 */
		public String getSessionid() {
			try {
				return (String) ShiroUtils.getSession().getId();
			} catch (Exception e) {
				return "";
			}
		}

		@Override
		public String toString() {
			return id;
		}

	}
}
