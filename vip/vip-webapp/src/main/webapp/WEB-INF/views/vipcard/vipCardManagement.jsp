<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<style type="text/css">
		.btn{
			width: 60px;
		    height: 20px;
		    border: none;
		    background: #698DC3;
		    border-radius: 5px;
		    text-align: center;
		    color: #FFF;
		}
		.btn_ex{
			width: 45px
		}
</style>
	
<script type="text/javascript">
	var grid;
	function searchByCondition(){
		grid.datagrid('load',{'_cardNo':$("#_cardNo").val(),"_cardState":$("#cardStateSel option:selected").val()});
	};
	
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
							pageSize : 20,
							pageList : [20, 30, 40, 50 ],
							columns : [ [
									{
										width : '180',
										title : '卡号',
										field : 'cardNo',
										align : 'left',
										sortable : true
									},
									{
										width : '150',
										title : '初始价格',
										field : 'initMoney',
										align : 'left',
										sortable : true
									},

									{
										width : '120',
										title : 'NFC号',
										field : 'nfcId',
										align : 'left',
										sortable : true
									},
									{
										width : '180',
										title : '导入时间',
										field : 'importTime',
										align : 'left',
										sortable : true,
									},
									{
										width : '150',
										title : '过期时间',
										field : 'expiryTime',
										align : 'left',
										sortable : true,
									},
									{
										width : '150',
										title : 'vip卡状态',
										field : 'cardState',
										align : 'left',
										sortable : true,
										formatter : function(value, row, index) {
											switch (value) {
											case 0:
												return '未绑定';
											case 1:
												return '绑定成功';
											case 2:
												return '已退卡';
											case 3:
												return '绑定失败';
											case 4:
												return '待绑定';
											case 5:
												return '解绑失败';
											case 6:
												return '绑定成功已激活';
											case 7:
												return '已过期';
											}
											
										}
									},
									{
										title : '操作',
										field : 'action',
										width : '90',
										align : 'left',
										formatter : function(value, row) {
											var str = '';
											str += sy
													.formatString(
															'<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/> 删除',
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

	//导入vip卡
	function importExcel() {
		$('#fm').form('submit', {
			url : sy.contextPath + "/vipCard/importExcel",
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(_d) {
				console.log("_d:" + _d);
				if (_d.result == 0) {
					$.messager.show({
						title : 'Error',
						msg : "数据格式出错"
					});
				} else {
					// reload the user data
					$('#grid').datagrid('reload');
				}
			}
		});
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">

	<div id="toolbar" style="display: none;">
	
		<table>
			<tr>
				<!-- 导入按钮 -->
				<td>
					<form id="fm" name="excelImportForm"
						action="${pageContext.request.contextPath}/vipCard/importExcel"
						method="post" enctype="multipart/form-data">
						<input class="easyui-validatebox" id="excel_file" type="file"	name="filename" accept="xls" style="width: 190px" data-options="missingMessage:'必填项',required:true"/> 
						<input class="btn btn_ex" id="excel_button" type="button" onclick="importExcel();" value="导入" />
					</form> 
				</td>
				<td style="width: 10px"></td>
				
				<!-- 导出按钮 -->
				<td><a href="${pageContext.request.contextPath}/vipCard/exportExcel"><button class="btn btn_ex">导出</button></a></td>
				<td style="width: 10px"></td>
				
				<!-- 搜索条件框 -->
				<td>
					<form id="searchForm">
						<table>
							<tr>
								<td>
									<select name="_cardState" id="cardStateSel">
										<option value="">全部vip卡状态</option>
										<option value="0">未绑定</option>
										<option value="1">绑定成功</option>
										<option value="2">已退卡</option>
										<option value="3">绑定失败</option>
										<option value="4">待绑定</option>
										<option value="5">解绑失败</option>
										<option value="6">绑定成功已激活</option>
                                        <option value="7">已过期</option>
									</select>
								</td>
								<td style="width: 10px"></td>
								<td><input id="_cardNo" name="_cardNo" value="" style="width: 120px;" placeholder="请输入卡号" /></td>
								<td><input type="button" value="查询" class="btn" onclick="searchByCondition();"></td>
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