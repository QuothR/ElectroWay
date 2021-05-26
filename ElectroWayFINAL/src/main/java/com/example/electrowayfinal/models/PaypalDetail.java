package com.example.electrowayfinal.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "PaypalDetail")
@Table(name = "paypal_detail", schema = "electroway", uniqueConstraints = {
        @UniqueConstraint(name = "paypal_detail_user_id_fk", columnNames = "owner_id")
})
public class PaypalDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "bigint")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", columnDefinition = "bigint")
    private User user;

    @Column(name = "client_id", nullable = false, updatable = true)
    private String clientId;

    @Column(name = "secret", nullable = false, updatable = true)
    private String secret;
}
