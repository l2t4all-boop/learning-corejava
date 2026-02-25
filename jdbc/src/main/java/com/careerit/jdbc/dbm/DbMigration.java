package com.careerit.jdbc.dbm;

import com.careerit.jdbc.util.ConnectionUtil;
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
    private static final String CHANGELOG = "main-migration/main-liquibase.xml";
    private static final String MASTER_CHANGELOG= "master-migration/master-liquibase.xml";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static{
        System.out.println("DB Migration has started");
        DbMigration dbMigration = new DbMigration();
        dbMigration.runMigration();
        dbMigration.runTenantMigration();
        System.out.println("DB Migration has completed");
    }

    public void runMigration() {
        Connection con = null;
        Liquibase liquibase = null;
        try {
            con = ConnectionUtil.getConnection();
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(con));
            liquibase = new Liquibase(MASTER_CHANGELOG, new ClassLoaderResourceAccessor(), database);
            liquibase.update();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.close(liquibase, con);
        }
    }

    public void runTenantMigration() {
        int scount = 0;
        int fcount = 0;
        Map<String, String> map = getTenantConnectionDetails();

        for(Map.Entry<String,String> entry:map.entrySet()) {
            String tenantId = entry.getKey();
            String conStr = entry.getValue();
            Connection con = null;
            Liquibase liquibase = null;
            try {
                System.out.println("Migration started for tenant :" + tenantId);
                Map<String, String> conDetails = OBJECT_MAPPER
                        .readValue(conStr, new TypeReference<Map<String, String>>() {
                        });
                String url = conDetails.get("url");
                String username = conDetails.get("username");
                String password = conDetails.get("password");

                con = DriverManager.getConnection(url, username, password);
                Database database = DatabaseFactory.getInstance()
                        .findCorrectDatabaseImplementation(new JdbcConnection(con));
                liquibase = new Liquibase(CHANGELOG, new ClassLoaderResourceAccessor(), database);
                liquibase.update();
                scount++;
            } catch (Exception e) {
                fcount++;
                e.printStackTrace();
            } finally {
                ConnectionUtil.close(liquibase, con);
            }
        }
        System.out.println("Total " + scount + " databases are migrated");
        System.out.println("Total " + fcount + " databases migration failed");
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
