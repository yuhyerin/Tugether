package com.web.curation.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Follow {
	
	private String profile_photo;
	private String nickname;
	private String email;
	private boolean follow;

}
