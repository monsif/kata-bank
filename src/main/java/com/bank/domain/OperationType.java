package com.bank.domain;

public enum OperationType {

    WITHDARAWL("-"), DEPOSIT("+");

    private final String operation;

    OperationType(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return this.operation;
    }

}
