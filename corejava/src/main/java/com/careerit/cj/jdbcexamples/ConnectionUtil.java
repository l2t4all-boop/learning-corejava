package com.careerit.cj.jdbcexamples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public final class ConnectionUtil {

    private static Properties properties ;

    private ConnectionUtil(){

    }
    static {
        try{
            properties = new Properties();
            properties.load(ConnectionUtil.class.getResourceAsStream("/application.properties"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static  Connection getConnection(){
        String url =  properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        Connection con = null;
        try {
             con = DriverManager.getConnection(url, username, password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return con;
    }
    public  static void close(AutoCloseable... args){
        try{
            for(AutoCloseable ele: args){
                if(ele != null){
                    ele.close();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
