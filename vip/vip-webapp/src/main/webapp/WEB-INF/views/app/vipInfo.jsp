<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">

	<div style="margin:50px;">
		<c:if test="${!empty orderInfo }">
			<c:forEach items="${orderInfo }" var="order">
				<div>订单编号：${order.order_id }</div>
				<div style="height: 5px;"></div>
				<div>开卡日期：${order.order_time }</div>
				<div>开卡场站：${order.airport_name }</div>
				<div>业务员：${order.name }</div>
				<div>业务员电话：${order.phone }</div>
			</c:forEach>
		</c:if>
		<hr>
		<c:if test="${!empty vipCardInfo }">
		  <c:forEach items="${ vipCardInfo}" var="card">
		  	<div>VIP卡编号：${card.card_no }</div>
			<div>有效期：${card.valide_time }</div>
			<div>vip卡金额：${card.remain_money }</div>
			<div>支付方式：${card.pay_type }</div>
			<div>支付时间：${card.pay_time }</div>
			<div>卡片激活短信发送时间：${card.activat_time }</div>
			<div style="height: 10px;"></div>
		  </c:forEach>
		</c:if>
		<hr>
		<div>
			<h3>个人信息</h3>
		</div>
		<div>用户姓名：${customer.customerName }</div>
		<div>身份证：${customer.customerIdenti }</div>
		<div>用户手机号：${customer.phone }</div>
		<div>
			用户性别：
			<c:choose>
				<c:when test="${customer.sex==1 }">
				男
			</c:when>
				<c:otherwise>女</c:otherwise>
			</c:choose>
		</div>
		<div>常驻城市：${customer.address }</div>
		<div>邮箱地址：${customer.email }</div>
		<hr>
		<c:if test="${!empty postInfo }">
			<c:forEach items="${postInfo }" var="post">
				<div>发票信息：${post.invoice_flag }</div>
				<div>邮寄地址：${post.address }</div>
				<div>发票类型：${post.invoice_type }</div>
				<div>发票抬头：${post.invoice_title }</div>
			</c:forEach>
		</c:if>
		<hr>
		<c:if test="${!empty backMoneyInfo }">
			<div>
				<h3>退款信息</h3>
			</div>
			<c:forEach items="${backMoneyInfo }" var="backMoneyInfo">
				<div>提款金额：${backMoneyInfo.money }</div>
				<div>退款方式：${backMoneyInfo.back_type }</div>
				<div style="height: 5px;"></div>
			</c:forEach>
		</c:if>

		<div>
			<h3>VIP卡使用记录</h3>
		</div>
		<c:if test="${!empty vipCardRescordInfo }">
			<c:forEach items="${vipCardRescordInfo }" var="vip">
				<div>${vip.viproom_name }&nbsp;&nbsp;${vip.consume_money }&nbsp;&nbsp;${vip.consume_time }</div>
			</c:forEach>
		</c:if>
	</div>

</body>
</html>