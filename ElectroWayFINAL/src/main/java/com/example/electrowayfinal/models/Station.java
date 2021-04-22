package com.example.electrowayfinal.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity
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
    @Column(name = "map_latitude_location", nullable = false, columnDefinition = "double")
    private double latitude;
    @Column(name = "map_longitude_location", nullable = false, columnDefinition = "double")
    private double longitude;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User user;
    private double mapLatitudeLocation;
    private double mapLongitudeLocation;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "map_latitude_location")
    public double getMapLatitudeLocation() {
        return mapLatitudeLocation;
    }

    public void setMapLatitudeLocation(double mapLatitudeLocation) {
        this.mapLatitudeLocation = mapLatitudeLocation;
    }

    @Basic
    @Column(name = "map_longitude_location")
    public double getMapLongitudeLocation() {
        return mapLongitudeLocation;
    }

    public void setMapLongitudeLocation(double mapLongitudeLocation) {
        this.mapLongitudeLocation = mapLongitudeLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        if (id != station.id) return false;
        if (Double.compare(station.mapLatitudeLocation, mapLatitudeLocation) != 0) return false;
        if (Double.compare(station.mapLongitudeLocation, mapLongitudeLocation) != 0) return false;
        if (address != null ? !address.equals(station.address) : station.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (address != null ? address.hashCode() : 0);
        temp = Double.doubleToLongBits(mapLatitudeLocation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mapLongitudeLocation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
