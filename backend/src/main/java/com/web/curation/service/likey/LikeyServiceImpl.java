package com.web.curation.service.likey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.repo.LikeyRepo;

@Service
public class LikeyServiceImpl implements LikeyService{

	@Autowired
	private LikeyRepo likeyRepo;
	@Override
	public void deleteLikeyByArticleId(int articleid) {
		likeyRepo.deleteLikeyByArticleId(articleid);
	}
	
}
