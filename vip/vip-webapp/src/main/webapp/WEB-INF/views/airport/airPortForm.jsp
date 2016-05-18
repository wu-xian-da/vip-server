<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<%
	String contextPath = request.getContextPath();
%>
<%
	String id = request.getParameter("id");
	if (id == null) {
		id = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			var url;
			if ($(':input[name="id"]').val()!='') {
				url = sy.contextPath + '/airport/update';
			} else {
				url = sy.contextPath + '/airport/save';
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
		if ($(':input[name="id"]').val()!='') {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(sy.contextPath + '/base/syrole!getById.sy', {
				id : $(':input[name="id"]').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'data.id' : result.id,
						'data.name' : result.name,
						'data.description' : result.description,
						'data.seq' : result.seq
					});
				}
				parent.$.messager.progress('close');
			}, 'json');
		}
	});
</script>
</head>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>场站基本信息</legend>
			<input name="id" type="hidden" value="${ariPort.id }" readonly="readonly" />
			<table class="table" style="width: 100%;">
				<tr style="padding-top: 5px;">
					<th>场站名称:</th>
					<td><input name="name"  value="${ariPort.name }" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr style="padding-top: 5px;">
					<th>省市信息:</th>
					<td>
						<div class="demo-wrap">
							<div><input id="pr1" type="text" name="province" placeholder="省份" value="${ariPort.province }"  /></div>
							<div><input id="ci1" type="text" name="city" placeholder="城市" style="display: none"  value="${ariPort.city }"/></div>
							<div><input id="co1" type="text" name="country" placeholder="县级" style="display: none" value="${ariPort.country }" /></div>
						</div>
					</td>
				</tr>
				<tr style="padding-top: 5px;">
					<th>场站负责人:</th>
					<td><input name="headerName" class="easyui-validatebox" data-options="required:true" ${ariPort.headerName } /></td>
				</tr>
				<tr style="padding-top: 5px;">
					<th>负责人联系方式:</th>
					<td><input name="headerPhone" class="easyui-validatebox" data-options="required:true" ${ariPort.headerPhone } /></td>
				</tr>
				<tr style="padding-top: 5px;">
					<th>业务员数量:</th>
					<td>
					<input class="easyui-numberspinner" name="agentNum"  value="6" data-options="increment:1" style="width:120px;" ${ariPort. agentNum}></input>
				</tr>
				<tr style="padding-top: 5px;">
					<th>场站状态:</th>
					<c:if test="${ariPort.state==0 }">
						<td><input type="radio" value="0" name="state"  checked>运营<input type="radio" value="1" name="state" >冻结</td>
					</c:if>
					<c:if test="${empty ariPort or ariPort.state==1 }">
						<td><input type="radio" value="0" name="state"  checked>运营<input type="radio" value="1" name="state" >冻结</td>
					</c:if>
				</tr>
			</table>
		</fieldset>
	</form>
	 <script type="text/javascript">
            new locationCard({
                ids: ['pr1', 'ci1', 'co1']
            }).init();
            new locationCard({
                ids: ['pr2', 'ci2', 'co2']
            }).init();
        </script>
</body>
</html>