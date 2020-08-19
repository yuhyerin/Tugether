package com.web.curation.dto.article;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrontArticle {

	private int article_id;
	private String email;
	private String writer;
	private LocalDateTime reg_time;
	private String image;
	private String profile_photo;
	private String content;
	private String link;
	private int like_cnt;	// 게시글의 좋아요 수
	private boolean like;	// 내가 좋아요 눌렀는 지
	private int comment_cnt; // 게시글의 댓글 수
	private int scrap_cnt;
	private String[] tag_name; // 게시글에 달린 태그들
	private boolean scrap;
	

}
