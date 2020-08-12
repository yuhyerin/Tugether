package com.web.curation.service.mypage;

import java.util.List;

import com.web.curation.dto.article.Article;
import com.web.curation.dto.article.FrontArticle;

public interface MyPageService {
	
	//1. 내 게시글 가져오기
	List<FrontArticle> findArticles(String email); 
	//2. 스크랩한 게시글 가져오기
	List<FrontArticle> findScraps(String email);
	
	//3. 팔로우 확인
	boolean findFollow(String userEmail, String email);
	
	public FrontArticle makeFront(String email, int article_id);

}
