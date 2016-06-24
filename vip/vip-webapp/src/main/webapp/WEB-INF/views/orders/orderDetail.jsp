<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<title></title>


<link rel="stylesheet" href="public/css/theme/default/easyui.css">
<link rel="stylesheet" href="public/css/theme/icon.css">
<link rel="stylesheet" href="public/css/site.css">

<script src="public/js/jquery.min.js"></script>
<script src="public/js/jquery.easyui.min.js"></script>
<script src="public/js/plugins/datagrid-scrollview.js"></script>

</head>
<body>

	<div id="order-details-container">
		<div class="goback-box">
			<button onClick="javascript:history.go(-1);">返回</button>
		</div>

		<div class="order-card-info">

			<div class="order-list-title">
				订单编号：<span>${orderDetailInfo.orderId}</span>
			</div>
			<ul>
				<li><label>订单日期：</label><fmt:formatDate value="${orderDetailInfo.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
				<li><label>开卡场站：</label>${orderDetailInfo.airportName}</li>
				<li><label>业务员：</label>${orderDetailInfo.agentName}</li>
				<li><label>业务员电话：</label>${orderDetailInfo.agentPhone}</li>
			</ul>


			<div class="order-list-title">
				VIP卡编号：<span>${orderDetailInfo.vipCardNo}</span>
			</div>
			<ul>
				<li><label>有效期：</label>${orderDetailInfo.cardType}年</li>
				<li><label>vip卡金额：</label><fmt:formatNumber value="${orderDetailInfo.initMoney}" pattern="0.00"/> 元</li>
				<li><label>支付方式：
				      <c:choose>
				      	<c:when test="${orderDetailInfo.payMethod == 1}">
				      		微信支付
				      	</c:when>
				      	<c:when test="${orderDetailInfo.payMethod == 2}">
				      		支付宝支付
				      	</c:when>
				      	<c:when test="${orderDetailInfo.payMethod == 3}">
				      		银行卡支付
				      	</c:when>
				      	<c:otherwise>
				      		现金支付
				      	</c:otherwise>
				      </c:choose>
					</label>
				
				
				</li>
				<li><label>支付时间：</label><fmt:formatDate value="${orderDetailInfo.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
				<li><label>卡片激活短信发送时间：</label><fmt:formatDate value="${orderDetailInfo.activatTime}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
			</ul>

			<div class="order-list-title">个人资料</div>
			<ul>
				<li>用户姓名：${orderDetailInfo.customerName}</li>
				<li>身份证：${orderDetailInfo.customerIdent }</li>
				<li>用户手机号：${orderDetailInfo.customerPhone }</li>
				<li>用户性别：${orderDetailInfo.sex ==1 ? '男' : '女' }</li>
				<li>常住地址：${orderDetailInfo.customerProvinceName} ${orderDetailInfo.customerCityName} ${orderDetailInfo.address}</li>
				<li>邮箱地址：${orderDetailInfo.email}</li>
			</ul>
			
			<!-- 发票信息 -->
			<div class="order-list-title">发票信息（
				<c:if test="${orderDetailInfo.invoiceFlag ==0}">
					未开
				</c:if>
				<c:if test="${orderDetailInfo.invoiceFlag ==1}">
					发票未邮寄
				</c:if>
				<c:if test="${orderDetailInfo.invoiceFlag ==2}">
					发票已邮寄
				</c:if>
				）
			</div>
			<c:if test="${orderDetailInfo.invoiceFlag != 0}">
				<ul>
						<li>邮寄地址：${invoice.address }</li>
						<li>发票类型：${invoice.invoiceType ==1? '个人':'公司' }</li>
						<li>发票抬头：${invoice.invoiceTitle}</li>
						<c:if test="${orderDetailInfo.invoiceFlag == 2}">
						<li>发票编号：${invoice.invoiceNo}</li>
						</c:if>
				</ul>
			</c:if>
			
			
			<div class="order-list-title">
				退款信息 <span class="order-tips-red">（${orderDetailInfo.orderState ==4 ?'已退款':'未退款'}）</span>
			</div>
			<c:if test="${orderDetailInfo.orderState ==4}">
				<ul>
					<li>退款金额：<fmt:formatNumber value="${appCardBack.money}" pattern="0.00"/> 元</li>
					<li>退款方式：
						<c:choose>
							<c:when test="${appCardBack.backType ==1}">
								微信转账
							</c:when>
							<c:when test="${appCardBack.backType ==2 }">
								支付宝转账
							</c:when>
							<c:when test="${appCardBack.backType ==3 }">
								银行卡转账
							</c:when>
							<c:otherwise>
								现金转账
							</c:otherwise>
						</c:choose>
					</li>
					<li>转账账号：${appCardBack.customerCard}</li>
				</ul>
			</c:if>
			
			<div class="order-list-title">VIP卡使用记录</div>
			<ul>
				<c:if test="${!empty consumeList}">
					<c:forEach items="${consumeList}" var="consume">
						<li>${consume.viproomName}&nbsp;单价：200元 &nbsp;消费时间：<fmt:formatDate value="${consume.consumeTime}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
					</c:forEach>
					
				</c:if>
				
				
			</ul>
			
			<div class="order-list-title">反馈信息</div>
			<ul>
				<c:if test="${!empty appuserFeedBackInfoList}">
					<c:forEach items="${appuserFeedBackInfoList }" var="appUserFeedBackInfo">
						<li>反馈时间：<fmt:formatDate value="${appUserFeedBackInfo.feedbackTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;反馈内容：${appUserFeedBackInfo.feedbackContent}</li>
					</c:forEach>
				</c:if>
			</ul>
			
			<c:if test="${!empty appCardBack.agreementUrl}">	
				<div class="order-list-title">紧急退款照片</div>
				<div><img width="400px" src="${appCardBack.agreementUrl}"></div>
			</c:if>
			
		</div>

	</div>
	

</body>
</html>