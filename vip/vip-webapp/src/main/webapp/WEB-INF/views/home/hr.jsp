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
	<script type="text/javascript">
		var subgo = function(state) {
			var dialog = parent.sy.modalDialog({
				title : '详情',
				url : '${ctx}/droll/home?state='+state
			});
		};
	</script>
</head>
<body>
	<style>
				.bashboard-content{ text-align: center; padding: 20px 0 30px; font-size: 14px; font-family: "Microsoft YaHei"; color: #ff4242; }
				.dashboard div img{ width: 100%; }
			</style>
			<div class="dashboard">
				<div class="bashboard-top"><img src="${ctx }/style/images/dashboard-1.jpg" alt=""></div>
				<div class="bashboard-content">
					<p>上月入职新员工 <span><a href="javascript:void(0)" onclick="subgo(0)">${come }</a> </span> 人&nbsp;&nbsp;离职<span><a href="javascript:void(0)" onclick="subgo(1)" >${out }</a> </span> 人</p>
				</div>
				<div class="bashboard-bottom"><img src="${ctx }/style/images/dashboard-2.jpg" alt=""></div>
			</div>
</body>
</html>