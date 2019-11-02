package com.taltech.foodapp.util;

import com.taltech.foodapp.dao.User;
import com.taltech.foodapp.exceptions.AuthenticationException;
import com.taltech.foodapp.exceptions.UserException;
import com.taltech.foodapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public abstract class TokenValidator {

}
