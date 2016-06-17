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
				text : '保存',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	
	var initPwd = function(id,code){
		$.ajax({
			  type: 'POST',
			  url : sy.contextPath + '/busizz/initPwd',
			  data: {id:id,code:code},
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
			url : sy.contextPath + '/busizz/form?id=' + id,
			buttons : [ {
				text : '保存',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
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
			sortName : 'total',
			sortOrder : 'desc',
			pageSize : 20,
			pageList : [ 20, 30, 40, 50],
			frozenColumns : [ [ {
				width : '100',
				title : '工号',
				field : 'code'
			}, {
				width : '120',
				title : '姓名',
				field : 'name'
			} ] ],
			columns : [ [  {
				width : '440',
				title : '所属场站',
				field : 'ariPortsNames'
			},{
				width : '120',
				title : '职位',
				field : 'job'
			}, {
				width : '120',
				title : '手机号码',
				field : 'phone'
			}, {
				width : '80',
				title : '上月开卡数',
				field : 'lmonth',
				sortable : true
			}, {
				width : '80',
				title : '本月开卡数',
				field : 'tmonth',
				sortable : true
			}, {
				width : '80',
				title : '开卡总量',
				field : 'total',
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
				width : '200',
				align : 'center',
				formatter : function(value, row) {
					var str = '';
					<%if (anyPermissionsTag.showTagBody("system:busizz:update")) {%>
						str += sy.formatString('<img class="iconImg ext-icon-note_edit" title="编辑" onclick="editFun(\'{0}\');"/> 编辑&nbsp;', row.id);
					<%}%>
					<%if (anyPermissionsTag.showTagBody("system:busizz:initPwd")) {%>
					 str += sy.formatString('&nbsp;<img class="iconImg ext-icon-lock_edit" title="重置密码" onclick="initPwd(\'{0}\',\'{1}\');"/> 重置密码', row.id,row.code);
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
	function search(){
		grid.datagrid('load',{'name':$("#name").val()});
	};
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				
			</tr>
			<tr>
				<td>
					<table>
						<tr>
						<shiro:hasPermission name="system:user:add">
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
						</shiro:hasPermission>
						<td>
							<form id="searchForm">
								<table>
									<tr>
										<td>姓名：</td>
										<td><input id="name" style="width: 150px" placeholder='输入业务员名字'></input></td>
										<td>
							<input type="button" value="查询" style="width: 60px;height: 20px;
						    border: none;
						    background: #698DC3;
						    border-radius: 5px;
						    text-align: center;
						    color: #FFF;" onclick="search();" /></td>
									</tr>
								</table>
							</form>
						</td>
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