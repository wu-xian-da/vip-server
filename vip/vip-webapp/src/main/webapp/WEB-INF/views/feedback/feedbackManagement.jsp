<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="height:99%; margin:0;">
<head>
<title></title>


<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/theme/default/easyui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/theme/icon.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/site.css">

<script src="${pageContext.request.contextPath}/public/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/js/jquery.easyui.min.js"></script>
<script src="${pageContext.request.contextPath}/public/js/plugins/datagrid-scrollview.js"></script>
<script src="${pageContext.request.contextPath}/public/js/locale/easyui-lang-zh_CN.js"></script>

</head>
<body style="height:99%; margin:0;">
	<div id="order-container" style="height:99%; margin:0;">
		<!--搜索条件框  -->
		<div id="order-condition-box" style="height:10%; padding:0;">
			<div style="padding-left:20px; padding-top:20px;">
				<div class="order-condition-item">
					<select name="" id="feedbackStateSelect">
						<option value="2">全部处理状态</option>
						<option value="0">未处理</option>
						<option value="1">已处理</option>
					</select>
				</div>
				<div class="order-condition-item" style="text-align: left">
					<button id="searchBt" style="height: 20px">查询</button>
				</div>
			</div>
			
		</div>
	
		<div style="clear:both; height:93%;" data-options="region:'center',fit:true,border:false">
		<style type="text/css">
			.panel-body{ background:none;}
			#order-container {
				    width: auto;
			}

		</style>
			<table id="tt" title="" style="height:99%"
				data-options="
							singleSelect:true,
							collapsible:true,
	                                url:'feedbackList',
	                                method:'get',
	                                fit:true,border:false,
	                                remoteSort:false,
	                                multiSort:true"
				pagination="true">
				<thead>
					<tr>
						<th data-options="align:'center', field:'id',width:100">序号</th>
						<th data-options="align:'center', field:'customerName',width:120">用户名</th>
						<th data-options="align:'center', field:'customerPhone',width:100">用户手机</th>
						<th data-options="align:'center', field:'feedbackTime',width:150">反馈日期</th>
						<th data-options="align:'center', field:'feedbackStateName',width:150">是否已处理</th>
						<th data-options="align:'center', field:'opr',width:210">操作</th>
					</tr>
				</thead>
	
			</table>
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
        	//处理状态
        	var feedbackState = $("#feedbackStateSelect option:selected").val();
        	var url = "feedbackList?feedbackState="+feedbackState;
        	$('#tt').datagrid({url:url});
        	
        })
        
        //删除用户反馈信息
        function delFeedBackInfo(id){
        	$.messager.confirm('提示框', '确定要删除吗?',function(data){
        		if(data){
        			url = "delFeedBackInfo?id="+id
                	$.get(url,function(_d){
                		if(_d.result == 1){
                			$('#tt').datagrid('reload');
                		}
                	})
        		}
        		
        	})
         }
		
	</script>

</body>
</html>