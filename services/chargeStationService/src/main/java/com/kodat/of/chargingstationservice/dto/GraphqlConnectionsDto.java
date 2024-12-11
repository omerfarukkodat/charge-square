package com.kodat.of.chargingstationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GraphqlConnectionsDto {
    private String id;
    private int connectionTypeId;
    private int statusTypeId;
    private int levelId;
    private int powerKW;
    private int currentTypeId;
    private int quantity;
    private boolean isFast;
}