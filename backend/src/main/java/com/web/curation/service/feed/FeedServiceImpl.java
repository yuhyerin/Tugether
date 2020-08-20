package com.web.curation.service.feed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.web.curation.dto.article.Article;
import com.web.curation.dto.article.FrontArticle;
import com.web.curation.dto.article.Likey;
import com.web.curation.dto.article.Scrap;
import com.web.curation.dto.notice.Notice;
import com.web.curation.repo.ArticleRepo;
import com.web.curation.repo.ArticleTagRepo;
import com.web.curation.repo.FavtagRepo;
import com.web.curation.repo.FollowingRepo;
import com.web.curation.repo.LikeyRepo;
import com.web.curation.repo.NoticeRepo;
import com.web.curation.repo.ProfileRepo;
import com.web.curation.repo.ScrapRepo;
import com.web.curation.repo.TagRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
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
	private ScrapRepo scrapRepo;
	@Autowired
	private NoticeRepo noticeRepo;
	@Autowired
	private ProfileRepo profileRepo;

	// 1. 태그기반 피드
//	@Override
//	public List<FrontArticle> findArticleListByTag(String email) {
//		List<FrontArticle> result = new ArrayList<>();
//		List<Article> articles = new ArrayList<>();
//		// favtag에서 이메일로 저장된 tagid 가져와
//		List<Integer> tagIDs = favtagRepo.findTagIdByEmail(email);
//
//		// ArticleTag테이블에서 tag_id로 article_id들 리스트로 받아와
//		TreeSet<Integer> articleIdList = new TreeSet<>();
//		for (int l = 0; l < tagIDs.size(); l++) {
//			List<Integer> temp = articletagRepo.findArticleIdByTagId(tagIDs.get(l));
//			for (int m = 0; m < temp.size(); m++)
//				articleIdList.add(temp.get(m));
//		}
//
//		int length = articleIdList.size();
//		for (int i = 0; i < length; i++) {
//			// Article테이블에서 article_id로 article리스트 다 데려와
//			Article a = articleRepo.findArticleByArticleIdandEmail(articleIdList.pollLast(), email);
//			for (int j = 0; j < a.size(); j++)
//				articles.add(a.get(j));
//		}
//
//		// ArticleTag테이블에서 article_id로 List<tag_id> 가져와 => Tag테이블에서 tag_id로 tag_name 가져와
//		for (int i = 0; i < articles.size(); i++) {
//			FrontArticle ar = makeFront(email, articles.get(i).getArticle_id());
//			result.add(ar);
//		}
//
//		System.out.println("result : " + result.toString());
//		return result;
//		
//	}
//
//	// 2. 팔로우기반 피드
//	@Override
//	public List<FrontArticle> findArticleListByFollow(String email) {
//		List<FrontArticle> result = new ArrayList<>();
//
//		// following테이블에서 from_user=email로 to_user 리스트 찾아와
//		List<String> toUser = followingRepo.findToUserByFromUser(email);
//
//		// article 테이블에서 uid = email인 List<article>로 다 가져가
//		for (int l = 0; l < toUser.size(); l++) {
//			List<Article> list = articleRepo.findArticleByEmail(toUser.get(l));
//
//			for (int m = 0; m < list.size(); m++) {
//				// ArticleTag테이블에서 article_id로 List<tag_id> 가져와 => Tag테이블에서 tag_id로 tag_name 가져와
//				FrontArticle a = makeFront(email, list.get(m).getArticle_id());
//				result.add(a);
//			}
//
//		}
//		System.out.println("result : " + result.toString());
//		return result;
//	}

	@Override // 좋아요 클릭 시
	public FrontArticle updateLike(int article_id, String email) {
		Article a = articleRepo.findArticleByArticleId(article_id);
		// 1. likey테이블에서 좋아요 여부 확인
		if (likeRepo.findLike(article_id, email).isPresent()) { // 좋아요 한 적 있음
//			System.out.println("article_id : "+article_id + "email : "+email);
			likeRepo.deleteLikey(email, article_id);
//			System.out.println("여기 들어오면 지워야돼");
			if (!email.equals(a.getEmail()))
				noticeRepo.deleteNotice(email, article_id);
		} else { // 좋아요 한 적 없음
			likeRepo.save(Likey.builder().article_id(article_id).email(email).build());
			if (!email.equals(a.getEmail())) {
				Notice n = Notice.builder().notice_to(a.getEmail()).notice_type(2).notice_from(email)
						.article_id(article_id).build();
				noticeRepo.save(n);
			}
		}

//		int like_cnt = likeRepo.findLikeByArticleId(article_id).size(); // 게시글의 좋아요 갯수
		Article temp = articleRepo.findArticleByArticleId(article_id);
		System.out.println("너의 likeCNT는  BEFORE? " + temp.getLike_cnt());
		temp.setLike_cnt(likeRepo.findLikeByArticleId(article_id).size());
		System.out.println("너의 likeCNT는  AFTER? " + temp.getLike_cnt());
//		System.out.println(article_id + "의 likeCNT 는  ? "+temp.getLike_cnt());
		articleRepo.save(temp); // article테이블 업데이트
		System.out.println("너의 likeCNT는  save AFTER? " + articleRepo.findArticleByArticleId(article_id).getLike_cnt());
//		System.out.println("like 했니 ? "+likeRepo.findLike(article_id, email).isPresent());
		return makeFront(email, article_id);
	}

	@Override
	public Map<String, Object> checkScrap(String email, int article_id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("scrapcheck", scrapRepo.findScrap(email, article_id).isPresent());
		resultMap.put("mycheck",email.equals(articleRepo.findArticleByArticleId(article_id).getEmail()));
		return  resultMap;
	}

	@Override
	public FrontArticle scrap(String email, int article_id) {

		Article a = articleRepo.findArticleByArticleId(article_id);
		
		if (!scrapRepo.findScrap(email, article_id).isPresent()) {
			scrapRepo.save(Scrap.builder().article_id(article_id).email(email).build());
			int scrap_cnt = a.getScrap_cnt() + 1;
			a.setScrap_cnt(scrap_cnt);
			articleRepo.save(a);
		}
		return makeFront(email, article_id);
	}

	@Override
	public List<FrontArticle> findArticleListByTag(String email, int from, int to) {
		List<FrontArticle> result = new ArrayList<FrontArticle>();
		List<Article> temp = articleRepo.findArticleByEmailFromToTag(email, from, to);
		for (int l = 0; l < temp.size(); l++)
			result.add(makeFront(email, temp.get(l).getArticle_id()));
		return result;
	}

	@Override
	public List<FrontArticle> findArticleListByFollow(String email, int from, int to) {
		List<FrontArticle> result = new ArrayList<FrontArticle>();
		List<Integer> temp = articleRepo.findArticleIdByEmailFromToFollow(email, from, to);
		for (int l = 0; l < temp.size(); l++)
			result.add(makeFront(email, temp.get(l)));
		return result;
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

		boolean like = likeRepo.findLike(article_id, email).isPresent();
		boolean scrap = scrapRepo.findScrap(email, article_id).isPresent();
		String profile_photo = profileRepo.findProfilePhotoByEmail(now.getEmail());
		FrontArticle ar = FrontArticle.builder().article_id(article_id).email(now.getEmail()).writer(now.getWriter())
				.reg_time(now.getReg_time()).image(now.getImage()).profile_photo(profile_photo)
				.content(now.getContent()).link(now.getLink()).like_cnt(now.getLike_cnt()).like(like)
				.comment_cnt(now.getComment_cnt()).scrap_cnt(now.getScrap_cnt()).tag_name(temp)
				.scrap(scrap).build();

		return ar;
	}

	@Override
	@Transactional
	public List<FrontArticle> findByPageRequestTag(PageRequest pageRequest, String email) {
		// 1. 태그기반 피드
		// front에서 email get -> favtag테이블에서 해당 이메일로 tag_id 찾아와
		// ArticleTag테이블에서 tag_id로 article_id들 리스트로 받아와
		// Article테이블에서 article_id로 article리스트 다 데려와
		// ArticleTag테이블에서 article_id로 List<tag_id> 가져와 => Tag테이블에서 tag_id로 tag_name 가져와

		List<Article> list = articleRepo.findArticlesByTag(pageRequest, email).stream().collect(Collectors.toList());
		List<FrontArticle> result = new ArrayList<FrontArticle>();
		for (int i = 0; i < list.size(); i++)
			result.add(makeFront(email, list.get(i).getArticle_id()));
		return result;
	}

	@Override
	@Transactional
	public List<FrontArticle> findByPageRequestFollow(PageRequest pageRequest, String email) {
		// 2. 팔로우기반 피드
		// following테이블에서 from_user=email로 to_user 리스트 찾아와
		// article 테이블에서 uid = email인 List<article>로 다 가져가
		List<Article> list = articleRepo.findArticleByFollow(pageRequest, email).stream().collect(Collectors.toList());
		List<FrontArticle> result = new ArrayList<FrontArticle>();
		for (int i = 0; i < list.size(); i++)
			result.add(makeFront(email, list.get(i).getArticle_id()));
		return result;
	}

}
