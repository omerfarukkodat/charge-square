package com.kodat.of.chargingstationservice.mapper;


import com.kodat.of.chargingstationservice.dto.GraphqlAddressInfoDto;
import com.kodat.of.chargingstationservice.dto.GraphqlConnectionsDto;
import com.kodat.of.chargingstationservice.dto.GraphqlStationDto;
import com.kodat.of.chargingstationservice.entity.AddressInfo;
import com.kodat.of.chargingstationservice.entity.Connections;
import com.kodat.of.chargingstationservice.entity.Station;


import java.util.List;
import java.util.stream.Collectors;

public class GraphqlMapper {

    public static GraphqlStationDto toGraphqlStationDto(Station station) {
        return GraphqlStationDto.builder()
                .id(String.valueOf(station.getId()))
                .uuid(station.getUuid())
                .usageCost(station.getUsageCost())
                .addressInfoDto(graphqlAddressInfoDto(station.getAddressInfo()))
                .connectionsDtos(graphqlConnectionsDtos(station.getConnections()))
                .numberOfPoints(station.getNumberOfPoints())
                .build();
    }

    public static Station toStationEntity(GraphqlStationDto graphqlStationDto) {
        Station station = Station.builder()
                .id(Long.valueOf(graphqlStationDto.getId()))
                .uuid(graphqlStationDto.getUuid())
                .usageCost(graphqlStationDto.getUsageCost())
                .addressInfo(toAddressInfoEntity(graphqlStationDto.getAddressInfoDto()))
                .numberOfPoints(graphqlStationDto.getNumberOfPoints())
                .build();
        List<Connections> connections = toConnectionEntities(graphqlStationDto.getConnectionsDtos(), station);
        station.setConnections(connections);
        return station;
    }

    public static GraphqlAddressInfoDto graphqlAddressInfoDto(AddressInfo addressInfo) {
        return GraphqlAddressInfoDto.builder()
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

    public static AddressInfo toAddressInfoEntity(GraphqlAddressInfoDto graphqlAddressInfoDto) {
        return AddressInfo.builder()
                .id(graphqlAddressInfoDto.getAddressId())
                .title(graphqlAddressInfoDto.getTitle())
                .addressLine1(graphqlAddressInfoDto.getAddressLine1())
                .addressLine2(graphqlAddressInfoDto.getAddressLine2())
                .town(graphqlAddressInfoDto.getTown())
                .postcode(graphqlAddressInfoDto.getPostcode())
                .countryId(graphqlAddressInfoDto.getCountryId())
                .latitude(graphqlAddressInfoDto.getLatitude())
                .longitude(graphqlAddressInfoDto.getLongitude())
                .build();
    }

    public static List<Connections> toConnectionEntities(List<GraphqlConnectionsDto> graphqlConnectionsDtos, Station station) {
        return graphqlConnectionsDtos.stream()
                .map(connectionDto -> {
                    Connections connection = Connections.builder()
                            .id(Long.valueOf(connectionDto.getId()))
                            .powerKW(connectionDto.getPowerKW())
                            .currentTypeId(connectionDto.getCurrentTypeId())
                            .levelId(connectionDto.getLevelId())
                            .quantity(connectionDto.getQuantity())
                            .isFast(connectionDto.isFast())
                            .station(station) // Ensure the correct station is assigned to the connection
                            .build();
                    return connection;
                })
                .collect(Collectors.toList());
    }

    public static List<GraphqlConnectionsDto> graphqlConnectionsDtos(List<Connections> connections) {
        return connections.stream()
                .map(connections1 -> {
                    GraphqlConnectionsDto graphqlConnectionsDto = GraphqlConnectionsDto.builder()
                            .id(String.valueOf(connections1.getId()))
                            .connectionTypeId(connections1.getConnectionTypeId())
                            .statusTypeId(connections1.getStatusTypeId())
                            .levelId(connections1.getLevelId())
                            .powerKW(connections1.getPowerKW())
                            .currentTypeId(connections1.getCurrentTypeId())
                            .quantity(connections1.getQuantity())
                            .isFast(connections1.isFast())
                            .build();
                    return graphqlConnectionsDto;
                })
                .collect(Collectors.toList());
    }

    public static List<GraphqlStationDto> graphqlStationDtos(List<Station> stations) {
        return stations.stream()
                .map(GraphqlMapper::toGraphqlStationDto)
                .collect(Collectors.toList());
    }
}
