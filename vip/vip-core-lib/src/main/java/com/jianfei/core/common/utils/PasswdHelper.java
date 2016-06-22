package com.jianfei.core.common.utils;

import java.util.List;

import org.apache.shiro.crypto.hash.SimpleHash;

import com.jianfei.core.bean.Role;
import com.jianfei.core.service.sys.RoleManager;
import com.tencent.common.MD5;

public class PasswdHelper {

	/**
	 * 密码管理工具
	 * 
	 * @param roleMapper
	 * @return
	 */
	private static String passwdInit(String roelId, RoleManager roleManager) {
		if (!StringUtils.isEmpty(roelId)) {
			List<Role> roles = roleManager.getAll();
			for (Role role : roles) {
				if (String.valueOf(role.getId()).equals(roelId)) {
					return role.getInitPwd();
				}
			}
		}
		return StringUtils.EMPTY;
	}

	public static String passwdProdece(String roelId, RoleManager roleManager,
			String salt) {
		String passwd = passwdInit(roelId, roleManager);
		System.out.println(roelId + "    " + passwd + "   " + salt);
		if (StringUtils.isEmpty(passwd)) {
			return passwd;
		}
		if (!StringUtils.isEmpty(salt)) {
			return new SimpleHash("md5", passwd, salt).toString();
		}
		return new MD5().MD5Encode(passwd);
	}

	public static String defaultPasswdProdece() {
		return new MD5().MD5Encode(GloabConfig.getConfig("defalut.passwd"));
	}

	public static String defaultPasswdProdece(String salt) {
		SimpleHash hash = new SimpleHash("md5", defaultPasswdProdece(), salt);
		return hash.toString();
	}
}