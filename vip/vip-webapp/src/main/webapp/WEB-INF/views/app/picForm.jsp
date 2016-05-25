<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/WEB-INF/include/inc.jsp"></jsp:include>
<script type="text/javascript">
	var uploader;//上传对象
	var submitNow = function($dialog, $grid, $pjq) {
		var url;
			url = sy.contextPath + '/app/save';
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
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			if (uploader.files.length > 0) {
				uploader.start();
				uploader.bind('StateChanged', function(uploader) {// 在所有的文件上传完毕时，提交表单
					if (uploader.files.length === (uploader.total.uploaded + uploader.total.failed)) {
						submitNow($dialog, $grid, $pjq);
					}
				});
			} else {
				submitNow($dialog, $grid, $pjq);
			}
		}
	};
	$(function() {
		uploader = new plupload.Uploader({//上传插件定义
			browse_button : 'pickfiles',//选择文件的按钮
			container : 'container',//文件上传容器
			runtimes : 'html5,flash',//设置运行环境，会按设置的顺序，可以选择的值有html5,gears,flash,silverlight,browserplus,html4
			//flash_swf_url : sy.contextPath + '/jslib/plupload_1_5_7/plupload/js/plupload.flash.swf',// Flash环境路径设置
			url : sy.contextPath + '/plupload?fileFolder=/userPhoto',//上传文件路径
			max_file_size : '5mb',//100b, 10kb, 10mb, 1gb
			chunk_size : '10mb',//分块大小，小于这个大小的不分块
			unique_names : true,//生成唯一文件名
			// 如果可能的话，压缩图片大小
			/*resize : {
				width : 320,
				height : 240,
				quality : 90
			},*/
			// 指定要浏览的文件类型
			filters : [ {
				title : '图片文件',
				extensions : 'jpg,gif,png'
			} ]
		});
		uploader.bind('Init', function(up, params) {//初始化时
			//$('#filelist').html("<div>当前运行环境: " + params.runtime + "</div>");
			$('#filelist').html("");
		});
		uploader.bind('BeforeUpload', function(uploader, file) {//上传之前
			if (uploader.files.length > 1) {
				parent.$.messager.alert('提示', '只允许选择一张照片！', 'error');
				uploader.stop();
				return;
			}
			$('.ext-icon-cross').hide();
		});
		uploader.bind('FilesAdded', function(up, files) {//选择文件后
			$.each(files, function(i, file) {
				$('#filelist').append('<div id="' + file.id + '">' + file.name + '(' + plupload.formatSize(file.size) + ')<strong></strong>' + '<span onclick="uploader.removeFile(uploader.getFile($(this).parent().attr(\'id\')));$(this).parent().remove();" style="cursor:pointer;" class="ext-icon-cross" title="删除">&nbsp;&nbsp;&nbsp;&nbsp;</span></div>');
			});
			up.refresh();
		});
		uploader.bind('UploadProgress', function(up, file) {//上传进度改变
			var msg;
			if (file.percent == 100) {
				msg = '99';//因为某些大文件上传到服务器需要合并的过程，所以强制客户看到99%，等后台合并完成...
			} else {
				msg = file.percent;
			}
			$('#' + file.id + '>strong').html(msg + '%');

			parent.sy.progressBar({//显示文件上传滚动条
				title : '文件上传中...',
				value : msg
			});
		});
		uploader.bind('Error', function(up, err) {//出现错误
			$('#filelist').append("<div>错误代码: " + err.code + ", 描述信息: " + err.message + (err.file ? ", 文件名称: " + err.file.name : "") + "</div>");
			up.refresh();
		});
		uploader.bind('FileUploaded', function(up, file, info) {//上传完毕
			var response = $.parseJSON(info.response);
			if (response.status) {
				$('#' + file.id + '>strong').html("100%");
				//console.info(response.fileUrl);
				//console.info(file.name);
				//$('#f1').append('<input type="hidden" name="fileUrl" value="'+response.fileUrl+'"/>');
				//$('#f1').append('<input type="hidden" name="fileName" value="'+file.name+'"/><br/>');
				$(':input[name="pictureUrl"]').val(response.fileUrl);
				$("#pictureUrl").attr('src',sy.staticServer +response.fileUrl);
			}
		});
		uploader.init();

	});
</script>
</head>
<body>
	<form method="post" class="form">
		<fieldset><input name="pictureId" type="hidden" value="${appPicture.pictureId }" readonly="readonly" />
			<legend>用户基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>排序</th>
					<td><input class="easyui-numberspinner" name="priority"   required="required"  value="${appPicture.priority } data-options="increment:1,min:1,editable:false" style="width:120px;" ></td>
				</tr>
				<tr>
					<th>类型</th>
					<td><select class="easyui-combobox" name=imagetype data-options="panelHeight:'auto',editable:false" style="width: 155px;">
							<option value="0" <c:if test="${appPicture.imagetype==0 }">selected="selected"</c:if> >业务APP轮播图</option>
							<option value="1" <c:if test="${appPicture.imagetype==1 }">selected="selected"</c:if> >用户APP轮播图</option>
							<option value="2" <c:if test="${appPicture.imagetype==2 }">selected="selected"</c:if> >用户APP合作按钮</option>
					</select></td>
				</tr>
				<tr>
					<th>描述</th>
					<td><input name="descr" value="${appPicture.descr }" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>链接地址</th>
					<td><input name="clickUrl" value="${appPicture.clickUrl }" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>照片上传</th>
					<td><div id="container">
							<a id="pickfiles" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom'">选择文件</a>
							<div id="filelist">您的浏览器没有安装Flash插件，或不支持HTML5！</div>
						</div></td>
				</tr>
				<tr>
					<th></th>
					<td><input name="pictureUrl" value="${appPicture.pictureUrl }" readonly="readonly" style="display: none;" /> <img id="pictureUrl" src="" style="width: 200px; height: 200px;display: none;"></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>