package com.bank.domain;

import java.time.LocalDateTime;

class Operation {
    private OperationType type;
    private LocalDateTime date;
    private Amount amount;
    private Balance balance;

    Operation(OperationType type, Amount amount, Balance balance, LocalDateTime date) {
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.date = date;
    }

    void print(OperationPrinter printer) {
        printer.print(this.toString());
    }

    @Override
    public String toString() {
        return new StringBuilder().append(date).append(" | ").append(type.getOperation()).append(
                amount).append(" | ").append(balance).toString();
    }
}
