package com.kodat.of.commondto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressInfoDto {
    @JsonProperty("ID")
    private String addressId;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("AddressLine1")
    private String addressLine1;

    @JsonProperty("AddressLine2")
    private String addressLine2;

    @JsonProperty("Town")
    private String town;

    @JsonProperty("StateOrProvince")
    private String stateOrProvince;

    @JsonProperty("Postcode")
    private String postcode;

    @JsonProperty("CountryID")
    private int countryId;

    @JsonProperty("Latitude")
    private double latitude;

    @JsonProperty("Longitude")
    private double longitude;

    @JsonProperty("DistanceUnit")
    private int distanceUnit;
}


