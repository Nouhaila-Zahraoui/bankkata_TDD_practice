package com.bankkata.account;
import com.bankkata.StatementPrinter;
import com.bankkata.transaction.Transaction;
import com.bankkata.transaction.TransactionRepository;
import com.bankkata.exceptions.InsufficientFundsException;
import com.bankkata.exceptions.InvalidTransactionException;
import java.time.LocalDate;
import java.util.List;
/**
 * Account class represents an implementation of Account interface
 *  * @author Nouhaila Zahraoui
 */
public class Account implements AccountService {
    private final TransactionRepository transactionRepository;
    private final StatementPrinter statementPrinter;
    public Account(TransactionRepository transactionRepository, StatementPrinter statementPrinter) {
        this.transactionRepository = transactionRepository;
        this.statementPrinter = statementPrinter;
    }
    public void printStatement() {
        System.out.println("DEBUG: printStatement() was called!");
        List<Transaction> transactions = transactionRepository.getAllTransactions();
        statementPrinter.print(transactions);
    }
    @Override
    public void deposit(int amount) {
        if (amount <= 0) {
            throw new InvalidTransactionException("Deposit amount must be positive!");
        }
        int newBalance = getCurrentBalance() + amount;
        transactionRepository.addTransaction(new Transaction(LocalDate.now(), amount, newBalance));
    }
    @Override
    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new InvalidTransactionException("Withdrawal amount must be positive!");
        }
        int newBalance = getCurrentBalance() - amount;
        if (newBalance < 0) {
            throw new InsufficientFundsException("Insufficient funds! Cannot withdraw " + amount);
        }
        transactionRepository.addTransaction(new Transaction(LocalDate.now(), -amount, newBalance));
    }

    /**
     * Helper method to retrieve the current balance of the account by checking the
     * most recent transaction. Returns 0 if there are no transactions yet.
     */
    private int getCurrentBalance() {
        List<Transaction> transactions = transactionRepository.getAllTransactions();
        return transactions.isEmpty() ? 0 : transactions.get(transactions.size() - 1).getBalanceAfterTransaction();
    }
    public int getBalance() {
        return getCurrentBalance();
    }
}
