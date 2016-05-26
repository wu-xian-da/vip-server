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
	<script src="${ctx }/jslib/plugins/datagrid-scrollview.js"></script>
	<title>Airport-Card</title>
</head>
<body>
		
		<div id="person-manager-wrap">
			<h3>欢迎登陆机场VIP卡业务管理后台</h3>
			<div class="tips">
				注：此处展现的机场概况包括区域经理A 所管辖的所有机场概况
			</div>
			<ul>
				<li><span class="date-label">${dataStr }</span>各省业绩概况</li>
				<li>全国VIP开卡总量：<span class="total-nums">${total } </span>张</li>
				<li>开卡数前三的省份：</li>
				<c:forEach items="${top }" var="top">
					<li class="achievement-list province-list">${top.province } <span>${dataStr }</span> vip开卡 <span class="prominent-color">${top.order_sum }</span> ${top.bname }业务最佳：<span class="prominent-color">${top.bname }（${top.total } 张）</span></li>
				</c:forEach>
			</ul>
			${draw1.text}${draw1.xAxis} ${draw1.series}
			<div id="echart-wrap">
					<div id="echart-box-0" style="width:800px; height:300px"></div>
					<div id="echart-box-1" style="width:800px; height:300px"></div>
					<div id="echart-box-2" style="width:800px; height:300px"></div>
			</div>
			
		</div>


</body>
<script src="${ctx }/jslib/echarts.min.js"></script>
<script>
	var data = [{
		text: '${draw1.text}',
		xAxis: "${draw1.xAxis}",
		series:  "${draw1.series}"
	},{
		text: '${draw2.text}',
		xAxis: "${draw2.xAxis}",
		series:  "${draw2.series}"
	},{
		text: '${draw3.text}',
		xAxis: "${draw3.xAxis}",
		series:  "${draw3.series}"
	}];

	for (var i=0; i<data.length; i++) {
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
                data:['开卡数']
            },
            xAxis: {
                data: data[i].xAxis
            },
            yAxis: {},
            series: [{
                name: '开卡数',
                type: 'bar',
                data: data[i].series
            }]
        };

  
      myChart.setOption(option);
	}

	
</script>
</html>