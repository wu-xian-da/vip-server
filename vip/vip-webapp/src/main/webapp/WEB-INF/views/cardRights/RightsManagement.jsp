<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<style type="text/css">
	a{
	text-decoration: none;
	color:#404040
	}
</style>
<script type="text/javascript">
	var grid;
	//逻辑删除vip室
	var removeFun = function(id) {
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
				$.post(sy.contextPath + '/right/delRightById', {
					id : id
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
	//页面初始化时进行渲染
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : sy.contextPath + '/right/showRightByPage',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			
			pageSize : 20,
			pageList : [20, 30, 40, 50],
			columns : [ [{
				width : '350',
				title : '标题',
				field : 'title',
				align : 'left',
				sortable : true
			} ,{
				width : '200',
				title : '类型',
				align : 'left',
				field : 'type',
				formatter : function(value, row, index) {
					switch (value) {
					case 1:
						return '登录前vip卡权益';
					case 2:
						return '常见问题';
					case 3:
						return '关于';
					case 4:
						return '登录后vip卡权益';
					}
				}
				
			},{
				title : '操作',
				field : 'action',
				width : '180',
				align : 'left',
				formatter : function(value, row) {
					var str = '';
					str += "<a href='gotoUpdateRightView?id="+row.id+"'><img class='iconImg ext-icon-note_edit title='编辑'/> 编辑&nbsp;</a>";
						str += sy.formatString('<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/> 删除', row.id);
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
				<td><a href="gotoAddRightView" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true">添加素材</a></td>
				
			</tr>
			
		</table>
	</div>
	
	<!--数据表格展示 -->
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>