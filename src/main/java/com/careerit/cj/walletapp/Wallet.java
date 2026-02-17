package com.careerit.cj.walletapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    private int id;
    private int userId;
    private String userName;
    private double balance;
}