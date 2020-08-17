package com.web.curation.service.notice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dto.notice.FrontNotice;
import com.web.curation.dto.notice.Notice;
import com.web.curation.repo.ArticleRepo;
import com.web.curation.repo.NoticeRepo;
import com.web.curation.repo.ProfileRepo;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeRepo noticeRepo;
	@Autowired
	private ProfileRepo profileRepo;
	@Autowired
	private ArticleRepo articleRepo;

	@Override
	public List<FrontNotice> findNotice(String email) {
		List<Notice> list = noticeRepo.findNotices(email);
		List<FrontNotice> result = new ArrayList<>();
		for (int l = 0; l < list.size(); l++) {
			FrontNotice fn = makeFront(list.get(l), email);
			result.add(fn);
			System.out.println(fn);
		}
		return result;
	}

	@Override
	public FrontNotice makeFront(Notice notice, String email) {
//		Profile pro = profileRepo.findProfileByEmail(email);
		String from_nickname = profileRepo.findNicknameByEmail(notice.getNotice_from());
		String profile_photo = profileRepo.findProfilePhotoByEmail(notice.getNotice_from());

		FrontNotice fn = FrontNotice.builder().notice_to(notice.getNotice_to()).notice_type(notice.getNotice_type())
				.notice_from(notice.getNotice_from()).from_nickname(from_nickname).profile_photo(profile_photo)
				.reg_time(notice.getReg_time()).build();
		switch (fn.getNotice_type()) {
		case 1: // 댓글
			String img = articleRepo.findImgByArticleId(notice.getArticle_id());
			fn.setComment_id(notice.getComment_id());
			fn.setArticle_id(notice.getArticle_id());
			fn.setImg(img);
			break;
		case 2: // 좋아요
			img = articleRepo.findImgByArticleId(notice.getArticle_id());
			fn.setArticle_id(notice.getArticle_id());
			fn.setImg(img);
			break;
		case 3:	// 팔로우
			profile_photo = profileRepo.findProfilePhoto(notice.getNotice_from());
			img = articleRepo.findImgByArticleId(notice.getArticle_id());
//			fn.setImg(img);
			fn.setNotice_from(notice.getNotice_from());
			fn.setFrom_nickname(profileRepo.findNicknameByEmail(notice.getNotice_from()));
			fn.setProfile_photo(profile_photo);
			break;
		}
		return fn;
	}

	@Override
	public void deleteNoticeByArticleId(int articleid) {
		noticeRepo.deleteNoticeByArticleId(articleid);
	}

}
