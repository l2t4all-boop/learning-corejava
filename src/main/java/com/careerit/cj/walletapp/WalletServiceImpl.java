package com.careerit.cj.walletapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalletServiceImpl implements WalletService {

    private final Map<Integer, Wallet> walletMap = new HashMap<>();
    private final List<WalletTransaction> transactions = new ArrayList<>();
    private int walletIdSeq = 1000;
    private int userIdSeq = 5000;
    private int txnIdSeq = 8000;

    public Wallet createWallet(String userName) {
        int userId = userIdSeq++;
        Wallet wallet = new Wallet(walletIdSeq++, userId, userName, 0);
        walletMap.put(userId, wallet);
        return wallet;
    }

    @Override
    public Wallet loadAmount(int userId, double amount) {
        Wallet wallet = walletMap.get(userId);
        if (wallet == null) {
            System.out.println("Wallet not found for userId: " + userId);
            return null;
        }
        wallet.setBalance(wallet.getBalance() + amount);
        transactions.add(new WalletTransaction(txnIdSeq++, wallet.getId(), amount, TransactionType.CREDITED));
        return wallet;
    }

    @Override
    public Wallet withdraw(int userId, double amount) {
        Wallet wallet = walletMap.get(userId);
        if (wallet == null) {
            System.out.println("Wallet not found for userId: " + userId);
            return null;
        }
        if (wallet.getBalance() < amount) {
            System.out.printf("Insufficient balance. Available: %.2f, Requested: %.2f%n", wallet.getBalance(), amount);
            return null;
        }
        wallet.setBalance(wallet.getBalance() - amount);
        transactions.add(new WalletTransaction(txnIdSeq++, wallet.getId(), amount, TransactionType.DEBITED));
        return wallet;
    }

    @Override
    public Wallet transfer(int fromUserId, int toUserId, double amount) {
        Wallet fromWallet = walletMap.get(fromUserId);
        Wallet toWallet = walletMap.get(toUserId);
        if (fromWallet == null) {
            System.out.println("Sender wallet not found for userId: " + fromUserId);
            return null;
        }
        if (toWallet == null) {
            System.out.println("Receiver wallet not found for userId: " + toUserId);
            return null;
        }
        if (fromWallet.getBalance() < amount) {
            System.out.printf("Insufficient balance. Available: %.2f, Requested: %.2f%n", fromWallet.getBalance(), amount);
            return null;
        }
        fromWallet.setBalance(fromWallet.getBalance() - amount);
        toWallet.setBalance(toWallet.getBalance() + amount);
        transactions.add(new WalletTransaction(txnIdSeq++, fromWallet.getId(), amount, TransactionType.DEBITED));
        transactions.add(new WalletTransaction(txnIdSeq++, toWallet.getId(), amount, TransactionType.CREDITED));
        return fromWallet;
    }

    @Override
    public WalletStatement getWalletStatement(int userId) {
        Wallet wallet = walletMap.get(userId);
        if (wallet == null) {
            System.out.println("Wallet not found for userId: " + userId);
            return null;
        }
        List<WalletTransaction> txns = transactions.stream()
                .filter(txn -> txn.getWalletId() == wallet.getId())
                .toList();
        WalletStatement statement = new WalletStatement();
        statement.setWallet(wallet);
        statement.setTransactions(txns);
        return statement;
    }

    @Override
    public Wallet getWallet(int userId) {
        return walletMap.get(userId);
    }

    @Override
    public List<Wallet> getAllWallets() {
        return new ArrayList<>(walletMap.values());
    }
}