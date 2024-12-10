package com.kodat.of.chargingstationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GraphqlAddressInfoDto {
    private String addressId;
    private String title;
    private String addressLine1;
    private String addressLine2;
    private String town;
    private String stateOrProvince;
    private String postcode;
    private int countryId;
    private double latitude;
    private double longitude;
    private int distanceUnit;
}



