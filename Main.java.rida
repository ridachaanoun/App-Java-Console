import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Scanner;

// abstract class account
abstract class Account {

    private static final AtomicInteger counter = new AtomicInteger(1);

    protected String code;
    protected BigDecimal balance;
    protected List<Operation> operations;

    public Account() {
        this.code = generateCode();
        this.balance = BigDecimal.ZERO;
        this.operations = new ArrayList<Operation>();
    }

    public Account(BigDecimal initialBalance) {
        this.code = generateCode();
        this.balance = initialBalance;
        this.operations = new ArrayList<Operation>();
    }

    private String generateCode() {
        // ensures format CPT-00001, CPT-00002, ...
        int num = counter.getAndIncrement();
        return String.format("CPT-%05d", num);
    }

    public abstract void withdraw(BigDecimal amount, String destination);
    public abstract void displayDetails();

    public String getCode() {
        return code;
    } 
    public BigDecimal getBalance() {
        return balance;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    protected void validatePositive(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    public void addOperation(Operation op){
        operations.add(op);
    }

    public void deposit(BigDecimal amount, String source){
        validatePositive(amount);
        balance = balance.add(amount);
        addOperation(new Deposit(amount, source));
    }
}

// current account class
class CurrentAccount extends Account {

    private BigDecimal overdraftLimit ;

    public CurrentAccount(BigDecimal overdraftLimit, BigDecimal balance) {
        super(balance);
        this.overdraftLimit = overdraftLimit;
    }

    //override withdraw method
    public void withdraw(BigDecimal amount,String destination){
        validatePositive(amount);
        BigDecimal newBalance = balance.subtract(amount);
        if (newBalance.compareTo(overdraftLimit.negate()) < 0) {
            throw new RuntimeException("Withdrawal exceeds overdraft limit");
        }
        balance = newBalance;
        addOperation(new Withdraw(amount, destination));
    }

    // override displayDetails method
    public void displayDetails() {
        System.out.println("CurrentAccount: " + code +
            " | Balance: " + balance +
            " | Overdraft: " + overdraftLimit);
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }
}
// savings account class
class SavingsAccount extends Account {
    private BigDecimal interestRate;

    public SavingsAccount(BigDecimal balance, BigDecimal interestRate) {
        super(balance);
        this.interestRate = interestRate;
    }

    public BigDecimal getInterestRate() { return interestRate; }

    
    public void withdraw(BigDecimal amount, String destination) {
        validatePositive(amount);
        if (balance.compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds for withdrawal");
        }
        balance = balance.subtract(amount);
        addOperation(new Withdraw(amount, destination));
    }


    public BigDecimal calcInterest() {
        return balance.multiply(interestRate);
    }

    // override displayDetails method
    public void displayDetails() {
        System.out.println("SavingsAccount: " + code +
            " | Balance: " + balance +
            " | Interest Rate: " + interestRate);
    }
}

// abstract class operation
abstract class Operation {
    protected UUID id;
    protected LocalDateTime date;
    protected BigDecimal amount;

    public Operation(BigDecimal amount) {
        this.id = UUID.randomUUID();
        this.date = LocalDateTime.now();
        this.amount = amount;
    }

    public LocalDateTime getDate() { return date; }
    public BigDecimal getAmount() { return amount; }
    public UUID getId() { return id; }

    public abstract void display();
}

// deposit class
class Deposit extends Operation {
    private String source;

    public Deposit(BigDecimal amount, String source) {
        super(amount);
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    @Override
    public void display() {
        System.out.println("Deposit | Amount: " + amount +
                " | Source: " + source +
                " | Date: " + date);
    }
}
class Withdraw extends Operation {

    private String destination;

    public Withdraw(BigDecimal amount, String destination) {
        super(amount);
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }
    // override display method
    public void display() {
        System.out.println("Withdrawal | Amount: " + amount +
                " | Destination: " + destination +
                " | Date: " + date);
    }
    
}

class Main {
    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = readInt("Choose an option: ");
            switch (choice) {
                case 1:
                    createCurrentAccount();
                    break;
                case 2:
                    createSavingsAccount();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    withdraw();
                    break;
                case 5:
                    transfer();
                    break;
                case 6:
                    showAccountDetails();
                    break;
                case 7:
                    listAllAccounts();
                    break;
                case 8:
                    showAccountOperations();
                    break;
                case 9:
                    calculateInterest();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("\n--- Bank Menu ---");
        System.out.println("1) Create Current Account");
        System.out.println("2) Create Savings Account");
        System.out.println("3) Deposit");
        System.out.println("4) Withdraw");
        System.out.println("5) Transfer");
        System.out.println("6) Show Account Details");
        System.out.println("7) List All Accounts");
        System.out.println("8) Show Account Operations");
        System.out.println("9) Calculate Interest (Savings)");
        System.out.println("0) Exit");
    }

    private static void createCurrentAccount() {
        BigDecimal overdraft = readBigDecimal("Enter overdraft limit: ");
        BigDecimal initialBalance = readBigDecimal("Enter initial balance: ");
        CurrentAccount acc = new CurrentAccount(overdraft, initialBalance);
        accounts.add(acc);
        System.out.println("Created Current Account with code: " + acc.getCode());
    }

    private static void createSavingsAccount() {
        BigDecimal initialBalance = readBigDecimal("Enter initial balance: ");
        BigDecimal rate = readBigDecimal("Enter interest rate (e.g., 0.05 for 5%): ");
        SavingsAccount acc = new SavingsAccount(initialBalance, rate);
        accounts.add(acc);
        System.out.println("Created Savings Account with code: " + acc.getCode());
    }

    private static void deposit() {
        Account acc = findAccountByCode();
        if (acc != null) {
            BigDecimal amount = readBigDecimal("Enter deposit amount: ");
            System.out.print("Enter source: ");
            String source = scanner.nextLine();
            acc.deposit(amount, source);
            System.out.println("Deposit successful!");
        }
    }

    private static void withdraw() {
        Account acc = findAccountByCode();
        if (acc != null) {
            BigDecimal amount = readBigDecimal("Enter withdrawal amount: ");
            System.out.print("Enter destination: ");
            String dest = scanner.nextLine();
            try {
                acc.withdraw(amount, dest);
                System.out.println("Withdrawal successful!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void transfer() {
        System.out.print("Enter source account code: ");
        String sourceCode = scanner.nextLine();
        System.out.print("Enter destination account code: ");
        String destCode = scanner.nextLine();

        Account source = findAccount(sourceCode);
        Account dest = findAccount(destCode);

        if (source != null && dest != null) {
            BigDecimal amount = readBigDecimal("Enter transfer amount: ");
            try {
                source.withdraw(amount, dest.getCode());
                dest.deposit(amount, source.getCode());
                System.out.println("Transfer successful!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid account code(s).");
        }
    }

    private static void showAccountDetails() {
        Account acc = findAccountByCode();
        if (acc != null) {
            acc.displayDetails();
        }
    }

    private static void listAllAccounts() {
        for (Account acc : accounts) {
            acc.displayDetails();
        }
    }

    private static void showAccountOperations() {
        Account acc = findAccountByCode();
        if (acc != null) {
            for (Operation op : acc.getOperations()) {
                op.display();
            }
        }
    }

    private static void calculateInterest() {
        Account acc = findAccountByCode();
        if (acc != null && acc instanceof SavingsAccount) {
            SavingsAccount sAcc = (SavingsAccount) acc;
            System.out.println("Calculated interest: " + sAcc.calcInterest());
        } else {
            System.out.println("Not a savings account.");
        }
    }


private static int readInt(String message) {
    while (true) {
        try {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Please enter a number.");
                continue;
            }
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, try again.");
        }
    }
}

    private static BigDecimal readBigDecimal(String msg) {
    while (true) {
        try {
            System.out.print(msg);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Please enter a number.");
                continue;
            }
            return new BigDecimal(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, try again.");
        }
    }
}

    private static Account findAccountByCode() {
        System.out.print("Enter account code: ");
        String code = scanner.nextLine();
        return findAccount(code);
    }

    private static Account findAccount(String code) {
        for (Account acc : accounts) {
            if (acc.getCode().equals(code)) {
                return acc;
            }
        }
        System.out.println("Account not found.");
        return null;
    }
}