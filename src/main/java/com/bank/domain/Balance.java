package com.bank.domain;

class Balance {
    private final Amount amount;

    Balance(Amount amount) {
        this.amount = amount;
    }

    Amount getAmount() {
        return this.amount;
    }

    boolean isWithdrawalAuthorized(Amount withdrawal) {
        return !amount.subtract(withdrawal).isNegative();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Balance balance = (Balance) o;

        return amount != null ? amount.equals(balance.amount) : balance.amount == null;
    }

    @Override
    public String toString() {
        return amount.toString();
    }
}
