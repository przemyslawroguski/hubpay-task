package com.hubpay.task.interview.model.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
public class WalletTransactionAddRequest {
    @DecimalMin(value = "10.0", inclusive = true)
    @DecimalMax(value = "10000.0", inclusive = true)
    private Double amount;
}
