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
			<h3>欢迎登陆机场VIP卡业务管理后台</h3>
			<div class="tips">
				注：此处展现的机场概况包括区域经理A 所管辖的所有机场概况
			</div>
			<ul>
				<li><span class="date-label">2016年2月</span>各机场业绩概况</li>
				<li>辖区内VIP开卡总量：<span class="total-nums">1234 </span>张</li>
				<li>辖区内各场站业绩概况：</li>
				<li class="achievement-list">海南机场 <span>2016年02月</span> vip开卡 <span class="prominent-color">157</span> 张  业务最佳：<span class="prominent-color">张三（15 张）</span></li>
				<li class="achievement-list">重庆机场 <span>2016年02月</span> vip开卡 <span class="prominent-color">100</span> 张  业务最佳：<span class="prominent-color">王五（10 张）</span></li>
			</ul>
			
			<div id="echart-wrap">
					<div id="echart-box-0" style="width:800px; height:300px"></div>
					<div id="echart-box-1" style="width:800px; height:300px"></div>
					<div id="echart-box-2" style="width:800px; height:300px"></div>
			</div>
			
		</div>



</body>
<script src="./public/js/echarts.min.js"></script>
<script>
	var data = [{
		text: '海南机场：横轴业务员，纵轴开卡数，上个月的开卡数',
		xAxis: ["张三","张四","张五","张六","张七","张八","s1","s2","s3","s4","s5","s6"],
		series: [5, 20, 36, 10, 10, 20,11,121,131,141,151,161]
	},{
		text: '重庆机场：横轴业务员，纵轴开卡数，上个月的开卡数',
		xAxis: ["李三","李四","李五","李六","李七","李八","t1","t2","t3","t4","t5","t6"],
		series: [5, 20, 36, 10, 10, 20,11,11,31,41,1,6]
	},{
		text: '上海机场：横轴业务员，纵轴开卡数，上个月的开卡数',
		xAxis: ["王三","王四","王五","王六","王七","王二","q1","q2","q3","q4","q5","q6"],
		series: [15, 0, 6, 1, 110, 0,11,121,131,141,11,161]
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