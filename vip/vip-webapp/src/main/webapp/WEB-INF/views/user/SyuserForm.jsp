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
	var submitNow = function($dialog, $grid, $pjq) {
	   if (checksubmit()) { 
		   console.info("......");
			var url= sy.contextPath + '/user/save';
			var arids = '';
			$("#area input:checkbox").each(function(){
				if(this.checked){
					arids=arids+$(this).val()+",";
				}
			});
			var nodes = $('#tree').tree('getChecked',
					[ 'checked', 'indeterminate' ]);
			$("#arids").val(arids);
			var ids = [];
			for (var i = 0; i < nodes.length; i++) {
				ids.push(nodes[i].id);
			}
			$("#roleids").val(ids.join(','));
			$.post(url, sy.serializeObject($('form')), function(result) {
				if (result.ok) {
					$pjq.messager.alert('提示', result.msgBody, 'info');
					$grid.datagrid('load');
					$dialog.dialog('destroy');
				} else {
					checksubmitflg = false; 
					layer.alert(result.msgBody, {
						icon : 2,
						skin : 'layer-ext-moon' 
					});
				}
			}, 'json');
	    } 
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
		$("#all").click(function() {
			 var isChecked = $(this).prop("checked");
			$("#area input:checkbox").checkCbx(isChecked);
		});
	});
	$(function(){
		$('#cc').combobox({
			onSelect: function(record){
				console.info(record);
				$("#job").val(record.text);
			}
		});
	});
	//自定义验证
	  $.extend($.fn.validatebox.defaults.rules, {
	  loginNameRex: {
	    validator: function(value){
		    var rex=/^[a-z0-9A-Z_.]+$/;
		    if(rex.test(value))
		    {
		      return true;
		    }else
		    {
		     //alert('false '+value);
		       return false;
		    }
	    },
	    message: '字母数字的组合'
	  },
	  codeRex: {
		    validator: function(value){
		    var rex2=/^\d{3,}$/ ;
		    if(rex2.test(value))
		    {
		      // alert('t'+value);
		      return true;
		    }else
		    {
		     //alert('false '+value);
		       return false;
		    }
		      
		    },
		    message: '工号最小长度为3且只能是数字'
		  }
	});
</script>
</head>
<body>
	<form method="post" class="form">
	<input type="hidden" name="job" value="${user.job }" id="job">
		<fieldset>
		<input name="id" type="hidden" value="${user.id }" readonly="readonly" />
		<input name="arids" type="hidden" id="arids">
		<input name="roleids" type="hidden" id="roleids">
			<legend>用户基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>登录名称</th>
					<td><input name="loginName" value="${user.loginName }" class="easyui-validatebox" data-options="missingMessage:'必填项',required:true,validType:'loginNameRex'" /></td>
					<th>姓名</th>
					<td><input name="name" value="${user.name }" class="easyui-validatebox" data-options="missingMessage:'必填项',required:true" /></td>
				</tr>
				<tr>
					<th>工号</th>
					<td><input name="code" value="${user.code }" <c:if test="${!empty user.code }">readonly="readonly" </c:if>   class="easyui-validatebox"  data-options="missingMessage:'必填项',required:true,validType:'codeRex'"  /></td>
					<th>用户角色</th>
					<td>
						<select id="cc" class="easyui-combobox" name="roleids" style="width:200px;">
							<c:forEach items="${roleSeclect }" var="role">
								<option value="${role.id }" <c:if test="${!empty selected and role.id==selected.id }">selected="selected"</c:if> >${role.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
						<th>性别</th>
					<td><select class="easyui-combobox" name="sex" data-options="panelHeight:'auto',editable:false" style="width: 155px;">
							<option value="1" <c:if test="${user.sex==1 }">selected="selected"</c:if> >男</option>
							<option value="0" <c:if test="${user.sex==0 }">selected="selected"</c:if> >女</option>
					</select></td>
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
								<c:if test="${(i.index)%3==0 }">
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