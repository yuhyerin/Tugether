package com.web.curation.service.feed;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;

import com.web.curation.dto.article.FrontArticle;

public interface FeedService {

//	public List<FrontArticle> findArticleListByTag(String email);
//	public List<FrontArticle> findArticleListByFollow(String email);

	public FrontArticle updateLike(int article_id, String email);
	public Map<String, Object> checkScrap(String email, int article_id);
	public FrontArticle scrap(String email, int article_id);
	
	public List<FrontArticle> findArticleListByTag(String email, int from, int to);
	public List<FrontArticle> findArticleListByFollow(String email, int from, int to);

	public FrontArticle makeFront(String email, int article_id);

	// 무한 스크롤
	List<FrontArticle> findByPageRequestTag(PageRequest pageRequest, String email);
	List<FrontArticle> findByPageRequestFollow(PageRequest pageRequest, String email);

}
