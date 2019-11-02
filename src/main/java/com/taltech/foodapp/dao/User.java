package com.taltech.foodapp.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Entity(name = "user")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    private static final long serialId= 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int userId;

    @Column(length = 50)
    @NotNull
    private String name;

    @Column(length = 100, unique = true)
    @NotNull
    private String email;

    @Column(length = 100)
    @NotNull
    private String password;

    @Column(name = "credit_card", columnDefinition = "LONGTEXT")
    private String creditCard;

    @Column(name = "address_1", length = 100)
    private String address1;

    @Column(name = "address_2", length = 100)
    private String address2;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String region;

    @Column(name = "postal_code", length = 100)
    private String postalCode;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "mob_phone", length = 100)
    private String mobPhone;

    @Column(name = "authorities", columnDefinition = "LONGTEXT")
    private String authorities;

    @Column(name = "token", columnDefinition = "LONGTEXT")
    @Null
    private String token;

}
