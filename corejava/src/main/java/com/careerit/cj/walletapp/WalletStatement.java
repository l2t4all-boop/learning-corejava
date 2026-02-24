package com.careerit.cj.walletapp;

import lombok.Data;

import java.util.List;

@Data
public class WalletStatement {
        private Wallet wallet;
        private List<WalletTransaction> transactions;
}
