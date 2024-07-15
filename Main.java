import java.util.Scanner;

class InsufficientFundsError extends Exception {
    InsufficientFundsError(String message) {
        super(message);
    }   
}

class ProgramStopError extends Exception {
    ProgramStopError(String message) {
        super(message);
    }
}

class BankAccount {
    private double balance;

    public void deposit(double balance) {
        this.balance += balance;
    }

    public void withdraw(double balance) throws InsufficientFundsError {
        if (balance > this.balance) {
            throw new InsufficientFundsError("Insufficient funds available in account");
        }
        this.balance -= balance;
    }
    
    public double checkBalance() {
        return this.balance;
    }

    public BankAccount(double bal) {
        this.balance = bal;
    }

    public BankAccount() {
        this.balance = 0.0;
    }
}

class ATM {
    public void showMenu() {
        System.out.println("1. Check your balance");
        System.out.println("2. Deposit money to ATM");
        System.out.println("3. Withdraw money from ATM");
        System.out.println("4. Exit the program");
    }

    public void performOperation(int choice, BankAccount userAcct, Scanner sc) throws ProgramStopError {
        switch (choice) {
            case 1: {
                System.out.println("Your current balance is: " + userAcct.checkBalance());
                break;
            }
            case 2: {
                System.out.print("Enter amount to deposit: ");
                double balance = sc.nextDouble();
                userAcct.deposit(balance);
                System.out.println("Successfully deposited " + userAcct.checkBalance() + " in your account");
                System.out.println("Your new balance is: " + userAcct.checkBalance());
                break;
            }
            case 3: {
                System.out.print("Enter amount to withdraw: ");
                double balance = sc.nextDouble();
                try {
                    userAcct.withdraw(balance);
                    System.out.println("Successfully withdrew " + userAcct.checkBalance() + " from your account");
                    System.out.println("Your new balance is: " + userAcct.checkBalance());
                } catch (InsufficientFundsError err) {
                    System.out.println(err.getMessage());
                }
                break;
            } case 4: {
                System.out.println("Thank you for using our ATM!");
                throw new ProgramStopError("Program has been terminated");
            } default: {
                System.out.println("Please pick a proper number from given choices.");
                break;
            }
        }
    }
}

public class Main {

    public static void printBreak() {
        System.out.println("----------------------------");
    }
    public static void main(String[] args) {
        ATM userATM = new ATM();
        BankAccount userAcct = new BankAccount();
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            userATM.showMenu();
            try {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                printBreak();
                userATM.performOperation(choice, userAcct, scanner);
            } catch (ProgramStopError err) {
                isRunning = false;
            }
            printBreak();
        }
        scanner.close();
    }
}