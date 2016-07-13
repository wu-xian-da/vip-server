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
			<!-- 订单信息
			<c:if test="${!empty orderInfo}">
				<c:forEach items="${orderInfo }" var="order">
					<div class="order-list-title">
					订单编号：<span>${order.order_id}</span>
					</div>
					<ul>
						<li><label>开卡时间：</label>${order.order_time}</li>
						<li><label>开卡场站：</label>${order.airport_name}</li>
						<li><label>业务员：</label>${order.name}</li>
						<li><label>业务员电话：</label>${order.phone}</li>
					</ul>
				</c:forEach>
			</c:if>
			 -->
			
			<!--卡信息 
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
			 -->
			
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
				<li>出生日期：</li>
				<li>用户手机号：${customer.phone }</li>
				<li>用户性别：${customer.sex ==1 ? '男' : '女' }</li>
				<li>常住地址：${customer.provinceName} ${customer.cityName} ${customer.address }</li>
				<li>邮箱地址：${customer.email}</li>
			</ul>

			<!-- 发票信息
			<c:if test="${!empty postInfo }">
				<c:forEach items="${postInfo}" var="postInfo">

					<div class="order-list-title">
						发票信息（
						<c:if test="${postInfo.invoice_flag =='0'}">
							未开
						</c:if>
						<c:if test="${postInfo.invoice_flag =='1'}">
							发票未邮寄
						</c:if>
						<c:if test="${postInfo.invoice_flag =='2'}">
							发票已邮寄
						</c:if>
						）
					</div>
					 -->
					 <!--
					<c:if test="${postInfo.invoice_flag != '0'}">
						<ul>
							普通发票 
							<c:if test="${postInfo.invoice_kind =='0'}">
								<li>发票类型：普通发票</li>
								<li>邮寄地址：${postInfo.province} ${postInfo.city}
									${postInfo.country} ${postInfo.address }</li>
								<li>发票类型：${postInfo.invoice_type =='0'? '个人':'公司' }</li>
								<li>发票抬头：${postInfo.invoice_title}</li>
								<li>发票内容：${postInfo.invoice_content}</li>
								<li>邮 编：${postInfo.postcode }</li>
								<c:if test="${postInfo.invoice_flag == '2'}">
									<li>发票编号：${postInfo.invoice_no}</li>
								</c:if>
							</c:if>

							专用发票 
							<c:if test="${postInfo.invoice_kind =='1'}">
								<li>发票类型：专用发票</li>
								<li>邮寄地址：${postInfo.province} ${postInfo.city}
									${postInfo.country} ${postInfo.address }</li>
								<li>发票类型：${postInfo.invoice_type =='0'? '个人':'公司' }</li>
								<li>发票抬头：${postInfo.invoice_title}</li>
								<li>发票内容：${postInfo.invoice_content}</li>
								<li>邮 编：${postInfo.postcode }</li>
								<li>公司名称：${postInfo.company_name}</li>
								<li>公司税号：${postInfo.company_tax_no }</li>
								<li>公司地址：${postInfo.company_address }</li>
								<li>公司电话：${invpostInfooice.company_phone }</li>
								<li>营业执照：<img src="${postInfo.business_license_url}"
									style="width: 400px"></li>
								<c:if test="${postInfo.invoice_flag == '2'}">
									<li>发票编号：${postInfo.invoice_no}</li>
								</c:if>
							</c:if>
						</ul>
					</c:if>

				</c:forEach>
			</c:if>
			  -->

			<!-- 退款金额 
			<c:if test="${!empty backMoneyInfo }">
				<div class="order-list-title">
					退款信息 <span class="order-tips-red"></span>
				</div>
				<c:forEach items="${backMoneyInfo }" var="backMoneyInfo">
					<ul>
						<li>退款进度：${backMoneyInfo.order_state == 3?'审核通过':'已退款'}</li>
						<li>订单编号：${backMoneyInfo.order_id}</li>
						<li>退款金额：${backMoneyInfo.money} 元</li>
						<li>退款方式： <c:choose>
								<c:when test="${backMoneyInfo.back_type ==1}">
								微信转账
							</c:when>
								<c:when test="${backMoneyInfo.back_type ==2 }">
								支付宝转账
							</c:when>
								<c:when test="${backMoneyInfo.back_type ==3 }">
								银行卡转账
							</c:when>
								<c:otherwise>
								现金转账
							</c:otherwise>
							</c:choose>
						</li>
					</ul>
				</c:forEach>
			</c:if>
			-->
			<!-- vip卡使用记录 
			<c:if test="${!empty vipCardRescordInfo }">
				<div class="order-list-title">VIP卡使用记录</div>
				<ul>
					<c:forEach items="${vipCardRescordInfo }" var="vip">
						<li>${vip.viproom_name }&nbsp;&nbsp;${vip.consume_money }&nbsp;&nbsp;${vip.consume_time }</li>
					</c:forEach>
				</ul>
				
			</c:if>
			-->
			
			<!-- 反馈信息 -->
			<div class="order-list-title">
				反馈信息
			</div>
			<ul>
				<li><label>反馈信息：</label>${appUserFeedbackInfo.feedbackContent}</li>
				<li><label>反馈状态：</label>${appUserFeedbackInfo.feedbackState == 0 ? '未处理 ' : '已处理' }</li>
			</ul>
			

		</div>

	</div>
	

</body>
</html>