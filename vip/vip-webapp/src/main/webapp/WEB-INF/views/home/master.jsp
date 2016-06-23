<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="${ctx }/jslib/jquery-easyui-1.3.4/themes/default/easyui.css">
	<link rel="stylesheet" href="${ctx }/jslib/jquery-easyui-1.3.4/themes/icon.css">
	<link rel="stylesheet" href="${ctx }/style/vip.css">
	<script src="${ctx }/jslib/jquery-1.9.1.js"></script>
	<script src="${ctx }/jslib/jquery-easyui-1.3.4/jquery.easyui.min.js"></script>
	<title>Airport-Card</title>
</head>
<body>
		
		<div id="person-manager-wrap">
			<h3>欢迎登录机场VIP卡业务管理后台</h3>
			<c:if test="${empty error }">
			<ul>
				<li><span class="date-label">${dataStr }</span>各省业绩概况</li>
				<li>全国VIP开卡总量：<span class="total-nums">${total } </span>张</li>
				<li>开卡数前三的省份：</li>
				<c:forEach items="${top }" var="top">
					<li class="achievement-list province-list">${top.province } <span>${dataStr }</span> vip开卡 <span class="prominent-color">${top.sum_order }</span><c:if test="${top.max_order!=0  }"> ${top.bname }业务最佳：<span class="prominent-color">${top.bname }（${top.max_order } 张）</c:if></span></li>
				</c:forEach>
			</ul>
			
			<div id="echart-wrap">
					<div id="echart-box-0" style="width:800px; height:300px"></div>
					<div id="echart-box-1" style="width:800px; height:300px"></div>
					<div id="echart-box-2" style="width:800px; height:300px"></div>
			</div>
			</c:if>
		</div>


</body>
<script src="${ctx }/jslib/echarts.min.js"></script>
<script>
	var data = [{
		text: '${draw1.text}'.split(','),
		xAxis: "${draw1.xAxis}".split(','),
		series: "${draw1.series}".split(','),
		title: "${draw1.title}"
	},{
		text: '${draw2.text}'.split(','),
		xAxis: "${draw2.xAxis}".split(','),
		series:  "${draw2.series}".split(','),
		title:"${draw2.title}"
	},{
		text: '${draw3.text}'.split(','),
		xAxis: "${draw3.xAxis}".split(','),
		series:  "${draw3.series}".split(','),
		title:"${draw3.title}"
	}];
	for (var i=0; i<data.length; i++) {
		console.info(data[i].title.split(','));
		console.info("'"+data[i].title+"'");
		var myChart = echarts.init(document.getElementById('echart-box-'+i));  
        var option = {
            title: {
            		textStyle:{
            			fontSize: 12,
            			fontFamily:"Arial,Hiragino Sans GB W3,Hiragino Sans GB,Microsoft YaHei,WenQuanYi Micro Hei,sans-serif",
            			color:"#666"
            		},
            		text: data[i].text
            },
            tooltip: {},
            legend: {
                data:[data[i].title]
            },
            xAxis: {
                data: data[i].xAxis
            },
            yAxis: {},
            series: [{
                name: data[i].title,
                type: 'bar',
                data: data[i].series
            }]
        };

  
      myChart.setOption(option);
	}

	
</script>
</html>