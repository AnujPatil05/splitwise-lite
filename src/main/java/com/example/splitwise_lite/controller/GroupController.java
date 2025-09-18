package com.example.splitwise_lite.controller;

import com.example.splitwise_lite.model.Group;
import com.example.splitwise_lite.model.Expense;
import com.example.splitwise_lite.service.ExpenseService;
import com.example.splitwise_lite.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public Group createGroup(@RequestBody Group group) {
        return groupService.createGroup(group);
    }

    @GetMapping("/{id}")
    public Group getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id);
    }

    @PostMapping("/{groupId}/members/{userId}")
    public Group addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        return groupService.addUserToGroup(groupId, userId);
    }

    @GetMapping("/{groupId}/expenses")
    public List<Expense> getExpensesByGroup(@PathVariable Long groupId) {
        return expenseService.getExpensesByGroupId(groupId);
    }

}