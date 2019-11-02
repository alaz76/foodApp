package com.taltech.foodapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class AuthenticationException extends Exception {

    private static final long serialUID= 1L;

    private String code;
    private int status;
    private String message;
    private String field;

}
