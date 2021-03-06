<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.jianfei.core.common.security.shiro.HasAnyPermissionsTag"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html style="height:99%; margin:0;">
<head>
<title></title>


<link rel="stylesheet" href="public/css/theme/default/easyui.css">
<link rel="stylesheet" href="public/css/theme/icon.css">
<link rel="stylesheet" href="public/css/site.css">

<script src="public/js/jquery.min.js"></script>
<script src="public/js/jquery.easyui.min.js"></script>
<script src="public/js/plugins/datagrid-scrollview.js"></script>
<script src="${pageContext.request.contextPath}/public/js/locale/easyui-lang-zh_CN.js"></script>

</head>
<body style="height:99%; margin:0;">
	 <div id="order-container" style="height:99%; margin:0;">
	 	<style type="text/css">
	 			#order-container-wrap{ overflow:hidden;}
        		#order-container{width:auto;}
        		#order-container-calac,#order-condition-box{float:left; padding-top:5px;}
        		#order-container #order-condition-box{ padding-top:5px; margin-left:15px;}
        		#order-container #order-condition-box select{height:26px;}
        		.panel-body{ background:none;}
        		.order-condition-item{margin-right: 0px}
        		
        </style>
        <div id="order-container-wrap" style="margin:0; padding:0;">
	        <div style="width:1070px; overflow:hidden;">
	        
	        <style>
	        	#order-container-calac, #order-condition-box{ float:none;}
	        	#order-container-calac, #order-condition-box{ text-align:left; padding-left:20px;}
	        	#order-container #order-condition-box{ margin-left:0;}
	        </style>
	        <div style="width: 1200px;">
			        <div id="order-container-calac">
			            <label>订单日期：</label>
			            <input class="easyui-datebox" id="startTime" data-options="formatter:myformatter,parser:myparser" style="width:120px;height:26px">
			
			            <label>-</label>
			            <input class="easyui-datebox" id="endTime" data-options="formatter:myformatter,parser:myparser" style="width:120px;height:26px">
			
			        </div>
			
			        <div id="order-condition-box">
			        
			        	
			        	
			            <div class="order-condition-item" style="width: 150px">
			                <select id="airportIdSelect">
			                	
			                    <option value="">全部机场</option>
			                    <c:forEach items="${airPostList}" var="airPort">
			                    	<option value="${airPort.id}">${airPort.name }</option>
			                    </c:forEach>
			                    
			                </select>
			            </div>
			
			            <div class="order-condition-item" style="width: 140px">
			                <select id="orderStateSelect">
			                    <option value="">全部订单状态</option>
			                    <option value="0">正在支付</option>
			                    <option value="1">已支付</option>
			                    <!-- <option value="2">正在审核</option> -->
			                    <option value="3">审核通过</option>
			                    <option value="4">已退款</option>
			                    <option value="5">已失效</option>
			                </select>
			            </div>
			
			            <div class="order-condition-item" style="width: 140px">
			                <select id="invoiceSelect">
			                    <option value="">全部发票状态</option>
			                    <option value="0">不需要</option>
			                    <option value="1">未邮寄</option>
			                    <option value="2">已邮寄</option>
			                  
			                </select>
			            </div>
			            
			            <div class="order-condition-item" style="width: 130px">
			                <select id="payTypeSelect">
			                    <option value="">支付方式</option>
			                    <option value="1">微信</option>
			                    <option value="2">支付宝</option>
			                    <option value="3">刷卡</option>
			                    <option value="4">现金</option>
			                </select>
			            </div>
			
			            <div class="order-condition-item" style="width: 230px">
			                <input id="phoneOrUserName" type="text" placeholder="订单号/卡号/姓名/手机号" style="width: 150px">
			                <button id="searchBt">查询</button>
			            </div>
			            
			            <div class="order-condition-item" style="width: 70px;margin-left: 12px">
			                <button id="resetBt">重置</button>
			            </div>
			            <div class="order-condition-item" style="width: 65px">
			                 <button id="exportBt">导出</button>
			            </div>
			            
			        </div>
	        </div>
	        
	        </div>
		</div>        
        <div style="clear:both; height:93%;" id="data-grid-wrap" data-options="region:'center',fit:true,border:false">
	        <table id="tt" title=""
	                data-options="
	               					rownumbers:true,
	                				singleSelect:true,
	               					collapsible:true,
	                                url:'orderList',
	                                method:'get',
	                                remoteSort:false,
	                                fit:true,border:false,
	                                multiSort:true,
	                                pagination:true">
	            <thead>
	                <tr>
	                <th data-options="align:'center', field:'orderId',width:130">订单编号</th>
	                <th data-options="align:'center', field:'orderTime',width:150">订单创建日期</th>
	                <th data-options="align:'center', field:'airportName',width:150">场站</th>
	                <th data-options="align:'center', field:'agentName',width:100">业务员</th>
	                
	                <th data-options="align:'center', field:'customerName',width:100">用户姓名</th>
	                <th data-options="align:'center', field:'customerPhone',width:100">用户手机</th>
	                <th data-options="align:'center', field:'vipCardNo',width:100">vip卡号</th>
	                <th data-options="align:'center', field:'invoiceFlagName',width:80">发票状态</th>
	                <th data-options="align:'center', field:'orderStateName',width:100">订单状态</th>
	                 <th data-options="align:'center', field:'payTypeName',width:100">支付方式</th>
	                <th data-options="align:'center', field:'operation',width:230">操作</th>
	                </tr>
	            </thead>
	
	        </table>
        </div>
        
    </div>

    <!--用户账号信息录入 -->
	<div id="w" class="easyui-window" title="退款" 
            data-options = "modal:'true',closed:'true'" 
            style="width:500px;height:300px;padding:10px;">
       	<div class="easy-window-item">
            <div class="easy-window-radio-tab">
            	<!-- 支付方式提示 -->
            	<div class="radio-tab-content" style="font-weight:bolder ;color: red;font-size:large;">
                                               支付方式&nbsp;<label id="orderPayMethod" ></label>
                </div>
                
                <div class="radio-tab-head">
                    <label><input type="radio" name="card-radio" checked id="1">微信转账</label>
                    <label><input type="radio" name="card-radio" id="2">支付宝转账</label>
                    <label><input type="radio" name="card-radio" id="3">银行卡转账</label>
                </div>
					
				
                <div class="radio-tab-content">
                    <div class="raidp-tab-content-item" style="display:block" id="wxdiv1">
                    	<form id="wxform1">
                    	<input type="hidden" value="" id="hideOrderId"/>
                        <label>输入微信号&nbsp;</label><input class="easyui-validatebox" type="text" id="backCardNo0"  data-options="missingMessage:'必填项',required:true"/>
                    	</form>
                    </div>

                    <div class="raidp-tab-content-item" id="tbdiv2">
                    <form id="tbform2">
                        <label>输入支付宝账号&nbsp;</label><input class="easyui-validatebox" type="text" id="backCardNo1" data-options="missingMessage:'必填项',required:true"/>
                    </form>
                    </div>

                    <div class="raidp-tab-content-item" id="bankdiv3">
                    <form id="banckform3">
                    	<table>
                    		<tr>
                    			<td><label>输入开户行&nbsp;</label></td>
                    			<td><input class="easyui-validatebox" type="text" id="banckName" data-options="missingMessage:'必填项',required:true"/></td>
                    		</tr>
                    		<tr>
                    			<td> <label>输入银行卡账号&nbsp;</label></td>
                    			<td><input class="easyui-validatebox" type="text" id="backCardNo2" data-options="missingMessage:'必填项',required:true"></td>
                    		</tr>
                    		<tr>
                    			<td><label>输入持卡人姓名&nbsp;</label></td>
                    			<td><input class="easyui-validatebox" type="text" id="userNames" data-options="missingMessage:'必填项',required:true"/></td>
                    		</tr>
                    	</table>
                    </form>
                     </div>
                     
                </div>
                

                <div class="raido-tab-refund-price">
                    <label>可退金额&nbsp;&nbsp;<span id="remainMoney">0.00</span>元</label>
                </div>

                <div class="easyui-window-footer" style="padding-left:50px">
                    <button id="writerUserInfo">录入信息确认</button>
                    <button id="cancleBt">返回</button>
                </div>
            </div>
        </div>            
        
    </div>
    
    <!-- 退款确认 -->
	<div id="refund" class="easyui-window" title="退款"
		data-options="modal:'true',closed:'true'"
		style="width: 500px; height: 350px; padding: 10px;">
		<div class="easy-window-item">
			<div class="easy-window-radio-tab">
				<!-- 需要开发票的提示信息 -->
				<div class="radio-tab-content">
                    <label id="promptMessage" style="font-weight:bolder ;color: red;font-size:large;"></label>
                </div>
                <div class="radio-tab-content">
                    <label>支付方式&nbsp;</label><span id="orderPayType"></span>
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
        	//重置选择条件
        	$("#startTime").datebox("setValue","");
        	$("#endTime").datebox("setValue","");
        	$("#phoneOrUserName").val("");
        	$("#airportIdSelect").val("");
        	$("#orderStateSelect").val("");
        	$("#invoiceSelect").val("");
        	$("#payTypeSelect").val("");
        	
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
			//----4、支付返方式
			$("#orderPayType").text('');
			//提示信息
			$("#promptMessage").text('');
			
			//-----是否需要提示信息
			if(args.invoice == 2){
				$("#promptMessage").text("请确认是否收到发票！");
			}
			
			//支付方式
			if(args.orderPayType == 1){
				$("#orderPayType").text('微信支付');
			}else if(args.orderPayType ==2){
				$("#orderPayType").text('支付宝支付');
			}else if(args.orderPayType == 3){
				$("#orderPayType").text('银行卡支付');
			}else{
				$("#orderPayType").text('现金支付');
			}
			
			//申请途径-现场正常
			if(args.applyBackCardMethod == 0){
				$('#applyBackCardMethod').text('现场');
				if(args.orderPayType == 4){//现金支付
					$("#backMethod").text('退款方式 银行卡号');
					$("#payBackCardNo").text(args.backMoneyCard);
					$("#banckName2div").show();
					$("#banckName2").text(args.backName);
					$("#userName2div").show();
					$("#userName2").text(args.customerName);
				}else{
					if(args.backType ==1){
						$("#backMethod").text('退款方式');
						$("#payBackCardNo").text('微信转账');
					}else if(args.backType ==2){
						$("#backMethod").text('退款方式');
						$("#payBackCardNo").text('支付宝转账');
					}else if(args.backType == 3){
						$("#backMethod").text('退款方式');
						$("#payBackCardNo").text('银行卡转账');
					}
				}
				
			} else{//客服
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
        
        
		//2、财务人员进行退款操作
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
        
        
        /*
        *===================业务人员审核页面，录入用户信息=============
        */
		//1、审核通过时，输入用户的账户信息（已完成）
        function onRefund(args){
			//初始化页面
			$("input[name='card-radio']").get(0).checked=true;
			$('#wxdiv1').show();
			$('#tbdiv2').hide();
			$('#bankdiv3').hide();
			
			$('#orderPayMethod').text('');
			$('#backCardNo0').val('');
			$('#backCardNo1').val('');
			$('#banckName').val('');
			$('#backCardNo2').val('');
			$('#userNames').val('');
			$('#remainMoney').text('');

			//支付方式
			var payType = args.payType;
			if(payType == 1){
				$('#orderPayMethod').text('微信支付');
			}else if(payType == 2){
				$('#orderPayMethod').text('支付宝支付');
			}else if(payType == 3){
				$('#orderPayMethod').text('银行卡支付');
			}else{
				$('#orderPayMethod').text('现金支付');
			}
        	$("#remainMoney").text(args.remainMoney);
        	$("#hideOrderId").val(args.orderId);
        	$("#w").window('open');
        }
        //2、审核不通过
        function onError(args,elem){
        	var url = "applyBackCardaAudit?orderId="+args.orderId+"&opType="+2+"&phone="+args.phone;
        	$.get(url,function(_d){
        		if(_d.result == 1){
                	$(elem).parent().parent().prev().find("div").text(_d.orderStateName);
                	var $_tdwrap = $(elem).parent();
                	$_tdwrap.empty();
                   	$_tdwrap.append($(_d.data));
                 }
            })
        }
        //3、取消录入页面（已完成）
        $("#cancleBt").click(function(){
        	$("#w").window('close');
        })
		
		//3、将用户填写的信息写入到退卡流水表中
		$("#writerUserInfo").click(function(elem){
				var backCardNo = "";
				var userNames = "";//开户名
				var banckName = "";//开户行
				var remainMoney = $("#remainMoney").text();
				var payMethod = $('input:radio[name="card-radio"]:checked').attr("id");
				var orderId = $("#hideOrderId").val();
				if(payMethod==1){
					if($("#wxform1").form('validate')){
						backCardNo = $("#backCardNo0").val();
					}else{
						return;
					}
					
				}else if(payMethod == 2){
					if($("#tbform2").form('validate')){
						backCardNo = $("#backCardNo1").val();
					}else{
						return ; 
					}
					
				}else if(payMethod ==3){
					if($('#banckform3').form('validate')){
						backCardNo = $("#backCardNo2").val();
						userNames = $("#userNames").val();
						banckName = $("#banckName").val();
					}else{
						return;
					}
					
				}
				var url = "onRefund?orderId="+orderId+"&backCardNo="+backCardNo+"&remainMoney="+remainMoney+"&payMethod="+payMethod+"&opr="+3+"&userNames="+userNames+"&banckName="+banckName;
				$.get(url,function(_d){
					if(_d.result == 1){
						$("#w").window('close');
						$('#tt').datagrid('reload');
					}
				})
			
		})
        
		
		
       /*
       	*==================退单申请 已完成===============
       	*/
        function onRefundApplication(args,elem){
        	if(args.invoice == 2){//发票已邮寄
        		$.messager.alert("提示信息","该订单需要用户寄回发票才能进行最终退款操作！");
        	}
        	var url = "applyBackCard?orderId="+args.orderId+"&operationType="+args.opr+"&phone="+args.phone;
            $.get(url,function(_d){
                if(_d.result == 1){
                    $(elem).after($(_d.data));
                    $(elem).parent().parent().prev().find("div").text(_d.orderStateName);
                  	$(elem).remove();
                 
                }
            })
        }
       
      	$(".radio-tab-head label").on({
            "click":function(){
                var _index = $(this).index(".radio-tab-head label");
                $(".radio-tab-content .raidp-tab-content-item").eq(_index).css("display","block").siblings().css("display","none")
            }
            
        })
        
        //搜索
        $("#searchBt").click(function(){
        	//开始时间
        	var startTime = $("#startTime").datebox('getValue');
        	//结束时间
        	var endTime = $("#endTime").datebox('getValue');
        	//机场编号
        	var airportId = $("#airportIdSelect option:selected").val(); 
        	//订单状态
        	var orderState = $("#orderStateSelect option:selected").val();
        	//发票状态
        	var invoiceState = $("#invoiceSelect option:selected").val();
        	//支付方式
        	var payTypes = $("#payTypeSelect option:selected").val();
        	//手机号后置用户姓名
        	var phoneOrUserName = $("#phoneOrUserName").val();
        	
        	//判断结束时间是否大于开始时间
        	if(endTime < startTime ){
        		$.messager.alert('提示','结束时间不能小于开始时间！','warning'); 
        	}else{
        		var url = "orderList?startTime="+startTime+"&endTime="+endTime+"&airportId="+airportId+"&orderState="+orderState+
    			"&invoiceState="+invoiceState+"&payType="+payTypes+"&phoneOrUserName="+phoneOrUserName;
    			$('#tt').datagrid({url:url});
        	}
        })
        
        //导出功能
        
        $("#exportBt").click(function(){
        	//开始时间
        	var startTime = $("#startTime").datebox('getValue');
        	//结束时间
        	var endTime = $("#endTime").datebox('getValue');
        	//机场编号
        	var airportId = $("#airportIdSelect option:selected").val(); 
        	//订单状态
        	var orderState = $("#orderStateSelect option:selected").val();
        	//发票状态
        	var invoiceState = $("#invoiceSelect option:selected").val();
        	//手机号后置用户姓名
        	var phoneOrUserName = $("#phoneOrUserName").val();
        	var url = "exportOrderInfoToExcel?startTime="+startTime+"&endTime="+endTime+"&airportId="+airportId+"&orderState="+orderState+
			"&invoiceState="+invoiceState+"&phoneOrUserName="+phoneOrUserName;
        	location.href=url;    
        	
        })
        
        //重置按钮
        $("#resetBt").click(function(){
        	$("#startTime").datebox("setValue","");
        	$("#endTime").datebox("setValue","");
        	$("#phoneOrUserName").val("");
        	$("#airportIdSelect").val("");
        	$("#orderStateSelect").val("");
        	$("#invoiceSelect").val("");
        })
	</script>
	
</body>
</html>