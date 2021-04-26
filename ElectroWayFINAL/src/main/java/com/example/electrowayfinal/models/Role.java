//package com.example.electrowayfinal.models;
//
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.Collection;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@ToString
//@EqualsAndHashCode
//@Entity(name = "Role")
//@Table(name = "role", schema = "electroway", uniqueConstraints = {
//        @UniqueConstraint(name = "role_id_unique", columnNames = "id")
//})
//public class Role {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "bigint")
//    private long id;
//
//    @Column(name = "name", nullable = false, columnDefinition = "varchar(255)")
//    private String name;
//
//    @ManyToMany(mappedBy = "roles")
//    private Collection<User> users;
//
//    @ManyToMany
//    @JoinTable(
//            name = "role_privilege",
//            joinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "privilege_id", referencedColumnName = "id"))
//    private Collection<Privilege> privileges;
//}