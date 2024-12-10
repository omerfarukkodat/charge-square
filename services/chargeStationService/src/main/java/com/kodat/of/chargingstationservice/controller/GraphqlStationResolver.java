package com.kodat.of.chargingstationservice.controller;

import com.kodat.of.chargingstationservice.dto.GraphqlStationDto;
import com.kodat.of.chargingstationservice.service.GraphQLService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphqlStationResolver {

    private final GraphQLService graphQLService;

    public GraphqlStationResolver(GraphQLService graphQLService) {
        this.graphQLService = graphQLService;
    }

    @QueryMapping
    public List<GraphqlStationDto> allStations() {
        return graphQLService.getAllStations();
    }

    @QueryMapping
    public GraphqlStationDto stationById(@Argument String id) {
        return graphQLService.getStationById(id);
    }

    @QueryMapping
    public List<GraphqlStationDto> stationByRadius
            (@Argument Double latitude,
             @Argument Double longitude,
             @Argument Double radius){
        return graphQLService.getStationsByRadius(latitude, longitude, radius);
    }

    @QueryMapping
    public List<GraphqlStationDto> searchStation(@Argument String title, @Argument String usageCost) {
        return graphQLService.searchStations(title, usageCost);
    }

    @QueryMapping
    public List<GraphqlStationDto> fastChargingStations(){
        return graphQLService.fastChargingStations();
    }

    @MutationMapping
    public GraphqlStationDto addStation(@Argument GraphqlStationDto graphqlStationDto) {
        return graphQLService.addStation(graphqlStationDto);
    }
}
