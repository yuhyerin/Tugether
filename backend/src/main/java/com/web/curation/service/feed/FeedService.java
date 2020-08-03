package com.web.curation.service.feed;

import java.util.List;

import com.web.curation.dto.article.Article;

public interface FeedService {
	
	public List<Article> findArticleListByTag(String email);
	public List<Article> findArticleListByFollow(String email);

}
