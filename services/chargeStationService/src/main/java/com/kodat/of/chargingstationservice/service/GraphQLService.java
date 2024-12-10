package com.kodat.of.chargingstationservice.service;

import com.kodat.of.chargingstationservice.dto.GraphqlStationDto;

import java.util.List;

public interface GraphQLService {

    List<GraphqlStationDto> getAllStations();

    GraphqlStationDto getStationById(String id);

    GraphqlStationDto addStation(GraphqlStationDto stationDto);

    List<GraphqlStationDto> getStationsByRadius(Double latitude, Double longitude, Double radius);

    List<GraphqlStationDto> searchStations(String name, String usageCost);

    List<GraphqlStationDto> fastChargingStations();
}
