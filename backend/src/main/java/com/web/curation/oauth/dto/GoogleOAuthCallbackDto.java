package com.web.curation.oauth.dto;

import com.web.curation.user.dto.GoogleOauth2Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleOAuthCallbackDto {
	String code;
}
