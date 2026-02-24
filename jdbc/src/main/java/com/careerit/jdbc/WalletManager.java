package com.careerit.jdbc;

public class WalletManager {

    static{
        System.out.println("DB Migration has started");
        DbMigration dbMigration = new DbMigration();
        dbMigration.runMigration();
        dbMigration.runTenantMigration();
        System.out.println("DB Migration has completed");
    }

    public static void main(String[] args) {

            WalletService ws = new WalletService();
            createNewWallet(ws);
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
