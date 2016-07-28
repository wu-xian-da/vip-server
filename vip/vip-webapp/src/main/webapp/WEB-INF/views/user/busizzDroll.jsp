<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : sy.contextPath + '/busizz/droll?state=${state}',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
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
			columns : [ [ {
				width : '80',
				title : '职位',
				field : 'job'
			}, {
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
			}, {
				width : '100',
				title : '手机号码',
				field : 'phone'
			}, {
				width : '120',
				title : '入职时间',
				field : 'createdatetime'
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
						<shiro:hasPermission name="system:busizz:add">
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
						</shiro:hasPermission>
						<td>
							<form id="searchForm">
								<table>
									<tr>
										<td>姓名：</td>
										<td><input id="name" style="width: 200px" placeholder='输入业务员名字'></input></td>
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