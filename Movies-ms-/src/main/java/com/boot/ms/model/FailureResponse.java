package com.boot.ms.model;

import java.util.List;

import com.boot.ms.entity.Movies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FailureResponse {
	private List<Movies> moviesList;
	private String message;
}
