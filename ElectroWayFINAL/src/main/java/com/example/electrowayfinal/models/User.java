package com.example.electrowayfinal.models;

import com.example.electrowayfinal.Validation.ValidPassword;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;


@ValidPassword
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity(name = "user")
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
    @Column(name = "user_name", nullable = false, columnDefinition = "varchar(64)")
    private String username;
    @NotBlank(message = "New password is mandatory")
    @Column(name = "password_hash", nullable = false, columnDefinition = "varchar(64)")
    private String passwordHash;
    @Column(name = "first_name", nullable = false, columnDefinition = "varchar(64)")
    private String firstName;
    @Column(name = "last_name", nullable = false, columnDefinition = "varchar(64)")
    private String lastName;
    @Column(name = "phone_number", nullable = false, columnDefinition = "varchar(64)")
    private String phoneNumber;
    @Email
    @Column(name = "email_address", nullable = false, columnDefinition = "varchar(64)")
    private String emailAddress;
    @Column(name = "address1", nullable = false, columnDefinition = "varchar(64)")
    private String address1;
    @Column(name = "address2", columnDefinition = "varchar(64)")
    private String address2;
    @Column(name = "city", nullable = false, columnDefinition = "varchar(64)")
    private String city;
    @Column(name = "region", columnDefinition = "varchar(64)")
    private String region;
    @Column(name = "country", nullable = false, columnDefinition = "varchar(64)")
    private String country;
    @Column(name = "zipcode", nullable = false, columnDefinition = "varchar(64)")
    private String zipcode;
    @Column(name = "enabled", nullable = false, columnDefinition = "boolean default false")
    private boolean enabled;
    @Column(name = "password_reset_token", columnDefinition = "varchar(64)")
    private String passwordResetToken;

    public User(String username, String password, String emailAddress) {
        this.username = username;
        this.passwordHash = password;
        this.emailAddress = emailAddress;
    }

    public User(String username, String passwordHash, String firstName, String lastName, String phoneNumber, String emailAddress, String address1, String city, String country, String zipcode) {
        this.username = username;
        this.passwordHash = passwordHash;
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

    public User(String username, String passwordHash, String firstName, String lastName, String phoneNumber, String emailAddress, String address1, String address2, String city, String country, String zipcode) {
        this.username = username;
        this.passwordHash = passwordHash;
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

    public User(Long id, String username, String passwordHash, String firstName, String lastName, String phoneNumber, String emailAddress, String address1, String address2, String city, String country, String zipcode, boolean enabled) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.country = country;
        this.zipcode = zipcode;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return passwordHash;
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
}