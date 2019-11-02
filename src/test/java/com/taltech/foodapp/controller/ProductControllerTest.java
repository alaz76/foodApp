package com.taltech.foodapp.controller;

import com.taltech.foodapp.MyappApplication;
import com.taltech.foodapp.TestDataUtil;
import com.taltech.foodapp.dao.User;
import com.taltech.foodapp.dao.Product;
import com.taltech.foodapp.dto.UserResponseObj;
import com.taltech.foodapp.repository.CategoryRepository;
import com.taltech.foodapp.repository.UserRepository;
import com.taltech.foodapp.repository.ProductRepository;
import com.taltech.foodapp.service.UserService;
import com.taltech.foodapp.service.ProductService;
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
@Import({UserService.class, ProductService.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MyappApplication.class
)
@AutoConfigureMockMvc
public class ProductControllerTest extends TestDataUtil {

    @Autowired
    private
    MockMvc mockClient;

    @Autowired
    private
    ObjectMapper objectMapper;

    @MockBean
    private
    ProductRepository productRepository;

    @MockBean
    private
    CategoryRepository categoryRepository;

    @MockBean
    private
    UserRepository userRepository;
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
    public void findAll() throws Exception {
        Mockito.when(productRepository.findAll()).thenReturn(getProductResponseList());
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getProduct() throws Exception {
        Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getProductResponseList().get(0)));
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(1))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllProductsInCategory() throws Exception {
        Mockito.when(productRepository.findAllByCategory(Mockito.anyInt())).thenReturn(Optional.ofNullable(getProductResponseList()));
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/products/inCategory/3")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rows.[0].productId").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createProduct() throws Exception{
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(getProductResponseList().get(0));
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getCategoryData()));
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        mockClient.perform(
                MockMvcRequestBuilders
                        .post("/products")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(getProductRequestDataList().get(0)))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(1))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createProductUnauthorized() throws Exception{
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(getProductResponseList().get(0));
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getCategoryData()));
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(1).getUser());
        mockClient.perform(
                MockMvcRequestBuilders
                        .post("/products")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(getProductRequestDataList().get(0)))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteProduct() throws Exception{
        Mockito.doNothing().when(productRepository).deleteById(Mockito.anyInt());
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getCategoryData()));
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        mockClient.perform(
                MockMvcRequestBuilders
                        .delete("/products/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateProduct() throws Exception{
        Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getProductResponseList().get(0)));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(getProductResponseList().get(0));
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getCategoryData()));
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserResponseObj().get(0).getUser());
        mockClient.perform(
                MockMvcRequestBuilders
                        .put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(getProductRequestDataList().get(0)))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

}