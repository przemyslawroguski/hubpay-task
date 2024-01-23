package com.hubpay.task.interview.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubpay.task.interview.model.entities.CustomerEntity;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerServiceIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; 

    @Test
    public void testCreateCustomer() throws Exception {
        CustomerEntity customer = CustomerEntity.builder().username("przemyslaw").build();

        String customerJson = objectMapper.writeValueAsString(customer);

        mockMvc.perform(post("/customers/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").exists()); 
    }
    
}
