<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" charset="utf-8">
	var logoutFun = function() {
		parent.$.messager.confirm('询问', '确定要退出？', function(r) {
			if (r) {
				 window.location.href=sy.contextPath + '/logout';
			}
		});
	};
	var showMyInfoFun = function() {
		var dialog = parent.sy.modalDialog({
			title : '我的信息', 
			url : sy.contextPath + '/securityJsp/userInfo.jsp'
		});
	};
</script>
<div style="position: absolute; right: 0px; bottom: 0px;">
	<span>北京掌慧纵盈欢迎你！</span>
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'ext-icon-rainbow'">更换皮肤</a>
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'ext-icon-cog'">控制面板</a> 
	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-door_out'" onclick="logoutFun();" style="">注销</a>
</div>
<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="sy.changeTheme('default');" title="default">默认</div>
	<div onclick="sy.changeTheme('gray');" title="gray">灰色</div>
	<div onclick="sy.changeTheme('metro');" title="metro">灰白色</div>
	<div onclick="sy.changeTheme('bootstrap');" title="bootstrap">土灰色</div>
	<div class="menu-sep"></div>
	<div onclick="sy.changeTheme('metro-blue');" title="metro-blue">蓝色</div>
	<div onclick="sy.changeTheme('metro-gray');" title="metro-gray">灰色</div>
	<div onclick="sy.changeTheme('metro-green');" title="metro-green">绿色</div>
	<div onclick="sy.changeTheme('metro-orange');" title="metro-orange">橘红色</div>
	<div onclick="sy.changeTheme('metro-red');" title="metro-red">粉红色</div>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'ext-icon-user_edit'" onclick="$('#passwordDialog').dialog('open');">修改密码</div>
</div>
</div>