package com.kodat.of.chargingstationservice;

import graphql.GraphQL;
import graphql.schema.GraphQLScalarType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class ChargingStationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChargingStationServiceApplication.class, args);

    }
}
