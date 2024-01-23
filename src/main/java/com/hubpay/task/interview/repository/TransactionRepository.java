package com.hubpay.task.interview.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hubpay.task.interview.model.entities.TransactionEntity;
import com.hubpay.task.interview.model.entities.WalletEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findAllByWallet(WalletEntity wallet);
}
