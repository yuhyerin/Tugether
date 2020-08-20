package com.web.curation.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.article.Article;

@Repository
public interface ArticleRepo extends JpaRepository<Article, String> {

	@Query(value = "select * from article a where a.article_id=:article_id order by a.reg_time desc", nativeQuery = true)
	Article findArticleByArticleId(int article_id);

	@Query(value = "select * from article a where a.article_id=:article_id and a.email!=:email order by a.reg_time desc", nativeQuery = true)
	Article findArticleByArticleIdandEmail(int article_id, String email);

	//내 게시글
	@Query(value = "select * from article a where a.email=:email order by a.reg_time desc", nativeQuery = true)
	public List<Article> findArticleByEmail(String email);

	@Query(value = "select image from article a where a.article_id=:article_id", nativeQuery = true)
	String findImgByArticleId(int article_id);

	@Query(value = "select article_id from article a where a.email=:email order by a.reg_time desc", nativeQuery = true)
	List<Integer> findArticleIdByEmail(String email);

	@Query(value="select * from article a where a.article_id in (select distinct article_id from articletag t where t.tag_id in (select tag_id from favtag f where f.email=:email)) and a.email!=:email order by a.reg_time desc"
			, nativeQuery = true, countQuery = "select count(*) from article a where a.article_id in (select distinct article_id from articletag t where t.tag_id in (select tag_id from favtag f where f.email=:email)) and a.email!=:email order by a.reg_time desc" )
	Page<Article> findArticlesByTag(Pageable pageable, String email);

	@Query(value="select * from article a where a.email in (select `to_user` from `following` f where f.from_user = :email) and a.email!=:email order by a.reg_time desc"
			, nativeQuery = true, countQuery = "select count(*) from article a where a.email in (select `to_user` from `following` f where f.from_user = :email) and a.email!=:email order by a.reg_time desc")
	Page<Article> findArticleByFollow(Pageable pageable, String email);
	
	@Query(value="select * from article a where article_id in ( " + 
			"select distinct article_id from articletag ta where ta.tag_id in " + 
			"(select tag_id from favtag f where f.email = :email) ) and a.article_id >=:to and a.article_id <=:from and a.email!=:email order by reg_time desc", nativeQuery = true)
	List<Article> findArticleByEmailFromToTag(String email, int from, int to);
	
	@Query(value="select article_id from article a where a.email in ( " + 
			"select `to_user` from `following` f where f.from_user = :email ) and a.article_id >=:to and a.article_id <=:from and a.email!=:email order by reg_time desc", nativeQuery=true)
	List<Integer> findArticleIdByEmailFromToFollow(String email, int from, int to);
	
	// search by Tag
	@Query(value="select distinct article_id from article a where a.article_id in " + 
			"( select article_id from articletag ta where ta.tag_id in " + 
			"( select tag_id from tag t where t.tag_name like concat('%',:keyword,'%') ) ) order by reg_time desc", nativeQuery=true)
	List<Integer> findArticleByTagNameForSearch(String keyword);
	
	@Query(value="select distinct article_id from article a where a.article_id in " + 
			"( select article_id from articletag ta where ta.tag_id in " + 
			"( select tag_id from tag t where t.tag_name =:keyword ) ) order by reg_time desc", nativeQuery=true)
	List<Integer> findArticleIDByTagNameForSearch(String keyword);
	
}
