package com.hubpay.task.interview.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hubpay.task.interview.model.entities.CustomerEntity;
import com.hubpay.task.interview.model.entities.WalletEntity;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, UUID> {

    List<WalletEntity> findAllByCustomer(CustomerEntity customer);}
