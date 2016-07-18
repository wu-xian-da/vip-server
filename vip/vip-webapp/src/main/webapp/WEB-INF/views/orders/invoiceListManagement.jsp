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
			<div style="padding: 15px 0 0 20px;">
				<div id="order-condition-box">
					<div class="order-condition-item" style="width: 130px">
						<select id="invoiceFlagSelect">
							<option value="">全部发票状态</option>
							<option value="1">发票未邮寄</option>
							<option value="2">发票已邮寄</option>
						</select>
					</div>
					<div class="order-condition-item" style="width: 200px">
						<input id="phoneOrUserName" type="text" placeholder="用户手机号码/姓名">
						<button id="searchBt">查询</button>
					</div>
				</div>
			</div>
		</div>
		<div style="clear: both; height: 93%;" id="data-grid-wrap"
			data-options="region:'center',fit:true,border:false">
			<table id="tt" title=""
				data-options="
									rownumbers:true,
									singleSelect:true,
									collapsible:true,
	                                url:'invoiceList',
	                                method:'get',
	                                remoteSort:false,
	                                fit:true,border:false,
	                                multiSort:true,
	                                pagination:true">
				<thead>
					<tr>
						<th data-options="align:'center', field:'orderId',width:150">订单号</th>
						<th data-options="align:'center', field:'customerName',width:100">用户姓名</th>
						<th data-options="align:'center', field:'customerPhone',width:100">用户手机号</th>
						<th data-options="align:'center', field:'invoiceNo',width:200">发票单号</th>
						<th data-options="align:'center', field:'invoiceFlagName',width:100">发票状态</th>
						<th data-options="align:'center', field:'orderStateName',width:100">订单状态</th>
						<th data-options="align:'center', field:'operation',width:210">操作</th>
					</tr>
				</thead>

			</table>
		</div>

	</div>

	<!--发票信息录入 -->
	<div id="w" class="easyui-window" title="发票页面"
		data-options="modal:'true',closed:'true'"
		style="width: 500px; height: 600px; padding: 10px;">
		<div class="easy-window-item">
			<div class="easy-window-radio-tab" style="padding-left: 100px;padding-top: 20px;">
				<div class="radio-tab-content">
					<!--根据发票类型不同显示不同的数据 -->
					<div class="easy-common-content" id="commonInvoiceDiv">
						<div class="div-content-item">
							<label>发票类型</label><label class="label-content-item" id="cmInvoiceKind"></label>
						</div>
						<div class="div-content-item">
							<label>发票抬头</label><label class="label-content-item" id="cmInvoiceTitle"></label>
						</div>
						<div class="div-content-item">
							<label>发票内容</label><label class="label-content-item" id="cmInvoiceContent"></label>
						</div>
						<div class="div-content-item"> 
							<label>用户姓名</label><label class="label-content-item" id="cmCustomerName"></label>
						</div>
						<div class="div-content-item">
							<label>手机号码</label><label class="label-content-item" id="cmCustomerPhone"></label>
						</div>
						<div class="div-content-item">
							<label>邮寄地址</label><label class="label-content-item" id="cmEmailAddress"></label>
						</div>
						<div class="div-content-item">
							<label>邮　　编</label><label class="label-content-item" id="cmPostCode"></label>
						</div>
						<div class="div-content-item" id="cmInvoiceNoDiv">
							<label>发票编号</label><label class="label-content-item" id="cmInvoiceNo"></label>
						</div>
						
					</div>
					
					<div class="easy-common-content" id="specialInvoiceDiv">
						<div class="div-content-item">
							<label>发票类型</label><label class="label-content-item" id="spInvoiceKind"></label>
						</div>
						<div class="div-content-item">
							<label>发票内容</label><label class="label-content-item" id="spInvoiceContent"></label>
						</div>
						<div class="div-content-item"> 
							<label>公司名称</label><label class="label-content-item" id="spCompanyName"></label>
						</div>
						<div class="div-content-item">
							<label>公司税号</label><label class="label-content-item" id="spCompanyTaxNo"></label>
						</div>
						<div class="div-content-item">
							<label>公司地址</label><label class="label-content-item" id="spCompanyAddress"></label>
						</div>
						<div class="div-content-item">
							<label>公司电话</label><label class="label-content-item" id="spCompanyPhone"></label>
						</div>
						<div class="div-content-item"> 
							<label>用户姓名</label><label class="label-content-item" id="spCustomerName"></label>
						</div>
						<div class="div-content-item">
							<label>手机号码</label><label class="label-content-item" id="spCustomerPhone"></label>
						</div>
						<div class="div-content-item">
							<label>邮寄地址</label><label class="label-content-item" id="spEmailAddress"></label>
						</div>
						<div class="div-content-item">
							<label>邮　　编</label><label class="label-content-item" id="spPostCode"></label>
						</div>
						
						<div class="div-content-item">
							<label>营业执照</label><label class="label-content-item"><img style="height: 150px" id="spBusinessLicenseUrl"></label>
						</div>
						<div class="div-content-item" id="spInvoiceNoDiv">
							<label>发票编号</label><label class="label-content-item" id="spInvoiceNo"></label>
						</div>
					</div>
					
					<!--表单内容  -->
					<div class="raidp-tab-content-item" style="display: block" id="sumbitDiv">
						<form id="invoiceForm" method="post">
						<input type="hidden" id="invoiceId" name="invoiceId" value=""/> 
						<input type="hidden" id="orderId" name="orderId" value=""/> 
						<label>输入发票单号&nbsp;</label>
						<input class="easyui-validatebox" id="invoiceNo" required="true" data-options="missingMessage:'必填项'" />
						</form>
					</div>
				</div>

				<div class="easyui-window-footer" style="padding-left: 50px" id="buttonDiv">
					<button id="writerUserInfo">保存</button>
					<button id="cancleBt">返回</button>
				</div>
			</div>
		</div>
		

	</div>
	
	<script type="text/javascript">
        $(function(){
        	//初始化选择条件
        	$("#invoiceFlagSelect").val("");
        	$("#phoneOrUserName").val("");
        	
        	//初始化表格
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
        
       	//查看发票信息
        function lookOverInvoiceInfo(args){
        	if(args.invoiceKind ==0){//--普通发票
				$('#commonInvoiceDiv').show();
				$('#specialInvoiceDiv').hide();
				$('#cmInvoiceKind').text('普通发票');
				$('#cmInvoiceTitle').text(args.invoiceTitle);
				$('#cmInvoiceContent').text(args.invoiceContent);
				$('#cmCustomerName').text(args.customerName);
				$('#cmEmailAddress').text(args.provinceName+' '+args.cityName+' '+args.countryName+' '+args.address);
				$('#cmPostCode').text(args.postCode);
				$('#cmCustomerPhone').text(args.customerPhone);
				$('#cmInvoiceNo').text(args.invoiceNo);
				
			}else{//--专用发票
				$('#commonInvoiceDiv').hide();
				$('#specialInvoiceDiv').show();
				$('#spInvoiceKind').text('专用发票');
				$('#spInvoiceContent').text(args.invoiceContent);
				$('#spCompanyName').text(args.companyName);
				$('#spCompanyTaxNo').text(args.companyTaxNo);
				$('#spCompanyAddress').text(args.companyAddress);
				$('#spCompanyPhone').text(args.companyPhone);
				$('#spCustomerName').text(args.customerName);
				$('#spEmailAddress').text(args.provinceName+' '+args.cityName+' '+args.countryName+' '+args.address);
				$('#spPostCode').text(args.postCode);
				$('#spCustomerPhone').text(args.customerPhone);
				$('#spBusinessLicenseUrl').attr('src',args.businessLicenseUrl);
				$('#spInvoiceNo').text(args.invoiceNo);
			}
        	$('#cmInvoiceNoDiv').show();
			$('#spInvoiceNoDiv').show();
        	$('#sumbitDiv').hide();
        	$('#buttonDiv').hide();
        	$("#w").window('open');
        }
       	
       	
        //打开录入发票单号页面
		function drawBill(args){
			if(args.invoiceKind ==0){//--普通发票
				$('#commonInvoiceDiv').show();
				$('#specialInvoiceDiv').hide();
				$('#cmInvoiceKind').text('普通发票');
				$('#cmInvoiceTitle').text(args.invoiceTitle);
				$('#cmInvoiceContent').text(args.invoiceContent);
				$('#cmCustomerName').text(args.customerName);
				$('#cmEmailAddress').text(args.provinceName+' '+args.cityName+' '+args.countryName+' '+args.address);
				$('#cmPostCode').text(args.postCode);
				$('#cmCustomerPhone').text(args.customerPhone);
				
			}else{//--专用发票
				$('#commonInvoiceDiv').hide();
				$('#specialInvoiceDiv').show();
				$('#spInvoiceKind').text('专用发票');
				$('#spInvoiceContent').text(args.invoiceContent);
				$('#spCompanyName').text(args.companyName);
				$('#spCompanyTaxNo').text(args.companyTaxNo);
				$('#spCompanyAddress').text(args.companyAddress);
				$('#spCompanyPhone').text(args.companyPhone);
				$('#spCustomerName').text(args.customerName);
				$('#spEmailAddress').text(args.provinceName+' '+args.cityName+' '+args.countryName+' '+args.address);
				$('#spPostCode').text(args.postCode);
				$('#spCustomerPhone').text(args.customerPhone);
				$('#spBusinessLicenseUrl').attr('src',args.businessLicenseUrl);
			}
			$('#cmInvoiceNoDiv').hide();
			$('#spInvoiceNoDiv').hide();
			$('#sumbitDiv').show();
        	$('#buttonDiv').show();
        	$('#invoiceId').val(args.invoiceId);
        	$('#orderId').val(args.orderId);
        	$('#invoiceNo').val('');
        	$("#w").window('open');
        }
       
        //将发票信息录入到发票表中
		$("#writerUserInfo").click(function(){
			//检查表单必填项
			if($('#invoiceForm').form('validate')){
				var invoiceId = $("#invoiceId").val();
				var orderId = $("#orderId").val();
				var invoiceNo = $("#invoiceNo").val();
				var url = "handelInvoiceInfo?invoiceId="+invoiceId+"&invoiceNo="+invoiceNo+"&orderId="+orderId;
				$.get(url,function(_d){
					if(_d.result == 1){
						$("#w").window('close');
						$('#tt').datagrid('reload');
					}
				})
			}
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