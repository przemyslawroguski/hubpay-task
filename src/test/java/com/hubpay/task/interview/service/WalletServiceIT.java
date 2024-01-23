package com.hubpay.task.interview.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubpay.task.interview.model.entities.CustomerEntity;
import com.hubpay.task.interview.model.entities.WalletEntity;
import com.hubpay.task.interview.model.request.WalletTransactionAddRequest;
import com.hubpay.task.interview.model.request.WalletTransactionWithdrawRequest;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletServiceIT {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; 

    private WalletEntity wallet;

    @BeforeEach
    public void setup() {
        CustomerEntity customer = CustomerEntity.builder().username("przemyslaw").build();
        customer = entityManager.persist(customer);
        

        this.wallet = WalletEntity.builder().customer(customer).amount(BigDecimal.valueOf(100.0)).name("primary").build();
        this.wallet = entityManager.persist(wallet);

        entityManager.flush();
    }

    @Test
    public void testAddToWallet() throws Exception {
        WalletTransactionAddRequest request = new WalletTransactionAddRequest();
        request.setAmount(100.00);

        mockMvc.perform(put("/wallets/{walletId}/add", wallet.getWalletId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(wallet.getWalletId().toString()));
    }

    @Test
    public void testWithdrawFromWallet() throws Exception {
        WalletTransactionWithdrawRequest request = new WalletTransactionWithdrawRequest();
        request.setAmount(50.00);

        mockMvc.perform(put("/wallets/{walletId}/withdraw", wallet.getWalletId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(wallet.getWalletId().toString()));
    }

}
