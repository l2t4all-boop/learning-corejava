package com.careerit.jdbc.wallet;

import com.careerit.jdbc.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WalletService {

    public Wallet createWallet(Wallet wallet) {
        // Validations
        Connection con = null;
        PreparedStatement pst = null;
        try {
            UUID uuid = UUID.randomUUID();
            String sql = "insert into wallet(id,username,mobile,balance) values(?,?,?,?)";
            con = ConnectionUtil.getConnection();
            pst = con.prepareStatement(sql);
            pst.setObject(1, uuid);
            pst.setString(2, wallet.getUsername());
            pst.setString(3, wallet.getMobile());
            pst.setDouble(4, wallet.getBalance());
            int count = pst.executeUpdate();
            if (count > 0) {
                System.out.println("Wallet is created with id :" + uuid);
                wallet.setId(uuid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.close(pst, con);
        }
        return wallet;
    }

    public void updateEmail(String mobile, String email) {
        String update_email = "update wallet set email = ? where mobile = ?";

        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = ConnectionUtil.getConnection();
            pst = con.prepareStatement(update_email);
            pst.setString(1, email);
            pst.setString(2, mobile);
            int count = pst.executeUpdate();
            System.out.println(count + " records are updated");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.close(pst, con);
        }
    }

    public List<Wallet> getAllWallets() {
        String all_wallets = "select id, username, mobile, balance, email from wallet";

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        List<Wallet> wallets = new ArrayList<>();
        try {
            con = ConnectionUtil.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(all_wallets);
            while (rs.next()) {
                Wallet wallet = getWallet(rs);
                wallets.add(wallet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.close(rs, st, con);
        }
        return wallets;
    }

    public Wallet loadBalance(String mobile, double amount) {
        Wallet wallet = getWallet(mobile);
        if (wallet != null) {
            wallet.setBalance(wallet.getBalance() + amount);
            String update_balance = "update wallet set balance = ? where mobile = ?";

            Connection con = null;
            PreparedStatement pst = null;
            try {
                con = ConnectionUtil.getConnection();
                pst = con.prepareStatement(update_balance);
                pst.setDouble(1, wallet.getBalance());
                pst.setString(2, mobile);
                int count = pst.executeUpdate();
                System.out.println(count + " records are updated");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionUtil.close(pst, con);
            }
            return wallet;

        }
        throw new IllegalArgumentException("Wallet not found for the given mobile number :" + mobile);
    }

    public Wallet getWallet(String mobile) {
        String get_wallet = "select id, username, mobile, balance, email from wallet where mobile = ?";

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Wallet wallet = null;
        try {
            con = ConnectionUtil.getConnection();
            st = con.prepareStatement(get_wallet);
            st.setString(1, mobile);
            rs = st.executeQuery();
            if (rs.next()) {
                wallet = getWallet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.close(rs, st, con);
        }
        return wallet;
    }

    private static Wallet getWallet(ResultSet rs) throws SQLException {
        Wallet wallet = new Wallet();
        wallet.setId(UUID.fromString(rs.getString("id")));
        wallet.setUsername(rs.getString("username"));
        wallet.setBalance(rs.getDouble("balance"));
        wallet.setMobile(rs.getString("mobile"));
        wallet.setEmail(rs.getString("email"));
        return wallet;
    }

}
