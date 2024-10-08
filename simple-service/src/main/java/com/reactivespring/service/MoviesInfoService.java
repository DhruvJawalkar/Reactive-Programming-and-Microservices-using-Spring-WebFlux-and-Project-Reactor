package com.reactivespring.service;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.repository.MovieInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class MoviesInfoService {
    private final MovieInfoRepository movieInfoRepository;

    public MoviesInfoService(MovieInfoRepository movieInfoRepository){
        this.movieInfoRepository = movieInfoRepository;
    }

    public Flux<MovieInfo> getAllMovies(){
        return movieInfoRepository.findAll();
    }

    public Mono<MovieInfo> addMovie(MovieInfo movieInfo){
        log.info("addMovieInfo : {} " , movieInfo );
        return movieInfoRepository.save(movieInfo).log();
    }

    public Mono<MovieInfo> getMovieById(String id){
        return movieInfoRepository.findById(id);
    }

    public Flux<MovieInfo> getMoviesByYear(Integer year){
        return movieInfoRepository.findByYear(year);
    }

    public Mono<MovieInfo> updateMovieInfo(MovieInfo movieInfo, String id) {
        return movieInfoRepository.findById(id)
                .flatMap(movieInfo1 -> {
                    movieInfo1.setCast(movieInfo.getCast());
                    movieInfo1.setName(movieInfo.getName());
                    movieInfo1.setRelease_date(movieInfo.getRelease_date());
                    movieInfo1.setYear(movieInfo.getYear());
                    return movieInfoRepository.save(movieInfo1);
                });
    }

    public Mono<Void> deleteMovieInfoById(String id) {
        return movieInfoRepository.deleteById(id);
    }
}
