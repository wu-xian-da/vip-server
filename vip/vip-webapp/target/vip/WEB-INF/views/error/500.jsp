<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<title>温馨提示:您要访问的页面不存在！</title>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<STYLE type=text/css>
body {
	margin: 0 auto;
	background: #FFF;
	text-align: center;
}

a:link {
	text-decoration: none;
	color: #03F;
}

a:visited {
	text-decoration: none;
	color: #F60;
}

a:hover {
	text-decoration: underline;
	color: #F60;
}

a:active {
	text-decoration: none;
	colorwhite;
}

.main {
	margin: 0 auto;
}

.con {
	margin: 0 auto;
	width: 540px;
}

.errorPic {
	margin: 0 auto;
	width: 329px;
	height: 211px;
	padding: 10px;
}

.errorNotes {
	
}

.errorNotes ul {
	height: 30px;
}

.errorNotes li {
	float: left;
	width: 150px;
	text-align: center;
	line-height: 30px;
	list-style: none;
}

.re {
	margin: 0 auto;
	width: 280px;
	text-align: center;
}

.re .title {
	text-align: center;
	line-height: 30px;
	font-size: 20px;
	font-weight: bold;
	color: #F00;
}

.re dt {
	text-align: left;
	line-height: 30px;
}
</STYLE>
<BODY>
	<div class="main">
		<div class="con">
			<div class="errorPic">
				<img src="errordocs/images/error.gif">
			</div>
			<div class="errorNotes">
				<div class="re">
					<div class="title">抱歉，找不到您要的页面……</div>
					<dl>
						<dt>1、可能是您访问的内容不存在</dt>
						<dt>2、可能是您访问的内容已过期</dt>
						<dt>3、可能是您访问的网址有误</dt>
					</dl>
				</div>
				<ul>
					<li><a href="/">返回首页</a></li>
					<li><a href="javascript:history.go(-1);">返回上一页</a></li>
					<li><a href="http://www.qvdv.com" target="_blank">技术支持</a></li>
				</ul>
			</div>
		</div>
	</div>