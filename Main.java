import java.util.Arrays;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// abstaract class acount
abstract class Account {

    protected String code;
    protected BigDecimal balance;
    protected List<Operation> operations;

    public Account() {
        this.code = UUID.randomUUID().toString();
        this.balance = BigDecimal.ZERO;
        this.operations = new ArrayList<Operation>();
    }


    public abstract void withdraw (BigDecimal amount, String destination);
    public abstract void displayDetails ();


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


    public void deposit (BigDecimal amount, String source){
        validatePositive(amount);
        balance = balance.add(amount);
        addOperation(new Deposit(amount, source));

    };
}
// current account class
class CurrentAccount extends Account {

    private BigDecimal overdraftLimit ;

    public CurrentAccount(BigDecimal overdraftLimit, BigDecimal Balance) {
        super(Balance);
        this.overdraftLimit = overdraftLimit;
    }

    //override withdraw method
    public void withdraw(BigDecimal amount,String destination){
        validatePositive(amount);
        BigDecimal newBalance = balance.subtract(amount);
        if (newBalance < -overdraftLimit) {
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

    public SavingsAccount(BigDecimal Balance, BigDecimal interestRate) {
        super(Balance);
        this.interestRate = interestRate;
    }

    public BigDecimal getInterestRate() { return interestRate; }

    
    public void withdraw(BigDecimal amount, String destination) {
        validatePositive(amount);
        if (balance.compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds for withdrawal");
        }
        balance = balance.subtract(amount);
        addOperation(new Withdrawal(amount, destination));
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

    private String Source;


    public Deposit(BigDecimal amount, String Source) {
        super(amount);
        this.source = Source ;
    }

    public String getSource() {
        return source;
    }
    
    // override display method
    public void display() {
        System.out.println("Deposit | Amount: " + amount + " | Source: " + source + " | Date: " + date);
    }
}
class withdraw extends Operation {

    private String destination;

    public withdraw(BigDecimal amount, String destination) {
        super(amount);
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }
    // override display method
    public void display() {
        System.out.println("Withdrawal | Amount: " + amount + " | Destination: " + destination + " | Date: " + date);
    }
    
}

class Main {
    public static void main(String[] args) {

        // System.out.println("hello world");
        System.out.println("hello world");
    }
}
