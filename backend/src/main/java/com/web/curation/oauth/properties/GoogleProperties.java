package com.web.curation.oauth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("api.oauth.google")
public class GoogleProperties {
	private String appId;
    private String secret;
    private String access;
    private String loginRedirectUri;
    private String connectRedirectUri;
    private String clientToken;
}
