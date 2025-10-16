import java.util.List;
import java.util.Scanner;

public class ATM {
    private final Bank bank;
    private final Scanner scanner;

    public ATM(Bank bank) {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to SimpleBank ATM");
        while (true) {
            System.out.println("\n1) Login\n2) Exit\n3) (Admin) show bank summary");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                doLoginFlow();
            } else if (choice.equals("2")) {
                System.out.println("Goodbye!");
                break;
            } else if (choice.equals("3")) {
                bank.printBankSummary();
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    private void doLoginFlow() {
        System.out.print("Enter customer ID: ");
        String custId = scanner.nextLine().trim();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine().trim();

        try {
            Customer c = bank.authenticate(custId, pin);
            if (c == null) {
                System.out.println("Invalid credentials. Try again.");
                return;
            }
            System.out.println("Welcome, " + c.getName());
            accountMenu(c);
        } catch (AccountBlockedException abe) {
            System.out.println("Access denied: " + abe.getMessage());
        }
    }

    private void accountMenu(Customer c) {
        while (true) {
            System.out.println("\nYour accounts:");
            List<BankAccount> accounts = c.getAccounts();
            for (int i = 0; i < accounts.size(); i++) {
                BankAccount a = accounts.get(i);
                System.out.println((i+1) + ") " + a.getAccountNumber() + " (" + a.getClass().getSimpleName() + ") Balance: " + String.format("%.2f", a.getBalance()));
            }
            System.out.println("0) Logout");
            System.out.print("Select account number to manage: ");
            String sel = scanner.nextLine().trim();
            if (sel.equals("0")) break;
            int idx;
            try {
                idx = Integer.parseInt(sel) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection.");
                continue;
            }
            if (idx < 0 || idx >= accounts.size()) {
                System.out.println("Invalid selection.");
                continue;
            }
            BankAccount selected = accounts.get(idx);
            manageSelectedAccount(selected);
        }
    }

    private void manageSelectedAccount(BankAccount account) {
        while (true) {
            System.out.println("\nManaging account: " + account.getAccountNumber());
            System.out.println("1) Check Balance");
            System.out.println("2) Deposit");
            System.out.println("3) Withdraw");
            System.out.println("4) View Transaction History");
            System.out.println("5) Transfer to another account");
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();
            try {
                if (choice.equals("1")) {
                    System.out.println("Balance: " + String.format("%.2f", account.getBalance()));
                } else if (choice.equals("2")) {
                    System.out.print("Enter amount to deposit: ");
                    double amt = Double.parseDouble(scanner.nextLine().trim());
                    Transaction tx = account.deposit(amt);
                    if (tx.getStatus() == TransactionStatus.SUCCESS) {
                        System.out.println("Deposit successful. New balance: " + String.format("%.2f", account.getBalance()));
                        System.out.println("Receipt:\n" + tx.toReceipt());
                    } else {
                        System.out.println("Deposit failed: " + tx.getNote());
                    }
                } else if (choice.equals("3")) {
                    System.out.print("Enter amount to withdraw: ");
                    double amt = Double.parseDouble(scanner.nextLine().trim());
                    try {
                        Transaction tx = account.withdraw(amt);
                        if (tx.getStatus() == TransactionStatus.SUCCESS) {
                            System.out.println("Withdrawal successful. New balance: " + String.format("%.2f", account.getBalance()));
                            System.out.println("Receipt:\n" + tx.toReceipt());
                        } else {
                            System.out.println("Withdrawal failed: " + tx.getNote());
                        }
                    } catch (InsufficientFundsException e) {
                        System.out.println("Withdrawal failed: " + e.getMessage());
                    }
                } else if (choice.equals("4")) {
                    System.out.println("--- Transaction history for " + account.getAccountNumber() + " ---");
                    for (Transaction t : account.getTransactions()) {
                        System.out.println(t.toString());
                    }
                    System.out.println("--------------------------------------------");
                } else if (choice.equals("5")) {
                    System.out.print("Enter target account number: ");
                    String target = scanner.nextLine().trim();
                    BankAccount targetAcc = bank.getAccountByNumber(target);
                    if (targetAcc == null) {
                        System.out.println("Target account not found.");
                        continue;
                    }
                    System.out.print("Enter amount to transfer: ");
                    double amt = Double.parseDouble(scanner.nextLine().trim());
                    Transaction tx = account.transferTo(targetAcc, amt);
                    if (tx.getStatus() == TransactionStatus.SUCCESS) {
                        System.out.println("Transfer completed. New balance: " + String.format("%.2f", account.getBalance()));
                        System.out.println("Receipt:\n" + tx.toReceipt());
                    } else {
                        System.out.println("Transfer failed: " + tx.getStatus() + " | " + tx.getNote());
                    }
                } else if (choice.equals("0")) {
                    break;
                } else {
                    System.out.println("Invalid option.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid number input.");
            }
        }
    }
}
