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
				<li><label>订单创建日期：</label><fmt:formatDate value="${orderDetailInfo.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
				<li><label>开卡场站：</label>${orderDetailInfo.airportName}</li>
				<li><label>业务员：</label>${orderDetailInfo.agentName}</li>
				<li><label>业务员电话：</label>${orderDetailInfo.agentPhone}</li>
			</ul>


			<div class="order-list-title">
				VIP卡编号：<span>${cardInfo.cardNo}</span>
			</div>
			<ul>
				<li><label>有效期：</label>1年</li>
				<li><label>vip卡金额：</label><fmt:formatNumber value="${cardInfo.initMoney}" pattern="0.00"/> 元</li>
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
				      	<c:when test="${orderDetailInfo.payMethod == 4}">
				      		现金支付
				      	</c:when>
				      	<c:otherwise></c:otherwise>
				      </c:choose>
					</label>
				
				
				</li>
				<li><label>支付时间：</label><fmt:formatDate value="${orderDetailInfo.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
				<li><label>卡片激活时间：</label><fmt:formatDate value="${activityTime}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
			</ul>

			<div class="order-list-title">个人资料</div>
			<ul>
				<li>用户姓名：${orderDetailInfo.customerName}</li>
				<li>证件类型：
					<c:if test="${orderDetailInfo.indentType == 1}">身份证</c:if>
					<c:if test="${orderDetailInfo.indentType == 2}">护照</c:if>
					<c:if test="${orderDetailInfo.indentType == 3}">军官证</c:if>
					<c:if test="${orderDetailInfo.indentType == 4}">回乡证</c:if>
				</li>
				<li>证件号：${orderDetailInfo.customerIdent }</li>
				<li>出生日期：<fmt:formatDate value="${orderDetailInfo.birthDay}" pattern="yyyy-MM-dd"/></li>
				<li>用户手机号：${orderDetailInfo.customerPhone }</li>
				<li>用户性别：${orderDetailInfo.sex ==1 ? '男' : '女' }</li>
				<li>常住地址：${orderDetailInfo.customerProvinceName} ${orderDetailInfo.customerCityName}</li>
				<li>邮箱地址：${orderDetailInfo.email}</li>
			</ul>
			
			<!-- 发票信息 -->
			<div class="order-list-title">发票信息（
				<c:if test="${orderDetailInfo.invoiceFlag ==0}">
					不需要
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
					<!--普通发票  -->
					<c:if test="${invoice.invoiceKind ==0}">
						<li>发票种类：增值税普通发票</li>
						<li>邮寄地址：${invoice.province} ${invoice.city} ${invoice.country} ${invoice.address }</li>
						<li>发票类型：${invoice.invoiceType ==0? '个人':'公司' }</li>
						<li>发票抬头：${invoice.invoiceTitle}</li>
						<li>发票内容：${invoice.invoiceContent}</li>
						<li>邮　　编：${invoice.postcode }</li>
						<c:if test="${orderDetailInfo.invoiceFlag == 2}">
						<li>发票编号：${invoice.invoiceNo}</li>
						</c:if>
					</c:if>
					
					<!--专用发票 -->
					<c:if test="${invoice.invoiceKind ==1}">
						<li>发票种类：增值税专用发票</li>
						<li>邮寄地址：${invoice.province} ${invoice.city} ${invoice.country} ${invoice.address }</li>
						<li>发票内容：${invoice.invoiceContent}</li>
						<li>邮　　编：${invoice.postcode }</li>
						<li>公司名称：${invoice.companyName}</li>
						<li>公司税号：${invoice.companyTaxNo }</li>
						<li>公司地址：${invoice.companyAddress }</li>
						<li>公司电话：${invoice.companyPhone }</li>
						<li>营业执照：<img src="${invoice.businessLicenseUrl}" style="width: 350px;height: 300px"></li>
						<c:if test="${orderDetailInfo.invoiceFlag == 2}">
						<li>发票编号：${invoice.invoiceNo}</li>
						</c:if>
					</c:if>
				</ul>
			</c:if>

			<div class="order-list-title">退款信息</div>
			<ul>
				<c:if
					test="${orderDetailInfo.orderState==3 || orderDetailInfo.orderState==4}">

					<li>退款进度：${orderDetailInfo.orderState ==4 ?'已退款':'审核通过'}</li>
					<li>退款金额：<fmt:formatNumber value="${appCardBack.money}"
							pattern="0.00" /> 元
					</li>
					<li>退款方式： <c:choose>
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
								紧急退款
							</c:otherwise>
						</c:choose>
					</li>
					<li>退款申请时间：<fmt:formatDate value="${appCardBack.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
					<li>退款完成时间：<fmt:formatDate value="${appCardBack.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </li>
					<!--申请方式为现场，客服 -->
					<c:if test="${orderDetailInfo.applyType !=2}">
						<c:choose>
							<c:when test="${appCardBack.backType ==3}">
								<li>银行账号：${appCardBack.customerCard}</li>
								<li>开户行名称：${appCardBack.bankName}</li>
								<li>持卡人姓名：${appCardBack.customerName}</li>
							</c:when>
							<c:otherwise>
								<li>转账账号：${appCardBack.customerCard}</li>
							</c:otherwise>
						</c:choose>
					</c:if>

				</c:if>
			</ul>

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
				<div><img style="width:350px;height: 300px" src="${appCardBack.agreementUrl}"></div>
			</c:if>
			
		</div>

	</div>
	

</body>
</html>