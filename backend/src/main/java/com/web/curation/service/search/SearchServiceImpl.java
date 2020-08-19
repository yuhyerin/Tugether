package com.web.curation.service.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dto.article.Article;
import com.web.curation.dto.article.FrontArticle;
import com.web.curation.dto.profile.Profile;
import com.web.curation.dto.tag.Tag;
import com.web.curation.repo.ArticleRepo;
import com.web.curation.repo.ArticleTagRepo;
import com.web.curation.repo.LikeyRepo;
import com.web.curation.repo.ProfileRepo;
import com.web.curation.repo.ScrapRepo;
import com.web.curation.repo.TagRepo;
//1. 태그테이블에서 태그네임으로 태그아이디 찾고 
// search_cnt++ 해줘야해 ->얘는 검색버튼 눌렀을때!

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private TagRepo tagRepo;
	@Autowired
	private ArticleRepo articleRepo;
	@Autowired
	private ArticleTagRepo articletagRepo;
	@Autowired
	private LikeyRepo likeRepo;
	@Autowired
	private ProfileRepo profileRepo;
	@Autowired
	private ScrapRepo scrapRepo;

	@Override // 키워드 포함한 태그이름 찾기 // 이게 드롭다운용
	public List<String> findTagNamesByTag(String keyword) {
		List<String> tagNames = tagRepo.findTagNameByTagNameContains(keyword);
		return tagNames;
	}

	@Override // 얘가 피드용
	public List<FrontArticle> findArticlesByTagName(String email, String keyword) {
		List<Integer> temp = articleRepo.findArticleIDByTagNameForSearch(keyword);
		List<FrontArticle> result = new ArrayList<FrontArticle>();
		for (int i = 0; i < temp.size(); i++) {
			result.add(makeFront(email, temp.get(i)));
		}
		Optional<Tag> t = tagRepo.findTagByTagName(keyword);
		if (t.isPresent()) {
			Tag test = t.get();
			System.out.println("test : " + test.toString());
			test.setSearch_cnt(test.getSearch_cnt() + 1);
			tagRepo.save(test);
		}
		return result;
	}

	@Override
	public List<Profile> findUserByNickname(String keyword, String email) {
		List<Profile> list = profileRepo.findUserByNickname(keyword, email);
		return list;
	}

	@Override 
	public FrontArticle makeFront(String email, int article_id) {

		Article now = articleRepo.findArticleByArticleId(article_id);
		System.out.println("now : " + now.toString());
		List<Integer> taglist = articletagRepo.findTagIdByArticleId(now.getArticle_id()); // 아티클태그케이블에서 태그 가져와야 프론트에 줄 수
																							// 있음
		String[] temp = new String[taglist.size()]; // 태그 리스트를 태그 배열로 만들거임
		for (int j = 0; j < taglist.size(); j++) {
			temp[j] = tagRepo.findTagNameByTagId(taglist.get(j)); // 태그테이블에서 태그아이디로 태그네임 찾아서 배열 저장
		}
//		if(article_id == 39)
//			System.out.println(now.getLike_cnt());
		boolean like = likeRepo.findLike(article_id, email).isPresent();
		boolean scrap = scrapRepo.findScrap(email, article_id).isPresent();
		String profile_photo = profileRepo.findProfilePhotoByEmail(now.getEmail());
		FrontArticle ar = FrontArticle.builder().article_id(article_id).email(now.getEmail()).writer(now.getWriter())
				.reg_time(now.getReg_time()).image(now.getImage()).profile_photo(profile_photo)
				.content(now.getContent()).link(now.getLink()).like_cnt(now.getLike_cnt()).like(like)
				.comment_cnt(now.getComment_cnt()).scrap_cnt(now.getScrap_cnt()).tag_name(temp).scrap(scrap).build();

		return ar;
	}

}
