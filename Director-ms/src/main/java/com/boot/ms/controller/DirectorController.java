package com.boot.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
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

import com.boot.ms.entity.Director;
import com.boot.ms.model.Movies;
import com.boot.ms.service.DirectorService;

@RestController
@RequestMapping("/director")
@RibbonClient(name = "MOVIES-MS")
public class DirectorController {

	@Autowired
	DirectorService service;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/getAllDirectors")
	public ResponseEntity<List<Director>> getAllDirectors() {
		return new ResponseEntity<List<Director>>(service.getAllDirectors(), HttpStatus.OK);
	}

	@PostMapping("/insert")
	public ResponseEntity<Director> addDirector(@RequestBody Director director) {
		return new ResponseEntity<Director>(service.addDirector(director), HttpStatus.OK);
	}

	@GetMapping("/getDirectorById/{director_Id}")
	public ResponseEntity<?> getDirectorById(@PathVariable int director_Id) {
		Director director = service.getDirectorById(director_Id);
		ResponseEntity<?> responseEntity = null;

		if (director == null) {
			responseEntity = new ResponseEntity<String>("No director found with the given id", HttpStatus.NOT_FOUND);
		} else {
			responseEntity = new ResponseEntity<Director>(service.getDirectorById(director_Id), HttpStatus.OK);
		}
		return responseEntity;
	}

	@PutMapping("/updateDirector")
	public ResponseEntity<Director> updateDirector(@RequestBody Director director) {
		Director directorList = service.getUpdateDirector(director);
		return new ResponseEntity<Director>(directorList, HttpStatus.OK);
	}

	@DeleteMapping("/deleteDirector/{director_Id}")
	public ResponseEntity<String> deleteDirector(@PathVariable int director_Id) {
		service.deleteDirector(director_Id);
		return new ResponseEntity<String>("Deleted Succesfully", HttpStatus.OK);
	}

	// Load Balancing
	@GetMapping("/getMovie/{id}")
	public ResponseEntity<Movies> getMovie(@PathVariable int id) {
		Movies movies = restTemplate.getForObject("http://MOVIE-MS/movies/getMovieById/" + id, Movies.class);
		return new ResponseEntity<Movies>(movies, HttpStatus.OK);
	}
}
