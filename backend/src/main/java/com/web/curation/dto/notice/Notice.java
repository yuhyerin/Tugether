package com.web.curation.dto.notice;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="notice")
@Table(name="notice")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Notice {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int notice_id;
	
	@Column(name="notice_to", nullable = false)
	private String notice_to;
	
	@Column(name="notice_type", nullable=false)
	private int notice_type;
	
	@Column(name="notice_from", nullable=false)
	private String notice_from;
	
	@Column(name="comment_id")
	private int comment_id;
	
	@Column(name="reg_time", insertable = false)
	private LocalDateTime reg_time;
	
	@Column(name="article_id")
	private int article_id;
	
}
