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
	<div id="clerk-wrap" style="margin: auto;">
		<div id="clerk-container">
				<input name="id" value="${id }" type="hidden">

				<div class="clerk-container-item">
					<label>责任区域：</label>
					<div class="clerk-area">
						<label><input type="checkbox" @click="onCountry" v-model="toggle" v-bind:true-value=false v-bind:false-value=true>全国</label>
						<div>
							<label v-for="item in air_port_data"><input type="checkbox" v-model="item.checked" checked="{{ item.checked }}">{{ item.name }}</label>
						</div>
					</div>
				</div>
		</div>

		<div id="clerk-footer" style="margin: auto;">
			<button class="btn btn-save" @click="onRefund">保存</button>
			<button class="btn btn-cancel" id="cancle" onclick="cancle();">取消</button>
		</div>
	</div>
</body>
<script>

$(function(){

	var _air_port_data;

	var App = new Vue({
		el:"#clerk-wrap",
		data: {
			status:"active",
			toggle:true,
			air_port_data : _air_port_data
		},

		methods:{
			onRefund:function(){
				$.post(sy.contextPath + '/user/datapermission/update',JSON.stringify(App.$data),function(_d){
					var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
					parent.layer.close(index); //再执行关闭   
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


	$.get(sy.contextPath +'/airport/datePermission/data',function(data){
			App.air_port_data = data;
	})

})

var cancle = function(){
	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	parent.layer.close(index); //再执行关闭   
}

</script>
</html>