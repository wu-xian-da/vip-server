package com.jianfei.core.common.utils;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.crypto.hash.SimpleHash;

import com.jianfei.core.bean.Role;
import com.jianfei.core.service.sys.RoleManager;

public class PasswdHelper {

	/**
	 * 获取密码
	 * 
	 * @param roleMapper
	 * @return
	 */
	private static String passwdInit(String roelId, RoleManager roleManager,
			Long id) {
		// 从缓存中获取角色信息
		if (!StringUtils.isEmpty(roelId)) {
			List<Role> roles = roleManager.getAll();
			for (Role role : roles) {
				if (String.valueOf(role.getId()).equals(roelId)) {
					return role.getInitPwd();
				}
			}
		}
		// 从缓存中获取数据失败，从数据库中获取密码
		List<Role> list = roleManager.selectRoleByUserId(id);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0).getInitPwd();
		}
		// 从数据中获取密码失败，使用默认密码
		return GloabConfig.getConfig("defalut.passwd");
	}

	public static String passwdProdece(String roelId, RoleManager roleManager,
			Long userId, String salt) {
		String initPasswd = passwdInit(roelId, roleManager, userId);
		return new SimpleHash("md5", initPasswd, salt).toString();
	}

	public static String passwdProdeceNoSalt(String roelId,
			RoleManager roleManager, Long userId) {
		String initPasswd = passwdInit(roelId, roleManager, userId);
		SimpleHash hash = new SimpleHash("md5", initPasswd);
		return hash.toString();
	}

	/**
	 * 默认密码
	 * 
	 * @return
	 */
	public static String defaultPasswdProdece() {
		SimpleHash hash = new SimpleHash("md5",
				GloabConfig.getConfig("defalut.passwd"));
		return hash.toString();
	}

	/**
	 * 默认密码加盐
	 * 
	 * @param salt
	 * @return
	 */
	public static String defaultPasswdProdece(String salt) {
		SimpleHash hash = new SimpleHash("md5",
				GloabConfig.getConfig("defalut.passwd"), salt);
		return hash.toString();
	}

	/**
	 * 根据密码参数+盐生成密码
	 * 
	 * @param passwd
	 *            密码
	 * @param salt
	 *            盐
	 * @return
	 */
	public static String passwdProdece(String passwd, String salt) {
		SimpleHash hash = new SimpleHash("md5", passwd, salt);
		return hash.toString();
	}

	/**
	 * 密码MD5加密
	 * 
	 * @param passwd
	 * @return
	 */
	public static String passwdProdece(String passwd) {
		SimpleHash hash = new SimpleHash("md5", passwd);
		return hash.toString();
	}
}
