package com.web.curation.service.search;

import java.util.List;

import com.web.curation.dto.article.FrontArticle;

public interface SearchService {

	List<String> findTagNamesByTag(String keyword);
	public List<FrontArticle> findArticlesByTagName(String email, String keyword);

	List<String> findNickNamesByNickname(String keyword);
	List<FrontArticle> findArticlesByNickname(String email, String keyword);

	public FrontArticle makeFront(String email, int article_id);

}
