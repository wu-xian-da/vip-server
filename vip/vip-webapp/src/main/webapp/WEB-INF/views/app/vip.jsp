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
	var look = function(id) {
		parent.$.customerFrame({
			title:"查看VIP用户记录",
			height : '600px',
			wight : '700px',
			url : sy.contextPath + '/app/look?id=' + id});
	};
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : sy.contextPath + '/app/list/vip',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'customerId',
			pageSize : 20,
			pageList : [20, 30, 40, 50, ],
		
			columns : [ [ {
				width : '80',
				title : '姓名',
				field : 'customerName'
			},{
				width : '150',
				title : '手机号',
				field : 'phone'
			},{
				width : '150',
				title : '日期',
				field : 'createTime'
			},{
				width : '150',
				title : '常住地址',
				field : 'address'
			},{
				width : '150',
				title : '邮箱',
				field : 'email'
			},{
				width : '150',
				title : '用户状态',
				field : 'orderStatu',
				formatter : function(value, row, index) {
					switch (value) {
					case 0:
						return '未支付';
					case 1:
						return '已支付';
					case 2:
						return '正在审核';
					case 3:
						return '审核通过';
					case 4:
						return '已退款';
					}
				}
			}, {
				title : '操作',
				field : 'action',
				width : '90',
				formatter : function(value, row) {
					var str = '';
						str += sy.formatString('<img class="iconImg ext-icon-note" title="查看详情" onclick="look(\'{0}\');"/> 查看', row.customerId);
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
	
	function exportVip(){
	   window.open(sy.contextPath + '/app/download');
	}
	function blurClick(){
		grid.datagrid('load',
				{
				'_orderState':$('#orderState').combobox('getValue'),
				'_namephone':$("#searchBox").val()
				});
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
	<table>
			<tr>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_go',plain:true" onclick="exportVip()">导出</a></td>
				<td><div class="datagrid-btn-separator"></div></td>
				<td><select id="orderState" class="easyui-combobox" name="orderState" data-options="panelHeight:'auto',editable:false" style="width: 155px;">
							<option value="" >请选择用户状态</option>
							<option value="0" >未支付</option>
							<option value="1" >已支付</option>
							<option value="2" >正在审核</option>
							<option value="3" >审核通过</option>
							<option value="4" >已退款</option>
					</select></td>
				<td><input id="searchBox" class="easyui-validatebox" style="width: 150px" placeholder='输入手机号/姓名'></input></td>
				<td><a href="javascript:void(0);" onclick="blurClick();" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true">搜索</a></td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>