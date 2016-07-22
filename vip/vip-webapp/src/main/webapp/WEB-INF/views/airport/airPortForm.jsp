<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<script type="text/javascript">
var checksubmitflg = false; 
var checksubmit = function(){
	if(checksubmitflg==true){
		return false;
	}
	checksubmitflg=true;
	return true;
};
	var submitForm = function($dialog, $grid, $pjq) {
		if(checksubmit()){
			if ($('form').form('validate')) {
				
				var url;
				if ($(':input[name="id"]').val()!='') {
					url = sy.contextPath + '/airport/update';
				} else {
					url = sy.contextPath + '/airport/save';
				}
				$.post(url, sy.serializeObject($('form')), function(result) {
					if (result.ok) {
						$grid.datagrid('load');
						$dialog.dialog('destroy');
					} else {
						checksubmitflg = false; 
						$pjq.messager.alert('提示', result.msgBody, 'error');
					}
				}, 'json');
			}
		}
	};
	//自定义验证
	  $.extend($.fn.validatebox.defaults.rules, {
	  phoneRex: {
	    validator: function(value){
	    var rex=/^1[3-8]+\d{9}$/;
	    //var rex=/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	    //区号：前面一个0，后面跟2-3位数字 ： 0\d{2,3}
	    //电话号码：7-8位数字： \d{7,8
	    //分机号：一般都是3位数字： \d{3,}
	     //这样连接起来就是验证电话的正则表达式了：/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/		 
	    var rex2=/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	    if(rex.test(value)||rex2.test(value))
	    {
	      // alert('t'+value);
	      return true;
	    }else
	    {
	     //alert('false '+value);
	       return false;
	    }
	      
	    },
	    message: '请输入正确电话或手机格式'
	  }
	});
</script>
	<link rel="stylesheet" href="${ctx }/style/vip.css">
</head>
<style>
<!--
.combo-text validatebox-text{
	text-align: left;
}
-->
</style>
<body>
	<form method="post" class="form" >
		<fieldset>
			<legend>场站基本信息</legend>
			<input name="id" type="hidden" value="${ariPort.airport_id }" readonly="readonly" />
			<table class="table" style="width: 100%;">
				<tr style="padding-top: 5px;">
					<th style="width: 20%;">场站名称 </th>
					<td><input name="name"  value="${ariPort.airport_name }" class="easyui-validatebox"  data-options="missingMessage:'必填项',required:true" style="text-align: left;"/></td>
						<th>场站负责人</th>
					<td><input style="text-align: left;" name="headerName" class="easyui-validatebox" data-options="missingMessage:'必填项',required:true" value="${ariPort.header_name }" /></td>
				</tr>
				<tr>
					<th>负责人联系方式</th>
					<td><input style="text-align: left;" name="headerPhone" class="easyui-validatebox" data-options="missingMessage:'必填项',required:true,validType:'phoneRex'" value="${ariPort.header_phone }" /></td>
					<th>场站状态</th>
					<c:choose>
						<c:when test="${!empty ariPort and ariPort.state==0}">
							<td><input type="radio" value="0" name="state"  checked>运营<input type="radio" value="1" name="state" >冻结</td>
						</c:when>
						<c:when test="${!empty ariPort and ariPort.state==1}">
							<td><input type="radio" value="0" name="state"  >运营<input type="radio" value="1" name="state" checked>冻结</td>
						</c:when>
						<c:otherwise>
							<td><input type="radio" value="0" name="state" checked >运营<input type="radio" value="1" name="state"  >冻结</td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr >
					<th>所属省份</th>
					<td>
						<select id="cc" class="easyui-combobox" name="province" style="width:153px;text-align: left;">
							<c:forEach items="${citys }" var="city">
								<option style="text-align: left;"  value="${city.cid }" <c:if test="${!empty ariPort and ariPort.cid==city.cid }">selected="selected"</c:if> >${city.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	<script type="text/javascript">
		function returnFun(obj){
			if(obj==''||null==obj){
				return '0';
			}
			return obj;
		}
	</script>
</body>
</html>