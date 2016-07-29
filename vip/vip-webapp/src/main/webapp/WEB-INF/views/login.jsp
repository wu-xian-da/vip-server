<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
if (window != top) 
	top.location.href = location.href; 
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登入</title>
	<link rel="stylesheet" href="${ctx }/jslib/jquery-easyui-1.3.4/themes/default/easyui.css">
	<link rel="stylesheet" href="${ctx }/jslib/jquery-easyui-1.3.4/themes/icon.css">
	<link rel="stylesheet" href="${ctx }/style/vip.css">
	<script src="${ctx }/jslib/jquery-1.9.1.js"></script>
	<script src="${ctx }/jslib/jquery-easyui-1.3.4/jquery.easyui.min.js"></script>
	<link rel="icon" href="${ctx }/style/images/vip.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${ctx }/style/images/vip.ico" type="image/x-icon" />
</head>
<body>
		<style>
			html,body{ height: 100%; margin:0; background: #0d0d24; }
			body{ background: url(${ctx }/style/images/login-bg.jpg) no-repeat center bottom; }
			h1{ text-align: center; margin:0;  color: #FFF; background:url(${ctx }/style/images/logo3.png); height: 77px; width: 138px; position: absolute; left: 0; top: -77px;  }
			#login-wrap{ width: 605px; height: 290px; background: url(${ctx }/style/images/login-sprite.png) no-repeat -6px -6px; margin:0 auto;margin-top: 120px; padding-top: 110px; position: relative;  }
			
			.input-item {text-align: center; margin-bottom: 30px;}
			.input-item input{ background: url(${ctx }/style/images/login-sprite.png) no-repeat -6px -443px; width: 345px; height: 32px; padding: 10px; border: none; font-size:14px; }
			
			.submit-wrap{ text-align: center; }
			.submit-wrap input{ background: url(${ctx }/style/images/login-sprite.png) no-repeat -6px -530px; width: 365px; border: none; height: 55px; padding: 0; cursor: pointer;}
		</style>
		
		<div id="login-wrap">
				<h1></h1>
				<form action="${ctx }/login" method="POST" onsubmit="return submitFn();" id="formSub">
					<div class="input-item">
						<div style="margin: auto;color: red;" id="message">${shrio_error_message }</div>
					</div>
					<div class="input-item">
						<input type="text" placeholder="用户名" name="loginName" id="loginName">
					</div>
					<div class="input-item">
						<input type="password" placeholder="密码" name="password" id="password">
					</div>

					<div class="submit-wrap">
						<input type="submit" value=" ">
					</div>
				</form>
		</div>

	<script type="text/javascript">
		var submitFn = function(){
			if($("#loginName").val()==''){
				$("#message").html('用户名不能为空...');
				return false;
			}
			if($("#password").val()==''){
				$("#message").html('密码不能为空...');
				return false;
			}
			$("#formSub").submit();
		}
	</script>
	</body>
</html>