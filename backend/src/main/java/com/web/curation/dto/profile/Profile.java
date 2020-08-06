package com.web.curation.dto.profile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="profile")
@Table(name="profile")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Profile {

		
		@Id
		private String email;
		
		private String nickname;
		
		@Column(name = "profile_photo")
		private String profile_photo;
		
		@Column(name = "following_cnt")
		private int following_cnt;
		
		@Column(name = "follower_cnt")
		private int follower_cnt;
		
		@Column(name = "article_cnt")
		private int article_cnt;

}
		
