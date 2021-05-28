package com.example.electrowayfinal.models;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "Privilege")
@Table(name = "privilege", schema = "electroway", uniqueConstraints = {
        @UniqueConstraint(name = "privilege_id_unique", columnNames = "id")
})
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "bigint")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(255)")
    private String name;

    public Privilege(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    @Override
    public String toString() {
        return "Privilege{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
