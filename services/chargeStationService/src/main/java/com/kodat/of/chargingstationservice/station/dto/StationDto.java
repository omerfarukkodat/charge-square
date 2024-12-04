package com.kodat.of.chargingstationservice.station.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StationDto {

    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Latitude cannot be blank")
    @Pattern(regexp = "-?\\d{1,2}\\.\\d+$" , message = "Invalid latitude format")
    private String latitude;
    @NotBlank(message = "Longitude cannot be blank")
    @Pattern(regexp = "-?\\d{1,3}\\.\\d+$", message = "Invalid longitude format")
    private String longitude;
    @NotBlank(message = "Charging speed cannot be blank")
    private String chargingSpeed;
}
