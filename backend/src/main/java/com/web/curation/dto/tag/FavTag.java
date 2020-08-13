package com.web.curation.dto.tag;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "favtag")
@Table(name = "favtag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class FavTag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int fav_id;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "tag_id", nullable = false)
	private int tag_id;

}
