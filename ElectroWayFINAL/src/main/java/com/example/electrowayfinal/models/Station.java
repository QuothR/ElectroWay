package com.example.electrowayfinal.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity(name = "station")
@Table(name = "station", schema = "electroway", uniqueConstraints = {
        @UniqueConstraint(name = "user_id_unique", columnNames = "id")
})
public class Station implements Serializable {
    @Id
//    @SequenceGenerator(
//            name = "station_generator",
//            sequenceName = "station_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "station_generator"
//    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "bigint")
    private long id;
    @Column(name = "address", nullable = false, columnDefinition = "varchar(64)")
    private String address;
    @Column(name = "map_latitude_location", nullable = false, columnDefinition = "double")
    private double latitude;
    @Column(name = "map_longitude_location", nullable = false, columnDefinition = "double")
    private double longitude;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User user;
}

