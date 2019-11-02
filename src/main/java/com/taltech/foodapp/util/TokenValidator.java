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

    @Autowired
    private
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private
    UserService userService;

    protected User isTokenValid(String token) throws AuthenticationException, UserException {
        User user;
        String email= jwtTokenUtil.getUserEmailFromKey(token);
        if(!jwtTokenUtil.isTokenValid(token)){
            throw new AuthenticationException(Constants.AUT_01_CODE, HttpStatus.UNAUTHORIZED.value(), Constants.AUT_01_MESSAGE, "token");
        }
        user = userService.findByEmail(email)
                .orElseThrow(() -> new UserException(Constants.AUT_02_CODE, HttpStatus.UNAUTHORIZED.value(), Constants.AUT_02_MESSAGE, "customerId"));
        return user;
    }
}
