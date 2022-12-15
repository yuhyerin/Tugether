package com.web.curation.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleOauth2Dto {
	String code;
	String client_id;
	String client_secret;
	String redirect_uri;
	String grant_type;
}
