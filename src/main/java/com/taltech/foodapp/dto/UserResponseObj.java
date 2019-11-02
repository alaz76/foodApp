package com.taltech.foodapp.dto;

import com.taltech.foodapp.dao.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseObj implements Serializable {

    private User user;
    private String accessToken;
    private String expiresIn;
}
