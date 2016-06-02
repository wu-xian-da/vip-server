/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月13日-上午1:44:06
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.User;
import com.jianfei.core.common.persistence.MyBatisDao;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月13日 上午1:44:06
 * 
 * @version 1.0.0
 *
 */
@MyBatisDao
public interface UserMapper extends BaseMapper<User> {

	User getUserByName(String loginName);

	void deleteRolesFromUser(Long userId);


	void batchInsertUserRole(List<Map<String, Object>> list);

	/**
	 * 根据工号获得用户信息
	 * @param uno
	 * @return
     */
	User getUserByUno(String uno);

	/**
	 * 修改用户密码
	 * @param uno
	 * @param password
	 * @param newPassword
     */
	int updatePasswordByUno(@Param(value = "uno")String uno, @Param(value = "password")String password, @Param(value = "newPassword")String newPassword);
	
	public User getYeepayUser(String loginName);
}
