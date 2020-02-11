package com.bank.domain;

import java.util.ArrayList;
import java.util.List;

class Account {
    private final List<Operation> operations;
    private final DateTimeUtil dateTime;
    private Balance balance;

    Account(Balance balance, DateTimeUtil dateTime) {
        this.balance = balance;
        this.dateTime = dateTime;
        this.operations = new ArrayList<>();
    }

    void makeDeposit(Amount deposit) {
        balance = new Balance(balance.getAmount().add(deposit));
        operations.add(new Operation(OperationType.DEPOSIT, deposit, balance, dateTime.now()));
    }

    Balance getBalance() {
        return balance;
    }

    void makeWithdrawal(Amount withdrawal) throws NotEnoughFundsException {
        if (!balance.isWithdrawalAuthorized(withdrawal)) {
            throw new NotEnoughFundsException();
        }
        balance = new Balance(balance.getAmount().subtract(withdrawal));
        operations.add(new Operation(OperationType.WITHDARAWL, withdrawal, balance, dateTime.now()));
    }

    public void showHistory(OperationPrinter printer) {
        operations.forEach(o -> o.print(printer));
    }
}
