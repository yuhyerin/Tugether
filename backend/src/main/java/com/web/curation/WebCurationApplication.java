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
public class WebCurationApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebCurationApplication.class, args);
	}

}
