package com.djawalkar;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class DataTransformations {
    public Flux<String> mapExample(){
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .log();
    }
    public Flux<String> immutabilityExample(){
        var namesFlux = Flux.fromIterable(List.of("alex", "ben", "chloe"));
        namesFlux.map(String::toUpperCase).log();

        return namesFlux;
    }

    public Flux<String> filterExample(){
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .filter(s -> s.length() > 3)
                .log();
    }

    public Flux<String> flatMapExample(){
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .flatMap(this::splitString)
                .log();
    }

    public Flux<String> splitString(String name){
        String[] chars = name.split("");
        return Flux.fromArray(chars);
    }

    public Flux<String> flatMapAsyncExample(){
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .flatMap(this::splitStringWithDelay)
                .log();
    }

    public Flux<String> splitStringWithDelay(String name){
        String[] chars = name.split("");
        var delay = new Random().nextInt(1000);
        return Flux.fromArray(chars)
                .delayElements(Duration.ofMillis(delay));
    }

    public Flux<String> concatMapExample(){
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .concatMap(this::splitStringWithDelay)
                .log();
    }
}
