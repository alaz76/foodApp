package com.taltech.foodapp.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequestObj implements Serializable {

    private int userId;
    private String name;
    private String email;
    private String password;
    @JsonAlias("credit_card")
    private StringBuilder creditCard;
    @JsonAlias("address_1")
    private String address1;
    @JsonAlias("address_2")
    private String address2;
    private String city;
    private String region;
    @JsonAlias("postal_code")
    private String postalCode;
    private String country;
    private String mobPhone;
    private String authorities;
}
