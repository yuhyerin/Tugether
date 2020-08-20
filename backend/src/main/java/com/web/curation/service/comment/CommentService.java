package com.web.curation.service.comment;

import java.util.List;

import com.web.curation.dto.article.FrontArticle;
import com.web.curation.dto.comment.Comment;
import com.web.curation.dto.comment.FrontComment;

public interface CommentService {
	
	List<FrontComment> findComments(int article_id);
	void saveComment(Comment c);
	
	//1. comment_id 삭제 -> article_tb에서 article_id comment_cnt-1 -> article_id로 comment찾기
	void deleteComment(int comment_id);
	public FrontComment makeFront(Comment c);

	FrontArticle findArticle(String email, int article_id);
	
	void deleteCommentByArticleId(int article_id);
}
