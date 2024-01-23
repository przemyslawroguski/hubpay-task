package com.hubpay.task.interview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hubpay.task.interview.model.entities.TransactionEntity;
import com.hubpay.task.interview.model.entities.WalletEntity;
import com.hubpay.task.interview.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

     @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionService service;

    @Test
    public void testCreateTransaction() {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setWallet(new WalletEntity());
        transaction.setAmount(new BigDecimal("100.00"));

        when(repository.save(any(TransactionEntity.class))).thenReturn(transaction);

        TransactionEntity created = service.createTransaction(transaction);

        assertNotNull(created);
        assertNotNull(created.getTime()); 
        verify(repository).save(transaction);
    }

    @Test
    public void testGetAllWalletTransactions() {
        WalletEntity wallet = new WalletEntity();
        wallet.setWalletId(UUID.randomUUID());

        TransactionEntity transaction1 = new TransactionEntity();
        TransactionEntity transaction2 = new TransactionEntity();
        List<TransactionEntity> transactions = Arrays.asList(transaction1, transaction2);

        when(repository.findAllByWallet(wallet)).thenReturn(transactions);

        List<TransactionEntity> retrievedTransactions = service.getAllWalletTransactions(wallet);

        assertEquals(2, retrievedTransactions.size());
        verify(repository).findAllByWallet(wallet);
    }
    
}
