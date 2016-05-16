package com.jianfei.core.common.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.Resource;

public class TreeGrid<T> implements Serializable {

	private static final long serialVersionUID = -6321481733213979383L;

	private long total;

	private List<T> rows;

	private List<Map<String, Object>> footer;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public List<Map<String, Object>> getFooter() {
		return footer;
	}

	public void setFooter(List<Map<String, Object>> footer) {
		this.footer = footer;
	}

	public static TreeGrid<Map<String, Object>> bindTreeGriid(List<Resource> resources) {
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		for (Resource resource : resources) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", String.valueOf(resource.getId()));
			map.put("name", resource.getName());
			if (resource.getParent()!=null&&!StringUtils.isEmpty(String.valueOf(resource.getParent().getId()))
					&& !"0".equals(String.valueOf(resource.getParent().getId())))
				map.put("_parentId", String.valueOf(resource.getParent().getId()));
			map.put("url", resource.getUrl());
			map.put("iconCls", resource.getIconCls());
			map.put("permission", resource.getPermission());
			map.put("description", resource.getDescription());
			rows.add(map);
		}
		TreeGrid<Map<String, Object>> treeGrid = new TreeGrid<Map<String, Object>>();
		treeGrid.setTotal(rows.size());
		treeGrid.setRows(rows);
		return treeGrid;
	}
}
