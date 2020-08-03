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

@Entity(name="favtag")
@Table(name="favtag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class FavTag {
	
	@Id
	private int fav_id;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "tag_id")
	private int tag_id;
	
	
}
