package com.tickets.api.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HostnameInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(HostnameInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String clientHost = request.getRemoteHost();
		logger.info("Client Host: {}", clientHost);
		request.setAttribute("clientHost", clientHost);

		// Return true to proceed with the request; return false to block the request
		return true;
	}
}
