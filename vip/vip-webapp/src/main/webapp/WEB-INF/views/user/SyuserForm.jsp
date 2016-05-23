<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${ctx }/style/site.css" type="text/css">
<script src="${ctx }/jslib/vue.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	$(function() {
		var _air_port_data;

		var App = new Vue({
			el:"#clerk-wrap",
			data: {
				name:"",
				id:"",
				loginName:"",
				status:"active",
				toggle:true,
				air_port_data : _air_port_data
			},

			methods:{
				onRefund:function(){
					var ids = "";
					for(var i=0;i<App.$data.air_port_data.length;i++){
						if(App.$data.air_port_data[i].checked){
							ids=ids+App.$data.air_port_data[i].id+",";
						}
					}
					console.log(JSON.stringify(App.$data));
					 $.post(sy.contextPath + '/airport/datapermission/update',{name:App.$data.name,loginName:App.$data.loginName,ids:ids,id:App.$data.id},function(_d){
						 alert('dddd');
						 if (_d.ok) {
								$grid.datagrid('load');
								var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
								parent.layer.close(index); //再执行关闭   
							} else {
								$pjq.messager.alert('提示', _d.msgBody, 'error');
							}
					})
				},

				onCountry:function(){
					var _this = this;
					this.air_port_data.forEach(function(_d){
						_d.checked = _this.toggle;
					})
				}
			}
		})


		$.get(sy.contextPath +'/airport/datePermission/data?id=${id}',function(data){
				App.air_port_data = data;
		})
	});
</script>
</head>
<body>
	<form method="post" class="form">
	
	<div id="clerk-wrap">
		<div id="clerk-container">
				<input type="hidden" v-model="id"  name="id" value="{{id}}">
				<div class="clerk-container-item">
					<label>姓名：</label><input type="text" v-model="name" name="name" value="{{name}}">
				</div>

				<div class="clerk-container-item">
					<label>登入姓名：</label><input type="text" v-model="loginName" name="loginName" value="{{loginName}}">
				</div>


				<div class="clerk-container-item">
					<label>责任区域：</label>
					<div class="clerk-area">
						<label><input type="checkbox" @click="onCountry" v-model="toggle" v-bind:true-value=false v-bind:false-value=true>全国</label>
						<div>
							<label v-for="item in air_port_data"><input type="checkbox" v-model="item.checked" checked="{{ item.checked }}">{{ item.name }}</label>
						</div>
					</div>
				</div>

				<div class="clerk-container-item clerk-container-item-2">
					<label>状态：</label>
					<label class="no-width"><input type="radio" v-model="status" value="active">在职</label>
					<label class="no-width"><input type="radio" v-model="status" value="dimission">离职</label>
				</div>
		</div>

		<div id="clerk-footer">
			<button class="btn btn-save" @click="onRefund">保存</button>
			<button class="btn btn-cancel">取消</button>
		</div>
	</div>
	</form>
</body>
</html>