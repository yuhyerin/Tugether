package com.web.curation;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.web.curation.jwt.interceptor.JwtInterceptor;


@SpringBootApplication
public class WebCurationApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(WebCurationApplication.class, args);
	}

	@Autowired
	private JwtInterceptor jwtInterceptor;
	
	// JWTIneterceptor를 설치한다.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// account로 들어오는 요청은 내가 Interceptor하겠다고 선언...
		System.out.println("Interceptor등록 ");
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/tugether/**")
		.excludePathPatterns(Arrays.asList("/account/signin","/account/signup")); //적용 제외경로 

	}
	
	// Interceptor를 이용해서 처리하므로, 전역의 Corss Origin 처리를 해준다.
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins("*")
		.allowedMethods("*")
		.allowedHeaders("*")
		.exposedHeaders("jwt-auth-token");
	}
}
