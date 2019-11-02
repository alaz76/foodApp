package com.taltech.foodapp.controller;

import com.taltech.foodapp.dao.User;
import com.taltech.foodapp.dto.UserRequestObj;
import com.taltech.foodapp.dto.UserResponseObj;
import com.taltech.foodapp.service.UserService;
import com.taltech.foodapp.util.Constants;
import com.taltech.foodapp.util.JwtTokenUtil;
import com.taltech.foodapp.util.TokenValidator;
import com.taltech.foodapp.exceptions.AuthenticationException;
import com.taltech.foodapp.exceptions.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
class UserController extends TokenValidator {

    private Logger log= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /**
     * This endpoints allow a user to create a new account.
     * @param userRequestObj
     * @return ResponseEntity<UserResponseObj>
     * @throws UserException
     */
    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserResponseObj> registerUser(@RequestBody UserRequestObj userRequestObj) throws UserException {
        log.info("Attempt to save a user");
        log.debug("UserRequest object found to save a user as, {}", userRequestObj);
        return null;
    }

    /**
     * This endpoint allows a user to login to their user account.
     * @param userRequestObj
     * @return ResponseEntity<UserResponseObj>
     * @throws UserException
     */
    @PostMapping(value = "/users/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserResponseObj> login(@RequestBody UserRequestObj userRequestObj) throws UserException {
        log.info("User login request received.");
        log.debug("User with email, {} is trying to authenticate.", userRequestObj.getEmail());
        return null;
    }


    /**
     * This endpoint allows a user to logout from their user account.
     * @param userRequestObj
     * @return ResponseEntity<UserResponseObj>
     */
    @PostMapping(value = "/users/logout", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Boolean> logout(@RequestBody UserRequestObj userRequestObj) {
        log.info("User with email, {}, logout request received.", userRequestObj.getEmail());
        return null;
    }

    /**
     * This endpoint retrieves user information using the user id in the token provided in the header of the request.
     * @param request
     * @return ResponseEntity<User>
     * @throws AuthenticationException
     * @throws UserException
     */
    @GetMapping("/users")
    public ResponseEntity<User> findUserById(HttpServletRequest request) throws AuthenticationException, UserException {
        log.info("Fetching a user with the request token.");
        return null;
    }

    /**
     * This endpoint updates the user details.
     * @param request
     * @param userRequestObj
     * @return ResponseEntity<User>
     * @throws AuthenticationException
     * @throws UserException
     */
    @PutMapping("/user")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> updateUser(HttpServletRequest request, @RequestBody UserRequestObj userRequestObj)
            throws AuthenticationException, UserException {
        log.info("Authenticating a user with the request token.");
        log.info("Updating the existing user.");
        log.debug("Updating the existing user with the update request object, {}.", userRequestObj);
        return null;
    }

    /**
     * This endpoint updates the user address.
     * @param request
     * @param userRequestObj
     * @return ResponseEntity<User>
     * @throws AuthenticationException
     * @throws UserException
     */
    @PutMapping("/user/address")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> updateUserAddress(HttpServletRequest request, @RequestBody UserRequestObj userRequestObj)
            throws AuthenticationException, UserException {
        log.info("Authenticating a user with the request token, for user address update.");
        log.info("Updating a user Address with the received update request object.");
        log.debug("User address update request received with object, {}", userRequestObj);
        return null;
    }

    /**
     * This endpoint updates the user address.
     * @param request
     * @param userRequestObj
     * @return ResponseEntity<User>
     * @throws AuthenticationException
     * @throws UserException
     */
    @PutMapping("/user/creditCard")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> updateCreditCard(HttpServletRequest request, @RequestBody UserRequestObj userRequestObj) throws AuthenticationException, UserException {
        log.info("Authenticating a user with the request token, for credit card update request.");
        log.info("Updating credit card information of the user.");
        log.debug("Updating credit card information of the user, with request object as, {}.", userRequestObj);
        return null;
    }
}
