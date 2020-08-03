package com.web.curation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	private final String uploadImagesPath;
	
	public WebConfig(@Value("${upload-images}") String uploadImagePath) {
		this.uploadImagesPath = uploadImagePath;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		
		
	}
}
