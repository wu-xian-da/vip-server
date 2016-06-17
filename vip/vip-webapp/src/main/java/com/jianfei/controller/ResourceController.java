package com.jianfei.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianfei.core.bean.Menu;
import com.jianfei.core.bean.Resource;
import com.jianfei.core.bean.Role;
import com.jianfei.core.common.utils.JsonTreeData;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.TreeGrid;
import com.jianfei.core.service.sys.ResourceManager;

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
	private ResourceManager resourceManager;

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
	public List<Menu> menuTree() {
		return resourceManager.getCurrentMenus();
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
		return resourceManager.buildResourceTreeNode();
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
		return resourceManager.buildResourceTreeGrid();
	}

	@RequestMapping(value = "form")
	public String form(Resource resource, Model model) {
		if (0 != resource.getId()) {
			MessageDto<Resource> messageDto = resourceManager
					.findEntityById(resource.getId());
			if (messageDto.isOk()) {
				model.addAttribute("resource", messageDto.getData());
				if (messageDto.getData().getParent() != null
						&& messageDto.getData().getParent().getId() != 0) {
					MessageDto<Resource> pMessageDto = resourceManager
							.findEntityById(messageDto.getData().getParent()
									.getId());
					if (pMessageDto.isOk()) {
						model.addAttribute("parent", pMessageDto.getData());
					}
				}
			}
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
	public MessageDto<String> save(Resource resource) {
		MessageDto<String> dto = new MessageDto<String>();
		List<Resource> list = resourceManager.get(MapUtils
				.<Resource> entityInitMap(resource));
		if (!CollectionUtils.isEmpty(list)) {
			return dto.setMsgBody("资源名称已经存在...");
		}
		return resourceManager.save(resource);
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public MessageDto<String> update(Resource resource) {
		return resourceManager.update(resource);
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
	public MessageDto<String> delete(@PathVariable("id") Long id) {
		return resourceManager.delete(id);
	}

	@RequestMapping(value = "roleResources")
	@ResponseBody
	public List<Resource> roleResource(Role role) {
		return resourceManager.findResourceByRoleId(role.getId());
	}
}
