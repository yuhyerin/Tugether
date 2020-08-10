package com.web.curation.service.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dto.tag.Tag;
import com.web.curation.repo.FavtagRepo;
import com.web.curation.repo.TagRepo;

@Service
public class TagServiceImpl implements TagService{

	@Autowired
	private FavtagRepo favtagRepo;
	
	@Autowired
	private TagRepo tagRepo;
	

	// 태그 테이블에 해당태그명의 태그아이디 조회 
	@Override
	public int getTagByTagName(String tag_name) {
		
		return tagRepo.findTagIdByTagName(tag_name);
	}

	// 태그 테이블에 넣기. 
	@Override
	public void addTag(String tagname) {
		
		
	}

	

}
