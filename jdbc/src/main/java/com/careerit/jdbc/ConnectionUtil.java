package com.careerit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConnectionUtil {
    private static final Properties properties;

    static{
        properties = new Properties();
        try{
            properties.load(ConnectionUtil.class.getResourceAsStream("/application.properties"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private ConnectionUtil(){
    }

    public static Connection getConnection(){
        Connection con = null;
        try{
            con = DriverManager.getConnection(properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password"));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return con;
    }
    public static void close(AutoCloseable... resources){
        for(AutoCloseable resource: resources){
            try{
                if(resource != null){
                    resource.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
