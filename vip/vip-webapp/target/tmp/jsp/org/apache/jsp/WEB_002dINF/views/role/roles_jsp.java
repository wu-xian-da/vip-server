package org.apache.jsp.WEB_002dINF.views.role;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class roles_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html; charset=utf-8");
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
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\r\n");
      out.write("\"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("    <head>\r\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("        <title>New Web Project</title>\r\n");
      out.write("    ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/include/inc.jsp", out, false);
      out.write("\r\n");
      out.write("    </head>\r\n");
      out.write("<body class=\"easyui-layout\" data-options=\"fit:true,border:false\">\r\n");
      out.write("\t<div id=\"toolbar\" style=\"display: none;\">\r\n");
      out.write("\t\t<table>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td><a href=\"javascript:void(0);\" class=\"easyui-linkbutton\" data-options=\"iconCls:'ext-icon-note_add',plain:true\" onclick=\"addFun();\">添加</a></td>\r\n");
      out.write("\t\t\t\t<td><div class=\"datagrid-btn-separator\"></div></td>\r\n");
      out.write("\t\t\t\t<td><input id=\"searchBox\" class=\"easyui-searchbox\" style=\"width: 150px\" data-options=\"searcher:function(value,name){grid.datagrid('load',{'name':value});},prompt:'搜索角色名称'\"></input></td>\r\n");
      out.write("\t\t\t\t<td><a href=\"javascript:void(0);\" class=\"easyui-linkbutton\" data-options=\"iconCls:'ext-icon-zoom_out',plain:true\" onclick=\"$('#searchBox').searchbox('setValue','');grid.datagrid('load',{});\">清空查询</a></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div data-options=\"region:'center',fit:true,border:false\">\r\n");
      out.write("\t\t<table id=\"grid\" data-options=\"fit:true,border:false\"></table>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\tvar grid;\r\n");
      out.write("\tvar addFun = function() {\r\n");
      out.write("\t\tvar dialog = parent.sy.modalDialog({\r\n");
      out.write("\t\t\ttitle : '添加角色信息',\r\n");
      out.write("\t\t\turl : sy.contextPath + '/role/form',\r\n");
      out.write("\t\t\tbuttons : [ {\r\n");
      out.write("\t\t\t\ttext : '添加',\r\n");
      out.write("\t\t\t\thandler : function() {\r\n");
      out.write("\t\t\t\t\tdialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t} ]\r\n");
      out.write("\t\t});\r\n");
      out.write("\t};\r\n");
      out.write("\tvar showFun = function(id) {\r\n");
      out.write("\t\tvar dialog = parent.sy.modalDialog({\r\n");
      out.write("\t\t\ttitle : '查看角色信息',\r\n");
      out.write("\t\t\turl : sy.contextPath + '/securityJsp/base/SyroleForm.jsp?id=' + id\r\n");
      out.write("\t\t});\r\n");
      out.write("\t};\r\n");
      out.write("\tvar editFun = function(id) {\r\n");
      out.write("\t\tvar dialog = parent.sy.modalDialog({\r\n");
      out.write("\t\t\ttitle : '编辑角色信息',\r\n");
      out.write("\t\t\turl : sy.contextPath + '/securityJsp/base/SyroleForm.jsp?id=' + id,\r\n");
      out.write("\t\t\tbuttons : [ {\r\n");
      out.write("\t\t\t\ttext : '编辑',\r\n");
      out.write("\t\t\t\thandler : function() {\r\n");
      out.write("\t\t\t\t\tdialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t} ]\r\n");
      out.write("\t\t});\r\n");
      out.write("\t};\r\n");
      out.write("\tvar removeFun = function(id) {\r\n");
      out.write("\t\tparent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {\r\n");
      out.write("\t\t\tif (r) {\r\n");
      out.write("\t\t\t\t$.post(sy.contextPath + '/base/syrole!delete.sy', {\r\n");
      out.write("\t\t\t\t\tid : id\r\n");
      out.write("\t\t\t\t}, function() {\r\n");
      out.write("\t\t\t\t\tgrid.datagrid('reload');\r\n");
      out.write("\t\t\t\t}, 'json');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t};\r\n");
      out.write("\tvar grantFun = function(id) {\r\n");
      out.write("\t\tvar dialog = parent.sy.modalDialog({\r\n");
      out.write("\t\t\ttitle : '角色授权',\r\n");
      out.write("\t\t\turl : sy.contextPath + '/role/grantForm?id=' + id,\r\n");
      out.write("\t\t\tbuttons : [ {\r\n");
      out.write("\t\t\t\ttext : '授权',\r\n");
      out.write("\t\t\t\thandler : function() {\r\n");
      out.write("\t\t\t\t\tdialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t} ]\r\n");
      out.write("\t\t});\r\n");
      out.write("\t};\r\n");
      out.write("\t$(function() {\r\n");
      out.write("\t\tgrid = $('#grid').datagrid({\r\n");
      out.write("\t\t\ttitle : '',\r\n");
      out.write("\t\t\turl : '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/role/list',\r\n");
      out.write("\t\t\tstriped : true,\r\n");
      out.write("\t\t\trownumbers : true,\r\n");
      out.write("\t\t\tpagination : true,\r\n");
      out.write("\t\t\tsingleSelect : true,\r\n");
      out.write("\t\t\tidField : 'id',\r\n");
      out.write("\t\t\tsortName : 'seq',\r\n");
      out.write("\t\t\tsortOrder : 'asc',\r\n");
      out.write("\t\t\tfrozenColumns : [ [ {\r\n");
      out.write("\t\t\t\twidth : '100',\r\n");
      out.write("\t\t\t\ttitle : '角色名称',\r\n");
      out.write("\t\t\t\tfield : 'name',\r\n");
      out.write("\t\t\t\tsortable : true\r\n");
      out.write("\t\t\t} ] ],\r\n");
      out.write("\t\t\tcolumns : [ [ {\r\n");
      out.write("\t\t\t\twidth : '300',\r\n");
      out.write("\t\t\t\ttitle : '角色描述',\r\n");
      out.write("\t\t\t\tfield : 'description'\r\n");
      out.write("\t\t\t}, {\r\n");
      out.write("\t\t\t\ttitle : '操作',\r\n");
      out.write("\t\t\t\tfield : 'action',\r\n");
      out.write("\t\t\t\twidth : '300',\r\n");
      out.write("\t\t\t\tformatter : function(value, row) {\r\n");
      out.write("\t\t\t\t\tvar str = '';\r\n");
      out.write("\t\t\t\t\t\tstr += sy.formatString('<img class=\"iconImg ext-icon-note\" title=\"查看\" onclick=\"showFun(\\'{0}\\');\"/>', row.id);\r\n");
      out.write("\t\t\t\t\t\tstr += sy.formatString('&nbsp;<img class=\"iconImg ext-icon-note_edit\" title=\"编辑\" onclick=\"editFun(\\'{0}\\');\"/>', row.id);\r\n");
      out.write("\t\t\t\t\t\tstr += sy.formatString('&nbsp;<img class=\"iconImg ext-icon-key\" title=\"授权\" onclick=\"grantFun(\\'{0}\\');\"/>', row.id);\r\n");
      out.write("\t\t\t\t\t\tstr += sy.formatString('&nbsp;<img class=\"iconImg ext-icon-note_delete\" title=\"删除\" onclick=\"removeFun(\\'{0}\\');\"/>', row.id);\r\n");
      out.write("\t\t\t\t\treturn str;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t} ] ],\r\n");
      out.write("\t\t\ttoolbar : '#toolbar',\r\n");
      out.write("\t\t\tonBeforeLoad : function(param) {\r\n");
      out.write("\t\t\t\tparent.$.messager.progress({\r\n");
      out.write("\t\t\t\t\ttext : '数据加载中....'\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t},\r\n");
      out.write("\t\t\tonLoadSuccess : function(data) {\r\n");
      out.write("\t\t\t\t$('.iconImg').attr('src', sy.pixel_0);\r\n");
      out.write("\t\t\t\tparent.$.messager.progress('close');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t});\r\n");
      out.write("</script>\r\n");
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
