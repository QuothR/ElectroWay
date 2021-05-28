package com.example.electrowayfinal.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "PlugType")
@Table(name = "plug_type", schema = "electroway", uniqueConstraints = {
        @UniqueConstraint(name = "plug_type_id_unique", columnNames = "id")
})
public class PlugType {
    @Id
    @SequenceGenerator(
            name = "plug_type_sequence",
            sequenceName = "plug_type_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "plug_type_sequence"
    )
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "bigint")
    private long id;

    @Basic
    @Column(name = "plug_type", nullable = false, columnDefinition = "varchar(255)")
    private String plugType;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id", columnDefinition = "bigint")
    private Car car;
}
