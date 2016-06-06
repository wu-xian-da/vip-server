<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.jianfei.core.common.security.shiro.HasAnyPermissionsTag"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<%
	HasAnyPermissionsTag anyPermissionsTag = new HasAnyPermissionsTag();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>New Web Project</title>
    <jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
    </head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
			<shiro:hasPermission name="system:role:add">
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
			</shiro:hasPermission>
				<td><div class="datagrid-btn-separator"></div></td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
	
	<script type="text/javascript">
	var grid;
	var addFun = function() {
		var dialog = parent.sy.modalDialog({
			title : '添加角色信息',
			url : sy.contextPath + '/role/form',
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var editFun = function(id) {
		var dialog = parent.sy.modalDialog({
			title : '编辑角色信息',
			url : sy.contextPath + '/role/form?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var removeFun = function(id) {
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
				$.post(sy.contextPath + '/role/delete', {
					id : id
				}, function(result) {
					if (!result.ok) {
						layer.msg(result.msgBody,{icon: 2});
					}else{
						grid.datagrid('reload');
					}
				}, 'json');
			}
		});
	};
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : '${ctx}/role/list',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'seq',
			sortOrder : 'asc',
			frozenColumns : [ [ {
				width : '100',
				title : '角色名称',
				field : 'name',
				sortable : true
			} ] ],
			columns : [ [{
				width : '800',
				title : '角色权限',
				field : 'resources',
				formatter : function(value, row){
					var obj= value;
					var str = "";
					for(var i=0;i<obj.length;i++){
						str=str+obj[i].name+" ";
					}
					
					return str;
				}
			},  {
				width : '100',
				title : '首页地址',
				field : 'url'
			},{
				title : '操作',
				field : 'action',
				width : '150',
				formatter : function(value, row) {
					var str = '';
					<%if (anyPermissionsTag.showTagBody("system:role:update")) {%>
						str += sy.formatString('&nbsp;<img class="iconImg ext-icon-note_edit" title="编辑" onclick="editFun(\'{0}\');"/> 编辑', row.id);
					<%}%>
					<%if (anyPermissionsTag.showTagBody("system:role:delete")) {%>
						str += sy.formatString('&nbsp;<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/> 删除', row.id);
					<%}%>
						return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data) {
				$('.iconImg').attr('src', sy.pixel_0);
				parent.$.messager.progress('close');
			}
		});
	});
</script>
</body>
</html>