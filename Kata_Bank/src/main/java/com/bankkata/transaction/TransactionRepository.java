package com.bankkata.transaction;
import java.util.ArrayList;
import java.util.List;
/**
 * TransactionRepository class manages a collection of transactions. It allows adding new
 * transactions and retrieving all stored transactions.
 *  *  * @author Nouhaila Zahraoui
 */
public class TransactionRepository {
    private final List<Transaction> transactions = new ArrayList<>();

    /**
     * Adds a new transaction to the repository.
     *
     * @param transaction The transaction to be added.
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Retrieves all transactions stored in the repository.
     *
     * @return A list of all transactions.
     */
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
}
