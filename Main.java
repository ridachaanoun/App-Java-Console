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



class Main {
    public static void main(String[] args) {

        // System.out.println("hello world");
        System.out.println("hello world");
    }
}
