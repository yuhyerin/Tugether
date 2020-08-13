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

@Entity(name="scrap")
@Table(name="scrap")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scrap {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// 얘가 auto_increment
	private int uid;
	
	@Column(name="email", nullable=false)
	private String email;

	@Column(name="article_id", nullable=false)
	private int article_id;
	
	
}
