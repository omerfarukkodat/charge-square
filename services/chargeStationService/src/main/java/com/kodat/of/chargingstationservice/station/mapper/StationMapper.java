package com.kodat.of.chargingstationservice.station.mapper;

import com.kodat.of.chargingstationservice.station.Station;
import com.kodat.of.chargingstationservice.station.dto.StationDto;

import java.util.List;
import java.util.stream.Collectors;

public class StationMapper {
    public static StationDto toStationDto(Station station) {
        return StationDto.builder()
                .id(station.getId())
                .name(station.getName())
                .latitude(station.getLatitude())
                .longitude(station.getLongitude())
                .chargingSpeed(station.getChargingSpeed())
                .build();
    }

    public static Station toStation(StationDto stationDto) {
        return Station.builder()
                .name(stationDto.getName())
                .latitude(stationDto.getLatitude())
                .longitude(stationDto.getLongitude())
                .chargingSpeed(stationDto.getChargingSpeed())
                .build();
    }

    public static List<StationDto> toStationDtoList(List<Station> stations) {
       return stations.stream()
               .map(StationMapper::toStationDto)
               .collect(Collectors.toList());
    }
}
