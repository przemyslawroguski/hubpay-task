package com.hubpay.task.interview.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hubpay.task.interview.model.entities.TransactionEntity;
import com.hubpay.task.interview.model.entities.WalletEntity;
import com.hubpay.task.interview.repository.TransactionRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;

    public TransactionEntity createTransaction(TransactionEntity transaction) {
        log.info("adding new transaction for wallet: ", transaction.wallet.getWalletId(), " and amount: ", transaction.getAmount());

        transaction.setTime(ZonedDateTime.now());
        return this.repository.save(transaction);
    }

    public List<TransactionEntity> getAllWalletTransactions(WalletEntity wallet) {
        return this.repository.findAllByWallet(wallet);
    }
    
}
