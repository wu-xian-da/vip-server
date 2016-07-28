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
			url : sy.contextPath + '/log/list',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'log_id',
			pageSize : 20,
			pageList : [20, 30, 40, 50, ],
		
			columns : [ [ {
				width : '100',
				title : '关键字',
				field : 'search_key'
			},{
				width : '80',
				title : '模块类型',
				field : 'module_type',
				formatter : function(value, row, index) {
					switch (value) {
					case '001':
						return '订单模块';
					case '002':
						return '短信模块';
					case '003':
						return 'vip卡模块';
					}
				}
			},{
				width : '150',
				title : '操作时间',
				field : 'operate_time'
			},{
				width : '800',
				title : '操作内容',
				field : 'operate_content'
			}] ],
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
	
	function blurClick(){
		grid.datagrid('load',
				{
				_moduleType:$("#moduleType").val(),
				_searchKey:$('#searchKey').val(),
				_operateContent:$("#operateContent").val(),
				_start:$("#start").val(),
				_end:$("#end").val()
				});
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
	<table>
			<tr>
				<td><select id="moduleType" name="moduleType" >
											<option value="" selected="selected">请选择模块类型</option>
											<option value="001">订单模块</option>
											<option value="002">短信模块</option>
											<option value="003">vip卡模块</option>
										</select>&nbsp;&nbsp;</td>
				<td><input id="searchKey" class="easyui-validatebox" style="width: 200px" placeholder='输入关键字'></input></td>
				<td><input id="operateContent" class="easyui-validatebox" style="width: 200px" placeholder='输入操作内容'></input></td>
				<td>
					<input id="start" style="height: 16px;" class="Wdate" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'end\')||\'2020-10-01\'}'})" placeholder='起始时间'/> 
					<input id="end" class="Wdate" style="height: 16px;" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'start\')}',maxDate:'2020-10-01'})" placeholder='结束时间'/>
				</td>
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