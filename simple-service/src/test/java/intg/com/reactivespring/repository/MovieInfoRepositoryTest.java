package com.reactivespring.repository;

import com.reactivespring.domain.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryTest {
    @Autowired
    MovieInfoRepository movieInfoRepository;

    @BeforeEach
    void setUp() {
        var movies =  List.of(new MovieInfo(null, "Batman Begins",
                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(null, "The Dark Knight",
                        2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "Dark Knight Rises",
                        2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

        movieInfoRepository.saveAll(movies).blockLast();
    }

    @AfterEach
    void tearDown() {
        movieInfoRepository.deleteAll().block();
    }

    @Test
    void findAll() {
        var moviesFlux = movieInfoRepository.findAll().log();

        StepVerifier.create(moviesFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void findById() {
        var movieInfoMono = movieInfoRepository.findById("abc").log();

        StepVerifier.create(movieInfoMono)
                .assertNext(movieInfo -> assertEquals(movieInfo.getName(), "Dark Knight Rises"))
                .verifyComplete();
    }

    @Test
    void saveMovieInfo() {
        var newMovie = new MovieInfo(null, "sample",
                2005, List.of("test"), LocalDate.parse("2005-06-15"));

        var savedMovieInfoMono = movieInfoRepository.save(newMovie).log();

        StepVerifier.create(savedMovieInfoMono)
                .assertNext(movieInfo -> assertNotNull(movieInfo.getMovieInfoId()) )
                .verifyComplete();
    }

    @Test
    void updateMovieInfo() {
        var movieInfo = movieInfoRepository.findById("abc").block();
        assert movieInfo != null;
        movieInfo.setYear(2021);

        var updatedMovieInfo = movieInfoRepository.save(movieInfo).log();

        StepVerifier.create(updatedMovieInfo)
                .assertNext(updatedMovie -> {
                    assertNotNull(updatedMovie.getMovieInfoId());
                    assertEquals(2021, updatedMovie.getYear());
                })
                .verifyComplete();
    }

    @Test
    void deleteMovieInfo() {
        movieInfoRepository.deleteById("abc").block();

        var moviesFlux = movieInfoRepository.findAll().log();

        StepVerifier.create(moviesFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}