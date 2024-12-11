package com.kodat.of.chargingstationservice.service.impl;

import com.kodat.of.chargingstationservice.dto.GraphqlStationDto;
import com.kodat.of.chargingstationservice.entity.Connections;
import com.kodat.of.chargingstationservice.entity.Station;
import com.kodat.of.chargingstationservice.exception.StationNotFoundException;
import com.kodat.of.chargingstationservice.mapper.GraphqlMapper;
import com.kodat.of.chargingstationservice.repository.StationRepository;
import com.kodat.of.chargingstationservice.service.GraphQLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GraphQLServiceImpl implements GraphQLService {

    private static final Logger log = LoggerFactory.getLogger(GraphQLServiceImpl.class);
    private final StationRepository stationRepository;

    public GraphQLServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public List<GraphqlStationDto> getAllStations() {
        List<Station> stationsList = stationRepository.findAll();
        if (stationsList.isEmpty()) {
            log.info("No stations found");
        }
        return GraphqlMapper.graphqlStationDtos(stationsList);
    }

    @Override
    public GraphqlStationDto getStationById(String id) {
        Station station = stationRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new StationNotFoundException("Station not found by id " + id));
        return GraphqlMapper.toGraphqlStationDto(station);
    }

    @Override
    public GraphqlStationDto addStation(GraphqlStationDto graphqlStationDto) {
        Station station = GraphqlMapper.toStationEntity(graphqlStationDto);
        stationRepository.save(station);
        return GraphqlMapper.toGraphqlStationDto(station);
    }

    @Override
    public List<GraphqlStationDto> getStationsByRadius(Double latitude, Double longitude, Double radius) {
        return stationRepository.findAll().stream()
                .filter(station -> calculateDistance(station.getAddressInfo().getLatitude(), station.getAddressInfo().getLongitude(), latitude, longitude) <= radius)
                .map(GraphqlMapper::toGraphqlStationDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GraphqlStationDto> searchStations(String title, String usageCost) {
        return stationRepository.findAll().stream()
                .filter(station -> (title == null || station.getAddressInfo().getTitle().contains(title)) &&
                        (usageCost == null || station.getUsageCost().contains(usageCost)))
                .map(GraphqlMapper::toGraphqlStationDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GraphqlStationDto> fastChargingStations() {
        List<Station> stationsList = stationRepository.findAll();
        if (stationsList.isEmpty()) {
            log.info("Stations not found in the database");
        }
       return stationRepository.findAll().stream()
                .filter(station -> station.getConnections()
                        .stream()
                        .anyMatch(Connections::isFast))
               .map(GraphqlMapper::toGraphqlStationDto)
               .collect(Collectors.toList());

    }

    // Haversine formula
    private double calculateDistance(double latitude, double longitude, Double latitude1, Double longitude1) {
        final int R = 6371;
        double latDistance = Math.toRadians(latitude1 - latitude);
        double lonDistance = Math.toRadians(longitude1 - longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(latitude1)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
