<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.jianfei.core.common.security.shiro.HasAnyPermissionsTag"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<%
	HasAnyPermissionsTag anyPermissionsTag = new HasAnyPermissionsTag();
%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
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
			<shiro:hasPermission name="system:station:add">
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFuns();">添加</a></td>
				<td><div class="datagrid-btn-separator"></div></td>
			</shiro:hasPermission>
				<td><select id="state" name="state" >
										<option value="" selected="selected">请选择状态</option>
										<option value="0">运营</option>
										<option value="1">冻结</option>
									</select>&nbsp;&nbsp;</td>
				<td><input type="text" id="name" style="width: 200px" placeholder='请输入场站名称 '></input></td>
				<td>
					<input type="button" value="查询" style="width: 60px;height: 20px;
				    border: none;
				    background: #698DC3;
				    border-radius: 5px;
				    text-align: center;
				    color: #FFF;" onclick="search();" /></td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
	
	<script type="text/javascript">
	function search(){
		grid.datagrid('load',{'name':$("#name").val(),state:$("#state").val()});
	};
	var grid;
	var addFuns = function() {
		var dialog = parent.sy.modalDialog({
			title : '添加场站信息',
			height:400,
			url : sy.contextPath + '/airport/form',
			buttons : [ {
				text : '保存',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var showFun = function(id) {
		var dialog = parent.sy.modalDialog({
			title : '查看角色信息',
			url : sy.contextPath + '/securityJsp/base/SyroleForm.jsp?id=' + id
		});
	};
	var editFun = function(id) {
		var dialog = parent.sy.modalDialog({
			title : '编辑场站信息',
			url : sy.contextPath + '/airport/form?id=' + id,
			buttons : [ {
				text : '保存',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var removeFun = function(id) {
		parent.$.messager.confirm('询问', '您确定要删除该场站？', function(r) {
			if (r) {
				$.post(sy.contextPath + '/airport/delete/'+id, function(dataObj) {
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
			url : '${ctx}/airport/list',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'airport_id',
			pageSize : 20,
			pageList : [ 20, 30, 40, 50],
			sortName : 'updatetime',
			sortOrder : 'desc',
			columns : [ [{
				width : '100',
				title : '所属省市',
				field : 'province',
				sortable : true
			} , {
				width : '200',
				title : '场站名称',
				field : 'airport_name',
				sortable : true
			},{
				width : '100',
				title : '负责人',
				field : 'header_name'
			},{
				width : '100',
				title : '负责人电话',
				field : 'header_phone'
			},{
				width : '100',
				title : '业务员人数',
				field : 'agent_num',
				sortable : true
			},{
				width : '100',
				title : '开卡总量',
				field : 'total',
				sortable : true
			},{
				width : '60',
				title : '状态',
				field : 'state',
				sortable : true,
				formatter : function(value, row, index) {
					switch (value) {
					case 1:
						return '冻结';
					case 0:
						return '运营';
					}
				}
			}, {
				title : '操作',
				field : 'action',
				width : '300',
				formatter : function(value, row) {
					var str = '';
					<%if (anyPermissionsTag.showTagBody("system:station:update")) {%>
						str += sy.formatString('&nbsp;<img class="iconImg ext-icon-note_edit" title="编辑" onclick="editFun(\'{0}\');"/> 编辑', row.airport_id);
					<%}%>
					<%if (anyPermissionsTag.showTagBody("system:station:delete")) {%>
						str += sy.formatString('&nbsp;<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/> 删除', row.airport_id);
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