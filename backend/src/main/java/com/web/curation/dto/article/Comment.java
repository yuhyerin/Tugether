package com.web.curation.dto.article;

import java.sql.Date;

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

@Entity(name="comment")
@Table(name="comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Comment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int comment_id;
	
	private String email;
	
	@Column(name="article_id")
	private int article_id;
	
	@Column(name="parent_id")
	private int parent_id;
	
	private String content;
	private boolean depth;
	
	@Column(name="reg_time", insertable=false)
	private Date reg_time;
	
}
