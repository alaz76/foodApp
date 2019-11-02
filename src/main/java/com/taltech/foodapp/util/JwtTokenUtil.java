package com.taltech.foodapp.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

/**
 * This class performs the creation and validation of jwt tokens.
 */
@Component
public class JwtTokenUtil implements Serializable {

    public static final long serialVersionUUID= -2550185264626066388L;
    @Value("${jwt.secret}")
    private String jwtKey;

}
