package com.taltech.foodapp.configuration;

import com.taltech.foodapp.dao.User;
import com.taltech.foodapp.service.UserService;
import com.taltech.foodapp.util.Constants;
import com.taltech.foodapp.util.JwtTokenUtil;
import liquibase.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    ApplicationContext applicationContext;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header= request.getHeader(Constants.HEADER_STRING);
        if (header == null || !header.startsWith(Constants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(request));
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req){

        String token = req.getHeader(Constants.HEADER_STRING).split(" ")[1];
            ServletContext context= req.getServletContext();
            WebApplicationContext webApplicationContext= WebApplicationContextUtils.getWebApplicationContext(context);
            this.jwtTokenUtil= Objects.requireNonNull(webApplicationContext).getBean(JwtTokenUtil.class);
            this.userService = webApplicationContext.getBean(UserService.class);
        if (token != null) {
            // parse the token.
            String email = jwtTokenUtil.getUserEmailFromKey(token);
            User user = userService.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("The user with email: "+email+", could not be found."));

            List<GrantedAuthority> authorities= jwtTokenUtil.getAuthoritiesList(user.getAuthorities());

            if (!StringUtils.isEmpty(email)) {
                return new UsernamePasswordAuthenticationToken(
                        user.getUserId(),
                        user.getPassword(),
                        authorities
                );
            }
            return null;
        }
        return null;
    }
}
