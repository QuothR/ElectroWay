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
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "bigint")
    private long id;

    @Basic
    @Column(name = "speed", nullable = false, columnDefinition = "bigint")
    private long speed;

    @Basic
    @Column(name = "consumption_kWh", nullable = false, columnDefinition = "bigint")
    private long consumptionKWh;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id", columnDefinition = "bigint")
    private Car car;
}
