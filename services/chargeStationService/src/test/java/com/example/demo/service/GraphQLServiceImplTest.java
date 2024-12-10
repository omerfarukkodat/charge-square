//package com.example.demo.service;
//
//import com.kodat.of.chargingstationservice.dto.GraphqlStationDto;
//import com.kodat.of.chargingstationservice.entity.AddressInfo;
//import com.kodat.of.chargingstationservice.entity.Connections;
//import com.kodat.of.chargingstationservice.entity.Station;
//import com.kodat.of.chargingstationservice.exception.StationNotFoundException;
//import com.kodat.of.chargingstationservice.mapper.GraphqlMapper;
//import com.kodat.of.chargingstationservice.repository.StationRepository;
//import com.kodat.of.chargingstationservice.service.impl.GraphQLServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//@ExtendWith(MockitoExtension.class)
//public class GraphQLServiceImplTest {
//
//    @Mock
//    private StationRepository stationRepository;
//
//    @InjectMocks
//    private GraphQLServiceImpl graphQLService;
//
//    private Station mockStation;
//    private AddressInfo mockAddressInfo;
//    private Connections mockConnections;
//
//    @BeforeEach
//    void setUp() {
//        mockConnections = Mockito.mock(Connections.class);
//        mockConnections.setFast(true);
//        mockConnections.setPowerKW(40);
//        mockConnections.setQuantity(2);
//
//        mockAddressInfo = new AddressInfo();
//        mockAddressInfo.setId("1");
//        mockAddressInfo.setAddressLine1("AddressLine1");
//        mockAddressInfo.setLatitude(14.234);
//        mockAddressInfo.setLongitude(23.456);
//
//        mockStation = new Station();
//        mockStation.setId(1L);
//        mockStation.setAddressInfo(mockAddressInfo);
//        mockStation.setUsageCost("5.00");
//        mockStation.setConnections(List.of(mockConnections));
//    }
//
//    @Test
//    void getAllStations_ShouldReturnStations() {
//        Mockito.when(stationRepository.findAll()).thenReturn(List.of(mockStation));
//        List<GraphqlStationDto> result = graphQLService.getAllStations();
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(mockStation.getAddressInfo().getAddressLine1(), result.get(0).getAddressInfoDto().getAddressLine1());
//    }
//
//    @Test
//    void getAllStations_ShouldReturnEmptyListWhenNoStationsFound() {
//        Mockito.when(stationRepository.findAll()).thenReturn(Collections.emptyList());
//        List<GraphqlStationDto> result = graphQLService.getAllStations();
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void getStationById_ShouldReturnStation_WhenFound() {
//        Mockito.when(stationRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockStation));
//        GraphqlStationDto result = graphQLService.getStationById("1");
//        assertNotNull(result);
//        assertEquals(mockStation.getId(), result.getId());
//    }
//
//    @Test
//    void getStationById_ShouldThrowException_WhenNotFound() {
//        Mockito.when(stationRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
//        StationNotFoundException exception = assertThrows(StationNotFoundException.class, () -> {
//            graphQLService.getStationById("1");
//        });
//        assertEquals("Station not found by id 1", exception.getMessage());
//    }
//
//    @Test
//    void addStation_ShouldReturnStation_WhenSuccessfullyAdded() {
//        GraphqlStationDto graphqlStationDto = new GraphqlStationDto();
//        Mockito.when(stationRepository.save(Mockito.any(Station.class))).thenReturn(mockStation);
//        GraphqlStationDto result = graphQLService.addStation(graphqlStationDto);
//        assertNotNull(result);
//        assertEquals(mockStation.getAddressInfo().getAddressLine1(), result.getAddressInfoDto().getAddressLine1());
//    }
//
//    @Test
//    void getStationsByRadius_ShouldReturnStationsWithinRadius() {
//        double radius = 10.0;
//        Mockito.when(stationRepository.findAll()).thenReturn(List.of(mockStation));
//        List<GraphqlStationDto> result = graphQLService.getStationsByRadius(40.0, 30.0, radius);
//        assertNotNull(result);
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    void searchStations_ShouldReturnStations_WhenCriteriaMatch() {
//        String title = "Title";
//        String usageCost = "5.00";
//        Mockito.when(stationRepository.findAll()).thenReturn(List.of(mockStation));
//        List<GraphqlStationDto> result = graphQLService.searchStations(title, usageCost);
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("Title", result.get(0).getAddressInfoDto().getTitle());
//    }
//
//    @Test
//    void fastChargingStations_ShouldReturnFastChargingStations() {
//        Mockito.when(stationRepository.findAll()).thenReturn(List.of(mockStation));
//        List<GraphqlStationDto> result = graphQLService.fastChargingStations();
//        assertNotNull(result);
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    void fastChargingStations_ShouldReturnEmpty_WhenNoFastChargingStations() {
//        mockStation.setConnections(List.of(new Connections()));
//        Mockito.when(stationRepository.findAll()).thenReturn(List.of(mockStation));
//        List<GraphqlStationDto> result = graphQLService.fastChargingStations();
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void getStationsByRadius_ShouldReturnEmpty_WhenNoStationsWithinRadius() {
//        double radius = 1.0; // Small radius
//        Mockito.when(stationRepository.findAll()).thenReturn(List.of(mockStation));
//        List<GraphqlStationDto> result = graphQLService.getStationsByRadius(40.0, 30.0, radius);
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void addStation_ShouldThrowError_WhenAddressIsNull() {
//        GraphqlStationDto graphqlStationDto = new GraphqlStationDto();
//        graphqlStationDto.setAddressInfoDto(GraphqlMapper.graphqlAddressInfoDto(mockAddressInfo)); // Null address
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            graphQLService.addStation(graphqlStationDto);
//        });
//        assertEquals("Address info cannot be null", exception.getMessage());
//    }
//}
