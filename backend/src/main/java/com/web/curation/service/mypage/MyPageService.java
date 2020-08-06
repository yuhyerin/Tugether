package com.web.curation.service.mypage;

import java.util.List;

import com.web.curation.dto.article.Article;

public interface MyPageService {
	
	//1. 내 게시글 가져오기
	List<Article> findArticles(String email); 
	//2. 스크랩한 게시글 가져오기
	List<Article> findScraps(String email);

}
