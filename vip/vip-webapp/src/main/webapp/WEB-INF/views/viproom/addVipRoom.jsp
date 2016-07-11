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
		<form method="post" id="fm">
			<div class="clerk-container-item">
				<label>vip室名称：</label>
				<input class="easyui-validatebox" type="text" name="viproomName" value="" data-options="missingMessage:'必填项',required:true"/>
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
				<input type="text" name="address" class="easyui-validatebox" data-options="missingMessage:'必填项',required:true"/>
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
					<!-- 加载编辑器的容器 -->
					<script id="container" name="remark1" type="text/plain">
					<style>#table-box{ border: 1px solid #aaa; border-collapse:collapse; border-color:#7b91a8}
                       #table-box tr, #table-box td{ border: 1px solid #aaa; border-color:#7b91a8}</style>
                     <table data-sort="sortDisabled" id="table-box">
                     	<tbody>
        					<tr class="firstRow">
            <td colspan="1" rowspan="12" style="word-break: break-all;" valign="top" width="67">
                <span style="color: #7b91a8; display:block; width:15px; margin:0 auto;">基础服务</span><br/>
            </td>
            <td colspan="1" rowspan="1" style="word-break: break-all;" valign="top" width="163">
                <span style="color: #7b91a8;">营业时间</span>
            </td>
            <td colspan="1" rowspan="1" style="word-break: break-all; text-align:center" valign="top" width="293">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
        <tr>
            <td style="word-break: break-all;" valign="top" width="139">
                <span style="color: #7b91a8;">位置指引</span>
            </td>
            <td style="word-break: break-all;text-align:center" valign="top" width="304">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
        <tr>
            <td style="word-break: break-all;" valign="top" width="139">
                <span style="color: #7b91a8;">协助取票<br/></span>
            </td>
            <td style="word-break: break-all;text-align:center" valign="top" width="304">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
        <tr>
            <td style="word-break: break-all;" valign="top" width="139">
                <span style="color: #7b91a8;">行李协助</span>
            </td>
            <td style="word-break: break-all;text-align:center" valign="top" width="304">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
        <tr>
            <td style="word-break: break-all;" valign="top" width="139">
                <span style="color: #7b91a8;">手机充电</span>
            </td>
            <td style="word-break: break-all;text-align:center" valign="top" width="304">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
        <tr>
            <td style="word-break: break-all;" valign="top" width="139">
                <span style="color: #7b91a8;">免费小食品<br/></span>
            </td>
            <td style="word-break: break-all;text-align:center" valign="top" width="304">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
        <tr>
            <td style="word-break: break-all;" valign="top" width="139">
                <span style="color: #7b91a8;">免费饮品<br/></span>
            </td>
            <td style="word-break: break-all;text-align:center" valign="top" width="304">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
        <tr>
            <td style="word-break: break-all;" valign="top" width="139">
                <span style="color: #7b91a8;">wifi<br/></span>
            </td>
            <td style="word-break: break-all;text-align:center" valign="top" width="304">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
        <tr>
            <td style="word-break: break-all;" valign="top" width="139">
                <span style="color: #7b91a8;">报刊书籍<br/></span>
            </td>
            <td style="word-break: break-all;text-align:center" valign="top" width="304">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
        <tr>
            <td style="word-break: break-all;" valign="top" width="139">
                <span style="color: #7b91a8;">电视<br/></span>
            </td>
            <td style="word-break: break-all;text-align:center" valign="top" width="304">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
        <tr>
            <td colspan="1" rowspan="1" style="word-break: break-all;" valign="top" width="139">
                <span style="color: #7b91a8;">行李寄存</span>
            </td>
            <td colspan="1" rowspan="1" style="word-break: break-all;text-align:center" valign="top" width="304">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
        <tr>
            <td colspan="1" rowspan="1" style="word-break: break-all;" valign="top" width="139">
                <span style="color: #7b91a8;">登车提醒<br/></span>
            </td>
            <td colspan="1" rowspan="1" style="word-break: break-all;text-align:center" valign="top" width="304">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
        <tr>
            <td colspan="1" rowspan="5" style="word-break: break-all;" valign="top" width="67">
                <span style="color: #7b91a8;width:15px; display:block; margin:0 auto;">五星要客服务<br/></span>
            </td>
            <td colspan="1" rowspan="1" style="word-break: break-all;" valign="top" width="160">
                <span style="color: #7b91a8;">专人迎送<br/></span>
            </td>
            <td colspan="1" rowspan="1" style="word-break: break-all;text-align:center" valign="top" width="293">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
        <tr>
            <td colspan="1" rowspan="1" style="word-break: break-all;" valign="top" width="139">
                <span style="color: #7b91a8;">送站登车<br/></span>
            </td>
            <td colspan="1" rowspan="1" style="word-break: break-all;text-align:center" valign="top" width="325">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
        <tr>
            <td colspan="1" rowspan="1" style="word-break: break-all;" valign="top" width="139">
                <span style="color: #7b91a8;">站台接车</span>
            </td>
            <td colspan="1" rowspan="1" style="word-break: break-all;text-align:center" valign="top" width="325">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
        <tr>
            <td colspan="1" rowspan="1" style="word-break: break-all;" valign="top" width="139">
                <span style="color: #7b91a8;">专用检票口</span>
            </td>
            <td colspan="1" rowspan="1" style="word-break: break-all;text-align:center" valign="top" width="325">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
        <tr>
            <td colspan="1" rowspan="1" style="word-break: break-all;" valign="top" width="139">
                <span style="color: #7b91a8;">专用安检通道<br/></span>
            </td>
            <td colspan="1" rowspan="1" style="word-break: break-all;text-align:center" valign="top" width="325">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
        <tr>
            <td colspan="1" rowspan="1" style="word-break: break-all;text-align:center" valign="top" width="67">
                <span style="color: #7b91a8; text-align:center">其他</span>
            </td>
            <td colspan="2" rowspan="1" style="word-break: break-all;text-align:center" valign="top" width="476">
                <span style="color: #7b91a8;">●</span>
            </td>
        </tr>
   						</tbody>
					</table>

               		</script>
				</div>

				<div id="clerk-footer">
			<input  class="btn btn-save" type="button" onclick="addVipInfo()" value="保存"/>
			<input type="button" class="btn btn-cancel" onClick="javascript:history.go(-1);" value="取消"/>
		</div>
	</form>
	</div>
	</div>
