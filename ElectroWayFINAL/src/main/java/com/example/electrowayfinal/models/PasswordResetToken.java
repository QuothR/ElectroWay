package com.example.electrowayfinal.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "PasswordResetToken")
@Table(name = "password_reset_token", schema = "electroway", uniqueConstraints = {
        @UniqueConstraint(name = "password_reset_token_id_unique", columnNames = "id")
})
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "bigint")
    private long id;

    @Basic
    @Column(name = "token", columnDefinition = "varchar(255)")
    private String token;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "bigint")
    private User user;

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
