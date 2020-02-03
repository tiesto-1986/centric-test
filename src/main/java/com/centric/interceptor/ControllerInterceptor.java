package com.centric.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class ControllerInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {
		long endTime = System.currentTimeMillis();

		long startTime = (Long) httpServletRequest.getAttribute("startTime");
		long executeTime = endTime - startTime;

		String logStr = "[" + httpServletRequest.getRequestURL() + "] took: " + executeTime + "ms";

		if (o instanceof HandlerMethod) {
			logStr += " (method: " + ((HandlerMethod) o).getMethod().toString() + ")";
		}
		log.info(logStr);
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
			throws Exception {
		long startTime = System.currentTimeMillis();
		httpServletRequest.setAttribute("startTime", startTime);
		return true;
	}

}
