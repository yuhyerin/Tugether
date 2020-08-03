package com.web.curation.dto.article;

import java.sql.Date;
import java.text.SimpleDateFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class FrontArticle {

	private int article_id;
	private String email;
	private String writer;
	private String reg_time;
	private String image;
	private String content;
	private String link;
	private int like_cnt;
	private int scrap_cnt;
	private String[] tag_name;
	

}
