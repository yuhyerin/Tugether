package com.web.curation.service.tag;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.repo.FavtagRepo;
import com.web.curation.repo.TagRepo;

@Service
public class TagServiceImpl implements TagService{

	@Autowired
	private FavtagRepo favtagRepo;
	
	@Autowired
	private TagRepo tagRepo;
	

	// 태그 업데이트
	@Override
	public ArrayList<Integer> updateFavtag(ArrayList<String> favtaglist) {
		
		ArrayList<Integer> favtagIdlist  = new ArrayList<Integer>();
		for(int i=0; i<favtaglist.size(); i++) {
			
			if(tagRepo.findTagIdByTagName(favtaglist.get(i)).isPresent()) { //존재한다면, 
				
				int tag_id = tagRepo.findTagIdByTagName(favtaglist.get(i)).get();
				favtagIdlist.add(tag_id);
				
			}else { //존재하지 않으면, 
				// 해당태그명이 테이블에 없다면, 태그 테이블에 등록하고 
				System.out.println("TagServiceImpl - 해당태그는 태그테이블에 존재하지 않아요.");
				tagRepo.addTag(favtaglist.get(i),1);
				int tag_id = tagRepo.findTagIdByTagName(favtaglist.get(i)).get();
				favtagIdlist.add(tag_id);
			}
			
			
		}
		return favtagIdlist;
		
	}

	

}
