package com.example.electrowayfinal.models;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "ChargingPoint")
@Table(name = "charging_point", schema = "electroway", uniqueConstraints = {
        @UniqueConstraint(name = "charging_point_id_unique", columnNames = "id")
})
public class ChargingPoint {
    @Id

    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "bigint")
    private long id;

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "id", columnDefinition = "bigint")
    private Station station;
}
