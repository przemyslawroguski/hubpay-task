package com.hubpay.task.interview.model.entities;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue
    @Column(name = "transaction_id", columnDefinition = "UUID")
    public UUID transactionId;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    public WalletEntity wallet;

    @Column(nullable = false)
    public BigDecimal amount;

    @Column
    public ZonedDateTime time;
}
