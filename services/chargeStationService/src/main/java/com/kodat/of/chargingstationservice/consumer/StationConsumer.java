package com.kodat.of.chargingstationservice.consumer;

import com.kodat.of.chargingstationservice.entity.AddressInfo;
import com.kodat.of.chargingstationservice.entity.Connections;
import com.kodat.of.chargingstationservice.entity.Station;
import com.kodat.of.chargingstationservice.mapper.StationMapper;
import com.kodat.of.chargingstationservice.repository.AddressInfoRepository;
import com.kodat.of.chargingstationservice.repository.ConnectionsRepository;
import com.kodat.of.chargingstationservice.repository.StationRepository;
import com.kodat.of.commondtomodule.dto.StationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationConsumer.class);

    private final StationRepository stationRepository;
    private final AddressInfoRepository addressInfoRepository;
    private final ConnectionsRepository connectionsRepository;

    public StationConsumer(StationRepository stationRepository, AddressInfoRepository addressInfoRepository, ConnectionsRepository connectionsRepository) {
        this.stationRepository = stationRepository;
        this.addressInfoRepository = addressInfoRepository;
        this.connectionsRepository = connectionsRepository;
    }


    @KafkaListener(topics = "stations" , groupId = "charge-station-consumer-service")
    public void consume(StationDto stationDto){
        if (stationDto.getAddressInfoDto() != null){
            AddressInfo addressInfo = StationMapper.toAddressInfoEntity(stationDto.getAddressInfoDto());
            addressInfoRepository.save(addressInfo);
        }
        if (stationDto.getConnectionsDtos() != null){
            Station station = StationMapper.toStationEntity(stationDto);
            stationRepository.save(station);
            List<Connections> connectionsList = StationMapper.toConnectionEntities(stationDto.getConnectionsDtos(),station);
            connectionsRepository.saveAll(connectionsList);
        }
        Station station = StationMapper.toStationEntity(stationDto);
        stationRepository.save(station);
        LOGGER.info("Stations saved to database");
    }

}
