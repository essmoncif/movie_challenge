package com.transperfect.movie.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transperfect.movie.exceptions.InternalServerException;
import com.transperfect.movie.exceptions.MessageError;
import com.transperfect.movie.exceptions.NotFoundException;
import com.transperfect.movie.models.Movie;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MovieService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${api.movies}")
    private String moviesEndpoint;

    public List<Movie> getMovies() throws InternalServerException {
        try {
        String response = restTemplate.getForObject(moviesEndpoint, String.class);
        return objectMapper.readValue(response, new TypeReference<List<Movie>>() {});
        } catch (JsonProcessingException e) {
            throw new InternalServerException(MessageError.INTERNAL_SERVER_ERR);
        }
    }

    public List<Movie> getRecommendedMovies(final String genre, List<Movie> movies) throws NotFoundException, InternalServerException {
        List<Movie> recommendedMovies = movies.stream()
                .filter(movie -> movie.getGenre().equals(genre))
                .sorted((Comparator.comparingInt(Movie::getReleaseYear).reversed()))
                .collect(Collectors.toList());
        if (recommendedMovies.isEmpty())
            throw new NotFoundException(MessageError.NOT_FOUND_MOVIE);
        return recommendedMovies;

    }
}
