<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<script type="text/javascript">
	var checksubmitflg = false; 
	var checksubmit = function(){
		if(checksubmitflg==true){
			return false;
		}
		checksubmitflg=true;
		return true;
	};
	var submitForm = function($dialog, $grid, $pjq) {
		if(checksubmit()){
			var url=sy.contextPath + '/pickup/updateState/'+$("#idKey").val();
			$.post(url, function(result) {
				if (result.ok) {
					$pjq.messager.alert('提示', result.msgBody, 'info');
					$grid.datagrid('load');
					$dialog.dialog('destroy');
				} else {
					layer.alert(result.msgBody, {
						icon : 2,
						skin : 'layer-ext-moon' 
					});
				}
			}, 'json');
		}
	};
	var destroy = function($dialog, $grid) {
		$dialog.dialog('destroy');
	};
</script>
<style type="text/css">
 table th {
 	width: 100px;
 	height: 20px;
 }
 table tr {
 	height: 60xp;
 	margin-bottom: 5px;
 }
</style>
</head>
<body>
	<form method="post" class="form">
		<fieldset>
		<input name="id" id="idKey" type="hidden" value="${pk.id }" readonly="readonly" />
		<legend>接送机基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>姓名</th>
					<td>${pk.name }</td>
				</tr>
				<tr>
					<th>性别</th>
					<td>${pk.sex }</td>
				</tr>
				<tr>
					<th>VIP卡号</th>
					<td>${pk.card_no }</td>
				</tr>
				<tr>
					<th>号码</th>
					<td>${pk.customer_phone }</td>
				</tr>
				<tr>
					<th>机场</th>
					<td>${pk.airport_name }</td>
				</tr>
				<tr>
					<th>航站楼</th>
					<td>${pk.flight_lou }</td>
				</tr>
				<tr>
					<th>航班号</th>
					<td>${pk.flight_num }</td>
				</tr>
				<tr>
					<th>航班日期</th>
					<td>${pk.flight_date }</td>
				</tr>
				<tr>
					<th>出发时间</th>
					<td>${pk.gooff_date }</td>
				</tr>
				<tr>
					<th>同行人数</th>
					<td>${pk.number_peers }</td>
				</tr>
				<tr>
					<c:if test="${pk.pickup_type==1 }">
						<th>出发地址</th>
						<td>(接机)${pk.pname }${pk.sname }${pk.cname }</td>
					</c:if>
					<c:if test="${pk.pickup_type==2 }">
						<th>到达地址</th>
						<td>(送机)${pk.pname }${pk.sname }${pk.cname }</td>
					</c:if>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>