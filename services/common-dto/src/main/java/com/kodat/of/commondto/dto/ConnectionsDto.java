package com.kodat.of.commondto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionsDto {

    @JsonProperty("ID")
    private Long id;

    @JsonProperty("ConnectionTypeID")
    private int connectionTypeId;

    @JsonProperty("StatusTypeID")
    private int statusTypeId;

    @JsonProperty("LevelID")
    private int levelId;

    @JsonProperty("PowerKW")
    private int powerKW;

    @JsonProperty("CurrentTypeID")
    private int currentTypeId;

    @JsonProperty("Quantity")
    private int quantity;

    @JsonProperty("IsFast")
    private boolean isFast;
}