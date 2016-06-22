<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.jianfei.core.common.security.shiro.HasAnyPermissionsTag"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<%
	HasAnyPermissionsTag anyPermissionsTag = new HasAnyPermissionsTag();
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<script type="text/javascript">
	var grid;
	var addFun = function() {
		var dialog = parent.sy.modalDialog({
			title : '添加用户信息',
			url : sy.contextPath + '/user/form',
			buttons : [ {
				text : '保存',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var initPwd = function(id,salt,roleIds){
		$.ajax({
			  type: 'POST',
			  url : sy.contextPath + '/busizz/initPwd',
			  data: {id:id,salt:salt,roleId:roleIds},
			  success: function(result){
				  if (result.ok) {
					  layer.alert(result.msgBody, {
							icon : 1,
							skin : 'layer-ext-moon' 
						});
					}else{
						layer.alert(result.msgBody, {
							icon : 2,
							skin : 'layer-ext-moon' 
						});
					}
			  },
			  dataType: 'json'
			});
	}
	var editFun = function(id) {
		var dialog = parent.sy.modalDialog({
			title : '编辑用户信息',
			url : sy.contextPath + '/user/form?id=' + id,
			buttons : [ {
				text : '保存',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var removeFun = function(id) {
		parent.$.messager.confirm('询问', '您确定要删除该用户？', function(r) {
			if (r) {
				$.post(sy.contextPath + '/user/delete', {
					id : id
				}, function(dataObj) {
					if(!dataObj.ok){
			    		$.messager.alert('msg',dataObj.msgBody,'error');
			    		return ;
			    	};
					grid.datagrid('reload');
				}, 'json');
			}
		});
	};
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : sy.contextPath + '/user/list',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'createdatetime',
			sortOrder : 'desc',
			pageSize : 20,
			pageList : [20, 30, 40, 50 ],
			frozenColumns : [ [ {
				width : '120',
				title : '登录名',
				field : 'loginName',
				sortable : true
			}, {
				width : '120',
				title : '姓名',
				field : 'name',
				sortable : true
			} ] ],
			columns : [ [  {
				width : '550',
				title : '区域',
				field : 'ariPortNames'
			}, {
				width : '120',
				title : '角色',
				field : 'roelNames'
			}, {
				width : '120',
				title : '工号',
				field : 'code'
			}, {
				title : '操作',
				field : 'action',
				align : 'center',
				width : '250',
				formatter : function(value, row) {
					var str = '';
					<%if (anyPermissionsTag.showTagBody("system:user:update")) {%>
						str += sy.formatString('<img class="iconImg ext-icon-note_edit" title="编辑" onclick="editFun(\'{0}\');"/> 编辑&nbsp;', row.id);
					<%}%>
					<%if (anyPermissionsTag.showTagBody("system:user:delete")) {%>
						str += sy.formatString('&nbsp;<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/> 删除', row.id);
					<%}%>
					<%if (anyPermissionsTag.showTagBody("system:user:initPwd")) {%>
					 str += sy.formatString('&nbsp;<img class="iconImg ext-icon-lock_edit" title="重置密码" onclick="initPwd(\'{0}\',\'{1}\',\'{2}\');"/> 重置密码', row.id,row.salt,row.roleIds);
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
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				<td>
					<form id="searchForm">
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
						<shiro:hasPermission name="system:user:add">
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
						</shiro:hasPermission>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>