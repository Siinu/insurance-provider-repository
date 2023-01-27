package com.boot.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.ms.entity.Director;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer> {

}
