package com.web.curation.service.feed;

import java.util.List;

import com.web.curation.dto.article.Article;
import com.web.curation.dto.article.FrontArticle;

public interface FeedService {
	
	public List<FrontArticle> findArticleListByTag(String email);
	public List<FrontArticle> findArticleListByFollow(String email);
	
	public int updateLike(int article_id, String email, boolean like);

}
