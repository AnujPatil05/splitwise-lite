package com.example.splitwise_lite.exception;

public class EmptyGroupException extends RuntimeException {
    public EmptyGroupException(Long groupId) {
        super("Cannot add expense to group with id " + groupId + " because it has no members.");
    }
}

