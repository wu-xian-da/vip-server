<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
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
</script>
	<link rel="stylesheet" href="${ctx }/style/vip.css">
</head>
<body>
	<form method="post" class="form" >
		<fieldset>
			<legend>场站基本信息</legend>
			<input name="id" type="hidden" value="${ariPort.airport_id }" readonly="readonly" />
			<table class="table" style="width: 100%;">
				<tr style="padding-top: 5px;">
					<th>场站名称:</th>
					<td><input name="name"  value="${ariPort.airport_name }" class="easyui-validatebox" data-options="required:true" style="text-align: left;"/></td>
				</tr>
				<tr style="padding-top: 5px;">
					<th>省市信息:</th>
					<td>
					
						<div class="demo-wrap">
							<div id="selectbox">

							</div>
						</div>
					</td>
				</tr>
				<tr style="padding-top: 5px;">
					<th>场站负责人:</th>
					<td><input style="text-align: left;" name="headerName" class="easyui-validatebox" data-options="required:true" value="${ariPort.header_name }" /></td>
				</tr>
				<tr style="padding-top: 5px;">
					<th>负责人联系方式:</th>
					<td><input style="text-align: left;" name="headerPhone" class="easyui-validatebox" data-options="required:true" value="${ariPort.header_phone }" /></td>
				</tr>
				<tr style="padding-top: 5px;">
					<th>业务员数量:</th>
					<td>
					<input class="easyui-numberspinner" name="agentNum"  value="${ariPort.agent_num } data-options="increment:1" style="width:120px;" ></input>
				</tr>
				<tr style="padding-top: 5px;">
					<th>场站状态:</th>
					<c:choose>
						<c:when test="${!empty ariPort and ariPort.state==1}">
							<td><input type="radio" value="1" name="state"  checked>运营<input type="radio" value="0" name="state" >冻结</td>
						</c:when>
						<c:otherwise>
							<td><input type="radio" value="1" name="state"  >运营<input type="radio" value="0" name="state" checked >冻结</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</table>
		</fieldset>
	</form>
       <script>
		$(function() {
			function SelectedBox(args) {
				var $_selectBox = $("#" + args.elem);
				_selectboxHTMl = '<style type=\"text/css\">' + '#' + args.elem + ' select{ margin-right: 20px; }' + '</style>' + '<select name=\"\" id=\"province-select\">' + '<option value=\"\">请选择省份</option>' + '</select><br>'
				+ '<select name=\"\" id=\"city-select\">' + '<option value=\"\">请选择城市</option>' + '</select><br>'
				+ '<select name=\"\" id=\"region-select\">' + '<option value=\"\">请选择区域</option>' + '</select>'
				+ '<input type=\"hidden\" value=\"\" name=\"province\" id=\"region-input-id\">';
				$_selectBox.html($(_selectboxHTMl));
				var $_regionInputId = $("body #region-input-id");
				$.get(args.provinceUrl, function(_d) {
					var _provinOption = '<option value=\"\">请选择省份</option>';
					for (var i = 0; i < _d.length; i++) {
						_provinOption += '<option value=\"' + _d[i].cid + '\">' + _d[i].name + '</option>';
					}
					$("body #province-select").html(_provinOption);
				}, 'json');
				$("body").on('change', '#province-select', function() {
					var _pId = $(this).val();
					if (_pId != "") {
						$.get(args.cityUrl, {
							pid: _pId
						}, function(_d) {
							var _cityOption = "<option value=\"\">请选择城市</option>";
							for (var i = 0; i < _d.length; i++) {
								_cityOption += '<option value=\"' + _d[i].cid + '\">' + _d[i].name + '</option>';
							}
							$("body #city-select").html(_cityOption);
							$("body #region-select").html('<option value=\"\">请选择区域</option>')
							$_regionInputId.val(_pId);
						}, 'json')
					}
				});
				$("body").on('change', '#city-select', function() {
					var _cId = $(this).val();
					if (_cId != "") {
						$.get(args.regionUrl, {
							pid: _cId
						}, function(_d) {
							var _regionOption = '<option value=\"\">请选择区域</option>';
							for (var i = 0; i < _d.length; i++) {
								_regionOption += '<option value=\"' + _d[i].cid + '\">' + _d[i].name + '</option>';
							}
							$("body #region-select").html(_regionOption);
							var _regionInputId = $_regionInputId.val().split('-')
							$_regionInputId.val(_regionInputId[0] + '-' + _cId);
						}, 'json')
					}
				});
				$("body").on("change", '#region-select', function() {
					var _regionId = $(this).val();
					if (_regionId != "") {
						$_regionInputId.val($_regionInputId.val() + '-' + _regionId);
					}
				});
			}
			new SelectedBox({
				elem: "selectbox",
				provinceUrl: '${ctx}/airport/selectCity?pid=0',
				cityUrl: '${ctx}/airport/selectCity',
				regionUrl: '${ctx}/airport/selectCity'
			});
		})
		
		function returnFun(obj){
			if(obj==''||null==obj){
				return '0';
			}
			return obj;
		}
	</script>
</body>
</html>