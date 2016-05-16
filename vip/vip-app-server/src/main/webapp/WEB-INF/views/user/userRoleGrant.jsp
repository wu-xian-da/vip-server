<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq) {
		var nodes = $('#tree').tree('getChecked',
				[ 'checked', 'indeterminate' ]);
		var ids = [];
		for (var i = 0; i < nodes.length; i++) {
			ids.push(nodes[i].id);
		}
		$.post(sy.contextPath + '/user/grantRole', {
			id : $(':input[name="data.id"]').val(),
			ids : ids.join(',')
		}, function(result) {
			if (result.ok) {
				$dialog.dialog('destroy');
			} else {
				$pjq.messager.alert('提示', result.msgBody, 'error');
			}
			$pjq.messager.alert('提示', result.msgBody, 'info');
		}, 'json');
	};
	$(function() {
		parent.$.messager.progress({
			text : '数据加载中....'
		});
		$('#tree').tree(
				{
					url : sy.contextPath + '/role/tree',
					parentField : 'pid',
					checkbox : true,
					formatter : function(node) {
						return node.name;
					},
					onLoadSuccess : function(node, data) {
						$.get(sy.contextPath + '/role/selectroles/'
								+ $(':input[name="data.id"]').val(), function(
								result) {
							if (result) {
								for (var i = 0; i < result.length; i++) {
									var node = $('#tree').tree('find',
											result[i].id);
									if (node) {
										var isLeaf = $('#tree').tree('isLeaf',
												node.target);
										if (isLeaf) {
											$('#tree').tree('check',
													node.target);
										}
									}
								}
							}
							parent.$.messager.progress('close');
						}, 'json');
					}
				});
	});
</script>
</head>
<body>
	<input name="data.id" value="${user.id }" readonly="readonly"
		type="hidden" />
	<fieldset>
		<legend>所属角色</legend>
		<ul id="tree"></ul>
	</fieldset>
</body>
</html>