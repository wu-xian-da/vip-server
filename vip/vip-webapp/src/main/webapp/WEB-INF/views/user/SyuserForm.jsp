<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<script type="text/javascript">
	var submitNow = function($dialog, $grid, $pjq) {
		var url= sy.contextPath + '/user/save';
		var arids = '';
		$("#area input:checkbox").each(function(){
			if(this.checked){
				arids=arids+$(this).val()+",";
			}
		});
		var nodes = $('#tree').tree('getChecked',
				[ 'checked', 'indeterminate' ]);
		$("#arids").val(arids);
		var ids = [];
		for (var i = 0; i < nodes.length; i++) {
			ids.push(nodes[i].id);
		}
		$("#roleids").val(ids.join(','));
		$.post(url, sy.serializeObject($('form')), function(result) {
			if (result.ok) {
				$pjq.messager.alert('提示', result.msgBody, 'info');
				$grid.datagrid('load');
				$dialog.dialog('destroy');
			} else {
				layer.alert(result.msgBody, {
					icon : 2,
					skin : 'layer-ext-moon' 
				});
			}
		}, 'json');
	};
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			submitNow($dialog, $grid, $pjq);
		}
	};
	$(function() {
		$.fn.checkCbx = function() {
			return this.each(function() {
				this.checked = !this.checked;
			});
		};
		$(function() {
			$("#all").click(function() {
				$("#area input:checkbox").checkCbx();
			});
		});
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
						$.get(sy.contextPath + '/role/selectroles/'+ $(':input[name="id"]').val(), function(
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
	<form method="post" class="form">
		<fieldset>
		<input name="id" type="hidden" value="${user.id }" readonly="readonly" />
		<input name="arids" type="hidden" id="arids">
		<input name="roleids" type="hidden" id="roleids">
			<legend>用户基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>姓名</th>
					<td><input name="name" value="${user.name }" class="easyui-validatebox" data-options="required:true" /></td>
					<th>登陆名称</th>
					<td><input name="loginName" value="${user.loginName }" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td colspan="4">
						<fieldset>
							<legend>所属角色</legend>
							<ul id="tree"></ul>
						</fieldset>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<fieldset>
						<legend>所属区域</legend>
						<label><input name="Fruit" type="checkbox" value="" id="all" />全国 </label> <br /><br /> 
						<div id="area" style="padding-left: 20px;margin-top: -20px;">
							<table border="0">
							<tr>
							<c:forEach var="data" items="${datas }" varStatus="i" >
								<c:if test="${(i.index)%3==0 }">
								</tr>
								</c:if>
								<td style="border: none;">
									<label><input  type="checkbox" value="${data.id }" <c:if test="${data.checked }">checked="checked" </c:if> />${data.name }</label>
								</td>
							</c:forEach>
							</tr>
							</table>
						</div>
						</fieldset>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>