<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${ctx }/style/site.css" type="text/css">
<script src="${ctx }/jslib/vue.min.js" type="text/javascript" charset="utf-8"></script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="clerk-wrap">
		<div id="clerk-container">
				<input name="id" value="${id }" type="hidden">
				<div class="clerk-container-item">
					<label>职位：</label><input type="text" v-model="job" value="{{job}}">
				</div>
				<div class="clerk-container-item">
					<label>姓名：</label><input type="text" v-model="username" value="{{username}}">
				</div>

				<div class="clerk-container-item">
					<label>工号：</label><input type="text" v-model="job_number" value="{{job_number}}">
				</div>


				<div class="clerk-container-item">
					<label>性别：</label> 
					<select name="" id="" v-model="gender">
						<option value="male">男</option>
						<option value="female">女</option>
					</select>
				</div>

				<div class="clerk-container-item">
					<label>手机号码：</label><input type="text" v-model="user_phone" value="{{user_phone}}">
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

				<div class="clerk-container-item">
					<label>APP登陆密码：</label>
					<button class="btn" id="app-login-passwd">初始化</button>
				</div>
		</div>

		<div id="clerk-footer">
			<button class="btn btn-save" @click="onRefund">保存</button>
			<button class="btn btn-cancel">取消</button>
		</div>
	</div>
</body>
<script>

$(function(){

	var _air_port_data;

	var App = new Vue({
		el:"#clerk-wrap",
		data: {
			job:"",
			username:"",
			job_number:"",
			gender:"male",
			user_phone:"",
			status:"active",
			toggle:true,
			air_port_data : _air_port_data
		},

		methods:{
			onRefund:function(){
				alert('fuck');

				console.log(JSON.stringify(App.$data));
				// $.post('./ari-port-data.json',JSON.stringify(App.$data),function(_d){
				// 	alert("success")
				// })
				$dialog.dialog('destroy');
			},

			onCountry:function(){
				var _this = this;
				this.air_port_data.forEach(function(_d){
					_d.checked = _this.toggle;
				})
			}
		}
	})


	$.get(sy.contextPath +'/airport/datePermission/data',function(data){
			App.air_port_data = data;
	})

})
var submitNow = function($dialog, $grid, $pjq) {
		var url;
		if ($(':input["id"]').val()!= '') {
			url = sy.contextPath + '/datapermission/update';
		} else {
			url = sy.contextPath + '/datapermission/save';
		}
		$.post(url, sy.serializeObject($('form')), function(result) {
			parent.sy.progressBar('close');//关闭上传进度条

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
	};

</script>
</html>