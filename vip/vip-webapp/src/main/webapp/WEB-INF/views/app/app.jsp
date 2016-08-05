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
			title : '添加轮播图',
			url : sy.contextPath + '/app/form',
			buttons : [ {
				text : '保存',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var editFun = function(id) {
		var dialog = parent.sy.modalDialog({
			title : '编辑轮播图',
			url : sy.contextPath + '/app/form?id=' + id,
			buttons : [ {
				text : '保存',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var removeFun = function(id) {
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
				$.post(sy.contextPath + '/app/delete', {
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
			url : sy.contextPath + '/app/list',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'pictureId',
			pageSize : 10,
			pageList : [5, 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
		
			columns : [ [{
				width : '120',
				title : '名称',
				field : 'name'
			}, {
				width : '150',
				title : '类型',
				align : 'center',
				field : 'imagetype',
				formatter : function(value, row, index) {
					switch (value) {
					case 0:
						return '业务APP轮播图';
					case 1:
						return '用户APP轮播图';
					case 2:
						return '用户APP合作按钮';
					case 3:
						return 'VIP卡权益登入前轮播';
					case 4:
						return 'VIP卡权益登入后轮播';
					}
				}
			},{
				width : '350',
				title : '链接',
				field : 'clickUrl'
			},{
				width : '150',
				title : '图片',
				align : 'center',
				field : 'pictureUrl',
				formatter : function(value, row) {
					if(value){
						return sy.formatString('<img src="{0}" style="width: 70px;height:80px;">', sy.staticServer +value);
					}
				}
			}, {
				title : '操作',
				field : 'action',
				width : '200',
				align : 'center',
				formatter : function(value, row) {
					var str = '';
					<%if (anyPermissionsTag.showTagBody("system:app:update")) {%>
						str += sy.formatString('<img class="iconImg ext-icon-note_edit" title="编辑" onclick="editFun(\'{0}\');"/> 编辑', row.pictureId);
					<%}%>
					<%if (anyPermissionsTag.showTagBody("system:app:delete")) {%>
						str += sy.formatString('&nbsp;&nbsp;<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/> 删除', row.pictureId);
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
					<table>
						<tr>
						<shiro:hasPermission name="system:app:add">
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