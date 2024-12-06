package com.kodat.of.chargingstationservice.controller;

import com.kodat.of.chargingstationservice.service.StationService;
import com.kodat.of.commondtomodule.dto.StationDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stations")
public class StationController {
    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping
    public ResponseEntity<List<com.kodat.of.commondtomodule.dto.StationDto>> getAllStations() {
        return ResponseEntity.ok(stationService.getAllStations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.kodat.of.commondtomodule.dto.StationDto> getStationById(@PathVariable Long id) {
        return ResponseEntity.ok(stationService.getStationById(id));
    }

    @PostMapping
    public ResponseEntity<com.kodat.of.commondtomodule.dto.StationDto> addStation(
            @RequestBody @Valid StationDto stationDto
    ) {
        return ResponseEntity.accepted().body(stationService.addStation(stationDto));
    }


}
