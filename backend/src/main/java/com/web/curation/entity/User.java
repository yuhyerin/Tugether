package com.web.curation.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.web.curation.util.CipherUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User {

	@Id
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "signup_date", insertable = false)
	private Date signupDate;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "gender", nullable = false)
	private char gender;

	@Column(name = "birth_year", nullable = false)
	private int birthYear;

	@Column(name = "init_pwd_status")
	private boolean initPwdStatus; // 비밀번호 초기화 상태

	@Enumerated(EnumType.STRING)
	private OAuthPlatform oauthPlatform = OAuthPlatform.TUGETHER;

	public void setInitPwdStatusYes() {
		this.initPwdStatus = true;
	}

	public void setInitPwdStatusNo() {
		this.initPwdStatus = false;
	}

	public void setOAuthPlatform(OAuthPlatform op) {
		this.oauthPlatform = op;
	}

	// 비밀번호 암호화
	public void setEncryptedPassword() {
		this.password = CipherUtil.encrypt(this.password);
	}

}
