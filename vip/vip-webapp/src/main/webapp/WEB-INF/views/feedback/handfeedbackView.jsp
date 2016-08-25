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
			<!--个人资料  -->
			<div class="order-list-title">个人资料</div>
			<ul>
				<li>用户姓名：${customer.customerName}</li>
				<li>证件类型：
					<c:if test="${customer.cardType == 1}">身份证</c:if>
					<c:if test="${customer.cardType == 2}">护照</c:if>
					<c:if test="${customer.cardType == 3}">军官证</c:if>
					<c:if test="${customer.cardType == 4}">回乡证</c:if>
				</li>
				<li>证件号：${customer.customerIdenti }</li>
				<li>出生日期：<fmt:formatDate value="${customer.birthDay}" pattern="yyyy-MM-dd"/> </li>
				<li>用户手机号：${customer.phone }</li>
				<li>用户性别：${customer.sex ==1 ? '男' : '女' }</li>
				<li>常住地址：${customer.provinceName} ${customer.cityName}</li>
				<li>邮箱地址：${customer.email}</li>
			</ul>

			
			<div class="order-list-title">用户反馈信息</div>
			<ul>
				<li>${feedbackContent}</li>
			</ul>
			

		</div>
		
		<!-- 处理按钮 -->
		<form action="updateFeedbackState" method="post">
			<input type="hidden" name="id" value="${feedbackId}">
			<div id="clerk-footer">
				<input type="submit" class="btn btn-submit" value="处理" style="background: #64A6E0"/>
			</div>
		</form>
		
		

	</div>
	

</body>
</html>