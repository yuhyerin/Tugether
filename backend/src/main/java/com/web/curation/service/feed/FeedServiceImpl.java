package com.web.curation.service.feed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dto.article.Article;
import com.web.curation.dto.article.FrontArticle;
import com.web.curation.repo.ArticleRepo;
import com.web.curation.repo.ArticleTagRepo;
import com.web.curation.repo.FavtagRepo;
import com.web.curation.repo.FollowingRepo;
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

	SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	// 1. 태그기반 피드
	@Override
	public List<FrontArticle> findArticleListByTag(String email) {
		List<FrontArticle> result = new ArrayList<>();
		List<Article> res = new ArrayList<>();
		// email -> favtag테이블에서 해당 이메일로 List<tag_id>찾아와
//		System.out.println("service 입장 : findArticleListByTag");
		List<Integer> tags = favtagRepo.findTagIdByEmail(email);
		System.out.println(tags.toString());// 디버깅
		TreeSet<Integer> tagList = new TreeSet<>();
		for (int i = 0; i < tags.size(); i++)
			tagList.add(tags.get(i));
		System.out.println("tagList: " + tagList.toString());

		// ArticleTag테이블에서 tag_id로 article_id들 리스트로 받아와
		TreeSet<Integer> articleIdList = new TreeSet<>();
		for (int l = 0; l < tagList.size(); l++) {
			List<Integer> temp = articletagRepo.findArticleIdByTagId(tagList.pollFirst());
			for (int m = 0; m < temp.size(); m++)
				articleIdList.add(temp.get(m));
		}
		if (articleIdList.size() == 0)
			System.out.println("articleIdList 없음");
		else
			System.out.println("articleIdList: " + articleIdList.toString());
		int length = articleIdList.size();
		for (int i = 0; i < length; i++) {
			// Article테이블에서 article_id로 article리스트 다 데려와
//			System.out.println(articleIdList.get(i)+":");
			List<Article> a = articleRepo.findArticleByArticleId(articleIdList.pollFirst());
			System.out.println(a.toString());
			for (int j = 0; j < a.size(); j++)
				res.add(a.get(j));
		}
		if (res.size() == 0)
			System.out.println("res 없음");
		else
			System.out.println(res.toString());

		// ArticleTag테이블에서 article_id로 List<tag_id> 가져와 => Tag테이블에서 tag_id로 tag_name 가져와
		for (int i = 0; i < res.size(); i++) {
			Article now = res.get(i);
			List<Integer> taglist = articletagRepo.findTagIdByArticleId(now.getArticle_id());
			String[] temp = new String[taglist.size()];
			for (int j = 0; j < taglist.size(); j++) {
				temp[j] = tagRepo.findTagNameByTagId(taglist.get(j));
			}
			FrontArticle ar = new FrontArticle(now.getArticle_id(), now.getEmail(), now.getWriter(),
					format.format(now.getReg_time()), now.getImage(), now.getContent(), now.getLink(),
					now.getLike_cnt(), now.getScrap_cnt(), temp);
			System.out.println(ar.toString());
			result.add(ar);
		}

		return result;
	}

	// 2. 팔로우기반 피드
	@Override
	public List<FrontArticle> findArticleListByFollow(String email) {
		List<FrontArticle> result = new ArrayList<>();

		// following테이블에서 from_user=email로 to_user 리스트 찾아와
		List<String> toUser = followingRepo.findToUserByFromUser(email);
		System.out.println(toUser.toString());

		// article 테이블에서 uid = email인 List<article>로 다 가져가
		for (int l = 0; l < toUser.size(); l++) {
			List<Article> list = articleRepo.findArticleByEmail(toUser.get(l));
			for (int m = 0; m < list.size(); m++) {
				// ArticleTag테이블에서 article_id로 List<tag_id> 가져와 => Tag테이블에서 tag_id로 tag_name 가져와
				Article now = list.get(m);
				if (!result.contains(now)) {
					List<Integer> taglist = articletagRepo.findTagIdByArticleId(now.getArticle_id());
					String[] temp = new String[taglist.size()];
					for (int j = 0; j < taglist.size(); j++) {
						temp[j] = tagRepo.findTagNameByTagId(taglist.get(j));
					}
					result.add(new FrontArticle(now.getArticle_id(), now.getEmail(), now.getWriter(),
							format.format(now.getReg_time()), now.getImage(), now.getContent(), now.getLink(),
							now.getLike_cnt(), now.getScrap_cnt(), temp));
				}
			}
		}

		return result;
	}

}
