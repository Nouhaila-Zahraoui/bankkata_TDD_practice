import com.bankkata.*;
import com.bankkata.account.Account;
import com.bankkata.exceptions.InsufficientFundsException;
import com.bankkata.exceptions.InvalidTransactionException;
import com.bankkata.transaction.Transaction;
import com.bankkata.transaction.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
/**
 * Unit tests for the Account class using Mockito for mocking dependencies.
 *  * * Author: Nouhaila zahraoui
 */
public class AccountTest {
    private Account account;
    private TransactionRepository transactionRepository;
    private StatementPrinter statementPrinter;

    /**
     * Setup method to initialize mocks before each test.
     */
    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        statementPrinter = mock(StatementPrinter.class);
        account = new Account(transactionRepository, statementPrinter);
    }
    /**
     * Test cases for deposit() method
     */
    @Test
    void depositShouldRecordTransaction() {
        // ARRANGE
        int amount = 1000;
        LocalDate date = LocalDate.now();

        // ACT
        account.deposit(amount);

        // ASSERT
        verify(transactionRepository).addTransaction(argThat(t ->
                t.getAmount() == amount && t.getDate().equals(date)
        ));
    }
    @Test
    void depositZeroShouldThrowException() {
        assertThrows(InvalidTransactionException.class, () -> account.deposit(0));
    }
    @Test
    void depositNegativeAmountShouldThrowException() {
        assertThrows(InvalidTransactionException.class, () -> account.deposit(-100));
    }

    /**
     * Test cases for withdraw() method
     */

    @Test
    void withdrawShouldRecordTransaction() {
        // ARRANGE
        int amount = 500;
        LocalDate date = LocalDate.now();
        when(transactionRepository.getAllTransactions()).thenReturn(
                List.of(new Transaction(date, 1000, 1000))
        );

        // ACT
        account.withdraw(amount);

        // ASSERT
        verify(transactionRepository).addTransaction(argThat(t ->
                t.getAmount() == -amount && t.getDate().equals(date)
        ));
    }

    @Test
    void withdrawZeroShouldThrowException() {
        assertThrows(InvalidTransactionException.class, () -> account.withdraw(0));
    }
    @Test
    void withdrawNegativeAmountShouldThrowException() {
        assertThrows(InvalidTransactionException.class, () -> account.withdraw(-100));
    }
    @Test
    void withdrawMoreThanBalanceShouldThrowException() {
        when(transactionRepository.getAllTransactions()).thenReturn(
                List.of(new Transaction(LocalDate.now(), 1000, 1000))
        );
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(2000));
    }
    @Test
    void withdrawExactBalanceShouldSucceed() {
        // Arrange
        LocalDate date = LocalDate.now();
        when(transactionRepository.getAllTransactions()).thenReturn(
                List.of(new Transaction(date, 2000, 2000)) // Initial balance is 2000
        );

        // Act
        account.withdraw(2000);
        when(transactionRepository.getAllTransactions()).thenReturn(
                List.of(new Transaction(date, 2000, 2000), new Transaction(date, -2000, 0)) // Now balance is 0
        );

        // Assert
        assertEquals(0, account.getBalance()); // Should be 0 after withdrawal
    }

    @Test
    void withdrawNoTransactionsShouldThrowException() {
        // No initial transactions in the account
        when(transactionRepository.getAllTransactions()).thenReturn(List.of());
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(100));
    }


    /**
     * Test cases for printStatement() method
     */


    @Test
    void printStatementShouldCallStatementPrinter() {
        // ARRANGE
        TransactionRepository transactionRepository = mock(TransactionRepository.class);
        StatementPrinter statementPrinter = mock(StatementPrinter.class);

        List<Transaction> transactions = List.of(
                new Transaction(LocalDate.of(2024, 1, 1), 1000, 1000),
                new Transaction(LocalDate.of(2024, 1, 2), -500, 500)
        );
        when(transactionRepository.getAllTransactions()).thenReturn(transactions);
        Account account = new Account(transactionRepository, statementPrinter);

        // Make mock print the transactions when called
        doAnswer(invocation -> {
            List<Transaction> capturedTransactions = invocation.getArgument(0);
            System.out.println("Date || Amount || Balance");
            for (Transaction t : capturedTransactions) {
                System.out.printf("%s || %d || %d%n", t.getDate(), t.getAmount(), t.getBalanceAfterTransaction());
            }
            return null;
        }).when(statementPrinter).print(anyList());

        // ACT
        account.printStatement();

        // ASSERT
        verify(statementPrinter).print(transactions);
    }

    @Test
    void printStatementEmptyShouldNotFail() {
        when(transactionRepository.getAllTransactions()).thenReturn(List.of());
        account.printStatement();
        verify(statementPrinter).print(List.of());
    }

    /**
     * Test multiple transactions
     */

    @Test
    void multipleTransactionsTest() {
        // Mock the initial state of the transaction repository
        when(transactionRepository.getAllTransactions()).thenReturn(
                List.of(new Transaction(LocalDate.now(), 0, 0)) // Start with an initial balance of 0
        );

        // Sequence of operations
        account.deposit(1000); // Balance should now be 1000
        when(transactionRepository.getAllTransactions()).thenReturn(
                List.of(new Transaction(LocalDate.now(), 1000, 1000)) // Updated state after deposit
        );

        account.withdraw(500); // Balance should now be 500
        when(transactionRepository.getAllTransactions()).thenReturn(
                List.of(new Transaction(LocalDate.now(), 1000, 1000),
                        new Transaction(LocalDate.now(), -500, 500)) // Updated state after withdrawal
        );

        account.deposit(2000); // Balance should now be 2500
        when(transactionRepository.getAllTransactions()).thenReturn(
                List.of(
                        new Transaction(LocalDate.now(), 1000, 1000),
                        new Transaction(LocalDate.now(), -500, 500),
                        new Transaction(LocalDate.now(), 2000, 2500)
                ) // Updated state after second deposit
        );

        // Verify the transactions were recorded correctly
        verify(transactionRepository).addTransaction(argThat(t -> t.getAmount() == 1000));
        verify(transactionRepository).addTransaction(argThat(t -> t.getAmount() == -500));
        verify(transactionRepository).addTransaction(argThat(t -> t.getAmount() == 2000));

        // Ensure correct final balance
        int finalBalance = account.getBalance();
        assertEquals(2500, finalBalance);

        // Display results
        System.out.println("Transactions:");
        List<Transaction> transactions = transactionRepository.getAllTransactions();
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getDate() + " || " + transaction.getAmount() + " || " + transaction.getBalanceAfterTransaction());
        }
        System.out.println("Final Balance: " + finalBalance);
    }
}
