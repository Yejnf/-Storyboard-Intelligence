package com.example.alibabademo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;

@RestController
@RequestMapping("/event-stream")
public class EventStreamController {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getEventStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> "Event " + sequence + " at " + LocalTime.now());
    }
}