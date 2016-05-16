package com.jianfei.core.common.shrio;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.jianfei.core.bean.MenBuilder;
import com.jianfei.core.bean.Resource;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.SpringContextHolder;
import com.jianfei.core.service.SystemService;

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
		ShrioUser userMsgDto = (ShrioUser) principals;
		System.out.println(userMsgDto.getLoginName()
				+ ".........................");
		return null;
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
			System.out.println(authcToken.getUsername());
			ShrioUser userMsgDto = new ShrioUser();
			userMsgDto.setName(authcToken.getUsername());
			User user = systemService.getUserMapper().getUserByName(
					authcToken.getUsername());
			if (null != user) {
				userMsgDto.setUser(user);
				List<Resource> resources = systemService.getResourceMapper()
						.findResourceByUserId(user.getId());
				List<MenBuilder> trees = MenBuilder.buildTree(resources);
				List<MenBuilder> menus = MenBuilder.buildMenu(trees);
				userMsgDto.setMenus(menus);
				if (user.isLogin()) {
					throw new AuthenticationException("msg:该帐号已经登录.");
				}
				return new SimpleAuthenticationInfo(userMsgDto,
						"f59cf5692216275b832bd98223516774",
						ByteSource.Util.bytes("refineli"), getName());
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
