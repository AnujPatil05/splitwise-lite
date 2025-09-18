package com.example.splitwise_lite.exception;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(Long id) {
        super("Group not found with id: " + id);
    }
}