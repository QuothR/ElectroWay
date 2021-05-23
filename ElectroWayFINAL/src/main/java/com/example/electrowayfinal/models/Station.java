package com.example.electrowayfinal.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "Station")
@Table(name = "station", schema = "electroway", uniqueConstraints = {
        @UniqueConstraint(name = "station_id_unique", columnNames = "id"),
        @UniqueConstraint(name = "station_owner_location_unique", columnNames = {"map_latitude_location", "map_longitude_location", "owner_id"})
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

    @Basic
    @Column(name = "address", nullable = false, columnDefinition = "varchar(255)")
    private String address;

    @Basic
    @Column(name = "map_latitude_location", nullable = false, columnDefinition = "double")
    private double latitude;

    @Column(name = "map_longitude_location", nullable = false, columnDefinition = "double")
    private double longitude;

    @Basic
    @Column(name = "description", nullable = true, columnDefinition = "varchar(255)")
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", columnDefinition = "bigint")
    private User user;

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}

