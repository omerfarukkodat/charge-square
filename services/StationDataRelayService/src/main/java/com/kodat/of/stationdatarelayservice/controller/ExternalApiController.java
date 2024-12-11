package com.kodat.of.stationdatarelayservice.controller;

import com.kodat.of.commondto.dto.StationDto;
import com.kodat.of.stationdatarelayservice.service.ExternalApiFetchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/fetch")
public class ExternalApiController {
    private final ExternalApiFetchService externalApiFetchService;

    public ExternalApiController(ExternalApiFetchService externalApiFetchService) {
        this.externalApiFetchService = externalApiFetchService;
    }


    @GetMapping
    public Mono<List<StationDto>> getAllStations(){
        return externalApiFetchService.getAllStations();
    }
}
