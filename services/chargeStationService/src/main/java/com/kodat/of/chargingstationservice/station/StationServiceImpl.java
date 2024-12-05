package com.kodat.of.chargingstationservice.station;

import com.kodat.of.chargingstationservice.exception.StationNotFoundException;
import com.kodat.of.chargingstationservice.station.dto.StationDto;
import com.kodat.of.chargingstationservice.station.mapper.StationMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    private final StationRepository stationRepository;

    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Cacheable(value = "stations" , key = "'allStations'")
    @Override
    public List<StationDto> getAllStations() {
        List<Station> stationsList = stationRepository.findAll();
        return StationMapper.toStationDtoList(stationsList);
    }

    @Cacheable(value = "station" , key = "#id")
    @Override
    public StationDto getStationById(Long id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(()-> new StationNotFoundException("Station not found by id " + id));
        return StationMapper.toStationDto(station);
    }

    @Override
    public StationDto addStation(StationDto stationDto) {
        Station station = StationMapper.toStation(stationDto);
        stationRepository.save(station);
        return StationMapper.toStationDto(station);
    }
}
