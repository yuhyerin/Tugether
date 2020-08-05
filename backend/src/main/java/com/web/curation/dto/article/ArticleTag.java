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


@Entity(name="articletag")
@Table(name="articletag")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ArticleTag {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// 얘가 auto_increment
	private int uid;
	
	@Column(name="article_id")
	private int article_id;
	
	@Column(name="tag_id")
	private int tag_id;
	
	
	
	

}
