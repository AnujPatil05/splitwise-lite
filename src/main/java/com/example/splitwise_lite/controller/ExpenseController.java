package com.example.splitwise_lite.controller;

import com.example.splitwise_lite.dto.ExpenseResponseDTO;
import com.example.splitwise_lite.model.Expense;
import com.example.splitwise_lite.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ExpenseResponseDTO createExpense(@Valid @RequestBody Expense expense) {
        return expenseService.createExpense(expense);
    }

}