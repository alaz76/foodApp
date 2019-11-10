package com.taltech.foodapp.controller;

import com.taltech.foodapp.MyappApplication;
import com.taltech.foodapp.TestDataUtil;
import com.taltech.foodapp.dao.User;
import com.taltech.foodapp.dto.UserResponseObj;
import com.taltech.foodapp.repository.UserRepository;
import com.taltech.foodapp.service.UserService;
import com.taltech.foodapp.util.Constants;
import com.taltech.foodapp.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.util.StringUtils;
import org.hamcrest.Matchers;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@RunWith(SpringRunner.class)
@Import({UserService.class, JwtTokenUtil.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MyappApplication.class
)
@AutoConfigureMockMvc
public class UserControllerTest extends TestDataUtil {

    @Autowired
    private
    MockMvc mockClient;

    @Autowired
    private
    ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private
    Session session;
    private String user_token;
    private String admin_token;

    @Before
    public void setUp() throws Exception {
        //For USER users.
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUserResponseObj().get(1).getUser());
        String result= mockClient.perform(MockMvcRequestBuilders
                .post("/users")
                .content(objectMapper.writeValueAsString(getUserRequestObj().get(1)))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.userId").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn().getResponse()
                .getContentAsString();
        UserResponseObj responseObj= objectMapper.readValue(result, UserResponseObj.class);
        this.user_token = StringUtils.isNotEmpty(this.user_token) ? this.user_token : responseObj.getAccessToken();

        //For ADMIN users.
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUserResponseObj().get(0).getUser());
        String admin_result= mockClient.perform(MockMvcRequestBuilders
                .post("/users")
                .content(objectMapper.writeValueAsString(getUserRequestObj().get(0)))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.userId").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn().getResponse()
                .getContentAsString();
        UserResponseObj responseObjA= objectMapper.readValue(admin_result, UserResponseObj.class);
        this.admin_token = StringUtils.isNotEmpty(this.admin_token) ? this.admin_token : responseObjA.getAccessToken();
    }

    @Test
    public void create() throws Exception {
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUserResponseObj().get(0).getUser());
        mockClient.perform(MockMvcRequestBuilders
                .post("/users")
                .content(objectMapper.writeValueAsString(getUserRequestObj().get(0)))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.userId").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createUser2() throws Exception {
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUserResponseObj().get(1).getUser());
        mockClient.perform(MockMvcRequestBuilders
                .post("/users")
                .content(objectMapper.writeValueAsString(getUserRequestObj().get(0)))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.userId").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test(expected = Exception.class)
    public void createUser2FailedCase() throws Exception {
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUserResponseObj().get(1).getUser());
        mockClient.perform(MockMvcRequestBuilders
                .post("/users")
                .content(String.valueOf(null))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.userId").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void login() throws Exception{
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUserResponseObj().get(0).getUser());
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        mockClient.perform(MockMvcRequestBuilders
                .post("/users/login")
                .content(objectMapper.writeValueAsString(getUserRequestObj().get(0)))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test(expected = Exception.class)
    public void loginFailedScenario() throws Exception{
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUserResponseObj().get(0).getUser());
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        mockClient.perform(MockMvcRequestBuilders
                .post("/users/login")
                .content(String.valueOf(null))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findUserById() throws Exception {

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUserResponseObj().get(0).getUser());
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getUserResponseObj().get(0).getUser()));
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        mockClient.perform(MockMvcRequestBuilders
        .get("/users")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .header(Constants.HEADER_STRING,this.admin_token))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.notNullValue()))
        .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findUserById2() throws Exception {

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUserResponseObj().get(1).getUser());
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getUserResponseObj().get(1).getUser()));
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(1).getUser());
        mockClient.perform(MockMvcRequestBuilders
        .get("/users")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.notNullValue()))
        .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findUserByIdFailedScenario() throws Exception {

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUserResponseObj().get(1).getUser());
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(1).getUser());
        mockClient.perform(MockMvcRequestBuilders
        .get("/users")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.notNullValue()))
        .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateUser() throws Exception {

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUpdatedUserResponseObj().getUser());
        Mockito.when(session.load(User.class,1)).thenReturn(getUserResponseObj().get(0).getUser());
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        mockClient.perform(MockMvcRequestBuilders
                .put("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .header(Constants.HEADER_STRING,this.admin_token)
                .content(objectMapper.writeValueAsString(getUpdateUserPaylod())))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Anupam Rakshit"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateUserFailedScenario() throws Exception {

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUpdatedUserResponseObj().getUser());
        Mockito.when(session.load(User.class,1)).thenReturn(getUserResponseObj().get(0).getUser());
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        mockClient.perform(MockMvcRequestBuilders
                .put("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(String.valueOf(null)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Anupam Rakshit"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateCreditCard() throws Exception {
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUpdatedCCResponseObj().getUser());
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        mockClient.perform(MockMvcRequestBuilders
                .put("/user/creditCard")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .header(Constants.HEADER_STRING,this.admin_token)
                .content(objectMapper.writeValueAsString(getUserRequestObj().get(1))))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.creditCard").value(Matchers.notNullValue()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateCreditCardFailedScenario() throws Exception {
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUpdatedCCResponseObj().getUser());
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        mockClient.perform(MockMvcRequestBuilders
                .put("/user/creditCard")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(String.valueOf(null)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.creditCard").value(Matchers.notNullValue()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void logout() throws Exception{
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUserResponseObj().get(0).getUser());
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        mockClient.perform(MockMvcRequestBuilders
                .post("/users/logout")
                .content(objectMapper.writeValueAsString(getUserRequestObj().get(0)))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .header(Constants.HEADER_STRING,this.admin_token))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }
}
