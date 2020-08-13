package com.web.curation.dto.comment;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrontComment {
	
	private int comment_id;
	private String profile_photo;
	private String email;
	private String nickname;
	private int article_id;
	private String content;
	private LocalDateTime reg_time;

}
