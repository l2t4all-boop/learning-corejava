package com.careerit.cj.walletapp;

import java.util.List;

public interface WalletService {
    Wallet loadAmount(int userId, double amount);
    Wallet withdraw(int userId, double amount);
    Wallet transfer(int fromUserId, int toUserId, double amount);
    WalletStatement getWalletStatement(int userId);
    Wallet getWallet(int userId);
    List<Wallet> getAllWallets();
}