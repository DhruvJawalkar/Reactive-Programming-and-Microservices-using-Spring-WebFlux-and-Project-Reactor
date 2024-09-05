package com.djawalkar;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class DataTransformationsTest {
    DataTransformations dataTransformations = new DataTransformations();
    @Test
    void mapExample() {
        var namesFlux = dataTransformations.mapExample();
        StepVerifier.create(namesFlux)
                .expectNext("ALEX", "BEN", "CHLOE")
                .verifyComplete();
    }

    @Test
    void immutabilityExample() {
        var namesFlux = dataTransformations.immutabilityExample();
        StepVerifier.create(namesFlux)
                .expectNext("alex", "ben", "chloe")
                .verifyComplete();
    }

    @Test
    void filterExample() {
        var namesFlux = dataTransformations.filterExample();
        StepVerifier.create(namesFlux)
                .expectNext("alex", "chloe")
                .verifyComplete();
    }

    @Test
    void flatMapExample() {
        var charsFlux = dataTransformations.flatMapExample();
        StepVerifier.create(charsFlux)
                .expectNext("a", "l", "e", "x")
                .expectNextCount(3)
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    //~4secs to complete
    //flatMap is asynchronous and returns the first item which is returned
    void flatMapAsyncExample() {
        var charsFlux = dataTransformations.flatMapAsyncExample();
        StepVerifier.create(charsFlux)
                //.expectNext("a", "l", "e", "x")
                .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    //~8secs to complete
    //concatMap is synchronous and waits for each item to arrive
    void concatMapAsyncExample() {
        var charsFlux = dataTransformations.concatMapExample();
        StepVerifier.create(charsFlux)
                .expectNext("a", "l", "e", "x", "b", "e", "n", "c", "h", "l", "o", "e")
                .verifyComplete();
    }
}