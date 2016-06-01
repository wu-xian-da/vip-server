<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<style type="text/css">
		.btn{
			padding: 0 8px;
			background: #D6DDE4;
			border-radius: 5px;
    		border: none;
    		border:1px solid #ccc;
    		box-shadow: 1px 1px 2px #DED7D7;
		}
		
		.btn-default{
			display: inline-block;
		    vertical-align: top;
		    width: auto;
		    line-height: 22px;
		    font-size: 12px;
			margin: 0 4px;
		}
</style>
	
<script type="text/javascript">
	var grid;
	//删除vip卡
	var removeFun = function(id) {
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
				$.post(sy.contextPath + '/vipCard/delVipCard', {
					cardNo : id
				}, function(dataObj) {
					if (!dataObj.ok) {
						$.messager.alert('msg', dataObj.msgBody, 'error');
						return;
					}
					;
					grid.datagrid('reload');

				}, 'json');
			}
		});
	};

	//页面初始化时进行渲染
	$(function() {
		grid = $('#grid')
				.datagrid(
						{
							title : '',
							url : sy.contextPath + '/vipCard/showVipCardList',
							striped : true,
							rownumbers : true,
							pagination : true,
							singleSelect : true,
							pageSize : 10,
							pageList : [ 5, 10, 20, 30, 40, 50 ],
							frozenColumns : [ [ {
								width : '100',
								title : '卡号',
								field : 'cardNo',
								align : 'center',
								sortable : true
							} ] ],
							columns : [ [
									{
										width : '150',
										title : 'NFC号',
										field : 'nfcId',
										align : 'center',
										sortable : true
									},
									{
										width : '150',
										title : '导入时间',
										field : 'importTime',
										align : 'center',
										sortable : true
									},
									{
										width : '150',
										title : '状态',
										field : 'cardState',
										align : 'center',
										sortable : true,
										formatter : function(value, row, index) {
											switch (value) {
											case 0:
												return '未激活';
											case 1:
												return '激活';
											case 2:
												return '退卡';
											}
										}
									},
									{
										title : '操作',
										field : 'action',
										width : '90',
										align : 'center',
										formatter : function(value, row) {
											var str = '';
											str += sy
													.formatString(
															'<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/>',
															row.cardNo);
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

		

		//输入框效果
		$("#filePath").focus(function() {
			var filePath = $("#filePath").val();
			if (filePath != "") {
				$("#filePath").val("");
			}
		})

		$("#filePath").blur(function() {
			var filePath = $("#filePath").val();
			if (filePath == "") {
				$("#filePath").val(this.defaultValue);
			}
		})

	});
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">

	<div id="toolbar" style="display: none;">
	
		<table>
			<tr>
				<td>
					<form name="excelImportForm"
						action="${pageContext.request.contextPath}/vipCard/importExcel"
						method="post" enctype="multipart/form-data">
						<input id="excel_file" type="file"	name="filename" accept="xls" style="width: 175px"/> 
						<input class="btn btn-default l-btn-left l-btn-icon-left easyui-linkbutton" id="excel_button" type="submit" value="导入vip卡号" />
					</form> 
					
				</td>
				
				<td><a href="${pageContext.request.contextPath}/vipCard/exportExcel"><button class="btn btn-default ">导出vip卡号</button></a></td>

				<!-- 搜索条件框 -->
				<td>
					<form id="searchForm">
						<table>
							<tr>

								<td><select name="_cardState" class="easyui-combobox"
									data-options="panelHeight:'auto',editable:false">
										<option value="3">选择状态</option>
										<option value="0">未激活</option>
										<option value="1">激活</option>
										<option value="2">退卡</option>

								</select></td>
								<td>请输入卡号</td>
								<td><input name="_cardNo" value="" style="width: 80px;" /></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-zoom',plain:true"
									onclick="grid.datagrid('load',sy.serializeObject($('#searchForm')));">检索</a><a
									href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-zoom_out',plain:true"
									onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置过滤</a></td>
							</tr>
						</table>
					</form>

				</td>
			</tr>

		</table>
	</div>

	<!--表格数据展示 -->
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>