package com.tickets.api.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class LoggingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		// Wrap the request to read the body without consuming it
		RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);

		// Log the request body
		String requestBody = requestWrapper.getBody();
		log.info("Request: {} {} - Body: {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), requestBody);

		// Wrap the response to read the body without consuming it
		ResponseWrapper responseWrapper = new ResponseWrapper(httpServletResponse);

		// Proceed with the filter chain
		chain.doFilter(requestWrapper, responseWrapper);

		// Log the response body
		String responseBody = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
		log.info("Response: {} {} - Body: {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), responseBody);

		// Copy the modified response to the original response
		responseWrapper.copyBodyToResponse();
	}

}
