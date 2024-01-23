package com.hubpay.task.interview.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.hubpay.task.interview.model.entities.CustomerEntity;
import com.hubpay.task.interview.model.entities.WalletEntity;
import com.hubpay.task.interview.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository repository;

    @InjectMocks
    private WalletService service;

    @Test
    public void testCreateWallet() {
        WalletEntity wallet = WalletEntity.builder()
                    .amount(BigDecimal.valueOf(100.0))
                    .customer(CustomerEntity.builder().username("test").build())
                    .build();
        when(repository.save(any(WalletEntity.class))).thenReturn(wallet);

        WalletEntity created = service.createWallet(wallet);

        assertNotNull(created);
        verify(repository).save(wallet);
    }

    @Test
    public void testPerformTransactionForWalletWithValidTransaction() {
        UUID walletId = UUID.randomUUID();
        BigDecimal initialAmount = BigDecimal.valueOf(100.0);
        BigDecimal transactionAmount = BigDecimal.valueOf(50.0);

        WalletEntity wallet = new WalletEntity();
        wallet.setAmount(initialAmount);

        when(repository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(repository.save(any(WalletEntity.class))).thenReturn(wallet);

        WalletEntity updatedWallet = service.performTransactionForWallet(walletId, transactionAmount);

        assertNotNull(updatedWallet);
        assertEquals(initialAmount.add(transactionAmount), updatedWallet.getAmount());
        verify(repository).save(wallet);
    }

    @Test
    public void testPerformTransactionForWalletWithInvalidTransaction() {
        UUID walletId = UUID.randomUUID();
        BigDecimal initialAmount = BigDecimal.valueOf(100.0);
        BigDecimal transactionAmount = BigDecimal.valueOf(-200.0);

        WalletEntity wallet = new WalletEntity(); // Set up wallet data
        wallet.setAmount(initialAmount);

        when(repository.findById(walletId)).thenReturn(Optional.of(wallet));

        assertThrows(ResponseStatusException.class, () -> {
            service.performTransactionForWallet(walletId, transactionAmount);
        });
    }

    @Test
    public void testPerformTransactionForWalletNotFound() {
        UUID walletId = UUID.randomUUID();
        BigDecimal transactionAmount = BigDecimal.valueOf(50.0);

        when(repository.findById(walletId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            service.performTransactionForWallet(walletId, transactionAmount);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}
