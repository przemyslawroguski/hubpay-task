package com.hubpay.task.interview.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hubpay.task.interview.model.entities.CustomerEntity;
import com.hubpay.task.interview.model.entities.WalletEntity;
import com.hubpay.task.interview.repository.WalletRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class WalletService {
    
    private final WalletRepository repository;

    public WalletEntity createWallet(WalletEntity wallet) {
        log.info("creating wallet for username", wallet.getCustomer().getUsername());

        return this.repository.save(wallet);
    };

    public List<WalletEntity> getAllWalletsByCustomer(CustomerEntity customer) {
        log.info("getting all customer wallets: ", customer.getUsername());

        return this.repository.findAllByCustomer(customer);
    }

    @Transactional
    public WalletEntity performTransactionForWallet(UUID walletId, BigDecimal amount) {
        log.info("performing transaction for wallet: ", walletId, " and amount: ", amount.toString());

        WalletEntity wallet = this.repository.findById(walletId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found wallet with id: " + walletId.toString()));

        BigDecimal newAmount = wallet.getAmount().add(amount);  
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            log.warn("amount lower than 0");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount after transaction is lower than 0");
        }

        wallet.setAmount(newAmount);

        return this.repository.save(wallet);
    }
}
