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
		var data =eval("("+'${draw}'+")");
	</script>
</head>
<body>
<style>
				.bashboard-content{ text-align: center; padding: 20px 0 30px; font-size: 14px; font-family: "Microsoft YaHei"; color: #ff4242; }
				.dashboard div img{ width: 100%; }
			</style>
			<div class="dashboard">
				<div class="bashboard-top"><img src="${ctx }/style/images/caiwu.jpg" alt=""></div>
				<div class="bashboard-content">
					<p>本月订单有 <span>${nowMonthOrder }</span> 条</p>
					<p>未处理的退卡申请有 <span>${unHandleOrder }</span> 条</p>
				</div>
				<div class="bashboard-bottom"><img src="${ctx }/style/images/dashboard-2.jpg" alt=""></div>
			</div>
</body>
</html>