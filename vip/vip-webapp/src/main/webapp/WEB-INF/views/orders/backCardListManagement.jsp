<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.jianfei.core.common.security.shiro.HasAnyPermissionsTag"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>


<link rel="stylesheet" href="public/css/theme/default/easyui.css">
<link rel="stylesheet" href="public/css/theme/icon.css">
<link rel="stylesheet" href="public/css/site.css">

<script src="public/js/jquery.min.js"></script>
<script src="public/js/jquery.easyui.min.js"></script>
<script src="public/js/plugins/datagrid-scrollview.js"></script>

</head>
<body>
	<div id="order-container">
		<div id="order-condition-box">
			<div class="order-condition-item">
				<select name="" id="backTypeSelect">
					<option value="4">请选择退款方式</option>
					<option value="0">微信</option>
					<option value="1">支付宝</option>
					<option value="2">银行卡</option>
					<option value="3">现场</option>
				</select>
			</div>

			<div class="order-condition-item">
				<select name="" id="applyTypeSelect">
					<option value="3">请选择申请途径</option>
					<option value="0">客服</option>
					<option value="1">现场</option>
					
				</select>
			</div>

			<div class="order-condition-item">
				<select name="" id="orderStateSelect">
					<option value="10">选择退款状态</option>
					<option value="3">未退</option>
					<option value="4">已退</option>
				</select>
			</div>

			<div class="order-condition-item" style="text-align: left">
				<button id="searchBt" style="height: 20px">搜索</button>
			</div>
		</div>

		<table id="tt" title="" style="width: 1070px; height: 600px"
			data-options="singleSelect:true,collapsible:true,
                                url:'backCardList',
                                method:'get',
                                remoteSort:false,
                                multiSort:true"
			pagination="true">
			<thead>
				<tr>
				
				

					<th data-options="align:'center', field:'orderId',width:100">订单号</th>
					<th data-options="align:'center', field:'customerName',width:100">用户名</th>
					<th data-options="align:'center', field:'customerPhone',width:100">用户手机</th>
					<th data-options="align:'center', field:'orderTime',width:100">申请日期</th>

					<th data-options="align:'center', field:'applyType',width:150">申请途径</th>
					<th data-options="align:'center', field:'backType',width:100">退卡方式</th>
					<th data-options="align:'center', field:'remainMoney',width:100">退卡金额</th>
					<th data-options="align:'center', field:'orderStateName',width:100">状态</th>
					<th data-options="align:'center', field:'operation',width:210">操作</th>
				</tr>
			</thead>

		</table>
	</div>

	<!-- 退款确认 -->
	<div id="refund" class="easyui-window" title="退款"
		data-options="modal:'true',closed:'true'"
		style="width: 500px; height: 260px; padding: 10px;">
		<div class="easy-window-item">
			<div class="easy-window-radio-tab">
				<div class="radio-tab-content">
					<div class="raidp-tab-content-item" style="display: block">
						<input type="hidden" value="" id="backCardOrderId" /> <label
							id="backMethod">输入微信号:</label><input type="text"
							id="payBackCardNo" readonly="readonly" />
					</div>

				</div>

				<div class="raido-tab-refund-price">
					<label>可退金额: ￥<span id="remainMoney2">0.00</span></label>
				</div>

				<div class="easyui-window-footer">
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
        
		/*
		*==================最终退款页面===============
		*/
        //1、打开最终退款页面
        function finalBackMoneyToUser(args){
			var backMethod = "";
			if(args.backType == 0){
				backMethod="微信账号";
			}else if(args.backType ==1){
				backMethod = '支付宝账号';
			}else if(args.backType == 2){
				backMethod = '银行卡号';
			}
			
			$("#backCardOrderId").val(args.orderId);
			$("#backMethod").text(backMethod);
			$("#payBackCardNo").val(args.backMoneyCard);
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
        
        $(".radio-tab-head label").on({
            "click":function(){
                var _index = $(this).index(".radio-tab-head label");
                $(".radio-tab-content .raidp-tab-content-item").eq(_index).css("display","block").siblings().css("display","none")
            }
            
        })
	</script>

</body>
</html>