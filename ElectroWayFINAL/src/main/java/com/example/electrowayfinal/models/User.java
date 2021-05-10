package com.example.electrowayfinal.models;

import com.example.electrowayfinal.Validation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Slf4j
@ValidPassword
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "User")
@Table(name = "user", schema = "electroway", uniqueConstraints = {
        @UniqueConstraint(name = "user_id_unique", columnNames = "id"),
        @UniqueConstraint(name = "user_user_name_unique", columnNames = "user_name"),
        @UniqueConstraint(name = "user_phone_number_unique", columnNames = "phone_number"),
        @UniqueConstraint(name = "user_email_address_unique", columnNames = "email_address"),
})
public class User implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )


    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "bigint")
    private long id;

    @Column(name = "user_name", nullable = false, columnDefinition = "varchar(255)")
    private String username;

    @NotBlank(message = "New password is mandatory")
    @Column(name = "password_hash", nullable = false, columnDefinition = "varchar(255)")
    private String password;

    @Column(name = "first_name", columnDefinition = "varchar(255)")
    private String firstName;

    @Column(name = "last_name", columnDefinition = "varchar(255)")
    private String lastName;

    @Column(name = "phone_number", columnDefinition = "varchar(255)")
    private String phoneNumber;

    @Email
    @Column(name = "email_address", nullable = false, columnDefinition = "varchar(255)")
    private String emailAddress;

    @Column(name = "address1", columnDefinition = "varchar(255)")
    private String address1;

    @Column(name = "address2", columnDefinition = "varchar(255)")
    private String address2;

    @Column(name = "city", columnDefinition = "varchar(255)")
    private String city;

    @Column(name = "region", columnDefinition = "varchar(255)")
    private String region;

    @Column(name = "country", columnDefinition = "varchar(255)")
    private String country;

    @Column(name = "zipcode", columnDefinition = "varchar(255)")
    private String zipcode;

    @Column(name = "is_enabled", nullable = false, columnDefinition = "boolean default false")
    private boolean isEnabled;

    @Column(name = "password_reset_token", columnDefinition = "varchar(255)")
    private String passwordResetToken;

    @JsonCreator
    public User(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("phoneNumber") String phoneNumber,
            @JsonProperty("emailAddress") String emailAddress,
            @JsonProperty("address1") String address1,
            @JsonProperty("city") String city,
            @JsonProperty("country") String country,
            @JsonProperty("zipcode") String zipcode,
            @JsonProperty("roles") List<String> roles){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.address1 = address1;
        this.city = city;
        this.country = country;
        this.zipcode = zipcode;
    }

    public User(String username, String password, String firstName, String lastName, String phoneNumber, String emailAddress, String address1, String city, String country, String zipcode) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.address1 = address1;
        this.city = city;
        this.country = country;
        this.zipcode = zipcode;
        this.address2 = "";
    }

    public User(String username, String password, String firstName, String lastName, String phoneNumber, String emailAddress, String address1, String address2, String city, String country, String zipcode) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.country = country;
        this.zipcode = zipcode;
    }


    public User(String username, String password, String firstName, String lastName, String phoneNumber, String emailAddress, String address1, String city, String region, String country, String zipcode, List<String> roles) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.address1 = address1;
        this.city = city;
        this.region = region;
        this.country = country;
        this.zipcode = zipcode;
        //TODO  :)))))
        for (String role : roles) {
            Role role2 = new Role(role);
            this.roles.add(role2);
        }
        log.info("CONSTRUCTOR");
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String emailAddress) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
    }

    public User(String username, String password, String phoneNumber, String emailAddress) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", isEnabled=" + isEnabled +
                ", passwordResetToken='" + passwordResetToken + '\'' +
                '}';
    }
}