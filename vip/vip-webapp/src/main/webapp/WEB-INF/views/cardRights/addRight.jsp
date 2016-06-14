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
		<form action="addRight" method="post">
			<div class="clerk-container-item">
				<label>素材标题：</label>
				<input type="text" name="title" value=""/>
			</div>
			
			
			<div class="clerk-container-item">
				<label>素材类型：</label>
				<select name="type">
					<option value="1">vip卡权益</option>
					<option value="2">常见问题</option>
					<option value="3">关于</option>
				</select>
			</div>
			
			<div class="clerk-container-item">
				<script id="container" name="content" type="text/plain">
        		
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
	
	
</script>
</html>