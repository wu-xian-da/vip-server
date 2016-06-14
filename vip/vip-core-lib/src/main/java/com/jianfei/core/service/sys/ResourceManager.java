/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午2:03:44
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.sys;

import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.Menu;
import com.jianfei.core.bean.Resource;
import com.jianfei.core.common.utils.JsonTreeData;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.TreeGrid;

/**
 *
 * @Description: resouce操作
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月25日 下午2:03:44
 * 
 * @version 1.0.0
 *
 */
public interface ResourceManager {
	/**
	 * getCurrentMenus(二级菜单)
	 * 
	 * @return List<Menu>
	 * @version 1.0.0
	 */
	List<Menu> getCurrentMenus();

	/**
	 * buildResourceTreeNode(资源树)
	 * 
	 * @return List<JsonTreeData>
	 * @version 1.0.0
	 */
	List<JsonTreeData> buildResourceTreeNode();

	/**
	 * buildResourceTreeGrid(这里用一句话描述这个方法的作用)
	 * 
	 * @return TreeGrid<Map<String,Object>>
	 * @version 1.0.0
	 */
	TreeGrid<Map<String, Object>> buildResourceTreeGrid();

	/**
	 * findEntityById(根据ID查找resource)
	 * 
	 * @param id
	 * @return MessageDto<Resource>
	 * @version 1.0.0
	 */
	MessageDto<Resource> findEntityById(Long id);

	/**
	 * save(保存操作)
	 * 
	 * @param resource
	 * @return MessageDto<String>
	 * @version 1.0.0
	 */
	MessageDto<String> save(Resource resource);

	/**
	 * save(保存操作)
	 * 
	 * @param resource
	 * @return MessageDto<String>
	 * @version 1.0.0
	 */
	MessageDto<String> update(Resource resource);

	/**
	 * delete(删除操作)
	 * 
	 * @param id
	 * @return MessageDto<String>
	 * @version 1.0.0
	 */
	MessageDto<String> delete(Long id);

	/**
	 * findResourceByRoleId(根据roleId查找所有的资源)
	 * 
	 * @param id
	 * @return List<Resource>
	 * @version 1.0.0
	 */
	List<Resource> findResourceByRoleId(Long id);

	/**
	 * get(多维度查询)
	 * 
	 * @param map
	 * @return List<Resource>
	 * @version 1.0.0
	 */
	List<Resource> get(Map<String, Object> map);

	/**
	 * findResourceByUserId(根据用户id 查找资源)
	 * 
	 * @param id
	 * @return List<Resource>
	 * @version 1.0.0
	 */
	List<Resource> findResourceByUserId(Long id);

}
