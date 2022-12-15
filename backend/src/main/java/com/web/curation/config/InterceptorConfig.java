package com.web.curation.config;

import com.web.curation.jwt.interceptor.JwtInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private JwtInterceptor jwtInterceptor;

    // JwtIneterceptor 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.debug("JwtInterceptor 등록");
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/tugether/**")
                .excludePathPatterns(Arrays.asList("/account/signin","/account/signup")); // 적용 제외경로

    }

    // Interceptor를 이용해서 처리하므로, 전역의 Cross Origin 처리를 해준다.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("jwt-auth-token");
    }
}
