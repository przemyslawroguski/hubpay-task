package com.hubpay.task.interview.controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hubpay.task.interview.model.entities.TransactionEntity;
import com.hubpay.task.interview.model.entities.WalletEntity;
import com.hubpay.task.interview.model.request.WalletTransactionAddRequest;
import com.hubpay.task.interview.model.request.WalletTransactionWithdrawRequest;
import com.hubpay.task.interview.service.TransactionService;
import com.hubpay.task.interview.service.WalletService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/wallets")
@AllArgsConstructor
public class WalletController {

    private final WalletService service;

    private final TransactionService transactionService;
    
    @PostMapping("/create")
    public ResponseEntity<WalletEntity> createWallet(@RequestBody WalletEntity wallet) {
        return ResponseEntity.ok().body(this.service.createWallet(wallet)); 
    }

    @PutMapping("/{walletId}/add")
    public ResponseEntity<WalletEntity> addToWallet(@PathVariable UUID walletId, @Valid @RequestBody WalletTransactionAddRequest request) {
        WalletEntity wallet = this.service.performTransactionForWallet(walletId, BigDecimal.valueOf(request.getAmount()));

        TransactionEntity transaction = TransactionEntity.builder()
            .wallet(wallet)
            .amount(BigDecimal.valueOf(request.getAmount()))
            .build();

        transactionService.createTransaction(transaction);

        return ResponseEntity.ok().body(wallet); 
    }

    @PutMapping("/{walletId}/withdraw")
    public ResponseEntity<WalletEntity> withdrawFromWallet(@PathVariable UUID walletId, @Valid @RequestBody WalletTransactionWithdrawRequest request) {
        BigDecimal newAmount = BigDecimal.valueOf(request.getAmount()).negate();

        WalletEntity wallet = this.service.performTransactionForWallet(walletId, newAmount);

        TransactionEntity transaction = TransactionEntity.builder()
            .wallet(wallet)
            .amount(newAmount)
            .build();

        transactionService.createTransaction(transaction);

        return ResponseEntity.ok().body(wallet); 
    }
}
