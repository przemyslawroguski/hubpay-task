package com.hubpay.task.interview.model.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
public class WalletTransactionWithdrawRequest {
    @DecimalMin(value = "1.0", inclusive = true)
    @DecimalMax(value = "5000.0", inclusive = true)
    private Double amount;
}
