
package com.example.splitwise_lite.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ExpenseResponseDTO {
    private Long expenseId;
    private String description;
    private BigDecimal totalAmount;
    private String paidByUsername;
    private int numberOfMembers;
    private BigDecimal perPersonShare;
}