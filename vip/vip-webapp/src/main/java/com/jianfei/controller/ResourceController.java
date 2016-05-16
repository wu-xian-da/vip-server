package com.jianfei.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianfei.core.bean.MenBuilder;
import com.jianfei.core.bean.Resource;
import com.jianfei.core.bean.Role;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.shrio.ShrioUser;
import com.jianfei.core.common.utils.JsonTreeData;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.TreeGrid;
import com.jianfei.core.service.SystemService;

/**
 *
 * @Description: 系统信息控制器
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月11日 上午11:19:41
 * 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping(value = "resource")
public class ResourceController extends BaseController {

	@Autowired
	private SystemService systemService;

	@RequestMapping(value = "home")
	public String home() {

		return "resource/resources";
	}

	/**
	 * menuTree(后台左侧菜单)
	 * 
	 * @return List<TreeNode>
	 * @version 1.0.0
	 */
	@RequestMapping(value = "/menus", method = RequestMethod.POST)
	@ResponseBody
	public List<MenBuilder> menuTree() {
		return getShrioUser().getMenus();
	}

	/**
	 * tree(资源数据树形展示)
	 * 
	 * @return List<JsonTreeData>
	 * @version 1.0.0
	 */
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public List<JsonTreeData> tree() {
		return systemService.buildResourceTreeNode();
	}

	/**
	 * treeGrid(展示资源数据)
	 * 
	 * @return TreeGrid<Map<String,Object>>
	 * @version 1.0.0
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public TreeGrid<Map<String, Object>> treeGrid() {
		return systemService.buildResourceTreeGrid();
	}

	@RequestMapping(value = "form")
	public String form(Resource resource, Model model) {
		if (0 != resource.getId()) {
			resource = systemService.getResourceMapper().findEntityById(
					resource.getId());
			model.addAttribute("resource", resource);
		}
		return "resource/SyresourceForm";
	}

	/**
	 * save(保存)
	 * 
	 * @param resource
	 * @return MessageDto
	 * @version 1.0.0
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public MessageDto save(Resource resource) {
		List<Resource> list = systemService.getResourceMapper().get(
				MapUtils.<Resource> entityInitMap(resource));
		if (!CollectionUtils.isEmpty(list)) {
			return buildDtoMsg(false).setMsgBody("资源名称已经存在...");
		}
		systemService.getResourceMapper().save(resource);
		return buildDtoMsg(true);
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public MessageDto update(Resource resource) {
		systemService.getResourceMapper().update(resource);
		return buildDtoMsg(true);
	}

	/**
	 * delete(删除资源)
	 * 
	 * @param id
	 * @return MessageDto
	 * @version 1.0.0
	 */
	@RequestMapping(value = "/delete/{id}")
	@ResponseBody
	public MessageDto delete(@PathVariable("id") Long id) {
		systemService.getResourceMapper().delete(id);
		return buildDtoMsg(true).setMsgBody("ok");
	}

	@RequestMapping(value = "roleResources")
	@ResponseBody
	public List<Resource> roleResource(Role role) {
		return systemService.getResourceMapper().findResourceByRoleId(
				role.getId());
	}
}
