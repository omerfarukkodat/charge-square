package com.kodat.of.chargingstationservice.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode(exclude = "station")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "connections")
public class Connections implements Serializable {
    @Id
    private Long id;

    private int connectionTypeId;

    private int statusTypeId;

    private int levelId;

    private int powerKW;

    private int currentTypeId;

    private int quantity;

    private boolean isFast;

    @ManyToOne
    @JoinColumn(name = "station_id")
    @JsonIgnore
    private Station station;


    public boolean isFast() {
        return levelId == 3;
    }
}
