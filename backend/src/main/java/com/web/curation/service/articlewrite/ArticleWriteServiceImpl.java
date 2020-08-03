package com.web.curation.service.articlewrite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.article.Article;
import com.web.curation.repo.ArticleWriteRepo;

@Service
public class ArticleWriteServiceImpl implements ArticleWriteService{

	@Autowired
	ArticleWriteRepo articleRepo;
	
	@Override
	public ResponseEntity<Object> addArticle(Article article) {
		
		ResponseEntity<Object> response = null;
		BasicResponse result = new BasicResponse();
		try {
			String email = article.getEmail();
			String writer = article.getWriter();
			String image = article.getImage();
			String content = article.getContent();
			String link = article.getLink();
			articleRepo.insertArticle(email, writer, image, content, link);
			System.out.println("디비에 글을 넣었습니다.");
			result.status = true;
			response = new ResponseEntity<>(result, HttpStatus.OK);
			
		}catch(IllegalArgumentException e) {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
		
	}

	
}
