<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<script type="text/javascript">
	var grid;
	
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
			
			pageSize : 10,
			pageList : [10, 20, 30, 40, 50],
			columns : [ [{
				width : '100',
				title : '所属场站',
				field : 'airportName',
				align : 'center',
				sortable : true
			} ,{
				width : '150',
				title : 'vip室名称',
				align : 'center',
				field : 'viproomName',
				
			}, {
				width : '150',
				title : '场站负责人',
				field : 'headerName',
				align : 'center',
				
			}, {
				width : '150',
				title : '负责人电话',
				field : 'headerPhone',
				align : 'center',
				
			},{
				title : '操作',
				field : 'action',
				width : '90',
				align : 'center',
				formatter : function(value, row) {
					var str = '';
					str += "<a href='gotoUpdateVipRoomView?viproomId="+row.viproomId+"'><img class='iconImg ext-icon-note_edit title='编辑'/></a>";
						str += sy.formatString('<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/>', row.viproomId);
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
				<!-- <td><button><a href="gotoAddVipRoomView">添加VIP室</a></button></td> -->
				
				<td><a href="gotoAddVipRoomView" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true">添加VIP室</a></td>
				<td>
					<!-- 搜索条件框 -->
					<form id="searchForm">
						<table>
							<tr>
								<td>输入搜索关键字</td>
								<td><input name="_searchContent" style="width: 125px;" placeholder="场站名称/VIP室名称"/></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="grid.datagrid('load',sy.serializeObject($('#searchForm')));">检索</a>
									<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-zoom_out',plain:true"
									onclick="$('#searchForm input').val('');grid.datagrid('load',{});">显示全部</a>
								</td>
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