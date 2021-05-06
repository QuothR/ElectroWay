package com.example.electrowayfinal.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "Car")
@Table(name = "car", schema = "electroway", uniqueConstraints = {
        @UniqueConstraint(name = "car_id_unique", columnNames = "id")
})
public class Car {
    @Id
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "bigint")
    private long id;

    @Basic
    @Column(name = "model", nullable = false, columnDefinition = "varchar(255)")
    private String model;

    @Basic
    @Column(name = "year", nullable = false, columnDefinition = "bigint")
    private long year;

    @Basic
    @Column(name = "battery_capacity", nullable = false, columnDefinition = "bigint")
    private long batteryCapacity;

    @Basic
    @Column(name = "charging_capacity", nullable = false, columnDefinition = "double")
    private double chargingCapacity;

    @Basic
    @Column(name = "plug_type", nullable = false, columnDefinition = "varchar(255)")
    private String plugType;

    @Basic
    @Column(name = "vehicle_max_speed", nullable = false, columnDefinition = "bigint")
    private long vehicleMaxSpeed;

    @Basic
    @Column(name = "auxiliary_kwh", nullable = false, columnDefinition = "bigint")
    private long auxiliaryKwh;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", columnDefinition = "bigint")
    private User user;
}
