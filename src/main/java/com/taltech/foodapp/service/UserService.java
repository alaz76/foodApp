package com.taltech.foodapp.service;

import com.taltech.foodapp.dao.User;
import com.taltech.foodapp.dto.UserRequestObj;
import com.taltech.foodapp.dto.UserResponseObj;
import com.taltech.foodapp.util.Constants;
import com.taltech.foodapp.util.JwtTokenUtil;
import com.taltech.foodapp.util.Validators;
import com.taltech.foodapp.exceptions.UserException;
import com.taltech.foodapp.repository.UserRepository;
import liquibase.util.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        this.session= entityManager.unwrap(Session.class);
    }

    public UserResponseObj save(UserRequestObj userRequestObj) throws UserException {
        validateEmail(userRequestObj.getEmail());
        errorIfEmailExists(userRequestObj);
        isPasswordValid(userRequestObj.getPassword());
        validateName(userRequestObj.getName());
        User user = new User();
        if(Objects.isNull(userRequestObj.getAuthorities())){
            user.setAuthorities(Constants.Roles.USER.name());
        }else{
            user.setAuthorities(userRequestObj.getAuthorities());
        }
        user.setName(userRequestObj.getName());
        user.setEmail(userRequestObj.getEmail());
        user.setPassword(userRequestObj.getPassword());

        String token= jwtTokenUtil.generateToken(user.getEmail());
        user.setToken(token);

        User savedUser = userRepository.save(user);
        UserResponseObj userResponseObj = new UserResponseObj();
        userResponseObj.setUser(savedUser);
        userResponseObj.setAccessToken(token);
        userResponseObj.setExpiresIn(jwtTokenUtil.getExpiredDateFromToken(token).toString());

        return userResponseObj;
    }

    public Optional<User> findById(int userId){
        return userRepository.findById(userId);
    }

    public Optional<User> findByEmail(String email){
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    public User update(User existingUser, UserRequestObj userRequestObj) throws UserException {
        validateEmail(userRequestObj.getEmail());
        existingUser.setEmail(userRequestObj.getEmail());
        existingUser.setName(userRequestObj.getName());
        existingUser.setMobPhone(userRequestObj.getMobPhone());
        return userRepository.save(existingUser);
    }

    public User updateUserAddress(User existingUser, UserRequestObj userRequestObj) {
        existingUser.setAddress1(Objects.nonNull(userRequestObj.getAddress1()) ? userRequestObj.getAddress1() : existingUser.getAddress1());
        existingUser.setAddress2(Objects.nonNull(userRequestObj.getAddress2()) ? userRequestObj.getAddress2() : existingUser.getAddress2());
        existingUser.setCity(Objects.nonNull(userRequestObj.getCity()) ? userRequestObj.getCity() : existingUser.getCity());
        existingUser.setRegion(Objects.nonNull(userRequestObj.getRegion()) ? userRequestObj.getRegion() : existingUser.getRegion());
        existingUser.setPostalCode(Objects.nonNull(userRequestObj.getPostalCode()) ? userRequestObj.getPostalCode() : existingUser.getPostalCode());
        return userRepository.save(existingUser);
    }

    public User updateCreditCard(User user, UserRequestObj userRequestObj) throws UserException {
        validateCreditCard(userRequestObj.getCreditCard());
        user.setCreditCard(String.valueOf(userRequestObj.getCreditCard()));
        return userRepository.save(user);
    }

    public UserResponseObj authenticateLogin(UserRequestObj userRequestObj) throws UserException {

       validateEmail(userRequestObj.getEmail());
       isPasswordValid(userRequestObj.getPassword());

        User user = userRepository.findByEmail(userRequestObj.getEmail());
        if(Objects.isNull(user)){
            throw new UserException(Constants.USR_05_CODE, HttpStatus.UNAUTHORIZED.value(), Constants.USR_05_MESSAGE, "Email");
        }
        if(!userRequestObj.getPassword().equals(user.getPassword())){
            throw new UserException(Constants.USR_01_CODE, HttpStatus.UNAUTHORIZED.value(), Constants.USR_01_MESSAGE, "Email|Password");
        }
        if(Objects.isNull(user.getAuthorities())){
            throw new UserException(Constants.AUT_01_CODE, HttpStatus.UNAUTHORIZED.value(), Constants.AUT_01_MESSAGE, "Authority");
        }
        List<GrantedAuthority> authorities= jwtTokenUtil.getAuthoritiesList(user.getAuthorities());
        if(authorities.contains(new SimpleGrantedAuthority(Constants.Roles.ADMIN.name()))
                || authorities.contains(new SimpleGrantedAuthority(Constants.Roles.USER.name()))){
            throw new UserException(Constants.AUT_01_CODE, HttpStatus.UNAUTHORIZED.value(), Constants.AUT_01_MESSAGE, "Authority");
        }
        String token;
        if(StringUtils.isEmpty(user.getToken())){
            token= jwtTokenUtil.generateToken(user.getEmail());
            user.setToken(token);
            userRepository.save(user);
        }else{
            token= user.getToken();
        }
        UserResponseObj userResponseObj = new UserResponseObj();
        userResponseObj.setUser(user);
        userResponseObj.setAccessToken(token);
        userResponseObj.setExpiresIn(jwtTokenUtil.getExpiredDateFromToken(token).toString());
        return userResponseObj;
    }

    private void errorIfEmailExists(UserRequestObj userRequestObj) throws UserException {
        User user = userRepository.findByEmail(userRequestObj.getEmail());
        if(Objects.nonNull(user)){
            throw new UserException(Constants.USR_04_CODE, HttpStatus.CONFLICT.value(), Constants.USR_04_MESSAGE, "Email");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException("No username found with the mentioned email.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                new BCryptPasswordEncoder().encode(user.getPassword()),
                jwtTokenUtil.getAuthoritiesList(user.getAuthorities()));
    }

    private List<GrantedAuthority> parseAuthority(List<GrantedAuthority> authorities){
        List<GrantedAuthority> grantedAuthorities= new ArrayList<>();
        authorities.forEach(
                grantedAuthority -> grantedAuthorities.add(new SimpleGrantedAuthority(grantedAuthority.getAuthority().replace("ROLE_","")))
        );
        return grantedAuthorities;
    }

    public boolean logout(UserRequestObj userRequestObj) {
        User user= userRepository.findByEmail(userRequestObj.getEmail());
        user.setToken(null);
        userRepository.save(user);
        return true;
    }
}
