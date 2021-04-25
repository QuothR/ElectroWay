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
    @Column(name = "autonomy", nullable = false, columnDefinition = "bigint")
    private long autonomy;

    @Basic
    @Column(name = "medium_consumption_kw", nullable = false, columnDefinition = "bigint")
    private long mediumConsumptionKw;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", columnDefinition = "bigint")
    private User user;
}
