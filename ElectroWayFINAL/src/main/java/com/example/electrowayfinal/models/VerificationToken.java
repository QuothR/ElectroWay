package com.example.electrowayfinal.models;


import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "VerificationToken")
@Table(name = "verification_token", schema = "electroway", uniqueConstraints = {
        @UniqueConstraint(name = "verification_token_id_unique", columnNames = "id")
})
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "bigint")
    private long id;

    @Basic
    @Column(name = "token", columnDefinition = "varchar(255)")
    private String token;

    @Basic
    @Column(name = "expiry_date", nullable = false, columnDefinition = "timestamp")
    private Timestamp expiryDate;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "bigint")
    private User user;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
