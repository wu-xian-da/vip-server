package com.jianfei.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.common.utils.UserAgentUtils;


/**
 *
 * @Description: 手机端视图拦截器
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月31日 上午8:27:57
 * 
 * @version 1.0.0
 *
 */
public class MobileInterceptor  implements
		HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			// 如果是手机或平板访问的话，则跳转到手机视图页面。
			if (UserAgentUtils.isMobileOrTablet(request)
					&& !StringUtils.startsWithIgnoreCase(
							modelAndView.getViewName(), "redirect:")) {
				modelAndView
						.setViewName("mobile/" + modelAndView.getViewName());
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
