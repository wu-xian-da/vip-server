<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.jianfei.core.common.security.shiro.HasAnyPermissionsTag"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html id="iframe" style="height: 99%; margin: 0;">
<head>
<title></title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/public/css/theme/default/easyui.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/public/css/theme/icon.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/public/css/site.css">

<script src="${pageContext.request.contextPath}/public/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/public/js/jquery.easyui.min.js"></script>
<script
	src="${pageContext.request.contextPath}/public/js/plugins/datagrid-scrollview.js"></script>
<script
	src="${pageContext.request.contextPath}/public/js/locale/easyui-lang-zh_CN.js"></script>

</head>
<body style="height: 99%; margin: 0;">
	<div id="order-container" style="height: 99%; margin: 0;">
		<style type="text/css">
#order-container-wrap {
	overflow: hidden;
}

#order-container {
	width: auto;
}

#order-container-calac, #order-condition-box {
	float: left;
	padding-top: 5px;
}

#order-container #order-condition-box {
	padding-top: 5px;
	margin-left: 20px;
}

#order-container #order-condition-box select {
	height: 26px;
}

.panel-body {
	background: none;
}
</style>
		<div id="order-container-wrap"
			style="height: 10%; margin: 0; padding: 0;">
			<div style="padding: 20px 0 0 20px;">
				<div id="order-condition-box">
					<div class="order-condition-item" style="width: 140px">
						<select id="invoiceFlagSelect">
							<option value="">全部发票状态</option>
							<option value="1">发票未邮寄</option>
							<option value="2">发票已邮寄</option>
						</select>
					</div>
					<div class="order-condition-item" style="width: 190px">
						<input id="phoneOrUserName" type="text" placeholder="用户手机号码/姓名">
						<button id="searchBt">查询</button>
					</div>
				</div>
			</div>
		</div>
		<div style="clear: both; height: 93%;" id="data-grid-wrap"
			data-options="region:'center',fit:true,border:false">
			<table id="tt" title=""
				data-options="singleSelect:true,collapsible:true,
	                                url:'invoiceList',
	                                method:'get',
	                                remoteSort:false,
	                                fit:true,border:false,
	                                multiSort:true,
	                                pagination:true">
				<thead>
					<tr>
						<th data-options="align:'center', field:'orderId',width:200">订单号</th>
						<th data-options="align:'center', field:'customerName',width:100">用户姓名</th>
						<th data-options="align:'center', field:'customerPhone',width:100">用户手机号</th>
						<th data-options="align:'center', field:'invoiceNo',width:200">发票单号</th>
						<th
							data-options="align:'center', field:'invoiceFlagName',width:100">发票状态</th>
						<th data-options="align:'center', field:'operation',width:210">操作</th>
					</tr>
				</thead>

			</table>
		</div>

	</div>

	<!--发票信息录入 -->
	<div id="w" class="easyui-window" title="开发票页面"
		data-options="modal:'true',closed:'true'"
		style="width: 500px; height: 300px; padding: 10px;">
		<div class="easy-window-item">
			<div class="easy-window-radio-tab">
				<div class="radio-tab-content">
					<div class="raidp-tab-content-item" style="display: block">
						<input type="hidden" id="invoiceId" value=""/> 
						<input type="hidden" id="orderId" value=""/> 
						<label>输入发票单号&nbsp;</label>
						<input type="text" id="invoiceNo"/>
					</div>
				</div>

				<div class="easyui-window-footer" style="padding-left: 50px">
					<button id="writerUserInfo">保存</button>
					<button id="cancleBt">返回</button>
				</div>
			</div>
		</div>
		

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
        
        //打开录入发票单号页面
		function drawBill(args){
        	$("#invoiceId").val(args.invoiceId);
        	$("#orderId").val(args.orderId);
        	$("#w").window('open');
        }
       
        //将发票信息录入到发票表中
		$("#writerUserInfo").click(function(){
			var invoiceId = $("#invoiceId").val();
			var orderId = $("#orderId").val();
			var invoiceNo = $("#invoiceNo").val();
			alert
			var url = "handelInvoiceInfo?invoiceId="+invoiceId+"&invoiceNo="+invoiceNo+"&orderId="+orderId;
			$.get(url,function(_d){
				if(_d.result == 1){
					$("#w").window('close');
					window.location.reload();
				}
			})
			
			
		})
        
		//搜索
        $("#searchBt").click(function(){
        	//发票状态
        	var invoiceFlag = $("#invoiceFlagSelect option:selected").val();
        	//手机号后置用户姓名
        	var phoneOrUserName = $("#phoneOrUserName").val();
        	var url = "invoiceList";
        	$('#tt').datagrid(
        			{url:url,queryParams:{
        				invoiceFlag:invoiceFlag,
        				phoneOrUserName:phoneOrUserName
        			}
        	});
        	
        })
        
        //取消录入页面
        $("#cancleBt").click(function(){
        	$("#w").window('close');
        })
		
       
	</script>

</body>
</html>