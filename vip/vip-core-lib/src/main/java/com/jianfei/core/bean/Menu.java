package com.jianfei.core.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class Menu implements Serializable {

	private static final long serialVersionUID = -6019058195662545348L;
	private String id;
	private String text;
	private String state = "open";// open,closed
	private boolean checked = false;
	private Object attributes;
	private String iconCls;
	private String pid;
	private Integer priority;
	private List<Menu> children = Lists.newArrayList();

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

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public static List<Menu> getSecondMenu(List<Resource> resources) {
		List<Menu> list = new ArrayList<Menu>();
		for (Resource resource : resources) {

			Menu node = new Menu();
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
			list.add(node);
		}
		List<Menu> menus = new ArrayList<Menu>();
		for (Menu menu : list) {
			if (menu.getPid() == null) {
				for (Menu child : list) {
					if (child.getPid() != null
							&& child.getPid().equals(menu.getId())) {
						menu.getChildren().add(child);
					}
				}
				menus.add(menu);
			}
		}
		return menus;
	}
}
