package com.bankkata.transaction;
import java.time.LocalDate;
/**
 * Transaction class represents a financial transaction in the bank system.
 * It stores information about the date of the transaction, the amount of money
 * involved, and the balance of the account after the transaction.
 *  *  * @author Nouhaila Zahraoui
 */
public class Transaction {
    private LocalDate date;
    private int amount;
    private int balanceAfterTransaction;
    public Transaction(LocalDate date, int amount, int balanceAfterTransaction) {
        this.date = date;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }
    public LocalDate getDate() {
        return date;
    }
    public int getAmount() {
        return amount;
    }
    public int getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }
}

