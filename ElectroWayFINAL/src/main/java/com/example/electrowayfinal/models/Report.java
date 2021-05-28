package com.example.electrowayfinal.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "Report")
@Table(name = "report", schema = "electroway", uniqueConstraints = {
        @UniqueConstraint(name = "report_id_unique", columnNames = "id")
})
public class Report {

    @Id
    @SequenceGenerator(
            name = "report_sequence",
            sequenceName = "report_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "report_sequence"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "bigint")
    private long id;

    @Basic
    @Column(name = "text_report", nullable = false, columnDefinition = "varchar(255)")
    private String textReport;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "bigint")
    private User user;

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "id", columnDefinition = "bigint")
    private Station station;
}