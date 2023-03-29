package com.transperfect.movie.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transperfect.movie.exceptions.InternalServerException;
import com.transperfect.movie.exceptions.NotFoundException;
import com.transperfect.movie.models.Movie;
import com.transperfect.movie.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class RecommendedMovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/recommendations")
    public ResponseEntity<List<Movie>> getRecommendedMoviesOf(@RequestParam String genre) throws InternalServerException, NotFoundException {
        final List<Movie> movies = this.movieService.getMovies();
        final List<Movie> recommendedMovies = this.movieService.getRecommendedMovies(genre, movies);
        return ResponseEntity.ok(recommendedMovies);
    }

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getMovies() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("movies.json");
        List<Movie> movies = objectMapper.readValue(inputStream, new TypeReference<List<Movie>>(){});
        return ResponseEntity.ok(movies);
    }

}
