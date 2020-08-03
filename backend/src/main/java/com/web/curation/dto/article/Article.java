package com.web.curation.dto.article;

import java.sql.Date;
import java.text.SimpleDateFormat;

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

@Entity(name="article")
@Table(name="article")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Article {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// 얘가 auto_increment
	private int article_id;
	
//	@Column(name="email")
	private String email;
	
//	@Column(name="writer")
	private String writer;
	
	@Column(name="reg_time", insertable=false)
	private Date reg_time;
	
//	@Column(name="image")	// 얘는 이미지 저장된 위치
	private String image;
	
	
//	@Column(name="content")
	private String content;
	
//	@Column(name="link")
	private String link;
	
	@Column(name="like_cnt")
	private int like_cnt;
	
	@Column(name="scrap_cnt")
	private int scrap_cnt;
}

