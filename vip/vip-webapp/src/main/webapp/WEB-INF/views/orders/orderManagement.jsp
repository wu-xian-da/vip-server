<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <div id="order-container-calac">
            <label>日期：</label>
            <input class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" style="width:180px;height:26px">

            <label>-</label>
            <input class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" style="width:180px;height:26px">

        </div>

        <div id="order-condition-box">
            <div class="order-condition-item">
                <select name="" id="">
                    <option value="">选择机场</option>
                    <option value="">广州机场</option>
                    <option value="">虹桥机场</option>
                    <option value="">广州机场</option>
                    <option value="">虹桥机场</option>
                </select>
            </div>

            <div class="order-condition-item">
                <select name="" id="">
                    <option value="">选择定票状态</option>
                    <option value="">已支付</option>
                    <option value="">未支付</option>
                    <option value="">已审核</option>
                    <option value="">未审核</option>
                </select>
            </div>

            <div class="order-condition-item">
                <select name="" id="">
                    <option value="">选择发票状态</option>
                    <option value="">已开</option>
                    <option value="">未开</option>
                </select>
            </div>

            <div class="order-condition-item">
                <input type="text" placeholder="输入用户手机号码/姓名">
                <button>搜索</button>
            </div>
        </div>
        
        <table id="tt" title="" style="width:1070px;height:600px" 
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
                
                <th data-options="align:'center', field:'airportName',width:150">场站</th>
                <th data-options="align:'center', field:'agentName',width:100">业务员</th>
                
                <th data-options="align:'center', field:'customerName',width:100">用户名称</th>
                <th data-options="align:'center', field:'customerPhone',width:100">用户手机</th>
                
                <th data-options="align:'center', field:'invoiceFlag',width:100">发票</th>
                <th data-options="align:'center', field:'orderStateName',width:100">状态</th>
                <th data-options="align:'center', field:'operation',width:210">操作</th>
                </tr>
            </thead>

        </table>
    </div>

    


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
                    <button>取消</button>
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


        function onRefund(args){
        	$("#w").window('open')
        }
        
        function onRefundApplication(args, elem){
            $.get('./on-refund-application.json',function(_d){
                if(_d.data != null){
                    $(elem).after($(_d.data));
                    $(elem).remove();
                }
            })
        }

        function onSuccess(args){
            alert(args);

            (function(){
                alert("成功-回调操作")
            })()
        }

        function onError(args){
            alert(args);

            (function(){
                alert("失败-回调操作")
            })()
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