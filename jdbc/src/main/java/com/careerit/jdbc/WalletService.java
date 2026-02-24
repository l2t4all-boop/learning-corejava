package com.careerit.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class WalletService {

        public Wallet createWallet(Wallet wallet){
            // Validations
            Connection con = null;
            PreparedStatement pst = null;
            try{
                UUID uuid = UUID.randomUUID();
                String sql = "insert into wallet(id,username,mobile,balance) values(?,?,?,?)";
                con = ConnectionUtil.getConnection();
                pst = con.prepareStatement(sql);
                pst.setObject(1,uuid);
                pst.setString(2,wallet.getUsername());
                pst.setString(3,wallet.getMobile());
                pst.setDouble(4, wallet.getBalance());
                int count = pst.executeUpdate();
                if(count > 0){
                    System.out.println("Wallet is created with id :"+uuid);
                    wallet.setId(uuid);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                ConnectionUtil.close(pst,con);
            }
            return wallet;
        }
}
