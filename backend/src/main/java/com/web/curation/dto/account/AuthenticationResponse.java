package com.web.curation.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Builder
public class AuthenticationResponse {


	
	    private String email;
	    private String nickname;
	    private Boolean temp;
	    
}
