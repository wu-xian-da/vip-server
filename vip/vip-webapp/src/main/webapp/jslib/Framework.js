/**
 * 弹出层
 * 
 * @author 李彬彬
 * 
 * @requires jQuery,layer
 */
var bbli = {
	data : {},
	fn : {}
}

jQuery.extend({
	customerFrame : function(options, obj) {
		var defaults = {
			title : '自定义弹出层',
			height : '800px',
			wight : '500px',
			maxmin:true,
			type : 2,
			callback : function() {
			}
		};
		defaults = $.extend(defaults, options);
		layer.open({
			type : defaults.type,
			title : defaults.title,
			shadeClose : true,
			shade : false,
			maxmin : defaults.maxmin, // 开启最大化最小化按钮
			area : [defaults.wight,defaults.height ],
			content : defaults.url,
			end : function() {
				defaults.callback;
			}
		});
	}
});
