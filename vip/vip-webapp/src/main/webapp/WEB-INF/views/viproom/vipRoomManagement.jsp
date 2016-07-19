<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<style type="text/css">
a {
	text-decoration: none;
	color: #404040
}
</style>
<script type="text/javascript">
	var grid;
	
	function searchByCondition(){
		grid.datagrid('load',{'_searchContent':$("#_searchContent").val()});
	};
	
	var editFun = function(id) {
		var dialog = parent.sy.modalDialog({
			title : '编辑用户信息',
			url : sy.contextPath + '/user/form?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	
	//逻辑删除vip室
	var removeFun = function(viproomId) {
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
				$.post(sy.contextPath + '/viproom/delVipRoomById', {
					viproomId : viproomId
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
	
	//启用vip室功能
	var startUsing = function(viproomId){
		parent.$.messager.confirm('询问', '您确定要启用该vip室？', function(r) {
			if (r) {
				$.post(sy.contextPath + '/viproom/startUsingVipRoomById', {
					viproomId : viproomId
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
	
	//禁用vip室功能
	var forbiddenUsing = function(viproomId){
		parent.$.messager.confirm('询问', '您确定要禁用该vip室？', function(r) {
			if (r) {
				$.post(sy.contextPath + '/viproom/forbiddenUsingVipRoomById', {
					viproomId : viproomId
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
	
	var grantRoleFun = function(id) {
		var dialog = parent.sy.modalDialog({
			title : '修改角色',
			url : sy.contextPath + '/user/grant?id=' + id,
			buttons : [ {
				text : '修改',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var grantOrganizationFun = function(id) {
		var dialog = parent.sy.modalDialog({
			title : '修改机构',
			url : sy.contextPath + '/securityJsp/base/SyuserOrganizationGrant.jsp?id=' + id,
			buttons : [ {
				text : '修改',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	
	//页面初始化时进行渲染
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : sy.contextPath + '/viproom/showVipRoomList',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			
			pageSize : 20,
			pageList : [20, 30, 40, 50],
			columns : [ [{
				width : '200',
				title : '所属场站',
				field : 'airportName',
				align : 'left',
				sortable : true
			} ,{
				width : '300',
				title : 'vip室名称',
				align : 'left',
				field : 'viproomName',
				
			}, {
				width : '150',
				title : '场站负责人',
				field : 'headerName',
				align : 'left',
				
			}, {
				width : '140',
				title : '负责人电话',
				field : 'headerPhone',
				align : 'left',
				
			},{
				width : '100',
				title : 'vip室状态',
				field : 'activeState',
				align : 'left',
				formatter : function(value, row, index) {
					switch (value) {
					case 0:
						return '启用';
					case 1:
						return '禁用';
					}
				}
				
			},{
				title : '操作',
				field : 'action',
				width : '200',
				align : 'left',
				formatter : function(value, row,index) {
					var str = '';
					switch (row.activeState){
						case 0:
							str += "<a href='gotoUpdateVipRoomView?viproomId="+row.viproomId+"'><img class='iconImg ext-icon-note_edit title='编辑'/> 编辑&nbsp;</a>";
							str += sy.formatString('<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/> 删除&nbsp;', row.viproomId);
							str +=  sy.formatString('<img class="iconImg ext-icon-note_error" title="禁用" onclick="forbiddenUsing(\'{0}\');"/> 禁用', row.viproomId);
							return str;
						case 1:
							str += "<a href='gotoUpdateVipRoomView?viproomId="+row.viproomId+"'><img class='iconImg ext-icon-note_edit title='编辑'/> 编辑&nbsp;</a>";
							str += sy.formatString('<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/> 删除&nbsp', row.viproomId);
							str +=  sy.formatString('<img class="iconImg ext-icon-note" title="启用" onclick="startUsing(\'{0}\');"/> 启用', row.viproomId);
							return str;
					}
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
				<!-- <td><button><a href="gotoAddVipRoomView">添加VIP室</a></button></td> -->

				<td><a href="gotoAddVipRoomView" class="easyui-linkbutton"
					data-options="iconCls:'ext-icon-note_add',plain:true">添加</a></td>
				<td>
					<!-- 搜索条件框 -->
					<form id="searchForm">
						<table>
							<tr>
								<td>关键字</td>
								<td><input id="_searchContent" name="_searchContent" style="width: 125px;"
									placeholder="场站名称/VIP室名称" /></td>
								<td><input type="button" value="查询"
									style="width: 60px; height: 20px; border: none; background: #698DC3; border-radius: 5px; text-align: center; color: #FFF;"
									onclick="searchByCondition();"></td>
							</tr>
						</table>
					</form>

				</td>
			</tr>

		</table>
	</div>

	<!--数据表格展示 -->
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>