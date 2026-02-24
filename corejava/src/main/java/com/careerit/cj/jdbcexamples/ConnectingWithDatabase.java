package com.careerit.cj.jdbcexamples;

import java.sql.*;

public class ConnectingWithDatabase {

    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = ConnectionUtil.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select id from tenant_master");
            while (rs.next()) {
                System.out.println(rs.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.close(rs, st, con);
        }

    }
}
