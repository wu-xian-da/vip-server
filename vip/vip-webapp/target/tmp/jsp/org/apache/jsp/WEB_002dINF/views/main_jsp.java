package org.apache.jsp.WEB_002dINF.views;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class main_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(1);
    _jspx_dependants.add("/WEB-INF/include/taglib.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_set_var_value_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_c_set_var_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_c_set_var_value_nobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      if (_jspx_meth_c_set_0(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>掌慧纵盈</title>\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/include/inc.jsp", out, false);
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tvar mainMenu;\r\n");
      out.write("\tvar mainTabs;\r\n");
      out.write("\r\n");
      out.write("\t$(function() {\r\n");
      out.write("\r\n");
      out.write("\t\tvar loginFun = function() {\r\n");
      out.write("\t\t\tif ($('#loginDialog form').form('validate')) {\r\n");
      out.write("\t\t\t\t$('#loginBtn').linkbutton('disable');\r\n");
      out.write("\t\t\t\t$.post(sy.contextPath + '/base/syuser!doNotNeedSessionAndSecurity_login.sy', $('#loginDialog form').serialize(), function(result) {\r\n");
      out.write("\t\t\t\t\tif (result.success) {\r\n");
      out.write("\t\t\t\t\t\t$('#loginDialog').dialog('close');\r\n");
      out.write("\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t$.messager.alert('提示', result.msg, 'error', function() {\r\n");
      out.write("\t\t\t\t\t\t\t$('#loginDialog form :input:eq(1)').focus();\r\n");
      out.write("\t\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t$('#loginBtn').linkbutton('enable');\r\n");
      out.write("\t\t\t\t}, 'json');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t};\r\n");
      out.write("\t\t$('#loginDialog').show().dialog({\r\n");
      out.write("\t\t\tmodal : true,\r\n");
      out.write("\t\t\tclosable : false,\r\n");
      out.write("\t\t\ticonCls : 'ext-icon-lock_open',\r\n");
      out.write("\t\t\tbuttons : [ {\r\n");
      out.write("\t\t\t\tid : 'loginBtn',\r\n");
      out.write("\t\t\t\ttext : '登录',\r\n");
      out.write("\t\t\t\thandler : function() {\r\n");
      out.write("\t\t\t\t\tloginFun();\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t} ],\r\n");
      out.write("\t\t\tonOpen : function() {\r\n");
      out.write("\t\t\t\t$('#loginDialog form :input[name=\"data.pwd\"]').val('');\r\n");
      out.write("\t\t\t\t$('form :input').keyup(function(event) {\r\n");
      out.write("\t\t\t\t\tif (event.keyCode == 13) {\r\n");
      out.write("\t\t\t\t\t\tloginFun();\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}).dialog('close');\r\n");
      out.write("\r\n");
      out.write("\t\t$('#passwordDialog').show().dialog({\r\n");
      out.write("\t\t\tmodal : true,\r\n");
      out.write("\t\t\tclosable : true,\r\n");
      out.write("\t\t\ticonCls : 'ext-icon-lock_edit',\r\n");
      out.write("\t\t\tbuttons : [ {\r\n");
      out.write("\t\t\t\ttext : '修改',\r\n");
      out.write("\t\t\t\thandler : function() {\r\n");
      out.write("\t\t\t\t\tif ($('#passwordDialog form').form('validate')) {\r\n");
      out.write("\t\t\t\t\t\t$.post(sy.contextPath + '/base/syuser!doNotNeedSecurity_updateCurrentPwd.sy', {\r\n");
      out.write("\t\t\t\t\t\t\t'data.pwd' : $('#pwd').val()\r\n");
      out.write("\t\t\t\t\t\t}, function(result) {\r\n");
      out.write("\t\t\t\t\t\t\tif (result.success) {\r\n");
      out.write("\t\t\t\t\t\t\t\t$.messager.alert('提示', '密码修改成功！', 'info');\r\n");
      out.write("\t\t\t\t\t\t\t\t$('#passwordDialog').dialog('close');\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t}, 'json');\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t} ],\r\n");
      out.write("\t\t\tonOpen : function() {\r\n");
      out.write("\t\t\t\t$('#passwordDialog form :input').val('');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}).dialog('close');\r\n");
      out.write("\r\n");
      out.write("\t\tmainMenu = $('#mainMenu').tree({\r\n");
      out.write("\t\t\turl : sy.contextPath + '/resource/menus',\r\n");
      out.write("\t\t\tparentField : 'pid',\r\n");
      out.write("\t\t\tonClick : function(node) {\r\n");
      out.write("\t\t\t\tif (node.attributes.url) {\r\n");
      out.write("\t\t\t\t\tvar src = sy.contextPath + node.attributes.url;\r\n");
      out.write("\t\t\t\t\tif (!sy.startWith(node.attributes.url, '/')) {\r\n");
      out.write("\t\t\t\t\t\tsrc = node.attributes.url;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\tif (node.attributes.target && node.attributes.target.length > 0) {\r\n");
      out.write("\t\t\t\t\t\twindow.open(src, node.attributes.target);\r\n");
      out.write("\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\tvar tabs = $('#mainTabs');\r\n");
      out.write("\t\t\t\t\t\tvar opts = {\r\n");
      out.write("\t\t\t\t\t\t\ttitle : node.text,\r\n");
      out.write("\t\t\t\t\t\t\tclosable : true,\r\n");
      out.write("\t\t\t\t\t\t\ticonCls : node.iconCls,\r\n");
      out.write("\t\t\t\t\t\t\tcontent : sy.formatString('<iframe src=\"{0}\" allowTransparency=\"true\" style=\"border:0;width:100%;height:99%;\" frameBorder=\"0\"></iframe>', src),\r\n");
      out.write("\t\t\t\t\t\t\tborder : false,\r\n");
      out.write("\t\t\t\t\t\t\tfit : true\r\n");
      out.write("\t\t\t\t\t\t};\r\n");
      out.write("\t\t\t\t\t\tif (tabs.tabs('exists', opts.title)) {\r\n");
      out.write("\t\t\t\t\t\t\ttabs.tabs('select', opts.title);\r\n");
      out.write("\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\ttabs.tabs('add', opts);\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t$('#mainLayout').layout('panel', 'center').panel({\r\n");
      out.write("\t\t\tonResize : function(width, height) {\r\n");
      out.write("\t\t\t\tsy.setIframeHeight('centerIframe', $('#mainLayout').layout('panel', 'center').panel('options').height - 5);\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\tmainTabs = $('#mainTabs').tabs({\r\n");
      out.write("\t\t\tfit : true,\r\n");
      out.write("\t\t\tborder : false,\r\n");
      out.write("\t\t\ttools : [ {\r\n");
      out.write("\t\t\t\ticonCls : 'ext-icon-arrow_up',\r\n");
      out.write("\t\t\t\thandler : function() {\r\n");
      out.write("\t\t\t\t\tmainTabs.tabs({\r\n");
      out.write("\t\t\t\t\t\ttabPosition : 'top'\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}, {\r\n");
      out.write("\t\t\t\ticonCls : 'ext-icon-arrow_left',\r\n");
      out.write("\t\t\t\thandler : function() {\r\n");
      out.write("\t\t\t\t\tmainTabs.tabs({\r\n");
      out.write("\t\t\t\t\t\ttabPosition : 'left'\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}, {\r\n");
      out.write("\t\t\t\ticonCls : 'ext-icon-arrow_down',\r\n");
      out.write("\t\t\t\thandler : function() {\r\n");
      out.write("\t\t\t\t\tmainTabs.tabs({\r\n");
      out.write("\t\t\t\t\t\ttabPosition : 'bottom'\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}, {\r\n");
      out.write("\t\t\t\ticonCls : 'ext-icon-arrow_right',\r\n");
      out.write("\t\t\t\thandler : function() {\r\n");
      out.write("\t\t\t\t\tmainTabs.tabs({\r\n");
      out.write("\t\t\t\t\t\ttabPosition : 'right'\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}, {\r\n");
      out.write("\t\t\t\ttext : '刷新',\r\n");
      out.write("\t\t\t\ticonCls : 'ext-icon-arrow_refresh',\r\n");
      out.write("\t\t\t\thandler : function() {\r\n");
      out.write("\t\t\t\t\tvar panel = mainTabs.tabs('getSelected').panel('panel');\r\n");
      out.write("\t\t\t\t\tvar frame = panel.find('iframe');\r\n");
      out.write("\t\t\t\t\ttry {\r\n");
      out.write("\t\t\t\t\t\tif (frame.length > 0) {\r\n");
      out.write("\t\t\t\t\t\t\tfor (var i = 0; i < frame.length; i++) {\r\n");
      out.write("\t\t\t\t\t\t\t\tframe[i].contentWindow.document.write('');\r\n");
      out.write("\t\t\t\t\t\t\t\tframe[i].contentWindow.close();\r\n");
      out.write("\t\t\t\t\t\t\t\tframe[i].src = frame[i].src;\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\tif (navigator.userAgent.indexOf(\"MSIE\") > 0) {// IE特有回收内存方法\r\n");
      out.write("\t\t\t\t\t\t\t\ttry {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tCollectGarbage();\r\n");
      out.write("\t\t\t\t\t\t\t\t} catch (e) {\r\n");
      out.write("\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t} catch (e) {\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}, {\r\n");
      out.write("\t\t\t\ttext : '关闭',\r\n");
      out.write("\t\t\t\ticonCls : 'ext-icon-cross',\r\n");
      out.write("\t\t\t\thandler : function() {\r\n");
      out.write("\t\t\t\t\tvar index = mainTabs.tabs('getTabIndex', mainTabs.tabs('getSelected'));\r\n");
      out.write("\t\t\t\t\tvar tab = mainTabs.tabs('getTab', index);\r\n");
      out.write("\t\t\t\t\tif (tab.panel('options').closable) {\r\n");
      out.write("\t\t\t\t\t\tmainTabs.tabs('close', index);\r\n");
      out.write("\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t$.messager.alert('提示', '[' + tab.panel('options').title + ']不可以被关闭！', 'error');\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t} ]\r\n");
      out.write("\t\t});\r\n");
      out.write("\r\n");
      out.write("\t});\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body id=\"mainLayout\" class=\"easyui-layout\">\r\n");
      out.write("\t<div data-options=\"region:'north',href:'");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/layout/north.jsp'\" style=\"height: 70px; overflow: hidden;\" class=\"logo\"></div>\r\n");
      out.write("\t<div data-options=\"region:'west',href:'',split:true\" title=\"导航\" style=\"width: 200px; padding: 10px;\">\r\n");
      out.write("\t\t<ul id=\"mainMenu\"></ul>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div data-options=\"region:'center'\" style=\"overflow: hidden;\">\r\n");
      out.write("\t\t<div id=\"mainTabs\">\r\n");
      out.write("\t\t\t<div title=\"掌慧纵盈\" data-options=\"\">\r\n");
      out.write("\t\t\t\t<iframe src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/index.jsp\" allowTransparency=\"true\" style=\"border: 0; width: 100%; height: 99%;\" frameBorder=\"0\"></iframe>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div data-options=\"region:'south',href:'");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/layout/south.jsp',border:false\" style=\"height: 30px; overflow: hidden;\"></div>\r\n");
      out.write("\r\n");
      out.write("\t<div id=\"loginDialog\" title=\"解锁登录\" style=\"display: none;\">\r\n");
      out.write("\t\t<form method=\"post\" class=\"form\" onsubmit=\"return false;\">\r\n");
      out.write("\t\t\t<table class=\"table\">\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<th width=\"50\">登录名</th>\r\n");
      out.write("\t\t\t\t\t<td>helloKitty<input name=\"data.loginname\" readonly=\"readonly\" type=\"hidden\" value=\"helloKitty\" /></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<th>密码</th>\r\n");
      out.write("\t\t\t\t\t<td><input name=\"data.pwd\" type=\"password\" class=\"easyui-validatebox\" data-options=\"required:true\" /></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t<div id=\"passwordDialog\" title=\"修改密码\" style=\"display: none;\">\r\n");
      out.write("\t\t<form method=\"post\" class=\"form\" onsubmit=\"return false;\">\r\n");
      out.write("\t\t\t<table class=\"table\">\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<th>新密码</th>\r\n");
      out.write("\t\t\t\t\t<td><input id=\"pwd\" name=\"data.pwd\" type=\"password\" class=\"easyui-validatebox\" data-options=\"required:true\" /></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<th>重复密码</th>\r\n");
      out.write("\t\t\t\t\t<td><input type=\"password\" class=\"easyui-validatebox\" data-options=\"required:true,validType:'eqPwd[\\'#pwd\\']'\" /></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\t</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_set_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_set_0.setPageContext(_jspx_page_context);
    _jspx_th_c_set_0.setParent(null);
    _jspx_th_c_set_0.setVar("ctx");
    _jspx_th_c_set_0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    int _jspx_eval_c_set_0 = _jspx_th_c_set_0.doStartTag();
    if (_jspx_th_c_set_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_0);
      return true;
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_0);
    return false;
  }
}
