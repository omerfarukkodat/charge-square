package com.kodat.of.chargingstationservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GraphqlStationDto {
    private String id;
    private String uuid;
    private GraphqlAddressInfoDto addressInfoDto;
    private List<GraphqlConnectionsDto> connectionsDtos;
    private int numberOfPoints;
    private String usageCost;
}

