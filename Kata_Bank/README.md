# Bank Kata - Java TDD with Mockito and JUnit

This project implements a basic **Bank Kata** following Test-Driven Development (TDD) principles using **Java 17**, **JUnit**, and **Mockito**. The code adheres to the **SOLID** principles, with a strong emphasis on **Single Responsibility Principle (SRP)**.

## Features

- **Deposit and Withdraw** functionality, with **exception handling** for invalid transactions.
- **Statement printing** that formats transactions and displays them correctly, using a dedicated **StatementPrinter** class to follow SRP.
- Unit tests covering all edge cases and behaviors using **Mockito** for mocking dependencies and **JUnit** for assertions.

## Key Concepts

- **Test-Driven Development (TDD)**: All features and functionality were developed incrementally with tests written before the code itself, ensuring that every piece of functionality is covered and verified.
- **Mockist Testing**: Dependencies like `TransactionRepository` and `StatementPrinter` are mocked to isolate and test the core logic of the `Account` class without external dependencies.
- **SOLID Principles**: The code follows **SOLID** principles, particularly:
    - **Single Responsibility Principle (SRP)**: The `Account` class is responsible solely for managing transactions, while the `StatementPrinter` handles printing the transaction history, keeping both classes independent and focused on one responsibility.
    - **Open/Closed Principle, Liskov Substitution, Interface Segregation, Dependency Inversion**: These principles are respected to ensure maintainability, scalability, and extensibility.

## Technologies Used

- **Java 17**
- **JUnit 5** for unit testing
- **Mockito** for mocking dependencies in tests

## How to Run

1. Clone the repository:
   ```bash
   git clone  https://github.com/Nouhaila-Zahraoui/bankkata_TDD_practice.git
2. Navigate into the project directory:
   ```bash
   cd bankkata_TDD_practice
3. Build the project with Maven:
      ```bash
   mvn clean install
4. Run the tests:
   ```bash
   mvn test
