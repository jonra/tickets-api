package com.tickets.api.config;

import com.tickets.api.interceptors.HostnameInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	private final HostnameInterceptor hostnameInterceptor;

	@Autowired
	public WebMvcConfig(HostnameInterceptor hostnameInterceptor) {
		this.hostnameInterceptor = hostnameInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(hostnameInterceptor);
	}
}
