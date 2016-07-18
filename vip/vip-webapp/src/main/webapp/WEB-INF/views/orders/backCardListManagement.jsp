<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.jianfei.core.common.security.shiro.HasAnyPermissionsTag"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html id="iframe" style="height:99%; margin:0;">
<head>
<title></title>


<link rel="stylesheet" href="public/css/theme/default/easyui.css">
<link rel="stylesheet" href="public/css/theme/icon.css">
<link rel="stylesheet" href="public/css/site.css">

<script src="public/js/jquery.min.js"></script>
<script src="public/js/jquery.easyui.min.js"></script>
<script src="public/js/plugins/datagrid-scrollview.js"></script>
<script src="${pageContext.request.contextPath}/public/js/locale/easyui-lang-zh_CN.js"></script>
<style type="text/css">
	#order-container #order-condition-box select {
	height: 26px;
}
</style>
</head>
<body style="height:100%;margin:0;">
	<div id="order-container"  style="height:99%;">
		<div id="order-condition-box" style="height:10%; margin:0; padding:0;">
		<div style="width:1000px; overflow:hidden;">
			<div style="padding-top:12px; padding-left:20px;width:1200px">
				<div class="order-condition-item">
					<select name="" id="backTypeSelect">
						<option value="">全部退款方式</option>
						<option value="1">微信</option>
						<option value="2">支付宝</option>
						<option value="3">银行卡</option>
						<option value="4">紧急退款</option>
					</select>
				</div>
	
				<div class="order-condition-item">
					<select name="" id="applyTypeSelect">
						<option value="">全部申请途径</option>
						<option value="0">现场</option>
						<option value="1">客服</option>
						
					</select>
				</div>
	
				<div class="order-condition-item">
					<select name="" id="orderStateSelect">
						<option value="">全部订单状态</option>
						<option value="3">审核通过</option>
						<option value="4">已退款</option>
					</select>
				</div>
				
				<div class="order-condition-item" style="width: 165px">
			          <input id="phoneOrUserName" type="text" placeholder="订单号/用户名/手机号" style="width: 140px">
			    </div>
			            
				<div class="order-condition-item" style="text-align: left">
					<button id="searchBt">查询</button>
				</div>
			</div>
			
		</div>
		</div>
		<style type="text/css">
			.panel-body{ background:none;}
			#order-container {
				    width: auto;
				}
		</style>
		<div style="clear:both; height:92%;" id="data-grid-wrap" data-options="region:'center',fit:true,border:false">
				<table id="tt" data-options="
										rownumbers:true,
										singleSelect:true,
										collapsible:true,
		                                url:'backCardList',
		                                method:'get',
		                                remoteSort:false,
		                                fit:true,border:false,
		                                multiSort:true,
		                                pagination:true">
					<thead>
						<tr>
						
						
		
							<th data-options="align:'center', field:'orderId',width:130">订单号</th>
							<th data-options="align:'center', field:'customerName',width:100">用户名</th>
							<th data-options="align:'center', field:'customerPhone',width:100">用户手机</th>
							<th data-options="align:'center', field:'orderTime',width:150">申请日期</th>
		
							<th data-options="align:'center', field:'applyTypeName',width:120">申请途径</th>
							<th data-options="align:'center', field:'backTypeName',width:80">退款方式</th>
							<th data-options="align:'center', field:'remainMoney',width:100">退卡金额</th>
							<th data-options="align:'center', field:'cardStateName',width:100">卡状态</th>
							<th data-options="align:'center', field:'orderStateName',width:100">订单状态</th>
							<th data-options="align:'center', field:'operation',width:200">操作</th>
						</tr>
					</thead>
		
				</table>
		</div>
		
	</div>

	<!-- 退款确认 -->
	<div id="refund" class="easyui-window" title="退款"
		data-options="modal:'true',closed:'true'"
		style="width: 500px; height: 300px; padding: 10px;">
		<div class="easy-window-item">
			<div class="easy-window-radio-tab">
				<!-- 需要开发票的提示信息 -->
				<div class="radio-tab-content">
                    <label id="promptMessage" style="font-weight:bolder ;color: red;font-size:large;"></label>
                </div>
                
                <div class="radio-tab-content">
                    <label>申请退卡途径&nbsp;</label><span id="applyBackCardMethod"></span>
                </div>
                
                <!--退款方式 -->
				<div class="radio-tab-content">
					<div class="raidp-tab-content-item" style="display: block">
						<input type="hidden" value="" id="backCardOrderId" /> 
						<label id="backMethod">输入微信号:</label>&nbsp;<span id="payBackCardNo"></span>
					</div>
				</div>
				
				<div id="banckName2div" class="radio-tab-content" style="display: none;">
                    <label>开户行&nbsp; <span id="banckName2"></span></label>
                </div>
                
                <div id="userName2div" class="radio-tab-content" style="display: none;">
                    <label>用户姓名&nbsp; <span id="userName2"></span></label>
                </div>
                
				<div class="raido-tab-refund-price">
					<label>可退金额 <span id="remainMoney2">0.00</span>元</label>
				</div>

				<div class="easyui-window-footer" style="padding-left:32px">
					<!-- 该按钮只有财务有权限操作 -->
					<shiro:hasPermission name="system:user:add">
						<button id="finalBackMoneyToUserBt">退款确认</button>
					</shiro:hasPermission>
					<button id="refundCloseWindow">取消</button>
				</div>
			</div>
		</div>

	</div>


	<script type="text/javascript">
        $(function(){
        	//1、重置条件选项
        	$("#backTypeSelect").val("");
        	$("#applyTypeSelect").val("");
        	$("#orderStateSelect").val("");
        	//2、表格初始化
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
        	//搜索关键字
        	var phoneOrUserName = $("#phoneOrUserName").val();
        	
        	var url = "backCardList?backType="+backType+"&applyType="+applyType+"&orderState="+orderState+"&phoneOrUserName="+phoneOrUserName;
        	$('#tt').datagrid({url:url});
        	
        })
        
		/*
		*==================最终退款页面===============
		*/
        function finalBackMoneyToUser(args){
			var backMethod = "";
			//----初始化页面
			$("#banckName2div").hide();
			$("#userName2div").hide();
			//----1、开户行
			$("#banckName2").text('');
			//----2、用户姓名
			$("#userName2").text('');
			//----3、转账卡号
			$('#payBackCardNo').text('');
			
			//-----是否需要提示信息
			if(args.invoice == 2){
				$("#promptMessage").text("请确认是否收到发票！");
			}
			
			//申请途径
			if(args.applyBackCardMethod == 0){
				$('#applyBackCardMethod').text('现场');
				if(args.backType ==1){
					$("#backMethod").text('退款方式');
					$("#payBackCardNo").text('微信转账');
				}else if(args.backType ==2){
					$("#backMethod").text('退款方式');
					$("#payBackCardNo").text('支付宝转账');
				}else if(args.backType == 3){
					$("#backMethod").text('退款方式');
					$("#payBackCardNo").text('银行卡转账');
				}else{
					$("#backMethod").text('退款方式 银行卡号');
					$("#payBackCardNo").text(args.backMoneyCard);
					$("#banckName2div").show();
					$("#banckName2").text(args.backName);
					$("#userName2div").show();
					$("#userName2").text(args.customerName);
				}
				
			} else{
				$('#applyBackCardMethod').text('客服');
				if(args.backType == 1){
					backMethod="微信账号";
				}else if(args.backType ==2){
					backMethod = '支付宝账号';
				}else if(args.backType == 3){
					backMethod = '银行卡号';
					$("#banckName2div").show();
					$("#banckName2").text(args.backName);
					$("#userName2div").show();
					$("#userName2").text(args.customerName);
				}
				$("#backMethod").text(backMethod);
				$("#payBackCardNo").text(args.backMoneyCard);
				
			}
			//----退卡表的订单号
			$("#backCardOrderId").val(args.orderId);
			//----退款金额
			$("#remainMoney2").text(args.remainMoney);
			$("#refund").window('open');
        }
        
        
		/*
		* ======2、财务人员进行退款操作
		*/
		$("#finalBackMoneyToUserBt").click(function(){
			var orderId = $("#backCardOrderId").val();
			var url = "finalRefundMoney?orderId="+orderId+"&opr="+4;
			$.get(url,function(_d){
				if(_d.result==1){
					$("#refund").window('close');
					window.location.reload();
				}
			})
		})
		
        //3、关闭最终退款页面
        $("#refundCloseWindow").click(function(){
        	$("#refund").window('close');
        })
        
        $(".radio-tab-head label").on({
            "click":function(){
                var _index = $(this).index(".radio-tab-head label");
                $(".radio-tab-content .raidp-tab-content-item").eq(_index).css("display","block").siblings().css("display","none")
            }
            
        })
        
        
		
	</script>

</body>
</html>