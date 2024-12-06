package com.kodat.of.chargingstationservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
@Entity(name = "addressesInfo")
public class AddressInfo implements Serializable {
    @Id
    private String id;

    private String title;

    private String addressLine1;

    private String addressLine2;

    private String town;

    private String stateOrProvince;

    private String postcode;

    private int countryId;

    private double latitude;

    private double longitude;
}
