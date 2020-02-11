package com.bank.domain;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankAccountTest {

    @Mock
    private DateTimeUtil dateTime;

    @Mock
    private OperationPrinter printer;

    @Before
    public void init() {
        when(dateTime.now()).thenReturn(
                LocalDateTime.of(2020, 2, 1, 10, 15));
    }

    @Test
    public void deposit_ShouldAdd_10_ToClientAccount() {
        Account account = new Account(new Balance(Amount.valueOf(10)), dateTime);
        account.makeDeposit(Amount.valueOf(10));
        assertThat(account.getBalance()).isEqualTo(new Balance(Amount.valueOf(20)));
    }

    @Test
    public void withdrawal_ShouldSubtract_10_ToClientAccount() {
        Account account = new Account(new Balance(Amount.valueOf(10)), dateTime);
        account.makeWithdrawal(Amount.valueOf(10));
        assertThat(account.getBalance()).isEqualTo(new Balance(Amount.valueOf(0)));
    }

    @Test(expected = IllegalAmountValueException.class)
    public void deposit_ShouldFail_WhenAmountIsNegative() throws IllegalAmountValueException {
        Account account = new Account(new Balance(Amount.valueOf(10)), dateTime);
        account.makeDeposit(Amount.valueOf(-10));
    }

    @Test(expected = IllegalAmountValueException.class)
    public void withdrawal_ShouldFail_WhenAmountIsNegative() throws IllegalAmountValueException {
        Account account = new Account(new Balance(Amount.valueOf(10)), dateTime);
        account.makeWithdrawal(Amount.valueOf(-10));
    }

    @Test(expected = NotEnoughFundsException.class)
    public void withdrawal_ShouldFail_WhenThereIsNoEnoughFunds() throws NotEnoughFundsException {
        Account account = new Account(new Balance(Amount.valueOf(10)), dateTime);
        account.makeWithdrawal(Amount.valueOf(20));
    }

    @Test
    public void printHistoryShouldShowAllOperations() throws IllegalAmountValueException {
        when(dateTime.now()).thenReturn(
                LocalDateTime.of(2020, 2, 1, 10, 15),
                LocalDateTime.of(2020, 2, 2, 11, 0),
                LocalDateTime.of(2020, 2, 3, 12, 0),
                LocalDateTime.of(2020, 2, 4, 13, 0));

        Account account = new Account(new Balance(Amount.valueOf(50)), dateTime);

        account.makeDeposit(Amount.valueOf(20));
        account.makeDeposit(Amount.valueOf(10));
        account.makeWithdrawal(Amount.valueOf(10));
        account.makeWithdrawal(Amount.valueOf(20));

        account.showHistory(printer);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(printer, times(4)).print(captor.capture());

        List<String> capturedValues = captor.getAllValues();

        assertThat(capturedValues).containsExactly(
                "2020-02-01T10:15 | +20.0 | 70.0",
                "2020-02-02T11:00 | +10.0 | 80.0",
                "2020-02-03T12:00 | -10.0 | 70.0",
                "2020-02-04T13:00 | -20.0 | 50.0");
        //uuuu-MM-dd'T'HH:mm
    }
}
