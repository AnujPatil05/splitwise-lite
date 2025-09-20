package com.example.splitwise_lite.service;

import com.example.splitwise_lite.dto.ExpenseResponseDTO;
import com.example.splitwise_lite.exception.EmptyGroupException;
import com.example.splitwise_lite.exception.GroupNotFoundException;
import com.example.splitwise_lite.exception.UserNotFoundException;
import com.example.splitwise_lite.model.Expense;
import com.example.splitwise_lite.model.Group;
import com.example.splitwise_lite.model.User;
import com.example.splitwise_lite.repository.ExpenseRepository;
import com.example.splitwise_lite.repository.GroupRepository;
import com.example.splitwise_lite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.splitwise_lite.dto.BalanceDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Expense> getExpensesByGroupId(Long groupId) {
        // We simply call the new method we created in the repository
        return expenseRepository.findByGroupId(groupId);
    }

    @Transactional
    public ExpenseResponseDTO createExpense(Expense expense) {
        Long groupId = expense.getGroup().getId();
        Long userId = expense.getPaidBy().getId();

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (group.getMembers().isEmpty()) {
            throw new EmptyGroupException(groupId);
        }

        expense.setGroup(group);
        expense.setPaidBy(user);

        BigDecimal totalAmount = expense.getAmount();
        int numberOfMembers = group.getMembers().size();
        BigDecimal perPersonShare = totalAmount.divide(BigDecimal.valueOf(numberOfMembers), 2, RoundingMode.HALF_UP);

        Expense savedExpense = expenseRepository.save(expense);

        return new ExpenseResponseDTO(
                savedExpense.getId(),
                savedExpense.getDescription(),
                savedExpense.getAmount(),
                user.getUsername(),
                numberOfMembers,
                perPersonShare
        );
    }


    public List<BalanceDTO> getGroupBalance(Long groupId) {
        // 1. Get the group and all its expenses
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
        List<Expense> expenses = expenseRepository.findByGroupId(groupId);

        int memberCount = group.getMembers().size();
        if (memberCount == 0) {
            return new ArrayList<>(); // Return empty list if no members
        }

        // 2. Calculate the total amount spent in the group
        BigDecimal totalSpent = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3. Calculate the average share per person
        BigDecimal averageShare = totalSpent.divide(BigDecimal.valueOf(memberCount), 2, RoundingMode.HALF_UP);

        // 4. Calculate how much each member has paid
        Map<User, BigDecimal> paidAmountMap = new HashMap<>();
        for (User member : group.getMembers()) {
            paidAmountMap.put(member, BigDecimal.ZERO);
        }
        for (Expense expense : expenses) {
            User paidBy = expense.getPaidBy();
            paidAmountMap.put(paidBy, paidAmountMap.get(paidBy).add(expense.getAmount()));
        }

        // 5. Calculate the final balance for each member
        List<BalanceDTO> balances = new ArrayList<>();
        for (Map.Entry<User, BigDecimal> entry : paidAmountMap.entrySet()) {
            User member = entry.getKey();
            BigDecimal amountPaid = entry.getValue();
            BigDecimal balance = amountPaid.subtract(averageShare);
            balances.add(new BalanceDTO(member.getUsername(), balance));
        }

        return balances;
    }

}