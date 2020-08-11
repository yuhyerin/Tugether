package com.web.curation.dto.notice;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FrontNotice {
	
	private String notice_to;
	private int notice_type;
	private String notice_from;
	private String from_nickname;
	private String profile_photo;
	private String img;
	private int comment_id;
	private LocalDateTime reg_time;
	private int article_id;
	
}
