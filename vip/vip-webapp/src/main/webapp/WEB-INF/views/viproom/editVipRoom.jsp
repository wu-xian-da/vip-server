<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title></title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/theme/default/easyui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/theme/icon.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/site.css">

<script src="${pageContext.request.contextPath}/public/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/js/jquery.easyui.min.js"></script>
<script src="${pageContext.request.contextPath}/public/js/vue.js"></script>
		
</head>
<body>
	<div id="clerk-wrap">
		<div id="clerk-container" class="clerk-container-viproom">
		<form action="editVipRoomInfo" method="post">
			<div class="clerk-container-item">
				<label>vip室名称：</label>
				<input type="hidden" name="viproomId" value="${viproom.viproomId}">
				<input type="text" name="viproomName" value="${viproom.viproomName }"/>
			</div>
			
			<div class="clerk-container-item">
				<label>vip室单价：</label>
				<input type="text" name="singleconsumeMoney" value="200" readonly="readonly">
			</div>
			
			<div class="clerk-container-item">
				<label>场站所属省会：</label>
				<select name="provinceId" id="provinceSele" onchange="getAirPortList()">
					<c:forEach items="${provinceList}" var="province">
						<option value="${province.cid}">${province.name}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="clerk-container-item">
				<label>场站名称：</label> 
				<select name="airportId" id="">
					<c:forEach items="${airportList}" var="airport">
						<option value="${airport.id}">${airport.name}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="clerk-container-item">
				<label>场站位置：</label>
				<input type="text" name="address" value="${viproom.address }"/>
			</div>
			
			<div class="clerk-container-item">
				<label>vip室图片：</label>
				<input type="file" name="file" /> 
			</div>
			
			<div class="clerk-container-item">
				<label>vip室信息编辑：</label>
				<textarea name="remark1">${viproom.remark1}</textarea>
			</div>

		<div id="clerk-footer">
			<input  class="btn btn-save" type="submit" value="保存"/>
			<button class="btn btn-cancel" onClick="javascript:history.go(-1);">取消</button>
		</div>
	</form>
	</div>
	</div>
</body>

</html>