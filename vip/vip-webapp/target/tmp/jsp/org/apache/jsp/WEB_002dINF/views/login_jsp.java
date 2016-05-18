package org.apache.jsp.WEB_002dINF.views;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title>登入</title>\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/include/inc.jsp", out, false);
      out.write("\r\n");
      out.write("<script language=\"JavaScript\">\r\n");
      out.write("function correctPNG()\r\n");
      out.write("{\r\n");
      out.write("    var arVersion = navigator.appVersion.split(\"MSIE\")\r\n");
      out.write("    var version = parseFloat(arVersion[1])\r\n");
      out.write("    if ((version >= 5.5) && (document.body.filters)) \r\n");
      out.write("    {\r\n");
      out.write("       for(var j=0; j<document.images.length; j++)\r\n");
      out.write("       {\r\n");
      out.write("          var img = document.images[j]\r\n");
      out.write("          var imgName = img.src.toUpperCase()\r\n");
      out.write("          if (imgName.substring(imgName.length-3, imgName.length) == \"PNG\")\r\n");
      out.write("          {\r\n");
      out.write("             var imgID = (img.id) ? \"id='\" + img.id + \"' \" : \"\"\r\n");
      out.write("             var imgClass = (img.className) ? \"class='\" + img.className + \"' \" : \"\"\r\n");
      out.write("             var imgTitle = (img.title) ? \"title='\" + img.title + \"' \" : \"title='\" + img.alt + \"' \"\r\n");
      out.write("             var imgStyle = \"display:inline-block;\" + img.style.cssText \r\n");
      out.write("             if (img.align == \"left\") imgStyle = \"float:left;\" + imgStyle\r\n");
      out.write("             if (img.align == \"right\") imgStyle = \"float:right;\" + imgStyle\r\n");
      out.write("             if (img.parentElement.href) imgStyle = \"cursor:hand;\" + imgStyle\r\n");
      out.write("             var strNewHTML = \"<span \" + imgID + imgClass + imgTitle\r\n");
      out.write("             + \" style=\\\"\" + \"width:\" + img.width + \"px; height:\" + img.height + \"px;\" + imgStyle + \";\"\r\n");
      out.write("             + \"filter:progid:DXImageTransform.Microsoft.AlphaImageLoader\"\r\n");
      out.write("             + \"(src=\\'\" + img.src + \"\\', sizingMethod='scale');\\\"></span>\" \r\n");
      out.write("             img.outerHTML = strNewHTML\r\n");
      out.write("             j = j-1\r\n");
      out.write("          }\r\n");
      out.write("       }\r\n");
      out.write("    }    \r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/style/login/skin.css\" rel=\"stylesheet\" type=\"text/css\">\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"correctPNG();\">\r\n");
      out.write("<form action=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/login\" method=\"POST\">\r\n");
      out.write("<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td height=\"42\" valign=\"top\"><table width=\"100%\" height=\"42\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"login_top_bg\">\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td width=\"1%\" height=\"21\">&nbsp;</td>\r\n");
      out.write("        <td height=\"42\">&nbsp;</td>\r\n");
      out.write("        <td width=\"17%\">&nbsp;</td>\r\n");
      out.write("      </tr>\r\n");
      out.write("    </table></td>\r\n");
      out.write("  </tr>\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td valign=\"top\"><table width=\"100%\" height=\"532\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"login_bg\">\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td width=\"49%\" align=\"right\"><table width=\"91%\" height=\"532\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"login_bg2\">\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td height=\"138\" valign=\"top\"><table width=\"89%\" height=\"427\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("                <tr>\r\n");
      out.write("                  <td height=\"149\">&nbsp;</td>\r\n");
      out.write("                </tr>\r\n");
      out.write("                <tr>\r\n");
      out.write("                  <td height=\"80\" align=\"right\" valign=\"top\"><img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/style/login/logo.png\" width=\"279\" height=\"68\"></td>\r\n");
      out.write("                </tr>\r\n");
      out.write("                <tr>\r\n");
      out.write("                  <td height=\"198\" align=\"right\" valign=\"top\"><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("                    <tr>\r\n");
      out.write("                      <td width=\"35%\">&nbsp;</td>\r\n");
      out.write("                      <td height=\"25\" colspan=\"2\" class=\"left_txt\"><p>1- 地区商家信息网门户站建立的首选方案...</p></td>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                    <tr>\r\n");
      out.write("                      <td>&nbsp;</td>\r\n");
      out.write("                      <td height=\"25\" colspan=\"2\" class=\"left_txt\"><p>2- 一站通式的整合方式，方便用户使用...</p></td>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                    <tr>\r\n");
      out.write("                      <td>&nbsp;</td>\r\n");
      out.write("                      <td height=\"25\" colspan=\"2\" class=\"left_txt\"><p>3- 强大的后台系统，管理内容易如反掌...</p></td>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                    <tr>\r\n");
      out.write("                      <td>&nbsp;</td>\r\n");
      out.write("                      <td width=\"30%\" height=\"40\"><img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/style/login/icon-demo.gif\" width=\"16\" height=\"16\"><a href=\"http://www.865171.cn\" target=\"_blank\" class=\"left_txt3\"> 使用说明</a> </td>\r\n");
      out.write("                      <td width=\"35%\"><img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/style/login/icon-login-seaver.gif\" width=\"16\" height=\"16\"><a href=\"http://www.865171.cn\" class=\"left_txt3\"> 在线客服</a></td>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                  </table></td>\r\n");
      out.write("                </tr>\r\n");
      out.write("              </table></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            \r\n");
      out.write("        </table></td>\r\n");
      out.write("        <td width=\"1%\" >&nbsp;</td>\r\n");
      out.write("        <td width=\"50%\" valign=\"bottom\"><table width=\"100%\" height=\"59\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"4%\">&nbsp;</td>\r\n");
      out.write("              <td width=\"96%\" height=\"38\"><span class=\"login_txt_bt\">掌慧VIP商旅后台管理</span></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("             <tr>\r\n");
      out.write("              <td width=\"4%\">&nbsp;</td>\r\n");
      out.write("              <td width=\"96%\" height=\"38\" style=\"color: red;\"><span>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${message }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("</span></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td>&nbsp;</td>\r\n");
      out.write("              <td height=\"21\"><table cellSpacing=\"0\" cellPadding=\"0\" width=\"100%\" border=\"0\" id=\"table211\" height=\"328\">\r\n");
      out.write("                  <tr>\r\n");
      out.write("                    <td height=\"164\" colspan=\"2\" align=\"middle\"><form name=\"myform\" action=\"index.html\" method=\"post\">\r\n");
      out.write("                        <table cellSpacing=\"0\" cellPadding=\"0\" width=\"100%\" border=\"0\" height=\"143\" id=\"table212\">\r\n");
      out.write("                          <tr>\r\n");
      out.write("                            <td width=\"13%\" height=\"38\" class=\"top_hui_text\"><span class=\"login_txt\">管理员：&nbsp;&nbsp; </span></td>\r\n");
      out.write("                            <td height=\"38\" colspan=\"2\" class=\"top_hui_text\"><input name=\"loginName\" class=\"editbox4\" value=\"\" size=\"20\">                            </td>\r\n");
      out.write("                          </tr>\r\n");
      out.write("                          <tr>\r\n");
      out.write("                            <td width=\"13%\" height=\"35\" class=\"top_hui_text\"><span class=\"login_txt\"> 密 码： &nbsp;&nbsp; </span></td>\r\n");
      out.write("                            <td height=\"35\" colspan=\"2\" class=\"top_hui_text\"><input class=\"editbox6\" type=\"password\" size=\"20\" name=\"password\" value=\"\">\r\n");
      out.write("                              <img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/style/login/luck.gif\" width=\"19\" height=\"18\"> </td>\r\n");
      out.write("                          </tr>\r\n");
      out.write("                          <tr>\r\n");
      out.write("                            <td width=\"13%\" height=\"35\" ><span class=\"login_txt\">验证码：</span></td>\r\n");
      out.write("                            <td height=\"35\" colspan=\"2\" class=\"top_hui_text\"><input class=wenbenkuang name=validateCode type=text value=\"\" maxLength=6 size=10>\r\n");
      out.write("                            <div class=\"yzm-box\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/validate/code\" style=\"cursor: pointer;\" onclick=\"changeImg(this);\" title=\"点击更换验证码\" />\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("                              </td>\r\n");
      out.write("                          </tr>\r\n");
      out.write("                          <tr>\r\n");
      out.write("                            <td height=\"35\" >&nbsp;</td>\r\n");
      out.write("                            <td width=\"20%\" height=\"35\" ><input name=\"Submit\" type=\"submit\" class=\"button\" id=\"Submit\" value=\"登 陆\"> </td>\r\n");
      out.write("                            <td width=\"67%\" class=\"top_hui_text\"><input name=\"cs\" type=\"button\" class=\"button\" id=\"cs\" value=\"取 消\" onClick=\"showConfirmMsg1()\"></td>\r\n");
      out.write("                          </tr>\r\n");
      out.write("                        </table>\r\n");
      out.write("                        <br>\r\n");
      out.write("                    </form></td>\r\n");
      out.write("                  </tr>\r\n");
      out.write("                  <tr>\r\n");
      out.write("                    <td width=\"433\" height=\"164\" align=\"right\" valign=\"bottom\"><img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/style/login/login-wel.gif\" width=\"242\" height=\"138\"></td>\r\n");
      out.write("                    <td width=\"57\" align=\"right\" valign=\"bottom\">&nbsp;</td>\r\n");
      out.write("                  </tr>\r\n");
      out.write("              </table></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("          </table>\r\n");
      out.write("          </td>\r\n");
      out.write("      </tr>\r\n");
      out.write("    </table></td>\r\n");
      out.write("  </tr>\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td height=\"20\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"login-buttom-bg\">\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td align=\"center\"><span class=\"login-buttom-txt\">Copyright &copy; 掌慧纵盈</span></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("    </table></td>\r\n");
      out.write("  </tr>\r\n");
      out.write("</table></form>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("\tfunction changeImg(obj){\r\n");
      out.write("\t\r\n");
      out.write("\t\t$(obj).attr(\"src\", '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx }", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/validate/code?'+new Date().getTime());\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
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
