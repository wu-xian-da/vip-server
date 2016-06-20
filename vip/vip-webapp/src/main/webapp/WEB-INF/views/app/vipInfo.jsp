<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="${ctx }/jslib/jquery-easyui-1.3.4/themes/default/easyui.css">
	<link rel="stylesheet" href="${ctx }/jslib/jquery-easyui-1.3.4/themes/icon.css">
	<link rel="stylesheet" href="${ctx }/style/vip.css">
	<script src="${ctx }/jslib/jquery-1.9.1.js"></script>
	<script src="${ctx }/jslib/jquery-easyui-1.3.4/jquery.easyui.min.js"></script>
<title></title>
</head>
<body>
		<div id="order-details-container">
			<div class="order-card-info">
					<c:if test="${!empty orderInfo }">
						<c:forEach items="${orderInfo }" var="order">
							<div class="order-list-title">订单编号：<span>${order.order_id }</span></div>			
							<ul>
								<li><label>日期：</label>${order.order_time }</li>
								<li><label>开卡场站：</label>${order.airport_name }</li>
								<li><label>业务员：</label>${order.name }</li>
								<li><label>业务员电话：</label>${order.phone }</li>
							</ul>
						</c:forEach>
					</c:if>
					<c:if test="${!empty vipCardInfo }">
					  <c:forEach items="${ vipCardInfo}" var="card">
						<div class="order-list-title">VIP卡编号：<span>${card.card_no }</span></div>
						<ul>			
							<li><label>有效期：</label>${card.valide_time }</li>
							<li><label>vip卡金额：</label>${card.remain_money }</li>
							<li><label>支付方式：</label>${bbli:payType(card.pay_type) }</li>
							<li><label>支付时间：</label>${card.pay_time }</li>
							<li><label>卡片激活短信发送时间：</label>${card.activat_time }</li>
						</ul>
					  </c:forEach>
					</c:if>
				 
				<div class="order-list-title">个人资料</div>
				<ul>
					<li>用户姓名：${customer.customerName }</li>
					<li>身份证：${customer.customerIdenti }</li>
					<li>用户手机号：${customer.phone }</li>
					<c:choose>
						<c:when test="${customer.sex==1 }">
						<li>用户性别：男</li>
					</c:when>
						<c:otherwise><li>用户性别：女</li></c:otherwise>
					</c:choose>
					
					<li>常住地址：${customer.address }</li>
					<li>邮箱地址：${customer.email }</li>
				</ul>
				 <c:if test="${!empty postInfo }">
					<c:forEach items="${postInfo }" var="post">
						<div class="order-list-title">发票信息</div>
							<ul>
								<li>邮寄地址：${post.address }</li>
								<li>发票类型：${bbli:postType(post.invoice_type) }</li>
								<li>发票抬头：${post.invoice_title }</li>
							</ul>
					</c:forEach>
				</c:if>

				<c:if test="${empty backMoneyInfo }">
					<c:forEach items="${backMoneyInfo }" var="back">
						<div class="order-list-title">退款信息</div>
						<ul>
							<li>提款金额：${back.money }元</li>
							<li>退款方式：${bbli:payType(back.back_type) }</li>
						</ul>
					</c:forEach>
				</c:if>
				
				<div class="order-list-title">VIP卡使用记录</div>
					<ul>
						<c:forEach items="${vipCardRescordInfo }" var="vp" varStatus="status">
							<li>记录${ status.index + 1}</li>
							<li style="padding-left: 20px;">${vp.viproom_name }&nbsp; 单价${vp.consume_money } &nbsp; 时间:${vp.consume_time }</li>
						</c:forEach>
					</ul>
			</div>
		</div>
	</body>
</html>