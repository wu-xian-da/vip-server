package com.jianfei.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request) throws Exception {
		// 如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
		String exceptionClassName = (String) request
				.getAttribute("shiroLoginFailure");
		// 根据shiro返回的异常类路径判断，抛出指定异常信息
		// if (exceptionClassName != null) {
		// if (UnknownAccountException.class.getName().equals(
		// exceptionClassName)) {
		// // 最终会抛给异常处理器
		// throw new Exception("账号不存在");
		// } else if (IncorrectCredentialsException.class.getName().equals(
		// exceptionClassName)) {
		// throw new Exception("用户名/密码错误");
		// } else if ("randomCodeError".equals(exceptionClassName)) {
		// throw new Exception("验证码错误 ");
		// } else {
		// throw new Exception();// 最终在异常处理器生成未知错误
		// }
		// }
		// 此方法不处理登陆成功（认证成功），shiro认证成功会自动跳转到上一个请求路径
		// 登陆失败还到login页面
		return "login";
	}

	@RequestMapping(value = { "/", "/main" })
	public String main() {
		return "main";
	}

	@RequestMapping(value = "layout/header")
	public String header() {
		return "layout/header";
	}

	@RequestMapping(value = "layout/south")
	public String south() {
		return "layout/south";
	}
}
