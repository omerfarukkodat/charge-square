package com.kodat.of.chargingstationservice.station;

import com.kodat.of.chargingstationservice.station.dto.StationDto;

import java.util.List;

public interface StationService {
    List<StationDto> getAllStations();

    StationDto getStationById(Long id);

    StationDto addStation(StationDto stationDto);
}
