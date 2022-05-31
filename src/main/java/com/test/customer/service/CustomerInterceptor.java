package com.test.customer.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CustomerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("interceptor pre handle");
		String requestMethod = request.getMethod();

		if (HttpMethod.DELETE.matches(requestMethod) ) {
			String userType = request.getHeader("user-type");
			if (null != userType && "admin".equals(userType)) {
				return true;	
			} else {
				response.sendError(HttpStatus.METHOD_NOT_ALLOWED.value());
				return false;
			}
		}

		return true;
	}
	
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception exception) throws Exception {}


}