</body>

<script>
	/*防重复提交 */
	var submitFlag = true;
	
	/* 实例化编辑器 */
	var editor = UE.getEditor('container', {
		//最大500个字符
		maximumWords : 500
	});

	$(function() {
		var provinceId = $("#provinceSele option:selected").val();
		var url = "getAirPortList?provinceId=" + provinceId
		$.get(url, function(_d) {
			var size = _d.length;
			console.log(_d)
			for (var index = 0; index < size; index++) {
				$("#airportId").append(
						"<option value='"+_d[index].airport_id+"'>"
								+ _d[index].airport_name + "</option>");
			}
		});

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
				resize : {
					width : 200,
					height : 200,
					quality : 90,
					crop : true
				     
				}, 
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

	})
	
	//根据省id显示场站列表
	function getAirPortList() {
		//清空下拉列表内容
		$("#airportId").empty();
		var provinceId = $("#provinceSele option:selected").val();
		var url = "getAirPortList?provinceId=" + provinceId
		$.get(url, function(_d) {
			var size = _d.length;
			console.log(_d)
			for (var index = 0; index < size; index++) {
				$("#airportId").append(
						"<option value='"+_d[index].airport_id+"'>"
								+ _d[index].airport_name + "</option>");
			}
		})
	}

	//添加vip室信息
	function addVipInfo() {
		var uploader = $('#uploader').pluploadQueue();
		if (uploader.files.length > 0) {
	            // When all files are uploaded ,submit form
	            uploader.bind('UploadComplete', function(up,file) {
	            	 if ((uploader.total.uploaded + uploader.total.failed) == uploader.files.length){
	            		if(submitFlag){
	            			submitFlag = false;
	            			$('#fm').form('submit', {
	    	          			url : "addVipRoomInfo",
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
	        	$.messager.alert("提示信息","请添加vip室图片！");

	   }

	
</script>
</html>