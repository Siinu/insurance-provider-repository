package com.boot.ms.model;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movies {

	@Id
	@Column(name = "movie_id")
	private int movie_Id;
	@Column(name = "movie_name")
	private String movie_Name;
	@Column(name = "movie_genre")
	private String movie_Genre;
	@Column(name = "director_id")
	private int director_Id;
	private String port;
}
