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
	var delivery = function(id) {
			var url = sy.contextPath + '/app/updateDeliveryState/'+id;
			$.post(url, function(result) {
				if (result.ok) {
					// window.location.reload();
					$('#grid').datagrid('reload');    // 重新载入当前页面数据 
				} else {
					layer.alert(result.msgBody, {
						icon : 2,
						skin : 'layer-ext-moon' 
					});
				}
			}, 'json');
	};
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : sy.contextPath + '/app/list/vip',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'customer_id',
			pageSize : 20,
			pageList : [20, 30, 40, 50, ],
		
			columns : [ [ {
				width : '80',
				title : '姓名',
				field : 'customer_name'
			},{
				width : '100',
				title : '手机号',
				field : 'customer_phone'
			},{
				width : '50',
				title : '性别',
				field : 'sex',
				formatter : function(value, row, index) {
					switch (value) {
					case 0:
						return '女';
					case 1:
						return '男';
					}
				}
			},{
				width : '100',
				title : '证件类型',
				field : 'card_type',
				formatter : function(value, row, index) {
					switch (value) {
					case 1:
						return '身份证';
					case 2:
						return '护照';
					case 3:
						return '军官证';
					case 4:
						return '回乡证';
					}
				}
			},{
				width : '150',
				title : '证件号码',
				field : 'customer_identi'
			},{
				width : '150',
				title : '出生日期',
				field : 'birthday'
			},{
				width : '150',
				title : '订单号',
				field : 'order_id'
			},{
				width : '80',
				title : '投保状态',
				field : 'insured',
				formatter : function(value, row, index) {
					switch (value) {
					case 0:
						return '未投保';
					case 1:
						return '已投保';
					}
				}
			},{
				width : '80',
				title : '用户状态',
				field : 'orderstate'
			}, {
				title : '操作',
				field : 'action',
				width : '90',
				formatter : function(value, row) {
					switch (value) {
					case 0:
						return  sy.formatString('<img class="iconImg ext-icon-note_edit" title="投保" onclick="delivery(\'{0}\');"/> 投保&nbsp;', row.order_id);
					case 1:
						return '<a href="${ctx }/returnOrderDetailInfoByOrderId?orderId='+row.order_id+'"><img class="iconImg ext-icon-note_edit" title="查看" /></a> 查看&nbsp;';
					//default: 
						// return  sy.formatString('<img class="iconImg ext-icon-note_edit" title="投保" onclick="delivery(\'{0}\');"/> 投保&nbsp;', row.id);
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
	
	function exportVip(){
		var url = sy.contextPath + '/app/download?_insured='+$("#insured").val()+"&_orderstate="+$('#orderstate').val()+"&_namephone="+$("#searchBox").val();
	   window.open(url=encodeURI(url));
	}
	function blurClick(){
		grid.datagrid('load',
				{
				_insured:$("#insured").val(),
				_orderstate:$('#orderstate').val(),
				_namephone:$("#searchBox").val()
				});
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
	<table>
			<tr>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_go',plain:true" onclick="exportVip()">导出</a></td>
				<td><select id="orderstate" name="orderstate" >
											<option value="" selected="selected">请选择用户状态</option>
											<option value="未支付">未支付</option>
											<option value="已支付">已支付</option>
											<option value="已退款">已退款</option>
										</select>&nbsp;&nbsp;</td>
										<td><select id="insured" name="insured">
											<option value="" selected="selected">请选择投保状态</option>
											<option value="0">未投保</option>
											<option value="1">已投保</option>
										</select>&nbsp;&nbsp;</td>
				<td><input id="searchBox" class="easyui-validatebox" style="width: 200px" placeholder='输入手机号/姓名'></input></td>
				<td>
							<input type="button" value="查询" style="width: 60px;height: 20px;
						    border: none;
						    background: #698DC3;
						    border-radius: 5px;
						    text-align: center;
						    color: #FFF;" onclick="blurClick();" /></td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>