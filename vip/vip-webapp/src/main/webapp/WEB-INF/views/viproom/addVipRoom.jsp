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
				<input type="text" name="address"/>
			</div>
			
			<div class="clerk-container-item">
				<label>vip室图片：</label>
				<input type="file" name="file"/> 
			</div>
				
			<div class="clerk-container-item">
				<label style="margin-bottom: 15px;">vip室信息编辑：</label>
				<!-- <textarea name="remark1"></textarea> -->
				<!-- 加载编辑器的容器 -->
    			<script id="container" name="remark1" type="text/plain">
        			<table data-sort="sortDisabled">
    					<body>
        					<tr class="firstRow">
            					<td valign="top" colspan="1" rowspan="12" style="word-break: break-all;" width="123">
               						<span style="color: rgb(216, 216, 216);">基础服务</span><br/>
            					</td>
            					<td valign="top" colspan="1" rowspan="1" style="word-break: break-all;" width="136">
               	 					<span style="color: rgb(216, 216, 216);">营业时间</span>
            					</td>
            					<td valign="top" colspan="1" rowspan="1" style="word-break: break-all;" width="218">
                					<span style="font-family: 宋体; font-size: 12px; line-height: 18px; text-align: center; color: rgb(216, 216, 216); background-color: rgb(240, 243, 248);">●</span>
            					</td>
        					</tr>
       						<tr>
            					<td width="133" valign="top" style="word-break: break-all;">
                					<span style="color: rgb(216, 216, 216);">位置指引</span>
            					</td>
            					<td width="226" valign="top" style="word-break: break-all;">
                					<span style="font-family: 宋体; font-size: 12px; line-height: 18px; text-align: center; color: rgb(216, 216, 216); background-color: rgb(240, 243, 248);">●</span>
            					</td>
        					</tr>
       	 					<tr>
            					<td width="133" valign="top" style="word-break: break-all;">
                					<span style="color: rgb(216, 216, 216);">协助取票<br/></span>
            					</td>
            					<td width="226" valign="top" style="word-break: break-all;">
                					<span style="font-family: 宋体; font-size: 12px; line-height: 18px; text-align: center; color: rgb(216, 216, 216); background-color: rgb(240, 243, 248);">●</span>
            					</td>
        					</tr>
        					<tr>
            					<td width="133" valign="top" style="word-break: break-all;">
                					<span style="color: rgb(216, 216, 216);">行李协助</span>
            					</td>
            					<td width="226" valign="top" style="word-break: break-all;">
                					<span style="font-family: 宋体; font-size: 12px; line-height: 18px; text-align: center; color: rgb(216, 216, 216); background-color: rgb(240, 243, 248);">●</span>
            					</td>
        					</tr>
        					<tr>
            					<td width="133" valign="top" style="word-break: break-all;">
                					<span style="color: rgb(216, 216, 216);">手机充电<br/></span>
           		 				</td>
            					<td width="226" valign="top" style="word-break: break-all;">
                					<span style="font-family: 宋体; font-size: 12px; line-height: 18px; text-align: center; color: rgb(216, 216, 216); background-color: rgb(240, 243, 248);">●</span>
            					</td>
        					</tr>
        					<tr>
            					<td width="133" valign="top" style="word-break: break-all;">
                					<span style="color: rgb(216, 216, 216);">免费小食品<br/></span>
            					</td>
            					<td width="226" valign="top" style="word-break: break-all;">
                					<span style="font-family: 宋体; font-size: 12px; line-height: 18px; text-align: center; color: rgb(216, 216, 216); background-color: rgb(240, 243, 248);">●</span>
            					</td>
        					</tr>
        					<tr>
           						<td width="133" valign="top" style="word-break: break-all;">
                					<span style="color: rgb(216, 216, 216);">免费饮品<br/></span>
            					</td>
            					<td width="226" valign="top" style="word-break: break-all;">
                					<span style="font-family: 宋体; font-size: 12px; line-height: 18px; text-align: center; color: rgb(216, 216, 216); background-color: rgb(240, 243, 248);">●</span>
            					</td>
        					</tr>
        					<tr>
           						<td width="133" valign="top" style="word-break: break-all;">
                					<span style="color: rgb(216, 216, 216);">wifi<br/></span>
            					</td>
            					<td width="226" valign="top" style="word-break: break-all;">
                					<span style="font-family: 宋体; font-size: 12px; line-height: 18px; text-align: center; color: rgb(216, 216, 216); background-color: rgb(240, 243, 248);">●</span>
            					</td>
        					</tr>
        					<tr>
            					<td width="133" valign="top" style="word-break: break-all;">
                					<span style="color: rgb(216, 216, 216);">报刊书籍<br/></span>
            					</td>
            					<td width="226" valign="top" style="word-break: break-all;">
                					<span style="font-family: 宋体; font-size: 12px; line-height: 18px; text-align: center; color: rgb(216, 216, 216); background-color: rgb(240, 243, 248);">●</span>
            					</td>
        					</tr>
        <tr>
            <td width="133" valign="top" style="word-break: break-all;">
                <span style="color: rgb(216, 216, 216);">电视<br/></span>
            </td>
            <td width="226" valign="top" style="word-break: break-all;">
                <span style="font-family: 宋体; font-size: 12px; line-height: 18px; text-align: center; color: rgb(216, 216, 216); background-color: rgb(240, 243, 248);">●</span>
            </td>
        </tr>
        <tr>
            <td valign="top" colspan="1" rowspan="1" style="word-break: break-all;" width="133">
                <span style="color: rgb(216, 216, 216);">行李寄存</span>
            </td>
            <td valign="top" colspan="1" rowspan="1" style="word-break: break-all;" width="226">
                <span style="font-family: 宋体; font-size: 12px; line-height: 18px; text-align: center; color: rgb(216, 216, 216); background-color: rgb(240, 243, 248);">●</span>
            </td>
        </tr>
        <tr>
            <td valign="top" colspan="1" rowspan="1" style="word-break: break-all;" width="133">
                <span style="color: rgb(216, 216, 216);">登车提醒<br/></span>
            </td>
            <td valign="top" colspan="1" rowspan="1" style="word-break: break-all;" width="226">
                <span style="font-family: 宋体; font-size: 12px; line-height: 18px; text-align: center; color: rgb(216, 216, 216); background-color: rgb(240, 243, 248);">●</span>
            </td>
        </tr>
        <tr>
            <td valign="top" colspan="1" rowspan="5" width="133" style="word-break: break-all;">
                <span style="color: rgb(216, 216, 216);">五星要客服务<br/></span>
            </td>
            <td valign="top" colspan="1" rowspan="1" style="word-break: break-all;" width="136">
                <span style="color: rgb(216, 216, 216);">专人迎送<br/></span>
            </td>
            <td valign="top" colspan="1" rowspan="1" width="218" style="word-break: break-all;">
                <span style="font-family: 宋体; font-size: 12px; line-height: 18px; text-align: center; color: rgb(216, 216, 216); background-color: rgb(240, 243, 248);">●</span>
            </td>
        </tr>
        <tr>
            <td valign="top" colspan="1" rowspan="1" width="133" style="word-break: break-all;">
                <span style="color: rgb(216, 216, 216);">送站登车<br/></span>
            </td>
            <td valign="top" colspan="1" rowspan="1" width="226" style="word-break: break-all;">
                <span style="font-family: 宋体; font-size: 12px; line-height: 18px; text-align: center; color: rgb(216, 216, 216); background-color: rgb(240, 243, 248);">●</span>
            </td>
        </tr>
        <tr>
            <td valign="top" colspan="1" rowspan="1" width="133" style="word-break: break-all;">
                <span style="color: rgb(216, 216, 216);">站台接车</span>
            </td>
            <td valign="top" colspan="1" rowspan="1" width="226" style="word-break: break-all;">
                <span style="font-family: 宋体; font-size: 12px; line-height: 18px; text-align: center; color: rgb(216, 216, 216); background-color: rgb(240, 243, 248);">●</span>
            </td>
        </tr>
        <tr>
            <td valign="top" colspan="1" rowspan="1" width="133" style="word-break: break-all;">
                <span style="color: rgb(216, 216, 216);">专用检票口</span>
            </td>
            <td valign="top" colspan="1" rowspan="1" width="226" style="word-break: break-all;">
                <span style="font-family: 宋体; font-size: 12px; line-height: 18px; text-align: center; color: rgb(216, 216, 216); background-color: rgb(240, 243, 248);">●</span>
            </td>
        </tr>
        <tr>
            <td valign="top" colspan="1" rowspan="1" width="133" style="word-break: break-all;">
                <span style="color: rgb(216, 216, 216);">专用安检通道<br/></span>
            </td>
            <td valign="top" colspan="1" rowspan="1" width="226" style="word-break: break-all;">
                <span style="font-family: 宋体; font-size: 12px; line-height: 18px; text-align: center; color: rgb(216, 216, 216); background-color: rgb(240, 243, 248);">●</span>
            </td>
        </tr>
        <tr>
            <td valign="top" colspan="1" rowspan="1" style="word-break: break-all;" width="133">
                <span style="color: rgb(216, 216, 216);">其他</span>
            </td>
            <td valign="top" colspan="1" rowspan="1" style="word-break: break-all;" width="136">
                <span style="color: rgb(216, 216, 216);">其他</span>
            </td>
            <td valign="top" colspan="1" rowspan="1" style="word-break: break-all;" width="218"></td>
        </tr>
    </body>
</table>
<p>
    <br/>
</p>

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