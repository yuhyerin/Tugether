package com.web.curation.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.dto.article.Article;

public interface StatsArticleRepo extends JpaRepository<Article, String>{

	// 좋아요가 가장많이 달린 게시글 리스트
	@Query(value="select * from article order by like_cnt desc limit 3", nativeQuery = true)
	ArrayList<Article> getTopLikeyArticle();
	
}
