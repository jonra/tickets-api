package com.tickets.api.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;

@Configuration
@EnableCaching
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class CacheConfig {
	public static final String ZONE_POLYGON = "zone_polygon";
	public static final String ZONE_RADIUS = "zone_radius";


	@Bean
	public CacheManager cacheManager() {
		ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
		cacheManager.setCacheNames(
				Arrays.asList(ZONE_POLYGON, ZONE_RADIUS));
		return cacheManager;
	}

	@CacheEvict(allEntries = true, value = { ZONE_POLYGON })
	@Scheduled(fixedDelay = 60_000)
	public void evictCache() {
	}

	@CacheEvict(allEntries = true, value = { ZONE_RADIUS })
	@Scheduled(fixedDelay = 60_000)
	public void evictCacheRadius() {
	}

}
