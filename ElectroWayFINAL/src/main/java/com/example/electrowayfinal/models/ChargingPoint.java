package com.example.electrowayfinal.models;

import lombok.*;

import javax.persistence.*;

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
    @SequenceGenerator(
            name = "charging_point_sequence",
            sequenceName = "charging_point_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "charging_point_sequence"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "bigint")
    private long id;

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "id", columnDefinition = "bigint")
    private Station station;
}
