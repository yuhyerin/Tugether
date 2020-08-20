package com.web.curation.dto.tag;

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

@Entity(name = "tag")
@Table(name = "tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 얘가 auto_increment
	private int tag_id;

	@Column(name = "tag_name", nullable = false)
	private String tag_name;

	@Column(name = "fav_cnt")
	private int fav_cnt;

	@Column(name = "article_cnt")
	private int article_cnt;
	
	@Column(name = "search_cnt")
	private int search_cnt;

}
