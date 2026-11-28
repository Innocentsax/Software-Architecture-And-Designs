import java.util.ArrayList;
import java.time.LocalDate;

/*
Advanced Banking System with Abstraction
Design and implement a modular banking system in Java using the concept of abstraction.

Your task is to create a flexible system that supports multiple types of bank accounts, each with different rules for transactions, interest calculation, and withdrawal limits.

Requirements

1. Abstract Class

Create an abstract class BankAccount that:

Contains:

accountNumber (String)

accountHolderName (String)

balance (double)

Provides:

A constructor to initialize all fields

A concrete method deposit(double amount)

A concrete method displayAccountInfo()

Declares the following abstract methods:

withdraw(double amount)

calculateInterest()

2. Concrete Classes

Implement the following subclasses:

SavingsAccount

Interest rate: 4%

Withdrawal limit: Cannot withdraw more than 3 times per session

Must implement all abstract methods

CurrentAccount

No interest

Allows overdraft up to 5000

Must override withdrawal logic accordingly

FixedDepositAccount

No withdrawals allowed before maturity

Interest rate: 7%

Add an additional field: maturityPeriod (in years)

3. Additional Requirements

Use runtime polymorphism to manage different account types

Store multiple accounts in a collection (e.g., ArrayList<BankAccount>)

Perform operations like:

Deposit

Withdraw

Interest calculation

4. Constraints

Prevent invalid operations (e.g., negative deposit, excessive withdrawal)

Ensure proper encapsulation

Follow clean code practices

Expected Output (Example)

Account: Ali - Savings
Deposit: 5000
Withdraw: 1000
Interest: 160

Account: Sara - Current
Withdraw: 7000 (Overdraft Used)

Account: Ahmed - Fixed Deposit
Withdrawal not allowed before maturity
Interest: 3500
 */
public abstract class BankAccount {

    private String accountNumber;
    private String accountHolderName;
    protected double balance;

    public BankAccount(String accountNumber,
                       String accountHolderName,
                       double balance) {

        if (balance < 0) {
            throw new IllegalArgumentException(
                    "Initial balance cannot be negative"
            );
        }

        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    // Concrete method
    public void deposit(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }

        balance += amount;

        System.out.println(
                "Successfully deposited: " + amount
        );
    }

    // Concrete method
    public void displayAccountInfo() {

        System.out.println("\nAccount Holder: " + accountHolderName);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Current Balance: " + balance);
    }

    // Abstract methods
    public abstract void withdraw(double amount);

    public abstract double calculateInterest();

    // Encapsulated balance access
    public double getBalance() {
        return balance;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}


public class SavingsAccount extends BankAccount {

    private static final double INTEREST_RATE = 0.04;
    private static final int WITHDRAWAL_LIMIT = 3;

    private int withdrawalCount = 0;

    public SavingsAccount(String accountNumber,
                          String accountHolderName,
                          double balance) {

        super(accountNumber, accountHolderName, balance);
    }

    @Override
    public void withdraw(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return;
        }

        if (withdrawalCount >= WITHDRAWAL_LIMIT) {
            System.out.println(
                    "Withdrawal limit exceeded. Maximum 3 withdrawals allowed."
            );
            return;
        }

        if (amount > balance) {
            System.out.println("Insufficient funds.");
            return;
        }

        balance -= amount;
        withdrawalCount++;

        System.out.println("Withdrawal successful: " + amount);
        System.out.println(
                "Remaining withdrawals: " +
                        (WITHDRAWAL_LIMIT - withdrawalCount)
        );
    }

    @Override
    public double calculateInterest() {
        return balance * INTEREST_RATE;
    }
}



public class CurrentAccount extends BankAccount {

    private static final double OVERDRAFT_LIMIT = 5000.0;

    public CurrentAccount(String accountNumber,
                          String accountHolderName,
                          double balance) {

        super(accountNumber, accountHolderName, balance);
    }

    @Override
    public void withdraw(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return;
        }

        if (amount > balance + OVERDRAFT_LIMIT) {
            System.out.println(
                    "Transaction declined. Overdraft limit exceeded."
            );
            return;
        }

        balance -= amount;

        System.out.println("Withdrawal successful: " + amount);

        if (balance < 0) {
            System.out.println(
                    "Overdraft used: " + Math.abs(balance)
            );
        }

        System.out.println("Remaining balance: " + balance);
    }

    @Override
    public double calculateInterest() {
        return 0.0;
    }
}


public class FixedDepositAccount extends BankAccount {

    private static final double INTEREST_RATE = 0.07;

    private int maturityPeriod;
    private LocalDate maturityDate;

    public FixedDepositAccount(String accountNumber,
                               String accountHolderName,
                               double balance,
                               int maturityPeriod) {

        super(accountNumber, accountHolderName, balance);

        if (maturityPeriod <= 0) {
            throw new IllegalArgumentException(
                    "Maturity period must be greater than zero."
            );
        }

        this.maturityPeriod = maturityPeriod;

        this.maturityDate = LocalDate.now()
                .plusYears(maturityPeriod);
    }

    @Override
    public void withdraw(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return;
        }

        if (LocalDate.now().isBefore(maturityDate)) {
            System.out.println(
                    "Withdrawal not allowed before maturity."
            );
            System.out.println(
                    "Maturity Date: " + maturityDate
            );
            return;
        }

        if (amount > balance) {
            System.out.println("Insufficient funds.");
            return;
        }

        balance -= amount;

        System.out.println(
                "Fixed deposit withdrawal successful: " + amount
        );
    }

    @Override
    public double calculateInterest() {
        return balance * INTEREST_RATE * maturityPeriod;
    }

    public int getMaturityPeriod() {
        return maturityPeriod;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }
}

public class AbstractBanking {

    public static void main(String[] args) {

        // Store different account types in one collection
        ArrayList<BankAccount> accounts = new ArrayList<>();

        BankAccount savings = new SavingsAccount(
                "SAV001", "Ali", 4000
        );

        BankAccount current = new CurrentAccount(
                "CUR001", "Sara", 2000
        );

        BankAccount fixedDeposit = new FixedDepositAccount(
                "FIX001", "Ahmed", 50000, 1
        );

        // Add accounts to the collection
        accounts.add(savings);
        accounts.add(current);
        accounts.add(fixedDeposit);

        // Display and operate on different account types
        for (BankAccount account : accounts) {

            account.displayAccountInfo();

            System.out.println("-------------------------");

            if (account instanceof SavingsAccount) {

                System.out.println("Account Type: Savings");

                account.deposit(5000);
                account.withdraw(1000);

            } else if (account instanceof CurrentAccount) {

                System.out.println("Account Type: Current");

                account.withdraw(7000);

            } else if (account instanceof FixedDepositAccount) {

                System.out.println("Account Type: Fixed Deposit");

                account.withdraw(1000);
            }

            // Runtime polymorphism:
            // Java invokes the implementation belonging
            // to the actual account object.
            System.out.println(
                    "Interest: " + account.calculateInterest()
            );

            System.out.println("=========================");
        }
    }
}


