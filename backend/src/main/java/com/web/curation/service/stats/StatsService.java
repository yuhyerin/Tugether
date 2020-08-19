package com.web.curation.service.stats;

import java.util.ArrayList;
import java.util.List;

import com.web.curation.dto.article.Article;
import com.web.curation.dto.tag.Tag;

public interface StatsService {

	// 가장 많이 검색된 태그 리스트
	ArrayList<Tag> getTopSearchKeyword();

	// 게시글에 가장많이 달린 태그 리스트
	ArrayList<Tag> getTopArticleTag();

	// 좋아요가 가장많이 달린 게시글 리스트
	List<Article> getTopLikeyArticle(String email);
	

}
