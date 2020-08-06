package com.web.curation.service.feed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dto.article.Article;
import com.web.curation.dto.article.Comment;
import com.web.curation.dto.article.FrontArticle;
import com.web.curation.dto.article.Likey;
import com.web.curation.repo.ArticleRepo;
import com.web.curation.repo.ArticleTagRepo;
import com.web.curation.repo.CommentRepo;
import com.web.curation.repo.FavtagRepo;
import com.web.curation.repo.FollowingRepo;
import com.web.curation.repo.LikeyRepo;
import com.web.curation.repo.TagRepo;

@Service
public class FeedServiceImpl implements FeedService {

	@Autowired
	private FavtagRepo favtagRepo;
	@Autowired
	private ArticleTagRepo articletagRepo;
	@Autowired
	private ArticleRepo articleRepo;
	@Autowired
	private FollowingRepo followingRepo;
	@Autowired
	private TagRepo tagRepo;
	@Autowired
	private LikeyRepo likeRepo;
	@Autowired
	private CommentRepo commentRepo;

	SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	// 1. 태그기반 피드
	@Override
	public List<FrontArticle> findArticleListByTag(String email) {
//		System.out.println("FEEDSERVICE 입장");
		List<FrontArticle> result = new ArrayList<>();
		List<Article> articles = new ArrayList<>();

		List<Integer> tags = favtagRepo.findTagIdByEmail(email);
		
		for (int i = 0; i < tags.size(); i++) {
			for(int j=i+1;j<tags.size();j++) {
				if(tags.get(i)==tags.get(j)) {
					tags.remove(j);
					j--;
				}
			}
		}

		// ArticleTag테이블에서 tag_id로 article_id들 리스트로 받아와
		TreeSet<Integer> articleIdList = new TreeSet<>();
		for (int l = 0; l < tags.size(); l++) {
			List<Integer> temp = articletagRepo.findArticleIdByTagId(tags.get(l));
			for (int m = 0; m < temp.size(); m++)
				articleIdList.add(temp.get(m));
		}
		
		int length = articleIdList.size();
		for (int i = 0; i < length; i++) {
			// Article테이블에서 article_id로 article리스트 다 데려와
			List<Article> a = articleRepo.findArticleByArticleId(articleIdList.pollLast());
			for (int j = 0; j < a.size(); j++)
				articles.add(a.get(j));
		}

		// ArticleTag테이블에서 article_id로 List<tag_id> 가져와 => Tag테이블에서 tag_id로 tag_name 가져와
		for (int i = 0; i < articles.size(); i++) {
			Article now = articles.get(i);
			
			List<Integer> taglist = articletagRepo.findTagIdByArticleId(now.getArticle_id());
			
			String[] temp = new String[taglist.size()];
			for (int j = 0; j < taglist.size(); j++) {
				temp[j] = tagRepo.findTagNameByTagId(taglist.get(j));
			}

			int article_id=now.getArticle_id();
			
			boolean like = false;
			// like 테이블에 article_id & email이 같은 게 있는지
			if(likeRepo.findLike(article_id, email).isPresent())
				like = true;
//			// scrap 테이블에 article_id & email이 같은 게 있는지
//			if(scrapRepo.findScrap(article_id, email).isPresent())
//				scrap = true;
			// comment_cnt
			int comment_cnt = commentRepo.findCommentByArticleId(article_id).size();

			FrontArticle ar = new FrontArticle(article_id, now.getWriter(),
					format.format(now.getReg_time()), now.getImage(), now.getContent(), now.getLink(),
					now.getLike_cnt(), like, comment_cnt, temp);
			result.add(ar);
		}
		
		System.out.println("result : "+result.toString());
		return result;
	}

	// 2. 팔로우기반 피드
	@Override
	public List<FrontArticle> findArticleListByFollow(String email) {
		List<FrontArticle> result = new ArrayList<>();

		// following테이블에서 from_user=email로 to_user 리스트 찾아와
		List<String> toUser = followingRepo.findToUserByFromUser(email);

		// article 테이블에서 uid = email인 List<article>로 다 가져가
		for (int l = 0; l < toUser.size(); l++) {
			List<Article> list = articleRepo.findArticleByEmail(toUser.get(l));
			for (int m = 0; m < list.size(); m++) {
				// ArticleTag테이블에서 article_id로 List<tag_id> 가져와 => Tag테이블에서 tag_id로 tag_name 가져와
				Article now = list.get(m);
//				if (!result.contains(now)) {
					List<Integer> taglist = articletagRepo.findTagIdByArticleId(now.getArticle_id());
					String[] temp = new String[taglist.size()];
					for (int j = 0; j < taglist.size(); j++) {
						temp[j] = tagRepo.findTagNameByTagId(taglist.get(j));
					}
					int article_id=now.getArticle_id();
					boolean like = false;
					// like 테이블에 article_id & email이 같은 게 있는지
					if(likeRepo.findLike(article_id, email).isPresent())
						like = true;
					// comment_cnt 찾아야해
					int comment_cnt = commentRepo.findCommentByArticleId(article_id).size();
					FrontArticle ar = new FrontArticle(article_id, now.getWriter(),
							format.format(now.getReg_time()), now.getImage(), now.getContent(), now.getLink(),
							now.getLike_cnt(), like, comment_cnt, temp);
					result.add(ar);
				}
//			}
		}

		return result;
	}

	// 좋아요 처리
	@Override
	public int updateLike(int article_id, String email, boolean like) {
		int likecnt;
		List<Likey> temp = likeRepo.findLikeByArticleId(article_id);
		
		if(like) {	// 1. 좋아요 누를 때
			// like 테이블에 로우 추가 = email이랑 article_id로 추가
			if(!likeRepo.findLike(article_id, email).isPresent())
				likeRepo.save(Likey.builder().article_id(article_id).email(email).build());
			
		} else {	// 2. 좋아요 취소할 때
			if(likeRepo.findLike(article_id, email).isPresent())
				likeRepo.delete(Likey.builder().article_id(article_id).email(email).build());
		}
		// like 테이블에서 article_id로 좋아요 갯수 세기
		return likeRepo.findLikeByArticleId(article_id).size();
	}

}
