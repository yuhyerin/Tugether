package com.web.curation.service.mypage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dto.article.Article;
import com.web.curation.dto.article.FrontArticle;
import com.web.curation.repo.ArticleRepo;
import com.web.curation.repo.ArticleTagRepo;
import com.web.curation.repo.FavtagRepo;
import com.web.curation.repo.FollowingRepo;
import com.web.curation.repo.LikeyRepo;
import com.web.curation.repo.ProfileRepo;
import com.web.curation.repo.ScrapRepo;
import com.web.curation.repo.TagRepo;

@Service
public class MyPageServiceImpl implements MyPageService {

	@Autowired
	private ArticleRepo articleRepo;
	@Autowired
	private ScrapRepo scrapRepo;
	@Autowired
	private FollowingRepo followingRepo;
	@Autowired
	private ArticleTagRepo articletagRepo;
	@Autowired
	private TagRepo tagRepo;
	@Autowired
	private FavtagRepo favtagRepo;
	@Autowired
	private ProfileRepo profileRepo;
	@Autowired
	private LikeyRepo likeRepo;

	@Override
	public List<FrontArticle> findArticles(String userEmail, String email) {
		List<Article> list = articleRepo.findArticleByEmail(userEmail);
		List<FrontArticle> result = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			result.add(makeFront(email, list.get(i).getArticle_id()));
		}
		return result;
	}

	@Override
	public List<FrontArticle> findScraps(String userEmail, String email) {
		List<Integer> articleIDs = scrapRepo.findArticleidByEmail(userEmail); // 이메일로 아티클아이디찾아옴
		List<FrontArticle> result = new ArrayList<>();
		for (int i = 0; i < articleIDs.size(); i++) {
			result.add(makeFront(email, articleIDs.get(i)));
		}
//		for(int l=0;l<articleIDs.size();l++)
//			articles.add(articleRepo.findArticleByArticleId(articleIDs.get(l)).get(0)); 
		return result;
	}

	@Override
	public List<FrontArticle> findArticles(String email) {
		List<Article> list = articleRepo.findArticleByEmail(email);
		List<FrontArticle> result = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			result.add(makeFront(email, list.get(i).getArticle_id()));
		}
		return result;
	}

	@Override
	public List<FrontArticle> findScraps(String email) {
		List<Integer> articleIDs = scrapRepo.findArticleidByEmail(email); // 이메일로 아티클아이디찾아옴
		List<FrontArticle> result = new ArrayList<>();
		for (int i = 0; i < articleIDs.size(); i++) {
			result.add(makeFront(email, articleIDs.get(i)));
		}
//		for(int l=0;l<articleIDs.size();l++)
//			articles.add(articleRepo.findArticleByArticleId(articleIDs.get(l)).get(0)); 
		return result;
	}

	@Override
	public boolean findFollow(String userEmail, String email) {
		List<String> list = followingRepo.findToUserByFromUser(email);
		return list.contains(userEmail);
	}

	@Override // email = like 체크 / article_id = 태그리스트
	public FrontArticle makeFront(String email, int article_id) {

		Article now = articleRepo.findArticleByArticleId(article_id);
		List<Integer> taglist = articletagRepo.findTagIdByArticleId(now.getArticle_id()); // 아티클태그케이블에서 태그 가져와야 프론트에 줄 수
																							// 있음
		String[] temp = new String[taglist.size()]; // 태그 리스트를 태그 배열로 만들거임
		for (int j = 0; j < taglist.size(); j++) {
			temp[j] = tagRepo.findTagNameByTagId(taglist.get(j)); // 태그테이블에서 태그아이디로 태그네임 찾아서 배열 저장
		}

		String profile_photo = profileRepo.findProfilePhoto(now.getEmail());
		boolean like = likeRepo.findLike(article_id, email).isPresent();
		boolean scrap = scrapRepo.findScrap(email, article_id).isPresent();
		FrontArticle ar = FrontArticle.builder().article_id(article_id).profile_photo(profile_photo)
				.email(now.getEmail()).profile_photo(profile_photo).writer(now.getWriter()).reg_time(now.getReg_time())
				.image(now.getImage()).content(now.getContent()).like(like).link(now.getLink())
				.like_cnt(now.getLike_cnt()).comment_cnt(now.getComment_cnt()).scrap_cnt(now.getScrap_cnt())
				.tag_name(temp).scrap(scrap).build();
		return ar;
	}

	@Override
	public List<String> findFavTags(String email) {
		List<Integer> tagIDs = favtagRepo.findTagIdByEmail(email);
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < tagIDs.size(); i++)
			result.add(tagRepo.findTagNameByTagId(tagIDs.get(i)));
		return result;
	}

}
