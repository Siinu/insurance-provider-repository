package com.boot.ms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movies {

	@Id
	@Column(name = "movie_id")
	private int movie_Id;
	@Column(name = "movie_name")
	private String movie_Name;
	@Column(name = "movie_genre")
	private String movie_Genre;
	@Column(name = "director_id")
	private int directorId;
	private String port;
}
