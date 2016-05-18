<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>


<link rel="stylesheet" href="public/css/theme/default/easyui.css">
<link rel="stylesheet" href="public/css/theme/icon.css">
<link rel="stylesheet" href="public/css/site.css">
<script src="public/js/jquery.min.js"></script>
<script src="public/js/jquery.easyui.min.js"></script>
<script src="public/js/plugins/datagrid-scrollview.js"></script>
</head>
<body>
	<table id="tt" title="" style="width:890px;height:300px" 
    		data-options="singleSelect:true,collapsible:true,
                			url:'orderList',
			                method:'get',
			                remoteSort:false,
			                multiSort:true"
			                pagination="true">
        <thead>
            <tr>
                <th data-options="align:'center', field:'orderId',width:100">订单编号</th>
                <th data-options="align:'center', field:'orderTime',width:100">日期</th>
                
                <th data-options="align:'center', field:'listprice',width:100">场站</th>
                <th data-options="align:'center', field:'unitcost',width:100">业务员</th>
                <th data-options="align:'center', field:'userphone',width:100">用户手机</th>
                
                <th data-options="align:'center', field:'invoiceFlag',width:50">发票</th>
                <th data-options="align:'center', field:'orderState',width:50">状态</th>
                <th data-options="align:'center', field:'operation',width:280">操作</th>
            </tr>
        </thead>

    </table>


		<div id="w" class="easyui-window" title="退款" 
            data-options = "modal:'true',closed:'true'" 
            style="width:500px;height:260px;padding:10px;">
       	<div class="easy-window-item">
            <div class="easy-window-radio-tab">
                <div class="radio-tab-head">
                    <label><input type="radio" name="card-radio" checked id="">微信转账</label>
                    <label><input type="radio" name="card-radio" id="">支付宝转账</label>
                    <label><input type="radio" name="card-radio" id="">银行卡转账</label>
                </div>

                <div class="radio-tab-content">
                    <div class="raidp-tab-content-item" style="display:block">
                        <label>输入微信号:</label><input type="text">
                    </div>

                    <div class="raidp-tab-content-item">
                        <label>输入支付宝账号:</label><input type="text">
                    </div>

                    <div class="raidp-tab-content-item">
                        <label>输入银行卡号:</label><input type="text">
                    </div>
                </div>

                <div class="raido-tab-refund-price">
                    <label>可退金额: ￥<span>111222</span></label>
                </div>

                <div class="easyui-window-footer">
                    <button>退款</button>
                    <button id="cancleButton">取消</button>
                </div>
            </div>
        </div>            
        
    </div>



    <script type="text/javascript">
        $(function(){
            $('#tt').datagrid();
        });

		$("#cancleButton").click(function(){
			$("#w").window('close')
		})

        function onRefund( args ){
        	$("#w").window('open')
        }
        

        $(".radio-tab-head label").on({
            "click":function(){
                var _index = $(this).index(".radio-tab-head label");
                $(".radio-tab-content .raidp-tab-content-item").eq(_index).css("display","block").siblings().css("display","none")
            }
            
        })









    </script>

	
</body>
</html>