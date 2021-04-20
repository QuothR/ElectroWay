package com.example.electrowayfinal.models;

import com.example.electrowayfinal.Validation.ValidPassword;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Entity
@Table
@ValidPassword
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@lombok.NonNull
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
    private Long id;
    @Setter(AccessLevel.NONE)
    @Column(name = "user_name")
    private String username;
    @NotBlank(message = "New password is mandatory")
    @Column(name = "password_hash")
    @org.springframework.lang.NonNull
    // TODO find why password has can't be renamed to password
    private String passwordHash;
    private String passwordResetToken;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @Email
    private String emailAddress;
    private String address1;
    @Nullable
    private String address2;
    private String city;
    @Nullable
    private String region;
    private String country;
    private String zipcode;
    private boolean enabled;

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