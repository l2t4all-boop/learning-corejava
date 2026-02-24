package com.careerit.jdbc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DbMigration {
    private static final String CHANGELOG = "main-liquibase/main-liquibase.xml";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public void runMigration() {
        Connection con = null;
        Liquibase liquibase = null;
        try {
            con = ConnectionUtil.getConnection();
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(con));
            liquibase = new Liquibase(CHANGELOG, new ClassLoaderResourceAccessor(), database);
            liquibase.update();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.close(liquibase, con);
        }
    }

    public void runTenantMigration() {
        Map<String, String> map = getTenantConnectionDetails();
        map.forEach((ele, conStr) -> {
            Connection con = null;
            Liquibase liquibase = null;
            try {
                Map<String, String> conDetails = OBJECT_MAPPER
                        .readValue(conStr, new TypeReference<Map<String, String>>() {});
                String url = conDetails.get("url");
                String username = conDetails.get("username");
                String password = conDetails.get("password");

                con = DriverManager.getConnection(url, username, password);
                Database database = DatabaseFactory.getInstance()
                        .findCorrectDatabaseImplementation(new JdbcConnection(con));
                liquibase = new Liquibase(CHANGELOG, new ClassLoaderResourceAccessor(), database);
                liquibase.update();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ConnectionUtil.close(liquibase, con);
            }
        });
    }

    public Map<String, String> getTenantConnectionDetails() {
        String sql = "select id,db_conn_info from tenant_master";
        Map<String, String> map = new HashMap<>();
        try (Connection con = ConnectionUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                map.put(rs.getString("id"), rs.getString("db_conn_info"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
