package com.taltech.foodapp.util;

import com.taltech.foodapp.dto.UserRequestObj;
import com.taltech.foodapp.exceptions.UserException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;

import java.util.Objects;

public abstract class Validators {

    protected boolean validateEmail(String email) throws UserException {
        return true;
    }
    protected boolean isPasswordValid(String pass) throws UserException {
        return true;
    }

    protected boolean validateCreditCard(StringBuilder creditCard) throws UserException {
        return true;
    }

    protected boolean isPhoneNumberValid(String phone) throws UserException {
        return true;
    }

    protected boolean validateName(String name) throws UserException {
        return true;
    }
}
