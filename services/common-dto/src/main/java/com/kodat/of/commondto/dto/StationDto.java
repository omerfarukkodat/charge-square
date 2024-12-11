package com.kodat.of.commondto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StationDto {

    @JsonProperty("ID")
    private Long id;

    @JsonProperty("UUID")
    private String uuid;

    @JsonProperty("AddressInfo")
    private AddressInfoDto addressInfoDto;

    @JsonProperty("Connections")
    private List<ConnectionsDto> connectionsDtos;

    @JsonProperty("NumberOfPoints")
    @Builder.Default
    private int numberOfPoints=0;

    @JsonProperty("UsageCost")
    @Builder.Default
    private String usageCost="null";
}
