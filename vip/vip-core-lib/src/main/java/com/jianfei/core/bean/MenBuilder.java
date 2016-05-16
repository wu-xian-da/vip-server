package com.jianfei.core.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jianfei.core.common.utils.StringUtils;

public class MenBuilder implements Serializable {

	private static final long serialVersionUID = -6019058195662545348L;
	private String id;
	private String text;
	private String state = "open";// open,closed
	private boolean checked = false;
	private Object attributes;
	private String iconCls;
	private String pid;
	private Integer priority;
	private List<MenBuilder> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<MenBuilder> getChildren() {
		return children;
	}

	public void setChildren(List<MenBuilder> children) {
		this.children = children;
	}

	/**
	 * 找到根节点
	 * 
	 * @param menuList
	 * @return
	 */
	public static List<MenBuilder> getRootTreeNode(List<MenBuilder> menuList) {
		List<MenBuilder> roots = new ArrayList<MenBuilder>();
		// 首先要找到树的根节点
		int number = 0;

		for (MenBuilder treeNodeI : menuList) {
			your: for (MenBuilder treeNodeJ : menuList) {
				if (null != treeNodeI.getPid()
						&& treeNodeI.getPid().equals(treeNodeJ.getId())) {
					number += 1;
					break your;
				}
			}

			if (number == 0) {
				roots.add(treeNodeI);
			}
			number = 0;
		}

		return roots;
	}

	public static class TreeNodeComparator implements Comparator<MenBuilder> {

		@Override
		public int compare(MenBuilder o1, MenBuilder o2) {
			return Integer.valueOf(o2.getPriority()).compareTo(
					Integer.valueOf(o1.getPriority()));
		}
	}

	public static List<MenBuilder> creationTreeNode(List<MenBuilder> menuList,
			String pid) {

		List<MenBuilder> childrenTreeNodes = new ArrayList<MenBuilder>();

		for (MenBuilder treeNode : menuList) {
			String parentId = String.valueOf(treeNode.getPid());
			String id = String.valueOf(treeNode.getId());
			if (!StringUtils.isEmpty(parentId) && parentId.equals(pid)) {
				@SuppressWarnings("unused")
				List<MenBuilder> childrenTreeNodesTemps = creationTreeNode(
						menuList, id);
				childrenTreeNodes.add(treeNode);
			}
		}

		Collections.sort(childrenTreeNodes, new TreeNodeComparator());

		return childrenTreeNodes;
	}

	public static List<MenBuilder> buildMenus(List<Resource> resources) {
		List<MenBuilder> menuList = buildTree(resources);
		List<MenBuilder> roots = new ArrayList<MenBuilder>();
		// 根节点集合(一个跟节点一棵树)
		List<MenBuilder> rootsTemp = getRootTreeNode(menuList);
		// 构建树
		for (MenBuilder root : rootsTemp) {
			List<MenBuilder> childrenTreeNodes = creationTreeNode(menuList,
					root.getId());
			root.setChildren(childrenTreeNodes);
			roots.add(root);
		}

		return roots;
	}

	public static List<MenBuilder> buildTree(List<Resource> resources) {
		List<MenBuilder> nodes = new ArrayList<MenBuilder>();
		for (Resource resource : resources) {

			MenBuilder node = new MenBuilder();
			node.setId(resource.getId() + "");
			if (null != resource.getParent()) {
				node.setPid(String.valueOf(resource.getParent().getId()));
			}
			node.setText(resource.getName());
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("url", resource.getUrl());
			attributes.put("target", resource.getTarget());
			node.setAttributes(attributes);
			node.setPriority(12);
			node.setIconCls(resource.getIconCls());
			nodes.add(node);
		}
		return nodes;
	}

}
