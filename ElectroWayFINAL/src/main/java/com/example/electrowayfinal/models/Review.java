package com.example.electrowayfinal.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "Review")
@Table(name = "review", schema = "electroway", uniqueConstraints = {
        @UniqueConstraint(name = "review_id_unique", columnNames = "id")
})
public class Review {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "bigint")
    private long id;

    @Basic
    @Column(name = "text_review", nullable = false, columnDefinition = "varchar(255)")
    private String textReview;

    @Basic
    @Column(name = "rating", nullable = false, columnDefinition = "tinyint")
    private byte rating;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "bigint")
    private User user;

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "id", columnDefinition = "bigint")
    private Station station;
}