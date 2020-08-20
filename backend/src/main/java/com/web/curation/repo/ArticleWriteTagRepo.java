package com.web.curation.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.tag.Tag;


@Repository
@Transactional
public interface ArticleWriteTagRepo extends JpaRepository<Tag, String>{
	
	@Query(value="select * from tag t where t.tag_name=:tagname",nativeQuery=true)
	Tag findTagByTagName(String tagname);
	
	@Query(value="insert into tag(tag_name, article_cnt) values(:tagname , :article_cnt)", nativeQuery=true)
	void insertTag(String tagname, int article_cnt);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="update tag t set t.article_cnt = t.article_cnt + 1 where t.tag_id = :tag_id",nativeQuery = true)
	public void countArticleCnt(@Param("tag_id")int tag_id);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="update tag t set t.article_cnt = t.article_cnt - 1 where t.tag_id = :tag_id",nativeQuery = true)
	public void countArticleMinusCnt(@Param("tag_id")int tag_id);
}
