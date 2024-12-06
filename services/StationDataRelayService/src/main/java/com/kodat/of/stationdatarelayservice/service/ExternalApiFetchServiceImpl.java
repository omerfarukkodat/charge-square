package com.kodat.of.stationdatarelayservice.service;

import com.kodat.of.commondtomodule.dto.StationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ExternalApiFetchServiceImpl implements ExternalApiFetchService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalApiFetchServiceImpl.class);
    private static final String topicName = "stations";
    private static final String API_URL="https://api.openchargemap.io/v3/poi/?output=json&countrycode=TR&maxresults=2000&compact=true&verbose=false&key=69ef4d48-df99-4382-98e7-156c9ee3967a";


    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, StationDto> kafkaTemplate;

    public ExternalApiFetchServiceImpl(WebClient.Builder webClientBuilder, KafkaTemplate<String, StationDto> kafkaTemplate) {
        this.webClientBuilder = webClientBuilder;
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public Mono<List<StationDto>> getAllStations() {
        return webClientBuilder
                .baseUrl(API_URL)
                .build()
                .get()
                .retrieve()
                .onStatus(HttpStatusCode::isError,clientResponse -> Mono.error(new RuntimeException("API error")))
                .bodyToMono(new ParameterizedTypeReference<List<StationDto>>() {})
                .doOnSuccess(this::publishToKafka);
    }

    private void publishToKafka(List<StationDto> stationDtos) {
        stationDtos.forEach(stationDto -> kafkaTemplate.send(topicName,stationDto));
        LOGGER.info("All stations published");
    }
}
