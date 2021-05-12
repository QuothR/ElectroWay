package com.example.electrowayfinal.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "Favourite")
@Table(name = "favourite", schema = "electroway", uniqueConstraints = {
        @UniqueConstraint(name = "favourite_id_unique", columnNames = "id")
})
public class Favourite {
    @Id
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "bigint")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "bigint")
    private User user;

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "id", columnDefinition = "bigint")
    private Station station;
}
