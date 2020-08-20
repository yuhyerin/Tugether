package com.web.curation.service.articlewrite;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.web.curation.dto.article.Article;

public interface ArticleWriteService {

	// Article 테이블에 insert 하고 article_id 반환. - 이미지까지 등록한 경우 
	public int addArticlewithImage(Article article);
	
	// Article 테이블에 insert 하고 article_id 반환.
	public int addArticle(Article article);

	// ArticleTag 테이블에 insert
	public void addArticleTag(int article_id, ArrayList<String> taglist);

}
