<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<script src="${pageContext.request.contextPath}/public/js/locale/easyui-lang-zh_CN.js"></script>

</head>
<body>
	 <div id="order-container">
        <div id="order-container-calac">
            <label>日期：</label>
            <input class="easyui-datebox" id="startTime" data-options="formatter:myformatter,parser:myparser" style="width:180px;height:26px">

            <label>-</label>
            <input class="easyui-datebox" id="endTime" data-options="formatter:myformatter,parser:myparser" style="width:180px;height:26px">

        </div>

        <div id="order-condition-box">
            <div class="order-condition-item">
                <select id="airportIdSelect">
                    <option value="">全部机场</option>
                    
                </select>
            </div>

            <div class="order-condition-item">
                <select id="orderStateSelect">
                    <option value="5">全部订单状态</option>
                    <option value="0">未支付</option>
                    <option value="1">已支付</option>
                    <option value="2">正在审核</option>
                    <option value="3">审核通过</option>
                    <option value="4">已退款</option>
                </select>
            </div>

            <div class="order-condition-item">
                <select id="invoiceSelect">
                    <option value="3">全部发票状态</option>
                    <option value="0">未开</option>
                    <option value="1">已开</option>
                  
                </select>
            </div>

            <div class="order-condition-item">
                <input id="phoneOrUserName" type="text" placeholder="用户手机号码/姓名">
                <button id="searchBt">检索</button>
            </div>
        </div>
        
        <table id="tt" title="" style="width:1120px;height:600px" 
                data-options="singleSelect:true,collapsible:true,
                                url:'orderList',
                                method:'get',
                                remoteSort:false,
                                multiSort:true"
                                pagination="true">
            <thead>
                <tr>
                <th data-options="align:'center', field:'orderId',width:100">订单编号</th>
                <th data-options="align:'center', field:'orderTime',width:150">订单日期</th>
                
                <th data-options="align:'center', field:'airportName',width:150">场站</th>
                <th data-options="align:'center', field:'agentName',width:100">业务员</th>
                
                <th data-options="align:'center', field:'customerName',width:100">用户名称</th>
                <th data-options="align:'center', field:'customerPhone',width:100">用户手机</th>
                
                <th data-options="align:'center', field:'invoiceFlagName',width:100">发票</th>
                <th data-options="align:'center', field:'orderStateName',width:100">状态</th>
                <th data-options="align:'center', field:'operation',width:210">操作</th>
                </tr>
            </thead>

        </table>
    </div>

    <!--用户账号信息录入 -->
	<div id="w" class="easyui-window" title="退款" 
            data-options = "modal:'true',closed:'true'" 
            style="width:500px;height:260px;padding:10px;">
       	<div class="easy-window-item">
            <div class="easy-window-radio-tab">
                <div class="radio-tab-head">
                    <label><input type="radio" name="card-radio" checked id="0">微信转账</label>
                    <label><input type="radio" name="card-radio" id="1">支付宝转账</label>
                    <label><input type="radio" name="card-radio" id="2">银行卡转账</label>
                </div>

                <div class="radio-tab-content">
                    <div class="raidp-tab-content-item" style="display:block">
                    	<input type="hidden" value="" id="hideOrderId"/>
                        <label>输入微信号:</label><input type="text" id="backCardNo0">
                    </div>

                    <div class="raidp-tab-content-item">
                        <label>输入支付宝账号:</label><input type="text" id="backCardNo1">
                    </div>

                    <div class="raidp-tab-content-item">
                        <label>输入银行卡号:</label><input type="text" id="backCardNo2">
                    </div>
                </div>

                <div class="raido-tab-refund-price">
                    <label>可退金额: ￥<span id="remainMoney">0.00</span></label>
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
            data-options = "modal:'true',closed:'true'" 
            style="width:500px;height:260px;padding:10px;">
       	<div class="easy-window-item">
            <div class="easy-window-radio-tab">
                <div class="radio-tab-content">
                    <div class="raidp-tab-content-item" style="display:block">
                    	<input type="hidden" value="" id="backCardOrderId"/>
                        <label id="backMethod">输入微信号:</label><input type="text" id="payBackCardNo" readonly="readonly"/>
                    </div>

                </div>

                <div class="raido-tab-refund-price">
                    <label>可退金额: ￥<span id="remainMoney2">0.00</span></label>
                </div>

                <div class="easyui-window-footer" style="padding-left:32px">
                	<shiro:hasPermission name="system:order:refundMoney">  
                  		  <button id="finalBackMoneyToUserBt">退款确认</button>
                    </shiro:hasPermission>
                    <button id="refundCloseWindow">返回</button>
                </div>
            </div>
        </div>            
        
    </div>


    <script type="text/javascript">
        $(function(){
            $('#tt').datagrid();
            //初始化机场下拉列表
            var url = "returnAirPortList";
            $.get(url,function(_d){
            	if(_d.result ==1){
            		var airport = _d.airportList;
            		for(var index =0 ;index < airport.length;index ++){
            			$("#airportIdSelect").append(" <option value='"+airport[index].id+"'>"+airport[index].name+"</option>");
            		}
            		
            	}
            })
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
        
        
        /*
        *===================业务人员审核页面，录入用户信息=============
        */
		//1、审核通过时，输入用户的账户信息（已完成）
        function onRefund(args){
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
			var remainMoney = $("#remainMoney").text();
			var payMethod = $('input:radio[name="card-radio"]:checked').attr("id");
			var orderId = $("#hideOrderId").val();
			//sconsole.log(payMethod);s
			if(payMethod==0){
				backCardNo = $("#backCardNo0").val();
			}else if(payMethod == 1){
				backCardNo = $("#backCardNo1").val();
			}else if(payMethod ==2){
				backCardNo = $("#backCardNo2").val();
			}
			var url = "onRefund?orderId="+orderId+"&backCardNo="+backCardNo+"&remainMoney="+remainMoney+"&payMethod="+payMethod+"&opr="+3;
			$.get(url,function(_d){
				if(_d.result == 1){
					/* $(elem).after($(_d.data));
                    $(elem).parent().parent().prev().find("div").text(_d.orderStateName);
                  	$(elem).remove(); */
					$("#w").window('close');
					window.location.reload();
				}
			})
			
			
		})
        
		
		
       /*
       	*==================退单申请 已完成===============
       	*/
        function onRefundApplication(args,elem){
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
        	//手机号后置用户姓名
        	var phoneOrUserName = $("#phoneOrUserName").val();
        	
        	var url = "orderList?startTime="+startTime+"&endTime="+endTime+"&airportId="+airportId+"&orderState="+orderState+
        			"&invoiceState="+invoiceState+"&phoneOrUserName="+phoneOrUserName;
        	$('#tt').datagrid({url:url});
        	
        })
	</script>
	
</body>
</html>