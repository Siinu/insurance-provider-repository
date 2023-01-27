package com.boot.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.boot.ms.entity.Movies;
import com.boot.ms.model.Director;
import com.boot.ms.model.FailureResponse;
import com.boot.ms.model.MoviesDirectorResponse;
import com.boot.ms.service.MoviesService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/movies")
public class MoviesController {

	@Autowired
	MoviesService service;

	@Autowired
	RestTemplate template;

	@Autowired
	Environment environment;

	@GetMapping("/getAllMovies")
	public ResponseEntity<List<Movies>> getAllDirectors() {
		return new ResponseEntity<List<Movies>>(service.getAllMovies(), HttpStatus.OK);
	}

	@PostMapping("/insert")
	public ResponseEntity<Movies> addMovies(@RequestBody Movies movies) {
		return new ResponseEntity<Movies>(service.addMovie(movies), HttpStatus.OK);
	}

//	@GetMapping("/getMovieById/{movie_Id}")
//	public ResponseEntity<?> getMovieById(@PathVariable int movie_Id) {
//		Movies movies = service.getMovieById(movie_Id);
//		ResponseEntity<?> responseEntity = null;
//
//		if (movies == null) {
//			responseEntity = new ResponseEntity<String>("No Movie found with the given id", HttpStatus.NOT_FOUND);
//		} else {
//			responseEntity = new ResponseEntity<Movies>(service.getMovieById(movie_Id), HttpStatus.OK);
//		}
//		return responseEntity;
//	}
//	

	// Load Balancing

	@GetMapping("/getMovieById/{movie_Id}")
	public ResponseEntity<Movies> getMoviesById(@PathVariable int movie_Id) {
		Movies movies = service.getMovieById(movie_Id);
		movies.setPort(environment.getProperty("local.server.port"));
		return new ResponseEntity<Movies>(movies, HttpStatus.OK);
	}

	@PutMapping("/updateMovie")
	public ResponseEntity<Movies> updateMovies(@RequestBody Movies movies) {
		Movies moviesList = service.getUpdateMovies(movies);
		return new ResponseEntity<Movies>(moviesList, HttpStatus.OK);
	}

	@GetMapping("/getMoviesByDirector/{directorId}")
	@CircuitBreaker(name = "DIRECTOR-MS", fallbackMethod = "callFallBack")
	public ResponseEntity<?> getMoviesAndDirector(@PathVariable int directorId) {
		List<Movies> movieList = service.getMoviesByDirectorId(directorId);
		ResponseEntity<?> responseEntity = null;
		if (movieList.isEmpty()) {
			responseEntity = new ResponseEntity<String>("No movies found by the director id: " + directorId,
					HttpStatus.NOT_FOUND);
		} else {
			String url = "http://localhost:9090/director/getDirectorById/" + directorId;
			Director director = template.getForObject(url, Director.class);
			MoviesDirectorResponse response = new MoviesDirectorResponse();
			response.setDirector(director);
			response.setMoviesList(movieList);
			responseEntity = new ResponseEntity<MoviesDirectorResponse>(response, HttpStatus.OK);
		}
		return responseEntity;
	}

	public ResponseEntity<?> callFallBack(Exception e) {
		FailureResponse response = new FailureResponse();
		response.setMessage("Director Service is down !! Try after some time");
		response.setMoviesList(service.getAllMovies());
		return new ResponseEntity<FailureResponse>(response, HttpStatus.OK);

	}

	@DeleteMapping("/deleteMovies/{movie_Id}")
	public ResponseEntity<String> deleteMovie(@PathVariable int movie_Id) {
		service.deleteMovie(movie_Id);
		return new ResponseEntity<String>("Deleted Succesfully", HttpStatus.OK);
	}
}
