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
<script src="${pageContext.request.contextPath}/public/js/locale/easyui-lang-zh_CN.js"></script>

<!-- 配置文件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/uedit/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/uedit/ueditor.all.js"></script>
		
</head>
<body>
	<div id="clerk-wrap">
		<div id="clerk-container" class="clerk-container-viproom">
		<form action="addVipRoomInfo" method="post"  enctype="multipart/form-data">
			<div class="clerk-container-item">
				<label>vip室名称：</label>
				<input class="easyui-validatebox" type="text" name="viproomName" value="" data-options="required:true"/>
			</div>
			
			<div class="clerk-container-item">
				<label>vip室单价：</label>
				<input type="text" name="singleconsumeMoney" value="200" readonly="readonly">
			</div>
			
			<div class="clerk-container-item">
				<label>场站所属省会：</label>
				<select name="province" id="provinceSele" onchange="getAirPortList()">
					<c:forEach items="${provinceList}" var="province">
						<option value="${province.cid}">${province.name}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="clerk-container-item">
				<label>场站名称：</label> 
				<select name="airportId" id="airportId">
					
				</select>
			</div>
			
			<div class="clerk-container-item">
				<label>场站位置：</label>
				<input type="text" name="address"/>
			</div>
			
			<div class="clerk-container-item">
				<label>vip室图片：</label>
				<input type="file" name="file"/> 
			</div>
				
			<div class="clerk-container-item">
				<label>vip室信息编辑：</label>
				<!-- <textarea name="remark1"></textarea> -->
				<!-- 加载编辑器的容器 -->
    			<script id="container" name="remark1" type="text/plain">
        		
    			</script>
			</div>

		<div id="clerk-footer">
			<input  class="btn btn-save" type="submit" value="保存"/>
			<button class="btn btn-cancel" onClick="javascript:history.go(-1);">取消</button>
		</div>
	</form>
	</div>
	</div>
</body>

<script>
	<!-- 实例化编辑器 -->
	var editor = UE.getEditor('container',{
		//最大500个字符
		maximumWords:500
	});
	
	$(function(){
		var provinceId = $("#provinceSele option:selected").val();
		var url = "getAirPortList?provinceId="+provinceId
		$.get(url,function(_d){
			var size = _d.length;
			console.log(_d)
			for(var index =0 ;index < size;index ++){
				$("#airportId").append("<option value='"+_d[index].airport_id+"'>"+_d[index].airport_name+"</option>");
			}
		})
	})
	//根据省id显示场站列表
	function getAirPortList(){
		//清空下拉列表内容
		$("#airportId").empty();
		var provinceId = $("#provinceSele option:selected").val();
		var url = "getAirPortList?provinceId="+provinceId
		$.get(url,function(_d){
			var size = _d.length;
			console.log(_d)
			for(var index =0 ;index < size;index ++){
				$("#airportId").append("<option value='"+_d[index].airport_id+"'>"+_d[index].airport_name+"</option>");
			}
		})
	}
	
</script>
</html>