package com.web.curation.service.comment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dto.comment.Comment;
import com.web.curation.dto.comment.FrontComment;
import com.web.curation.repo.CommentRepo;
import com.web.curation.repo.ProfileRepo;
import com.web.curation.repo.UserRepo;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ProfileRepo profileRepo;
	
	@Override
	public List<FrontComment> findComments(int article_id) {
		List<Comment> list = commentRepo.findCommentByArticleId(article_id);
		List<FrontComment> result = new ArrayList<>();
		for(int l=0;l<list.size();l++)
			result.add(makeFront(list.get(l)));
		return result;
	}

	
	@Override
	   public FrontComment makeFront(Comment c) {
	      String nickname = userRepo.findUserByEmail(c.getEmail()).getNickname();
	      String profile_photo = profileRepo.findProfilePhoto(c.getEmail());
	      FrontComment fc = FrontComment.builder().comment_id(c.getComment_id())
	            .profile_photo(profile_photo)
	            .email(c.getEmail()).nickname(nickname)
	            .article_id(c.getArticle_id())
	            .content(c.getContent())
	            .reg_time(c.getReg_time()).build();
	            
	      return fc;
	   }
	
}
