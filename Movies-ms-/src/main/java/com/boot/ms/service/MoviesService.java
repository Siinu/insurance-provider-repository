package com.boot.ms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.ms.entity.Movies;
import com.boot.ms.repository.MoviesRepository;

@Service
public class MoviesService {

	@Autowired
	MoviesRepository repository;

	public List<Movies> getAllMovies() {
		return repository.findAll();
	}

	public Movies addMovie(Movies movies) {
		return repository.save(movies);
	}

	public Movies getMovieById(int movie_Id) {
		return repository.findById(movie_Id).orElse(null);
	}

	public Movies getUpdateMovies(Movies movies) {
		Movies moviesData = repository.findById(movies.getMovie_Id()).get();
		moviesData.setMovie_Name(movies.getMovie_Name());
		moviesData.setMovie_Genre(movies.getMovie_Genre());
		return repository.save(moviesData);
	}

	public void deleteMovie(int movie_Id) {
		repository.deleteById(movie_Id);
	}

	public List<Movies> getMoviesByDirectorId(int directorId) {
		return repository.findAllByDirectorId(directorId);
	}

}
