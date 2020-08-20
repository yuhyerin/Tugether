package com.web.curation.service.comment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dto.article.Article;
import com.web.curation.dto.article.FrontArticle;
import com.web.curation.dto.comment.Comment;
import com.web.curation.dto.comment.FrontComment;
import com.web.curation.dto.notice.Notice;
import com.web.curation.repo.ArticleRepo;
import com.web.curation.repo.CommentRepo;
import com.web.curation.repo.NoticeRepo;
import com.web.curation.repo.ProfileRepo;
import com.web.curation.service.feed.FeedService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ProfileRepo profileRepo;
	@Autowired
	private NoticeRepo noticeRepo;
	@Autowired
	private ArticleRepo articleRepo;
	@Autowired
	private FeedService feedService;

	@Override
	public List<FrontComment> findComments(int article_id) {
		List<Comment> list = commentRepo.findCommentByArticleId(article_id);
		List<FrontComment> result = new ArrayList<>();
		for (int l = 0; l < list.size(); l++)
			result.add(makeFront(list.get(l)));
		return result;
	}

	public FrontComment makeFront(Comment c) {
//		Profile pro = profileRepo.findProfileByEmail(c.getEmail());
		String nickname = profileRepo.findNicknameByEmail(c.getEmail());
		String profile_photo = profileRepo.findProfilePhotoByEmail(c.getEmail());
		FrontComment fc = FrontComment.builder().comment_id(c.getComment_id()).email(c.getEmail()) // 댓글 작성자
				.nickname(nickname) // 댓글 작성자 닉넴
				.profile_photo(profile_photo) // 댓글 작성자 프로필사진
				.article_id(c.getArticle_id()) // 게시글아이디
				.content(c.getContent()) // 댓글내용
				.reg_time(c.getReg_time()).build();

		return fc;
	}

	@Override
	public void saveComment(Comment c) {
		commentRepo.save(c);
		String notice_to = articleRepo.findArticleByArticleId(c.getArticle_id()).getEmail();
		Comment temp = commentRepo.findCommentByEmailAndArticleId(c.getEmail(), c.getArticle_id()).get(0);
		Article a = articleRepo.findArticleByArticleId(c.getArticle_id());
		int comment_cnt = commentRepo.findCommentByArticleId(c.getArticle_id()).size();
		a.setComment_cnt(comment_cnt);
		articleRepo.save(a);
		if (!a.getEmail().equals(c.getEmail())) {
			Notice n = Notice.builder().article_id(c.getArticle_id()).comment_id(temp.getComment_id())
					.notice_from(c.getEmail()).notice_type(1).notice_to(notice_to).build();
			noticeRepo.save(n);
		}
	}

	@Override
	public void deleteComment(int comment_id) {
		// 1. comment_id 삭제 -> article_tb에서 article_id comment_cnt-1 -> article_id로
		// comment찾기
		int article_id = commentRepo.findArticleIdByCommentId(comment_id);
		commentRepo.deleteComment(comment_id);
		Article a = articleRepo.findArticleByArticleId(article_id);
		int comment_cnt = commentRepo.findCommentByArticleId(article_id).size();
		a.setComment_cnt(comment_cnt);
		articleRepo.save(a);
		noticeRepo.deleteByComment_ID(comment_id);
	}

	@Override
	public FrontArticle findArticle(String email, int article_id) {
		Article temp = articleRepo.findArticleByArticleId(article_id);

		return feedService.makeFront(email, article_id);
	}


	@Override
	public void deleteCommentByArticleId(int article_id) {
		commentRepo.deleteCommentByArticleId(article_id);
	}

}
