<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<script type="text/javascript">
	var submitNow = function($dialog, $grid, $pjq) {
		var url=sy.contextPath + '/busizz/save';
		var arids = '';
		$("#area input:checkbox").each(function(){
			if(this.checked){
				arids=arids+$(this).val()+",";
			}
		});
		$("#arids").val(arids);
		$.post(url, sy.serializeObject($('form')), function(result) {
			if (result.ok) {
				$pjq.messager.alert('提示', result.msgBody, 'info');
				$grid.datagrid('load');
				$dialog.dialog('destroy');
			} else {
				layer.alert(result.msgBody, {
					icon : 2,
					skin : 'layer-ext-moon' 
				});
			}
		}, 'json');
	};
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			submitNow($dialog, $grid, $pjq);
		}
	};
	$(function() {
		$.fn.checkCbx = function(isChecked) {
			return this.each(function() {
				//this.checked = !this.checked;
				$(this).prop("checked", isChecked);
			});
		};
		$(function() {
			$("#all").click(function() {
				 var isChecked = $(this).prop("checked");
				$("#area input:checkbox").checkCbx(isChecked);
			});
		});
	});
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
</head>
<body>
	<form method="post" class="form">
		<fieldset>
		<input name="id" type="hidden" value="${user.id }" readonly="readonly" />
		<input name="arids" type="hidden" id="arids">
		<input name="roleids" type="hidden" id="roleids">
			<legend>用户基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>职位</th>
					<td><input name="job" value="${user.job }" class="easyui-validatebox" data-options="missingMessage:'必填项',required:true" /></td>
					<th>姓名</th>
					<td><input name="name" value="${user.name }" class="easyui-validatebox" data-options="missingMessage:'必填项',required:true" /></td>
				</tr>
				<tr>
					<th>工号</th>
					<td><input name="code" value="${user.code }"   class="easyui-validatebox"  data-options="missingMessage:'必填项',required:true"  /></td>
					<th>性别</th>
					<td><select class="easyui-combobox" name="sex" data-options="panelHeight:'auto',editable:false" style="width: 155px;">
							<option value="1" <c:if test="${user.sex==1 }">selected="selected"</c:if> >男</option>
							<option value="0" <c:if test="${user.sex==0 }">selected="selected"</c:if> >女</option>
					</select></td>
				</tr>
				<tr>
					<th>手机号</th>
					<td><input name="phone" value="${user.phone }"  class="easyui-validatebox" data-options="validType:'phoneRex'" /></td>
					<th>状态</th>
					<td>
						<input type="radio" name="state" value="0" <c:if test="${user.state==0 }"> checked="checked"</c:if> >在职
					    <input type="radio" name="state" value="1" <c:if test="${user.state==1 }"> checked="checked"</c:if> >离职
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<fieldset>
						<legend>所属区域</legend>
						<label><input name="Fruit" type="checkbox" value="" id="all" />全国 </label> <br /><br /> 
						<div id="area" style="padding-left: 20px;margin-top: -20px;">
							<table border="0">
							<tr>
							<c:forEach var="data" items="${datas }" varStatus="i" >
								<c:if test="${(i.index)%4==0 }">
								</tr>
								</c:if>
								<td style="border: none;">
									<label><input  type="checkbox" value="${data.id }" <c:if test="${data.checked }">checked="checked" </c:if> />${data.name }</label>
								</td>
							</c:forEach>
							</tr>
							</table>
						</div>
						</fieldset>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>