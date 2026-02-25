package com.careerit.jdbc.wallet;

public class WalletManager {


    public static void main(String[] args) {

        WalletService ws = new WalletService();
        //createNewWallet(ws);
        //ws.updateEmail("9087654321", "krish@gmail.com");
        //List<Wallet> wallets = ws.getAllWallets();
        //System.out.println(wallets);
        Wallet wallet = ws.loadBalance("9087654321",2500);
        System.out.println(wallet);
    }

    private static void createNewWallet(WalletService ws) {

        Wallet wallet = new Wallet();
        wallet.setUsername("Krish");
        wallet.setMobile("9087654321");
        wallet.setBalance(500);
        Wallet savedWallet = ws.createWallet(wallet);
        System.out.println(savedWallet);

    }
}
