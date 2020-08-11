package com.web.curation.service.articlewrite;

import java.util.ArrayList;

import com.web.curation.dto.article.Article;

public interface ArticleUpdateService {

	// 게시글 수정폼에 내용 가져오기
	Article getArticle(int article_id);

	// 게시글에 등록한 태그들 초기화(다 지우기)
	void resetArticleTag(int article_id);

	// 게시글에 등록한 태그리스트 가져오기
	ArrayList<String> getArticleTag(int article_id);

}
