package com.example.electrowayfinal.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "Privilege")
@Table(name = "privilege", schema = "electroway", uniqueConstraints = {
        @UniqueConstraint(name = "privilege_id_unique", columnNames = "id")
})
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "bigint")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(255)")
    private String name;

//    @ManyToMany(mappedBy = "privileges")
//    private Collection<Role> roles;
}
