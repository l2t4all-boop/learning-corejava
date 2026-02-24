package com.careerit.cj.walletapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransaction {
    private int id;
    private int walletId;
    private double amount;
    private TransactionType transactionType;
}