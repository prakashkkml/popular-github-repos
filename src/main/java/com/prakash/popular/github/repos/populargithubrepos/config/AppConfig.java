package com.prakash.popular.github.repos.populargithubrepos.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig {

    public static final int MAX_IN_MEMORY_SIZE = 16 * 1024 * 1024;
    public static final int CACHE_MAXIMUM_SIZE = 1024 * 1024 * 256;
    public static final int TIME_TO_LIVE_IN_SECONDS = 30;

    @Bean
    public WebClient webClient() {
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(MAX_IN_MEMORY_SIZE))
                .build();
        return WebClient.builder()
                .exchangeStrategies(strategies)
                .build();
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }

    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterAccess(TIME_TO_LIVE_IN_SECONDS, TimeUnit.SECONDS);
    }

    @Bean
    public KeyGenerator repositoryCacheKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName());
                sb.append(method.getName());
                for (Object param : params) {
                    if (!ObjectUtils.isEmpty(param))
                        sb.append(param.toString());
                }
                return sb.toString();
            }
        };
    }

}
