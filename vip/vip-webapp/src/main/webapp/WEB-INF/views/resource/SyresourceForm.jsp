<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq, $mainMenu) {
		if ($('form').form('validate')) {
			var url;
			if ($(':input[name="id"]').val()!= '0') {
				url = sy.contextPath + '/resource/update';
			} else {
				url = sy.contextPath + '/resource/save';
			}
			$.post(url, sy.serializeObject($('form')), function(result) {
				if (result.ok) {
					$grid.treegrid('reload');
					$dialog.dialog('destroy');
					$mainMenu.tree('reload');
				} else {
					$pjq.messager.alert('提示', result.msgBody, 'error');
				}
			}, 'json');
		}
	};
	var showIcons = function() {
		var dialog = parent.sy.modalDialog({
			title : '浏览小图标',
			url : sy.contextPath + '/style/icons.jsp',
			buttons : [ {
				text : '确定',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.selectIcon(dialog, $('#iconCls'));
				}
			} ]
		});
	};
</script>
</head>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>资源基本信息</legend>
			<input name="id"  readonly="readonly" value="${resource.id }" type="hidden"/>
			<table class="table" style="width: 100%;">
				<tr>
					<th>资源名称</th>
					<td><input name="name" class="easyui-validatebox" data-options="missingMessage:'必填项',required:true" value="${resource.name }" /></td>
					<th>权限标识</th>
					<td><input class="easyui-validatebox" type="text" name="permission" data-options="missingMessage:'必填项',required:true" value =${resource.permission } ></input></td>
				</tr>
				<tr>
					<th>资源路径</th>
					<td><input name="url" value="${resource.url }"/></td>
					<th>顺序</th>
					<td><input name="seq"  value="${resource.seq }" class="easyui-numberspinner" data-options="missingMessage:'必填项',required:true,min:0,max:100000,editable:false" style="width: 155px;" value="100" /></td>
				</tr>
				<tr>
					<th>上级资源</th>
					<td><select id="syresource_id" name="parent.id"  value="${resource.parent.id }" class="easyui-combotree" data-options="editable:false,idField:'id',textField:'text',parentField:'pid',url:'${ctx }/resource/menus'" style="width: 155px;"></select><img class="iconImg ext-icon-cross" onclick="$('#syresource_id').combotree('clear');" title="清空" /></td>
					<th>资源图标</th>
					<td><input id="iconCls" name="iconCls"  value="${resource.iconCls }" readonly="readonly" style="padding-left: 18px; width: 134px;" /><img class="iconImg ext-icon-zoom" onclick="showIcons();" title="浏览图标" />&nbsp;<img class="iconImg ext-icon-cross" onclick="$('#iconCls').val('');$('#iconCls').attr('class','');" title="清空" /></td>
				</tr>
				<tr>
					<th>资源描述</th>
					<td colspan="3"><textarea name="description" style="width: 416px;height: 260px;" >${resource.description}</textarea></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>