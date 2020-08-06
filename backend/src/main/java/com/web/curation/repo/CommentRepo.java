package com.web.curation.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.dto.comment.Comment;

public interface CommentRepo extends JpaRepository<Comment, String>{
	
	@Query(value="select * from comment c where c.article_id=:article_id order by comment_id desc", nativeQuery = true)
	public List<Comment> findCommentByArticleId(int article_id);

}
