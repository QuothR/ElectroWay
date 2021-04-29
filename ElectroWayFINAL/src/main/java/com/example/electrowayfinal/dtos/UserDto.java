package com.example.electrowayfinal.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class UserDto {
    private String password;
    private String address1;
    private String firstName = null;
    private String lastName = null;
    private String zipcode = null;
    private String country = null;
    private String city = null;
    private String username;
    private String phoneNumber;
    private String emailAddress;
    private List<String> roles = new LinkedList<>();

    @JsonCreator
    public UserDto(
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
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.address1 = address1;
        this.city = city;
        this.country = country;
        this.zipcode = zipcode;
        this.roles = roles;
    }

    public UserDto(String username, String password, String phoneNumber, String emailAddress) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }
}
