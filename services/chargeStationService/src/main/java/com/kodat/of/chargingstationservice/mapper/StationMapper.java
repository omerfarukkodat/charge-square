package com.kodat.of.chargingstationservice.mapper;

import com.kodat.of.chargingstationservice.entity.AddressInfo;
import com.kodat.of.chargingstationservice.entity.Connections;
import com.kodat.of.chargingstationservice.entity.Station;
import com.kodat.of.commondto.dto.AddressInfoDto;
import com.kodat.of.commondto.dto.ConnectionsDto;
import com.kodat.of.commondto.dto.StationDto;

import java.util.List;
import java.util.stream.Collectors;

public class StationMapper {


    public static StationDto toStationDto(Station station) {
        return StationDto.builder()
                .id(station.getId())
                .uuid(station.getUuid())
                .usageCost(station.getUsageCost())
                .addressInfoDto(toAddressInfoDto(station.getAddressInfo()))
                .connectionsDtos(toConnectionDtoList(station.getConnections()))
                .numberOfPoints(station.getNumberOfPoints())
                .build();
    }


    public static Station toStationEntity(StationDto stationDto) {
        Station station = Station.builder()
                .id(stationDto.getId())
                .uuid(stationDto.getUuid())
                .usageCost(stationDto.getUsageCost())
                .addressInfo(toAddressInfoEntity(stationDto.getAddressInfoDto()))
                .numberOfPoints(stationDto.getNumberOfPoints())
                .build();
        List<Connections> connections = toConnectionEntities(stationDto.getConnectionsDtos(), station);
        station.setConnections(connections);
        return station;
    }

    public static AddressInfoDto toAddressInfoDto(AddressInfo addressInfo) {
        return AddressInfoDto.builder()
                .addressId(addressInfo.getId())
                .title(addressInfo.getTitle())
                .addressLine1(addressInfo.getAddressLine1())
                .addressLine2(addressInfo.getAddressLine2())
                .town(addressInfo.getTown())
                .stateOrProvince(addressInfo.getStateOrProvince())
                .postcode(addressInfo.getPostcode())
                .countryId(addressInfo.getCountryId())
                .latitude(addressInfo.getLatitude())
                .longitude(addressInfo.getLongitude())
                .build();

    }

    public static AddressInfo toAddressInfoEntity(AddressInfoDto addressInfoDto) {
        return AddressInfo.builder()
                .id(addressInfoDto.getAddressId())
                .title(addressInfoDto.getTitle())
                .addressLine1(addressInfoDto.getAddressLine1())
                .addressLine2(addressInfoDto.getAddressLine2())
                .town(addressInfoDto.getTown())
                .postcode(addressInfoDto.getPostcode())
                .countryId(addressInfoDto.getCountryId())
                .latitude(addressInfoDto.getLatitude())
                .longitude(addressInfoDto.getLongitude())
                .build();
    }

    public static List<Connections> toConnectionEntities(List<ConnectionsDto> connectionDtos, Station station) {
        return connectionDtos.stream()
                .map(connectionDto -> {
                    Connections connection = Connections.builder()
                            .id(connectionDto.getId())
                            .powerKW(connectionDto.getPowerKW())
                            .currentTypeId(connectionDto.getCurrentTypeId())
                            .levelId(connectionDto.getLevelId())
                            .quantity(connectionDto.getQuantity())
                            .station(station) // Ensure the correct station is assigned to the connection
                            .build();
                    return connection;
                })
                .collect(Collectors.toList());
    }

    public static List<ConnectionsDto> toConnectionDtoList(List<Connections> connections) {
        return connections.stream()
                .map(connections1 -> {
                    ConnectionsDto connectionsDto = ConnectionsDto.builder()
                            .id(connections1.getId())
                            .connectionTypeId(connections1.getConnectionTypeId())
                            .statusTypeId(connections1.getStatusTypeId())
                            .levelId(connections1.getLevelId())
                            .powerKW(connections1.getPowerKW())
                            .currentTypeId(connections1.getCurrentTypeId())
                            .quantity(connections1.getQuantity())
                            .isFast(connections1.isFast())
                            .build();
                    return connectionsDto;
                }).collect(Collectors.toList());
    }

    public static List<StationDto> toStationDtos(List<Station> stations) {
        return stations.stream()
                .map(StationMapper::toStationDto)
                .collect(Collectors.toList());
    }

}

