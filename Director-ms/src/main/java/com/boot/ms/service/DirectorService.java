package com.boot.ms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.ms.entity.Director;
import com.boot.ms.repository.DirectorRepository;

@Service
public class DirectorService {

	@Autowired
	DirectorRepository repository;

	public List<Director> getAllDirectors() {
		return repository.findAll();
	}

	public Director addDirector(Director director) {
		return repository.save(director);
	}

	public Director getDirectorById(int director_Id) {
		return repository.findById(director_Id).orElse(null);
	}

	public Director getUpdateDirector(Director director) {
		Director directorData = repository.findById(director.getDirectorId()).get();
		directorData.setDirectorName(director.getDirectorName());
		return repository.save(directorData);
	}

	public void deleteDirector(int director_Id) {
		repository.deleteById(director_Id);
	}
}