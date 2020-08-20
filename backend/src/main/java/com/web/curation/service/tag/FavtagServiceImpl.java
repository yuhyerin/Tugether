package com.web.curation.service.tag;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.tag.FavTag;
import com.web.curation.repo.FavtagRepo;
import com.web.curation.repo.TagRepo;

@Service
public class FavtagServiceImpl implements FavtagService {

	@Autowired
	private FavtagRepo favtagRepo;

	@Autowired
	private TagRepo tagRepo;

	// favtag 테이블에 insert 하는 문장
	@Override
	public ResponseEntity<Object> addFavtag(String uid, List<Integer> favtaglist) {
		ResponseEntity<Object> response = null;
		BasicResponse result = new BasicResponse();

		try {
			for (int i = 0; i < favtaglist.size(); i++) {
				favtagRepo.insertFavtagList(uid, favtaglist.get(i));
				tagRepo.updateFav_cnt(favtaglist.get(i)); // 관심태그로 등록되었다고 카운팅!
			}
			result.status = true;
			result.data = "success";
			System.out.println(result.toString());
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}

	// 관심태그 등록했는지 검사
	@Override
	public Boolean isSelectFavtag(String email) {
		List<FavTag> favlist = favtagRepo.findFavTagByEmail(email);
		if (favlist.size() != 0) {
			return true;
		}
		return false;

	}

	// 사용자의 관심태그 리스트 조회
	@Override
	public ArrayList<String> getFavtagList(String email) {
		List<FavTag> favlist = favtagRepo.findFavTagByEmail(email);
		ArrayList<String> favtagNamelist = new ArrayList<String>();
		for (int i = 0; i < favlist.size(); i++) {
			int tag_id = favlist.get(i).getTag_id();
			favtagNamelist.add(tagRepo.findTagNameByTagId(tag_id));
		}

		return favtagNamelist;
	}
}
