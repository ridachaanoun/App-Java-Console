package com.bank.ui;

import com.bank.exception.BankException;
import com.bank.model.Account;
import com.bank.model.SavingsAccount;
import com.bank.service.AccountService;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Scanner;

public class ConsoleApp {

    private final AccountService service = new AccountService();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        int choice;
        do {
            printMenu();
            choice = readInt("Choose: ");
            try {
                switch (choice) {
                    case 1:
                        createCurrent();
                        break;
                    case 2:
                        createSavings();
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
                        showAccount();
                        break;
                    case 7:
                        listAll();
                        break;
                    case 8:
                        showOperations();
                        break;
                    case 9:
                        calcInterest();
                        break;
                    case 0:
                        System.out.println("Bye!");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } catch (BankException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 0);
    }

    private void printMenu() {
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

    private void createCurrent() {
        BigDecimal overdraft = readBigDecimal("Overdraft limit: ");
        BigDecimal initial = readBigDecimal("Initial balance: ");
        System.out.println("Created Current Account: " + service.createCurrent(overdraft, initial).getCode());
    }

    private void createSavings() {
        BigDecimal initial = readBigDecimal("Initial balance: ");
        BigDecimal rate = readBigDecimal("Interest rate (e.g. 0.05): ");
        System.out.println("Created Savings Account: " + service.createSavings(initial, rate).getCode());
    }

    private void deposit() {
        String code = readLine("Account code: ");
        BigDecimal amount = readBigDecimal("Amount to deposit: ");
        String source = readLine("Source: ");
        service.find(code).deposit(amount, source);
        System.out.println("Deposit OK. New balance: " + service.find(code).getBalance());
    }

    private void withdraw() {
        String code = readLine("Account code: ");
        BigDecimal amount = readBigDecimal("Amount to withdraw: ");
        String dest = readLine("Destination: ");
        service.find(code).withdraw(amount, dest);
        System.out.println("Withdrawal OK. New balance: " + service.find(code).getBalance());
    }

    private void transfer() {
        String from = readLine("From account: ");
        String to = readLine("To account: ");
        BigDecimal amount = readBigDecimal("Amount: ");
        service.transfer(from, to, amount);
        System.out.println("Transfer complete.");
    }

    private void showAccount() {
        String code = readLine("Account code: ");
        service.find(code).displayDetails();
    }

    private void listAll() {
        Iterator<Account> it = service.all().iterator();
        if (!it.hasNext()) {
            System.out.println("No accounts.");
            return;
        }
        for (Account a : service.all()) {
            a.displayDetails();
        }
    }

    private void showOperations() {
        String code = readLine("Account code: ");
        Account acc = service.find(code);
        if (acc.getOperations().isEmpty()) {
            System.out.println("No operations.");
            return;
        }
        for (int i = 0; i < acc.getOperations().size(); i++) {
            acc.getOperations().get(i).display();
        }
    }

    private void calcInterest() {
        String code = readLine("Savings account code: ");
        Account acc = service.find(code);
        if (acc instanceof SavingsAccount) {
            SavingsAccount s = (SavingsAccount) acc;
            System.out.println("Interest: " + s.calcInterest());
        } else {
            System.out.println("Not a savings account.");
        }
    }

    // -------- helpers ----------
    private int readInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                String in = scanner.nextLine().trim();
                return Integer.parseInt(in);
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer.");
            }
        }
    }

    private BigDecimal readBigDecimal(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                String in = scanner.nextLine().trim();
                return new BigDecimal(in);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }
    }

    private String readLine(String msg) {
        System.out.print(msg);
        return scanner.nextLine().trim();
    }
}