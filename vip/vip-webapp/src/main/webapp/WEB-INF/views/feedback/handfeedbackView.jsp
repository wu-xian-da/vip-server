<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title></title>


<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/theme/default/easyui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/theme/icon.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/site.css">

<script src="${pageContext.request.contextPath}/public/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/js/jquery.easyui.min.js"></script>
<script src="${pageContext.request.contextPath}/public/js/plugins/datagrid-scrollview.js"></script>

</head>
<body>

	<div id="order-details-container">
		<div class="goback-box">
			<button onClick="javascript:history.go(-1);">返回</button>
		</div>

		<div class="order-card-info">
			<!-- 订单信息 -->
			<c:if test="${!empty orderInfo}">
				<c:forEach items="${orderInfo }" var="order">
					<div class="order-list-title">
					订单编号：<span>${order.order_id}</span>
					</div>
					<ul>
						<li><label>开卡时间：</label><fmt:formatDate value="${order.order_time}" pattern="yyyy-MM-dd HH:mm:ss"/> </li>
						<li><label>开卡场站：</label>${order.airport_name}</li>
						<li><label>业务员：</label>${order.name}</li>
						<li><label>业务员电话：</label>${order.phone}</li>
					</ul>
				</c:forEach>
			</c:if>
			
			<!--卡信息  -->
			<c:if test="${!empty vipCardInfo }">
				<c:forEach items="${ vipCardInfo}" var="card">
					<div class="order-list-title">
						VIP卡编号：<span>${card.card_no}</span>
					</div>
					<ul>
						<li><label>有效期：</label>${card.valide_time }年</li>
						<li><label>vip卡金额：</label>${card.remain_money}￥</li>
						<li><label>支付方式：</label>${card.pay_type }</li>
						<li><label>支付时间：</label>${card.pay_time}</li>
						<li><label>卡片激活短信发送时间：</label>${card.activat_time}</li>
					</ul>
				</c:forEach>
			</c:if>
			
			<!--个人资料  -->
			<div class="order-list-title">个人资料</div>
			<ul>
				<li>用户姓名：${customer.customerName}</li>
				<li>身份证：${customer.customerIdenti }</li>
				<li>用户手机号：${customer.phone }</li>
				<li>用户性别：${customer.sex ==1 ? '男' : '女' }</li>
				<li>常驻城市：${customer.address }</li>
				<li>邮箱地址：${customer.email}</li>
			</ul>

			<!-- 发票信息 -->
			<c:if test="${!empty postInfo }">
				<c:forEach items="${postInfo }" var="post">
					<div class="order-list-title">发票信息</div>
					<ul>
						<li></li>
						<li>邮寄地址：${post.address }</li>
						<li>发票类型：${post.invoice_type==1? '个人':'公司'}</li>
						<li>发票抬头：${post.invoice_title }</li>
					</ul>

				</c:forEach>
			</c:if>

			<!-- 退款金额 -->
			<c:if test="${!empty backMoneyInfo }">
				<div class="order-list-title">
					退款信息 <span class="order-tips-red"></span>
				</div>
				<c:forEach items="${backMoneyInfo }" var="backMoneyInfo">
					<ul>
						<li>退款金额：${backMoneyInfo.money }<</li>
						<li>退款方式：${backMoneyInfo.back_type }</li>
					</ul>
				</c:forEach>
				
			</c:if>
			
			<!-- vip卡使用记录 -->
			<c:if test="${!empty vipCardRescordInfo }">
				<div class="order-list-title">VIP卡使用记录</div>
				<ul>
					<c:forEach items="${vipCardRescordInfo }" var="vip">
						<li>${vip.viproom_name }&nbsp;&nbsp;${vip.consume_money }&nbsp;&nbsp;${vip.consume_time }</li>
					</c:forEach>
				</ul>
				
			</c:if>
			
			<div class="order-list-title">用户反馈信息</div>
			<ul>
				<li>${feedbackContent}</li>
			</ul>
			

		</div>
		
		<!-- 处理按钮 -->
		<form action="updateFeedbackState" method="post">
			<input type="hidden" name="id" value="${feedbackId }">
			<div id="clerk-footer">
				<input type="submit" class="btn btn-submit" value="处理" style="background: #64A6E0"/>
			</div>
		</form>
		
		

	</div>
	

</body>
</html>