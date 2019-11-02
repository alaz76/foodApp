package com.taltech.foodapp.exceptions;

import com.taltech.foodapp.MyappApplication;
import com.taltech.foodapp.TestDataUtil;
import com.taltech.foodapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@Import({UserService.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MyappApplication.class
)
@AutoConfigureMockMvc
public class CustomErrorControllerTest extends TestDataUtil {

    @Autowired
    private
    MockMvc mockClient;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Ignore
    public void exception() throws Exception {

        mockClient.perform(MockMvcRequestBuilders
                .get("/exception")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}