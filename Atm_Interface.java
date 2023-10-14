import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private String userId;
    private String userPin;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String userId, String userPin) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public boolean authenticate(String enteredPin) {
        return userPin.equals(enteredPin);
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdraw", amount));
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (balance >= amount) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer to " + recipient.getUserId(), amount));
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public String getUserId() {
        return userId;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public double getBalance() {
        return balance;
    }
}

public class Atm_Interface{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create an example account
        Account account = new Account("123456", "1234");

        System.out.println("Welcome to the ATM!");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (account.authenticate(pin) && userId.equals(account.getUserId())) {
            System.out.println("Authentication successful.");
            boolean quit = false;

            while (!quit) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Transactions History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                switch (choice) {
                    case 1:
                        // Display transaction history
                        List<Transaction> transactions = account.getTransactionHistory();
                        for (Transaction transaction : transactions) {
                            System.out.println(transaction.getType() + ": $" + transaction.getAmount());
                        }
                        break;

                    case 2:
                        // Withdraw
                        System.out.print("Enter the amount to withdraw: $");
                        double withdrawAmount = scanner.nextDouble();
                        account.withdraw(withdrawAmount);
                        break;

                    case 3:
                        // Deposit
                        System.out.print("Enter the amount to deposit: $");
                        double depositAmount = scanner.nextDouble();
                        account.deposit(depositAmount);
                        break;

                    case 4:
                        // Transfer
                        System.out.print("Enter the recipient's User ID: ");
                        String recipientUserId = scanner.nextLine();
                        Account recipientAccount = new Account(recipientUserId, ""); // Dummy PIN
                        System.out.print("Enter the amount to transfer: $");
                        double transferAmount = scanner.nextDouble();
                        account.transfer(recipientAccount, transferAmount);
                        break;

                    case 5:
                        // Quit the application
                        quit = true;
                        break;

                    default:
                        System.out.println("Invalid choice. Please choose a valid option.");
                }
            }
        } else {
            System.out.println("Authentication failed. Exiting.");
        }

        scanner.close();
    }
}
