package com.kodat.of.chargingstationservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = "connections")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "stations")
public class Station implements Serializable {
    @Id
    private Long id;
    private String uuid;
    private String usageCost;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_info_id")
    private AddressInfo addressInfo;

    @OneToMany(mappedBy = "station" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Connections> connections;
    private int numberOfPoints;

}
