package com.web.curation.service.feed;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.web.curation.dto.article.FrontArticle;

public interface FeedService {
	
	public List<FrontArticle> findArticleListByTag(String email);
	public List<FrontArticle> findArticleListByFollow(String email);
	
	public FrontArticle updateLike(int article_id, String email);
	
	public boolean checkScrap(String email, int article_id);
	public FrontArticle scrap(String email, int article_id);
	public FrontArticle makeFront(String email, int article_id);

	 List<FrontArticle> findByPageRequest(PageRequest pageRequest, String email);
}
