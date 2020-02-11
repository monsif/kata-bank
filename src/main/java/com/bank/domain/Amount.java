package com.bank.domain;

class Amount {

    private final double amount;

    private Amount(double amount) {
        this.amount = amount;
    }

    Amount add(Amount amount) {
        return new Amount(this.amount + amount.amount);
    }

    Amount subtract(Amount amount) {
        return new Amount(this.amount - amount.amount);
    }

    boolean isNegative() {
        return amount < 0;
    }

    static Amount valueOf(double amount) {
        if (amount < 0) {
            throw new IllegalAmountValueException();
        }
        return new Amount(amount);
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Amount amount = (Amount) o;

        return this.amount == amount.amount;
    }
}
