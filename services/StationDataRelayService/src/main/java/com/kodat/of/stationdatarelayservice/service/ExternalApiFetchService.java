package com.kodat.of.stationdatarelayservice.service;


import com.kodat.of.commondtomodule.dto.StationDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ExternalApiFetchService {
    Mono<List<StationDto>> getAllStations();
}