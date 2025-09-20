package com.example.splitwise_lite.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BalanceDTO {
    private String username;
    private BigDecimal balance;

    public BalanceDTO(String username, BigDecimal balance) {
        this.username = username;
        this.balance = balance;
    }
}