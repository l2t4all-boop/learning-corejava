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
            throw new WalletNotFoundException("Wallet not found for userId: " + userId);
        }
        wallet.setBalance(wallet.getBalance() + amount);
        transactions.add(new WalletTransaction(txnIdSeq++, wallet.getId(), amount, TransactionType.CREDITED));
        return wallet;
    }

    @Override
    public Wallet withdraw(int userId, double amount) {
        Wallet wallet = walletMap.get(userId);
        if (wallet == null) {
           throw new WalletNotFoundException("Wallet not found for userId: " + userId);
        }
        if (wallet.getBalance() < amount) {
            throw new InsufficientFundException(String.format("Insufficient balance. Available: %.2f, Requested: %.2f", wallet.getBalance(), amount));
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
            throw new WalletNotFoundException("Sender wallet not found for userId: " + fromUserId);
        }
        if (toWallet == null) {
            throw new WalletNotFoundException("Receiver wallet not found for userId: " + toUserId);
        }
        if (fromWallet.getBalance() < amount) {
            throw new InsufficientFundException(String.format("Insufficient balance. Available: %.2f, Requested: %.2f", fromWallet.getBalance(), amount));
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
            throw new WalletNotFoundException("Wallet not found for userId: " + userId);
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