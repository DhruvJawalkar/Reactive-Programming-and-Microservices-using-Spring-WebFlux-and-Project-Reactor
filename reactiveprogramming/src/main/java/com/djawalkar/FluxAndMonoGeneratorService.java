package com.djawalkar;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class FluxAndMonoGeneratorService {
    public Flux<String> namesFlux(){
        return Flux.fromIterable(List.of("alex", "ben", "chloe"));
    }

    public Mono<String> nameMono(){
        return Mono.just("alex");
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.namesFlux().subscribe(s -> System.out.println("Hello: " + s));

        fluxAndMonoGeneratorService.nameMono().subscribe(s -> System.out.println("Hello from Mono: " + s));
    }
}
