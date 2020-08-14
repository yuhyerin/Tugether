package com.web.curation.service.search;

import java.util.List;

import com.web.curation.dto.article.FrontArticle;

public interface SearchService {

	List<String> findTagNamesByTag(String keyword);
	List<FrontArticle> findByTagName(String email, String keyword);

	List<String> findNickNamesByNickname(String keyword);
	List<FrontArticle> findArticlesByNickname(String email, String keyword);

	public FrontArticle makeFront(String email, int article_id);

}
