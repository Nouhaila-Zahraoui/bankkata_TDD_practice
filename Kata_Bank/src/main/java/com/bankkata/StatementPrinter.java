package com.bankkata;
import com.bankkata.transaction.Transaction;

import java.util.List;

/**
 * StatementPrinter class is responsible for printing a list of transactions in a formatted statement.
 * It outputs the date, amount, and balance for each transaction.
 * * * Author: Nouhaila zahraoui
 */
public class StatementPrinter {

    /**
     * Prints the transaction details in a formatted table.
     *
     * @param transactions The list of transactions to be printed.
     */
    public void print(List<Transaction> transactions) {
        System.out.println("Date || Amount || Balance");
        for (Transaction transaction : transactions) {
            System.out.println(
                    transaction.getDate() + " || " + transaction.getAmount() + " || " + transaction.getBalanceAfterTransaction()
            );
        }
    }
}

