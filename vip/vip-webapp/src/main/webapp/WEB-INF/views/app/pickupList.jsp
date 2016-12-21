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
	var handled = function(id) {//投递信息
		var dialog = parent.sy.modalDialog({
			url : sy.contextPath + '/pickup/submit/'+id,
			width : 640,
			height : 410,
			buttons : [ {
				text : '投递',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} , {
				text : '取消',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.destroy(dialog, grid);
				}
			} ]
		});
	};	
	var look = function(id) {
		var dialog = parent.sy.modalDialog({
			url : sy.contextPath + '/pickup/submit/'+id,
			width : 640,
			height : 410,
			buttons : [ {
				text : '取消',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.destroy(dialog, grid);
				}
			} ]
		});
	};
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : sy.contextPath + '/pickup/list',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			pageSize : 20,
			pageList : [ 20, 30, 40, 50],
			frozenColumns : [ [{
				width : '100',
				title : '姓名',
				field : 'name'
			},{
				width : '120',
				title : '手机号码',
				field : 'phone'
			}  ] ],
			columns : [ [ {
				width : '160',
				title : '机场',
				field : 'airport_name'
			},{
				width : '100',
				title : '用车类型',
				field : 'pickup_type',
				formatter : function(value, row, index) {
					switch (value) {
					case 1:
						return '接机';
					case 2:
						return '送机';
					}
				}
			},{
				width : '100',
				title : '用车状态',
				field : 'submit',
				formatter : function(value, row, index) {
					switch (value) {
					case 1:
						return '未投递';
					case 2:
						return '已投递';
					 default: 
						 return '未投递';
					}
				}
			},{
				width : '120',
				title : '航班日期',
				field : 'flight_date',
				sortable : true,
			}, {
				width : '150',
				title : '出发时间',
				field : 'gooff_date',
				sortable : true,
			}, {
				width : '150',
				title : '创建时间',
				field : 'create_time',
				sortable : true,
			}, {
				width : '80',
				title : '同行人数',
				align:'center',
				field : 'number_peers',
				sortable : true
			},  {
				width : '200',
				title : '操作',
				field : 'action',
				formatter : function(value, row) {
					switch (value) {
					case 1:
						return  sy.formatString('<img class="iconImg ext-icon-note_edit" title="投递" onclick="handled(\'{0}\');"/> 投递&nbsp;', row.id);
					case 2:
						return sy.formatString('<img class="iconImg ext-icon-note_edit" title="查看" onclick="look(\'{0}\');"/> 查看&nbsp;', row.id);
					 default: 
						 return  sy.formatString('<img class="iconImg ext-icon-note_edit" title="投递" onclick="handled(\'{0}\');"/> 投递&nbsp;', row.id);
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
	function search(){
		grid.datagrid('load',{_nameandphone:$("#nameandphone").val(),_submit:$("#submit").val(),_pickup_type:$("#pickup_type").val()});
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
						<td>
							<form id="searchForm">
								<table>
									<tr>
										<td><select id="pickup_type" name="pickup_type" >
											<option value="" selected="selected">请选择用车类型</option>
											<option value="1">接机</option>
											<option value="2">送机</option>
										</select>&nbsp;&nbsp;</td>
										<td><select id="submit" name="submit">
											<option value="" selected="selected">请选择用车状态</option>
											<option value="1">未投递</option>
											<option value="2">已递交</option>
										</select>&nbsp;&nbsp;</td>
										<td><input id="nameandphone" name="nameandphone" style="width: 150px" placeholder='输入手机号/姓名'></input></td>
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