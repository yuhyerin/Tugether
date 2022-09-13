package com.web.curation.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
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

	@Column(name = "temp")
	private boolean temp;

	public boolean getTemp() {
		return this.temp;
	}

}
