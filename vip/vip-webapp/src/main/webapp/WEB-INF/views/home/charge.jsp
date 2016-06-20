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
	<script type="text/javascript">
		var data =eval("("+'${draw}'+")");
	</script>
</head>
<body>
		<div id="person-manager-wrap">
			<h3>欢迎登陆机场VIP卡业务管理后台</h3>
			<c:if test="${empty error }">
			<ul>
				<li><span class="date-label">${dataStr }</span>各机场业绩概况</li>
				<li>辖区内VIP开卡总量：<span class="total-nums">${total } </span>张</li>
				<li>辖区内各场站业绩概况：</li>
				<c:forEach items="${airPorts }" var="airPort">
					<li class="achievement-list">${airPort.airportname } <span>${dataStr }</span> vip开卡 <span class="prominent-color">${airPort.order_num }</span><c:if test="${airPort.maxOrder!=0  }"> ${airPort.bname }业务最佳：<span class="prominent-color">${airPort.bname }（${airPort.maxOrder } 张）</c:if></span></li>
				</c:forEach>
			</ul>
			
			<div id="echart-wrap">
					
			</div>
			</c:if>
		</div>



</body>
<script src="${ctx }/jslib/echarts.min.js"></script>
<script>
console.info(data);
	for (var i=0; i<data.length; i++) {
		$("#echart-wrap").append($("<div id=\"echart-box-"+ i +"\" style=\"width:800px; height:300px\"></div>"))
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