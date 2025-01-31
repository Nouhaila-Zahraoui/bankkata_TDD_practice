package com.bankkata.account;

/**
 * Interface representing a bank account management service.
 * This interface defines the fundamental operations that a bank account must support:
 * - Depositing money
 * - Withdrawing money
 * - Printing a bank statement
 *
 * @author Nouhaila Zahraoui
 */
public interface AccountService {

    /**
     * Deposits a specified amount into the account.
     *
     * @param amount The amount to deposit (must be strictly positive).
     * @throws IllegalArgumentException if the amount is negative or zero.
     */
    void deposit(int amount);

    /**
     * Withdraws a specified amount from the account.
     *
     * @param amount The amount to withdraw (must be strictly positive and not exceed the available balance).
     * @throws IllegalArgumentException if the amount is negative or zero.
     * @throws IllegalStateException if the balance is insufficient.
     */
    void withdraw(int amount);

    /**
     * Displays the transaction history as a bank statement.
     * The statement is sorted in descending order by date (most recent transactions first).
     */
    void printStatement();

    /**
     * The minimum allowed amount for transactions to prevent input errors.
     */
    int MIN_AMOUNT = 1;
}
