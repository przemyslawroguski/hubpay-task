package com.hubpay.task.interview.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hubpay.task.interview.model.entities.CustomerEntity;
import com.hubpay.task.interview.model.entities.TransactionEntity;
import com.hubpay.task.interview.model.entities.WalletEntity;
import com.hubpay.task.interview.service.CustomerService;
import com.hubpay.task.interview.service.TransactionService;
import com.hubpay.task.interview.service.WalletService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {
 
    private final TransactionService service;

    private final CustomerService customerService;

    private final WalletService walletService;

    @GetMapping("/{username}")
    List<TransactionEntity> getAllCustomerTransactions(@PathVariable String username) {
    
        CustomerEntity customerEntity = this.customerService.findByUsername(username);

        List<WalletEntity> wallets = walletService.getAllWalletsByCustomer(customerEntity);

        List<TransactionEntity> transactions = new ArrayList<>();

        wallets.forEach( wallet -> transactions.addAll(service.getAllWalletTransactions(wallet)));

        return transactions;
    }

}
