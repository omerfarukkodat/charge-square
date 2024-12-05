package com.kodat.of.chargingstationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ChargingStationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChargingStationServiceApplication.class, args);
    }

}
