package com.web.curation.dto.comment;

import java.time.LocalDateTime;

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

@Entity(name = "comment")
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int comment_id;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "article_id", nullable = false)
	private int article_id;

	@Column(name = "content")
	private String content;

	@Column(name = "reg_time", insertable = false)
	private LocalDateTime reg_time;

}
