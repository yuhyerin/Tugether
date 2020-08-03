package com.web.curation.service.feed;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dto.article.Article;
import com.web.curation.repo.FeedRepo;

@Service
public class FeedServiceImpl implements FeedService {
	
	@Autowired
	private FeedRepo feedRepo;
	
	// 1. 태그기반 피드
	@Override
	public List<Article> findArticleListByTag(String email) {
		List<Article> result = new ArrayList<>();

		// email -> favtag테이블에서 해당 이메일로 List<tag_id>찾아와
		List<String> tagList = feedRepo.findTagIdByEmail(email);
		System.out.println(tagList.toString());//디버깅
		
		// ArticleTag테이블에서 tag_id로 article_id들 리스트로 받아와
		List<String> articleIdList = new ArrayList<>();
		for(int l=0;l<tagList.size();l++) {
			List<String> temp = feedRepo.findArticleIdByTagId(tagList.get(l));
			for(int m=0;m<temp.size();m++)
				articleIdList.add(temp.get(m));
		}
		System.out.println(articleIdList.toString());
		
		for(int i=0;i<articleIdList.size();i++) {
			// Article테이블에서 article_id로 article리스트 다 데려와
			result.add(feedRepo.findArticleByArticleId(articleIdList.get(i)));
		}
		
		return result;
	}


	// 2. 팔로우기반 피드
	@Override
	public List<Article> findArticleListByFollow(String email) {
		List<Article> result = new ArrayList<>();

		// following테이블에서 from_user=email로 to_user 리스트 찾아와
		List<String> toUser = feedRepo.findToUserByFromUser(email);
		System.out.println(toUser.toString());
		
		// article 테이블에서 uid = email인 List<article>로 다 가져가
		
		return null;
	}
	
	

	
}
