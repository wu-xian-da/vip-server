<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<script type="text/javascript">
</script>
</head>
<body>
	<form action="">
		<table>
			<tr>
				<td>vip室名称</td>
				<td><input type="text" name="viproomName"/></td>
			</tr>
			<tr>
				<td>场站名称</td>
				<td><select name="airportId">
						<option value="0">请选择场站</option>
						<option value="1">北京场站</option>
						<option value="2">天津场站</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>vip室信息编辑</td>
				<td><textarea cols="20" rows="50"></textarea></td>
			</tr>
			<tr>
				<td><input type="submit" value="保存"/></td>
				<td><input type="reset" value="取消"/></td>
			</tr>
		</table>
	</form>
</body>
</html>