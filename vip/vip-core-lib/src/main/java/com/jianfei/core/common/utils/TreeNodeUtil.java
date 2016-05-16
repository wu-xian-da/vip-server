/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月13日-上午12:02:33
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.jianfei.core.bean.Resource;
import com.jianfei.core.bean.Role;

/**
 *
 * @Description:描述: 获取树节点集合(这里用一句话描述这个类的作用)
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月13日 上午12:02:33
 * 
 * @version 1.0.0
 *
 */
public class TreeNodeUtil {

	/**
	 * @Title: getfatherNode
	 * @Description 方法描述: 父节点
	 * @param 设定文件
	 *            ： @param treeDataList
	 * @param 设定文件
	 *            ： @return
	 * @return 返回类型：List<JsonTreeData>
	 * @throws
	 * @date 最后修改时间：2015年6月9日 下午6:39:26
	 */
	public final static List<JsonTreeData> getfatherNode(
			List<JsonTreeData> treeDataList) {
		List<JsonTreeData> newTreeDataList = new ArrayList<JsonTreeData>();
		for (JsonTreeData jsonTreeData : treeDataList) {
			if (jsonTreeData.getPid() == null) {
				// 获取父节点下的子节点
				jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId(),
						treeDataList));
				jsonTreeData.setState("open");
				newTreeDataList.add(jsonTreeData);
			}
		}
		return newTreeDataList;
	}

	/**
	 * @Title: getChildrenNode
	 * @Description 方法描述: 子节点
	 * @param 设定文件
	 *            ： @param pid
	 * @param 设定文件
	 *            ： @param treeDataList
	 * @param 设定文件
	 *            ： @return
	 * @return 返回类型：List<JsonTreeData>
	 * @throws
	 * @date 最后修改时间：2015年6月9日 下午6:39:50
	 */
	private final static List<JsonTreeData> getChildrenNode(String pid,
			List<JsonTreeData> treeDataList) {
		List<JsonTreeData> newTreeDataList = new ArrayList<JsonTreeData>();
		for (JsonTreeData jsonTreeData : treeDataList) {
			if (jsonTreeData.getPid() == null)
				continue;
			// 这是一个子节点
			if (jsonTreeData.getPid().equals(pid)) {
				// 递归获取子节点下的子节点
				jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId(),
						treeDataList));
				newTreeDataList.add(jsonTreeData);
			}
		}
		return newTreeDataList;
	}

	public static List<JsonTreeData> buildTree(List<Resource> resources) {
		List<JsonTreeData> treeDataList = new ArrayList<JsonTreeData>();
		for (Resource resource : resources) {
			JsonTreeData treeData = new JsonTreeData();
			treeData.setId(resource.getId() + "");
			if (null != resource.getParent())
				treeData.setPid(String.valueOf(resource.getParent().getId()));
			treeData.setText(resource.getName());
			treeData.setName(resource.getName());
			treeDataList.add(treeData);
		}
		// 最后得到结果集,经过FirstJSON转换后就可得所需的json格式
		return TreeNodeUtil.getfatherNode(treeDataList);
	}

	public static List<JsonTreeData> buildRoleTree(List<Role> roles) {
		List<JsonTreeData> treeDataList = new ArrayList<JsonTreeData>();
		for (Role resource : roles) {
			JsonTreeData treeData = new JsonTreeData();
			treeData.setId(resource.getId() + "");
			treeData.setText(resource.getName());
			treeData.setName(resource.getName());
			treeDataList.add(treeData);
		}
		// 最后得到结果集,经过FirstJSON转换后就可得所需的json格式
		return TreeNodeUtil.getfatherNode(treeDataList);
	}
}