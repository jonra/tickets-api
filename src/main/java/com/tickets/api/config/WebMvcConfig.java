package com.tickets.api.config;

import com.tickets.api.interceptors.HostnameInterceptor;
import com.tickets.api.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	private final HostnameInterceptor hostnameInterceptor;
	private final TenantService tenantService;

	@Autowired
	public WebMvcConfig(HostnameInterceptor hostnameInterceptor, TenantService tenantService) {
		this.hostnameInterceptor = hostnameInterceptor;
		this.tenantService = tenantService;
	}



	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(hostnameInterceptor);
	}


}
