<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>


<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/theme/default/easyui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/theme/icon.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/site.css">

<script src="${pageContext.request.contextPath}/public/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/js/jquery.easyui.min.js"></script>
<script src="${pageContext.request.contextPath}/public/js/plugins/datagrid-scrollview.js"></script>

</head>
<body>
	<div id="order-container">
		<!--搜索条件框  -->
		<div id="order-condition-box">
			<div class="order-condition-item">
				<select name="" id="backTypeSelect">
					<option value="2">请选择处理状态</option>
					<option value="0">为处理</option>
					<option value="1">已处理</option>
				</select>
			</div>
			<div class="order-condition-item" style="text-align: left">
				<button id="searchBt">搜索</button>
			</div>
		</div>

		<table id="tt" title="" style="width: 1070px; height: 600px"
			data-options="singleSelect:true,collapsible:true,
                                url:'feedbackList',
                                method:'get',
                                remoteSort:false,
                                multiSort:true"
			pagination="true">
			<thead>
				<tr>
					<th data-options="align:'center', field:'id',width:100">序号</th>
					<th data-options="align:'center', field:'customerName',width:100">用户名</th>
					<th data-options="align:'center', field:'customerPhone',width:100">用户手机</th>
					<th data-options="align:'center', field:'feedbacTime',width:100">日期</th>
					<th data-options="align:'center', field:'operation',width:210">操作</th>
				</tr>
			</thead>

		</table>
	</div>

	<script type="text/javascript">
        $(function(){
            $('#tt').datagrid();
        });

        function myformatter(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();
            return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);

        }


        function myparser(s){
            if (!s) return new Date();
            var ss = (s.split('-'));
            var y = parseInt(ss[0],10);
            var m = parseInt(ss[1],10);
            var d = parseInt(ss[2],10);
            if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
                return new Date(y,m-1,d);
            } else {
                return new Date();
            }
        }
        //搜索
        $("#searchBt").click(function(){
        	//退款方式
        	var backType=$("#backTypeSelect option:selected").val();
        	//申请途径
        	var applyType = $("#applyTypeSelect option:selected").val();
        	//退款状态
        	var orderState = $("#orderStateSelect option:selected").val();
        	
        	var url = "backCardList?backType="+backType+"&applyType="+applyType+"&orderState="+orderState;
        	$('#tt').datagrid({url:url});
        	
        })
        
		
	</script>

</body>
</html>