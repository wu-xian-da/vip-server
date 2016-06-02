<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="${ctx }/jslib/jquery-easyui-1.3.4/themes/default/easyui.css">
	<link rel="stylesheet" href="${ctx }/jslib/jquery-easyui-1.3.4/themes/icon.css">
	<link rel="stylesheet" href="${ctx }/style/vip.css">
	<script src="${ctx }/jslib/jquery-1.9.1.js"></script>
	<script src="${ctx }/jslib/jquery-easyui-1.3.4/jquery.easyui.min.js"></script>
	<title>Airport-Card</title>
</head>
<body>
		<div id="selectbox">

		</div>
	</body>
	<script>
		$(function() {
			function SelectedBox(args) {
				var $_selectBox = $("#" + args.elem);
				_selectboxHTMl = '<style type=\"text/css\">' + '#' + args.elem + ' select{ margin-right: 20px; }' + '</style>' + '<select name=\"\" id=\"province-select\">' + '<option value=\"\">请选择省份</option>' + '</select><br>'
				+ '<select name=\"\" id=\"city-select\">' + '<option value=\"\">请选择城市</option>' + '</select><br>'
				+ '<select name=\"\" id=\"region-select\">' + '<option value=\"\">请选择区域</option>' + '</select>'
				+ '<input type=\"hidden\" value=\"\" id=\"region-input-id\">';
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
				provinceUrl: '${ctx}/airport/selectCity',
				cityUrl: '${ctx}/airport/selectCity',
				regionUrl: '${ctx}/airport/selectCity'
			});
		})
	</script>

</html>