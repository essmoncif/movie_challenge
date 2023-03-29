package com.transperfect.movie;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transperfect.movie.exceptions.InternalServerException;
import com.transperfect.movie.exceptions.NotFoundException;
import com.transperfect.movie.models.Movie;
import com.transperfect.movie.services.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootTest
public class MovieServiceTest {

    @Mock
    private InputStream inputStream;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MovieService movieService;

    public MovieServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getRecommendedMovies() throws IOException, InternalServerException, NotFoundException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("movies.json");
        List<Movie> movies = objectMapper.readValue(inputStream, new TypeReference<List<Movie>>(){});
        List<Movie> actualMovies = movieService.getRecommendedMovies("Drama", movies);
        Assertions.assertEquals(actualMovies.size(), 2);
        Assertions.assertEquals(actualMovies.get(0).getReleaseYear(), 1994);
        Assertions.assertEquals(actualMovies.get(1).getReleaseYear(), 1972);
    }

}
