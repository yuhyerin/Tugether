package com.web.curation.dto.article;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="following")
@Table(name="following")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Following {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// 얘가 auto_increment
	private int uid;
	
	@Column(name="from_user")
	private String from_user;
	
	@Column(name="to_user")
	private String to_user;
}
