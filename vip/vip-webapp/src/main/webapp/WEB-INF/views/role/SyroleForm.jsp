<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			var nodes = $('#tree').tree('getChecked', [ 'checked', 'indeterminate' ]);
			var ids = [];
			for (var i = 0; i < nodes.length; i++) {
				ids.push(nodes[i].id);
			}
			$("#ids").val(ids);
			var url;
			if ($(':input[name="id"]').val()!= '') {
				url = sy.contextPath + '/role/saveAndgrant';
			} else {
				url = sy.contextPath + '/role/saveAndgrant';
			}
			$.post(url, sy.serializeObject($('form')), function(result) {
				if (result.ok) {
					$grid.datagrid('load');
					$dialog.dialog('destroy');
				} else {
					$pjq.messager.alert('提示', result.msgBody, 'error');
				}
			}, 'json');
		}
	};
	$(function() {
		parent.$.messager.progress({
			text : '数据加载中....'
		});
		
		$('#tree').tree({
			url : sy.contextPath + '/resource/tree',
			parentField : 'pid',
			checkbox : true,
			formatter : function(node) {
				return node.name;
			},
			onLoadSuccess : function(node, data) {
				if($(':input[name="id"]').val()!=''){
					$.post(sy.contextPath + '/resource/roleResources', {
						id : $(':input[name="id"]').val()
					}, function(result) {
						if (result) {
							for (var i = 0; i < result.length; i++) {
								var node = $('#tree').tree('find', result[i].id);
								if (node) {
									var isLeaf = $('#tree').tree('isLeaf', node.target);
									if (isLeaf) {
										$('#tree').tree('check', node.target);
									}
								}
							}
						}
						parent.$.messager.progress('close');
					}, 'json');
				}else
				parent.$.messager.progress('close');
			}
		});
	});
</script>
</head>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>角色基本信息</legend>
			<input name="id" type="hidden" value="${role.id }" />
			<input name="ids" type="hidden" id="ids" />
			<table class="table" style="width: 100%;">
				<tr>
					<th>角色名称</th>
					<td><input name="name" class="easyui-validatebox" data-options="required:true" value="${role.name }"/></td>
					<th>角色描述</th>
					<td><textarea name="description" >${role.description }</textarea></td>
				</tr>
				<tr>
					<td colspan="4">
						<fieldset>
							<legend>角色授权</legend>
							<ul id="tree"></ul>
						</fieldset>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>