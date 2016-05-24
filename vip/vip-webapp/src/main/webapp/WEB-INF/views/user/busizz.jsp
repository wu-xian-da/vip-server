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
			url : sy.contextPath + '/busizz/form',
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
			title : '编辑用户信息',
			url : sy.contextPath + '/busizz/form?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var removeFun = function(id) {
		parent.$.messager.confirm('询问', '您确定要禁用该用户？', function(r) {
			if (r) {
				$.post(sy.contextPath + '/busizz/delete', {
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
			url : sy.contextPath + '/busizz/list',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'createdatetime',
			sortOrder : 'desc',
			pageSize : 10,
			pageList : [5, 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			frozenColumns : [ [ {
				width : '100',
				title : '工号',
				field : 'code'
			}, {
				width : '80',
				title : '姓名',
				field : 'name'
			} ] ],
			columns : [ [  {
				width : '350',
				title : '所属场站',
				field : 'ariPortNames'
			}, {
				width : '80',
				title : '手机号码',
				field : 'phone'
			}, {
				width : '80',
				title : '上月开卡数',
				field : 'phone',
				sortable : true
			}, {
				width : '80',
				title : '本月开卡数',
				field : 'phone',
				sortable : true
			}, {
				width : '80',
				title : '开卡总量',
				field : 'phone',
				sortable : true
			},{
				width : '50',
				title : '状态',
				field : 'state',
				sortable : true,
				formatter : function(value, row, index) {
					switch (value) {
					case 0:
						return '在职';
					case 1:
						return '离职';
					}
				}
			}, {
				title : '操作',
				field : 'action',
				width : '90',
				formatter : function(value, row) {
					var str = '';
					<%if (anyPermissionsTag.showTagBody("system:user:update")) {%>
						str += sy.formatString('<img class="iconImg ext-icon-note_edit" title="编辑" onclick="editFun(\'{0}\');"/>', row.id);
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
						<table>
							<tr>
								<td>姓名</td>
								<td><input name="_name" style="width: 80px;" /></td>
								<!-- 
								<td>创建时间</td>
								<td><input name="_start"  id="d4311" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'d4312\')}'})" readonly="readonly" style="width: 120px;" />
								-<input id="d4312" name="_end" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'d4311\')}'})" readonly="readonly" style="width: 120px;" /></td> -->
								<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="grid.datagrid('load',sy.serializeObject($('#searchForm')));">过滤</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置过滤</a></td>
							
							</tr>
						</table>
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