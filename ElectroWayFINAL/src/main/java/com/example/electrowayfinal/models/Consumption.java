package com.example.electrowayfinal.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "Consumption")
@Table(name = "consumption", schema = "electroway", uniqueConstraints = {
        @UniqueConstraint(name = "consumption_id_unique", columnNames = "id")
})
public class Consumption {
    @Id
    @SequenceGenerator(
            name = "consumption_sequence",
            sequenceName = "consumption_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "consumption_sequence"
    )
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "bigint")
    private long id;

    @Basic
    @Column(name = "speed", nullable = false, columnDefinition = "bigint")
    private long speed;

    @Basic
    @Column(name = "consumption_kwh", nullable = false, columnDefinition = "double")
    private double consumptionKwh;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id", columnDefinition = "bigint")
    private Car car;
}
