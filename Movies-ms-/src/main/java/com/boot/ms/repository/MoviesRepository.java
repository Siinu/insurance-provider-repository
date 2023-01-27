package com.boot.ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.ms.entity.Movies;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Integer> {
	public List<Movies> findAllByDirectorId(int directorId);
}
