package com.jianfei.core.mapper;

import java.util.List;

import com.jianfei.core.bean.Resource;
import com.jianfei.core.common.persistence.MyBatisDao;

/**
 *
 * @Description: 菜单管理
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月11日 上午10:42:47
 * 
 * @version 1.0.0
 *
 */
@MyBatisDao
public interface ResourceMapper extends BaseMapper<Resource> {

	List<Resource> findResourceByRoleId(Long roleId);

	List<Resource> findResourceByUserId(Long userId);

	/**
	 * 根据父节点查找所有的子节点
	 * 
	 * @param pid
	 * @return List<Resource>
	 * @version 1.0.0
	 */
	List<Resource> selectChildResorceByPid(Long pid);

}
