package com.taltech.foodapp.service;

import com.taltech.foodapp.dao.User;
import com.taltech.foodapp.dto.UserRequestObj;
import com.taltech.foodapp.dto.UserResponseObj;
import com.taltech.foodapp.util.JwtTokenUtil;
import com.taltech.foodapp.util.Validators;
import com.taltech.foodapp.exceptions.UserException;
import com.taltech.foodapp.repository.UserRepository;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class UserService extends Validators implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    private Session session;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private Logger log= LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    private void setUp(){
    }

    public UserResponseObj save(UserRequestObj userRequestObj) throws UserException {

        return null;
    }

    public Optional<User> findById(int userId){
        return null;
    }

    public Optional<User> findByEmail(String email){
        return null;
    }

    public User update(User existingUser, UserRequestObj userRequestObj) throws UserException {
        return null;
    }

    public User updateUserAddress(User existingUser, UserRequestObj userRequestObj) {
        return null;
    }

    public User updateCreditCard(User user, UserRequestObj userRequestObj) throws UserException {
        return null;
    }

    public UserResponseObj authenticateLogin(UserRequestObj userRequestObj) throws UserException {
        return null;
    }

    private void errorIfEmailExists(UserRequestObj userRequestObj) throws UserException {
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    private List<GrantedAuthority> parseAuthority(List<GrantedAuthority> authorities){
        return null;
    }

    public boolean logout(UserRequestObj userRequestObj) {
        return false;
    }
}
