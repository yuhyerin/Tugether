package com.web.curation.dto.tag;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="tag")
@Table(name="tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Tag {

	@Id
	private int tag_id;
	
	@Column(name = "tag_name")
	private String tag_name;
	
	@Column(name = "fav_cnt")
	private int fav_cnt;
	
	@Column(name = "article_cnt")
	private int article_cnt;
	
}
