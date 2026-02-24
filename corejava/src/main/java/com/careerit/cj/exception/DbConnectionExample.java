package com.careerit.cj.exception;

import java.sql.*;

import static java.lang.IO.println;

public class DbConnectionExample {


    public static void main(String[] args) {


        String url = System.getenv("DB_URL");
        String username = System.getenv("DB_USER_NAME");
        String password = System.getenv("DB_PASSWORD");

        try (Connection con = DriverManager.getConnection(url, username, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("select count(*) from contact");
        ) {
            if (rs.next()) {
                println(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
