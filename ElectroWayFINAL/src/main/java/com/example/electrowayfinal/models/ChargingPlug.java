package com.example.electrowayfinal.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "ChargingPlug")
@Table(name = "charging_plug", schema = "electroway", uniqueConstraints = {
        @UniqueConstraint(name = "charging_plug_id_unique", columnNames = "id")
})
public class ChargingPlug {
    @Id
    @SequenceGenerator(
            name = "charging_plug_sequence",
            sequenceName = "charging_plug_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "charging_plug_sequence"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "bigint")
    private long id;

    @Basic
    @Column(name = "status", nullable = false, columnDefinition = "tinyint")
    private byte status;

    @Basic
    @Column(name = "level", nullable = false, columnDefinition = "bigint")
    private long level;

    @Basic
    @Column(name = "connector_type", nullable = false, columnDefinition = "varchar(255)")
    private String connectorType;

    @Basic
    @Column(name = "price_kw", nullable = false, columnDefinition = "double")
    private double priceKw;

    @Basic
    @Column(name = "charging_speed_kw", nullable = false, columnDefinition = "double")
    private double chargingSpeedKw;

    @ManyToOne
    @JoinColumn(name = "charging_point_id", referencedColumnName = "id", columnDefinition = "bigint")
    private ChargingPoint chargingPoint;
}
