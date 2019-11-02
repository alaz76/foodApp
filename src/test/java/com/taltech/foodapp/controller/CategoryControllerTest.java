package com.taltech.foodapp.controller;

import com.taltech.foodapp.MyappApplication;
import com.taltech.foodapp.TestDataUtil;
import com.taltech.foodapp.dao.Category;
import com.taltech.foodapp.dao.User;
import com.taltech.foodapp.dto.UserResponseObj;
import com.taltech.foodapp.repository.CategoryRepository;
import com.taltech.foodapp.repository.UserRepository;
import com.taltech.foodapp.service.CategoryService;
import com.taltech.foodapp.service.UserService;
import com.taltech.foodapp.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.util.StringUtils;
import org.hamcrest.Matchers;
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
@Import({UserService.class, CategoryService.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MyappApplication.class
)
@AutoConfigureMockMvc
public class CategoryControllerTest extends TestDataUtil {

    @Autowired
    private
    MockMvc mockClient;

    @Autowired
    private
    ObjectMapper objectMapper;

    @MockBean
    private
    UserRepository userRepository;

    @MockBean
    private
    CategoryRepository categoryRepository;

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
    public void getAllCategories() throws Exception {
        Mockito.when(categoryRepository.findAll()).thenReturn(getCategoryList());
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/categories")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].category_id").value(1))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getCategory() throws Exception {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCategoryData()));
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/categories/2")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Category 2"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createCategory() throws Exception {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(getCategoryData());
        mockClient.perform(
                MockMvcRequestBuilders
                        .post("/categories")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(getCategoryRequestObj().get(0)))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Category 2"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteCategory() throws Exception {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getCategoryData()));
        Mockito.doNothing().when(categoryRepository).delete(getCategoryData());
        mockClient.perform(
                MockMvcRequestBuilders
                        .delete("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateCategory() throws Exception {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getCategoryData()));
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(getCategoryData());
        mockClient.perform(
                MockMvcRequestBuilders
                        .put("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(getCategoryRequestObj().get(0)))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Category 1"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }
}