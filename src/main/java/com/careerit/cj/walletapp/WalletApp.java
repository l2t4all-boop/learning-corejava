package com.careerit.cj.walletapp;

import java.util.List;
import java.util.Scanner;

public class WalletApp {

    public static void main(String[] args) {
        WalletServiceImpl walletService = new WalletServiceImpl();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("       Wallet Management System");
            System.out.println("=".repeat(40));
            System.out.println("1. Create Wallet");
            System.out.println("2. Load Amount");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Wallet Statement");
            System.out.println("6. Check Balance");
            System.out.println("7. Show All Wallets");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter userName: ");
                    String userName = sc.next();
                    Wallet w = walletService.createWallet(userName);
                    System.out.printf("Wallet created for %s with userId: %d%n", w.getUserName(), w.getUserId());
                }
                case 2 -> {
                    System.out.print("Enter userId: ");
                    int userId = sc.nextInt();
                    System.out.print("Enter amount to load: ");
                    double amount = sc.nextDouble();
                    Wallet w = walletService.loadAmount(userId, amount);
                    if (w != null) {
                        System.out.printf("%.2f credited to %s's wallet. New balance: %.2f%n", amount, w.getUserName(), w.getBalance());
                    }
                }
                case 3 -> {
                    System.out.print("Enter userId: ");
                    int userId = sc.nextInt();
                    System.out.print("Enter amount to withdraw: ");
                    double amount = sc.nextDouble();
                    Wallet w = walletService.withdraw(userId, amount);
                    if (w != null) {
                        System.out.printf("%.2f debited from %s's wallet. New balance: %.2f%n", amount, w.getUserName(), w.getBalance());
                    }
                }
                case 4 -> {
                    System.out.print("Enter sender userId: ");
                    int fromUserId = sc.nextInt();
                    System.out.print("Enter receiver userId: ");
                    int toUserId = sc.nextInt();
                    System.out.print("Enter amount to transfer: ");
                    double amount = sc.nextDouble();
                    Wallet w = walletService.transfer(fromUserId, toUserId, amount);
                    if (w != null) {
                        System.out.printf("%.2f transferred from %s. New balance: %.2f%n", amount, w.getUserName(), w.getBalance());
                    }
                }
                case 5 -> {
                    System.out.print("Enter userId: ");
                    int userId = sc.nextInt();
                    WalletStatement statement = walletService.getWalletStatement(userId);
                    if (statement == null || statement.getTransactions().isEmpty()) {
                        System.out.println("No transactions found.");
                    } else {
                        System.out.printf("User: %s, Balance: %.2f%n", statement.getWallet().getUserName(), statement.getWallet().getBalance());
                        System.out.printf("%-5s %-10s %-10s %-12s%n", "TxnId", "WalletId", "Amount", "Type");
                        System.out.println("-".repeat(40));
                        for (WalletTransaction txn : statement.getTransactions()) {
                            System.out.printf("%-5d %-10d %-10.2f %-12s%n",
                                    txn.getId(), txn.getWalletId(), txn.getAmount(), txn.getTransactionType());
                        }
                    }
                }
                case 6 -> {
                    System.out.print("Enter userId: ");
                    int userId = sc.nextInt();
                    Wallet wallet = walletService.getWallet(userId);
                    if (wallet == null) {
                        System.out.println("Wallet not found for userId: " + userId);
                    } else {
                        System.out.printf("User: %s, Balance: %.2f%n", wallet.getUserName(), wallet.getBalance());
                    }
                }
                case 7 -> {
                    List<Wallet> wallets = walletService.getAllWallets();
                    if (wallets.isEmpty()) {
                        System.out.println("No wallets found.");
                    } else {
                        System.out.printf("%-8s %-15s %-10s%n", "UserId", "UserName", "Balance");
                        System.out.println("-".repeat(35));
                        for (Wallet w : wallets) {
                            System.out.printf("%-8d %-15s %-10.2f%n", w.getUserId(), w.getUserName(), w.getBalance());
                        }
                    }
                }
                case 8 -> {
                    running = false;
                    System.out.println("Exiting...");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        sc.close();
    }
}