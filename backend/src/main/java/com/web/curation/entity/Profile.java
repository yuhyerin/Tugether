package com.web.curation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "profile")
@Table(name = "profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

	@Id
	private String email;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "profile_photo")
	private String profilePhoto;

	@Column(name = "following_cnt")
	private int followingCnt;

	@Column(name = "follower_cnt")
	private int followerCnt;

	@Column(name = "article_cnt")
	private int articleCnt;

}
