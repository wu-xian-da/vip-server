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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plupload/jquery.plupload.queue/css/jquery.plupload.queue.css">

<script src="${pageContext.request.contextPath}/public/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/js/jquery.easyui.min.js"></script>
<script src="${pageContext.request.contextPath}/public/js/vue.js"></script>
<script src="${pageContext.request.contextPath}/public/js/locale/easyui-lang-zh_CN.js"></script>

<!--百度富文本 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/uedit/ueditor.config.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/uedit/ueditor.all.js"></script>

<!--plupload上传组件  -->
<script type="text/javascript" src="${pageContext.request.contextPath}/plupload/plupload.full.min.js"></script>  
<script type="text/javascript" src="${pageContext.request.contextPath}/plupload/jquery.plupload.queue/jquery.plupload.queue.js"></script>
<!-- 国际化中文支持 -->  
<script type="text/javascript" src="${pageContext.request.contextPath}/plupload/i18n/zh_CN.js"></script>
		
</head>
<body>
	<div id="clerk-wrap">
		<div id="clerk-container" class="clerk-container-viproom">
		<form action="" method="post" id='editForm'>
			<div class="clerk-container-item">
				<label>vip室名称：</label>
				<input type="hidden" name="viproomId" value="${viproom.viproomId}">
				<input class="easyui-validatebox" type="text" name="viproomName" value="${viproom.viproomName }" data-options="missingMessage:'必填项',required:true"/>
			</div>
			
			<div class="clerk-container-item">
				<label>vip室单价：</label>
				<input type="text" name="singleconsumeMoney" value="200" readonly="readonly">
			</div>
			
			<div class="clerk-container-item">
				<label>场站所属省会：</label>
				<select name="province" id="provinceSele" onchange="getAirPortList()">
					<c:forEach items="${provinceList}" var="province">
						<c:if test="${province.cid ==viproom.province}">
							<option value="${province.cid}" selected="selected">${province.name}</option>
						</c:if>
						<c:if test="${province.cid !=viproom.province}">
							<option value="${province.cid}">${province.name}</option>
						</c:if>
						
					</c:forEach>
				</select>
			</div>
			
			<div class="clerk-container-item">
				<label>场站名称：</label> 
				<select name="airportId" id="airportSele">
					<c:forEach items="${airPortlist}" var="airPort">
						<c:if test="${viproom.airportId == airPort.airport_id}">
							<option value="${airPort.airport_id }" selected="selected">${airPort.airport_name }</option>
						</c:if>
						<c:if test="${viproom.airportId != airPort.airport_id}">
							<option value="${airPort.airport_id }">${airPort.airport_name }</option>
						</c:if>
					</c:forEach>
				</select>
			</div>
			
			<div class="clerk-container-item">
				<label>场站位置：</label>
				<input type="text" name="address" value="${viproom.address}"/>
			</div>
			<div class="clerk-container-item">
				<label>vip室图片：</label>
				<c:forEach items="${pictureList}" var="appPicture">
					<img width="100px" src="${appPicture.pictureUrl}" id="img${appPicture.pictureId}"/>
					<input type="button" id="but${appPicture.pictureId}" value="X" style="background: red;border-radius: 15px;border: medium none" onclick="delPhoto(${appPicture.pictureId})">
				</c:forEach>
				
			</div>
			
			<!-- 当文件上传成功后，将文件新的url保存 -->
			<div id="newFileNameDiv">
			</div>
			
			<!-- 用户初始化plupload上传组件 -->
        	<div class="clerk-container-item"> 
        		<label style="margin-bottom: 15px;">vip室图片添加：</label> 
        		<div  id="uploader">
           			<p>您的浏览器未安装 Flash, Silverlight, Gears, BrowserPlus 或者支持 HTML5 .</p>  
           		</div>
        	</div> 
			
			
			<div class="clerk-container-item">
				<label style="margin-bottom: 15px;">vip室信息编辑：</label>
				<script id="container" name="remark1" type="text/plain">
        			${viproom.remark1}
    			</script>
			</div>

		<div id="clerk-footer">
			<input  class="btn btn-save" type="button" value="保存" onclick="editVipRoomInfoFun()"/>
			<input type="button" value="取消" class="btn btn-cancel" onClick="history.go(-1)"/>
		</div>
	</form>
	</div>
	</div>
