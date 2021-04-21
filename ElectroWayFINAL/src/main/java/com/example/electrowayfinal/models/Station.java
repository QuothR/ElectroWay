package com.example.electrowayfinal.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "station")
@Table(name = "station", schema = "electroway", uniqueConstraints = {
        @UniqueConstraint(name = "user_id_unique", columnNames = "id")
})
public class Station implements Serializable {
    @Id
    @SequenceGenerator(
            name = "station_sequence",
            sequenceName = "station_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "station_sequence"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "bigint")
    private long id;
    @Column(name = "address", nullable = false, columnDefinition = "varchar(64)")
    private String address;
    @Column(name = "mapLatitudeLocation", nullable = false, columnDefinition = "double")
    private double latitude;
    @Column(name = "mapLongitudeLocation", nullable = false, columnDefinition = "double")
    private double longitude;
    @ManyToOne
    @JoinColumn(name = "ownerID", referencedColumnName = "id")
    private User user;
}
