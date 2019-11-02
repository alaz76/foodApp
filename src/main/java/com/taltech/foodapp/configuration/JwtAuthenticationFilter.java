package com.taltech.foodapp.configuration;

import com.taltech.foodapp.service.UserService;
import com.taltech.foodapp.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        this.authenticationManager= authenticationManager;
    }
}