</body>


<script type="text/javascript">
	/* 防重复提交 */
	var submitFlag = true;
	
	/* 实例化编辑器 */
	var editor = UE.getEditor('container');
	
	/* 根据省id显示场站列表 */
	function getAirPortList() {
		//清空下拉列表内容
		$("#airportSele").empty();
		var provinceId = $("#provinceSele option:selected").val();
		var url = "getAirPortList?provinceId=" + provinceId
		$.get(url, function(_d) {
			var size = _d.length;
			console.log(_d)
			for (var index = 0; index < size; index++) {
				$("#airportSele").append("<option value='"+_d[index].airport_id+"'>"+ _d[index].airport_name + "</option>");
			}
		})
	}
	
	/*删除vip室图片  */
	function delPhoto(pictureId){
		var url = "delVipPhotoByPhotoId?pictureId="+pictureId;
		$.get(url,function(_d){
			if(_d.status == 1){//删除成功
				//将该位置的图片移除
				$("#img"+pictureId).remove();
				$("#but"+pictureId).remove();
			}
		});
	}
	
	/* 提交表单 */
	function editVipRoomInfoFun() {
		var uploader = $('#uploader').pluploadQueue();
		// When all files are uploaded ,submit form
		if(uploader.total.uploaded == uploader.files.length){
			if(submitFlag){
				submitFlag = false;
				$('#editForm').form('submit', {
	      			url : "editVipRoomInfo",
	      			onSubmit : function() {
	      				return $(this).form('validate');
	      			},
	      			success : function(_d) {
	      				console.log("_d:" + _d);
	      				if (_d.result == 0) {
	      					$.messager.show({
	      						title : 'Error',
	      						msg : "数据格式出错"
	      					});
	      				} else {
	      					history.go(-1);
	      				}
	      			}
	      		});
			}
			
		}
		else{
	        if (uploader.files.length > 0) {
	            // When all files are uploaded ,submit form
	            uploader.bind('UploadComplete', function(up,file) {
	                if ((uploader.total.uploaded + uploader.total.failed) == uploader.files.length){
	                	if(submitFlag){
	                		submitFlag = false;
	                		$('#fm').form('submit', {
	    	          			url : "editVipRoomInfo",
	    	          			onSubmit : function() {
	    	          				return $(this).form('validate');
	    	          			},
	    	          			success : function(_d) {
	    	          				console.log("_d:" + _d);
	    	          				if (_d.result == 0) {
	    	          					$.messager.show({
	    	          						title : 'Error',
	    	          						msg : "数据格式出错"
	    	          					});
	    	          				} else {
	    	          					history.go(-1);
	    	          				}
	    	          			}
	    	          		});
	                	}
	                }
	              });
	            uploader.start();
	        } else
	            alert('请至少选择一个上次的图片');

	   }
	}
	
	$(function(){
		/* 初始化plupload上次组件 */
		function plupload() {
			$("#uploader").pluploadQueue({
				runtimes : 'flash,html5,gears,browserplus,silverlight,html4',
				url : "../dumifileupload",
				//unique_names: true,  
				chunk_size : '1mb',
				//rename : true,  
				dragdrop : true,
				filters : {
					// Maximum file size               
					max_file_size : '10mb',
					// Specify what files to browse for  
					mime_types : [ {
						title : "Image files",
						extensions : "jpg,gif,png"
					} ]
				},
				// Resize images on clientside if we can           
				/* resize : {
					width : 200,
					height : 200,
					quality : 90,
					crop : true
				     
				},  */
				//Flash settings           
				flash_swf_url : '../plupload/Moxie.swf',
				//Silverlight settings           
				silverlight_xap_url : '../plupload/Moxie.xap',
			});

		}
		plupload();
		
		/*plupload上传完成时触发的事件 */
		var uploader = $('#uploader').pluploadQueue();
		uploader.bind('FileUploaded', function(up, file, res) {
			var data = JSON.parse(res.response);
			console.log("res="+res)
			$('#newFileNameDiv').append(
					'<input name="newFileUrl" type="hidden" value="'+data.newFileName+'"/>');
			});
	});
</script>
</html>