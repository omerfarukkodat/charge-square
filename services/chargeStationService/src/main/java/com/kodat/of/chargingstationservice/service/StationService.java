package com.kodat.of.chargingstationservice.service;


import com.kodat.of.commondto.dto.StationDto;
import java.util.List;

public interface StationService {
    List<StationDto> getAllStations();
    StationDto getStationById(Long id);
    StationDto addStation(StationDto stationDto);
}
