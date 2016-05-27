<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
				<li><label>日期：</label>${orderDetailInfo.orderTime}</li>
				<li><label>开卡场站：</label>${orderDetailInfo.airportName}</li>
				<li><label>业务员：</label>${orderDetailInfo.agentName}</li>
				<li><label>业务员电话：</label>${orderDetailInfo.agentPhone}</li>
			</ul>


			<div class="order-list-title">
				VIP卡编号：<span>${orderDetailInfo.vipCardNo}</span>
			</div>
			<ul>
				<li><label>有效期：</label>${orderDetailInfo.cardType}年</li>
				<li><label>vip卡金额：</label>${orderDetailInfo.initMoney}￥</li>
				<li><label>支付方式：
				      
					</label>
				
				
				</li>
				<li><label>支付时间：</label>${orderDetailInfo.payTime}</li>
				<li><label>卡片激活短信发送时间：</label>${orderDetailInfo.activatTime}</li>
			</ul>

			<div class="order-list-title">个人资料</div>
			<ul>
				<li>用户姓名：${orderDetailInfo.customerName}</li>
				<li>身份证：${orderDetailInfo.customerIdent }</li>
				<li>用户手机号：${orderDetailInfo.customerPhone }</li>
				<li>用户性别：${orderDetailInfo.sex ==1 ? '男' : '女' }</li>
				<li>常驻城市：${orderDetailInfo.address }</li>
				<li>邮箱地址：${orderDetailInfo.email}</li>
			</ul>
			
			<!-- 发票信息 -->
			<div class="order-list-title">发票信息（${orderDetailInfo.invoiceFlag ==0 ? '未开' : '已开' }）</div>
			<c:if test="${orderDetailInfo.invoiceFlag ==1}">
				<div>
					<ul>
						<li>邮寄地址：${invoice.address }</li>
						<li>发票类型：${invoice.invoiceType ==0? '个人':'公司' }</li>
						<li>发票抬头：${invoice.invoiceTitle}</li>
					</ul>
				</div>
			</c:if>
			
			

			<div class="order-list-title">
				退款信息 <span class="order-tips-red">（${orderDetailInfo.orderState ==4 ?'已退款':'未退款'}）</span>
			</div>
			<c:if test="${orderDetailInfo.orderState ==4}">
				<ul>
					<li>退款金额：${appCardBack.money}元</li>
					<li>退款方式：
						<c:choose>
							<c:when test="${appCardBack.backType ==0 }">
								微信转账
							</c:when>
							<c:when test="${appCardBack.backType ==1 }">
								支付宝转账
							</c:when>
							<c:otherwise>
								银行转账
							</c:otherwise>
						</c:choose>
					</li>
					<li>转账账号：${appCardBack.customerCard}</li>
				</ul>
			</c:if>
			

		</div>

	</div>
	

</body>
</html>