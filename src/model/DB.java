package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DB {
    // config for mysql connection
    private String url = "jdbc:mysql://localhost:3306/db_tmd_dpbo_updown";
    private String user = "root";
    private String password = "";
    private Statement stm = null; // query connection
    private ResultSet rs = null; // result
    private Connection conn; // mysql connection

    // Constructor
    public DB() throws Exception, SQLException {
        try {
            // creating mysql connection
            conn = DriverManager.getConnection(url, user, password);
//            conn.setTransactionIsolation(conn.TRANSACTION_READ_UNCOMMITTED);
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            // throw error when connection failed
            e.printStackTrace();
        }
    }

    public void createQuery(String query) throws Exception, SQLException {
        // executing query without manipulating data
        try {
            stm = conn.createStatement();
            // query execution
            rs = stm.executeQuery(query);
            if (stm.execute(query)) {
                // get result
                rs = stm.getResultSet();
            }
        } catch (SQLException e) {
            // throw error when query execution failed
            throw e;
        }
    }

    public void createUpdate(String query) throws Exception, SQLException {
        // executing query for manipulating data
        try {
            stm = conn.createStatement();
            // query execution
            int result = stm.executeUpdate(query);
        } catch (SQLException e) {
            // throw error when query execution failed
            throw e;
        }
    }

    public ResultSet getResult() throws Exception {
        ResultSet temp = null;
        try {
            return rs;
        } catch (Exception e) {
            return temp;
        }
    }

    public void closeResult() throws Exception, SQLException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                rs = null;
                throw e;
            }
        }

        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException e) {
                stm = null;
                throw e;
            }
        }
    }

    public void closeConnection() throws Exception, SQLException {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                conn = null;
                throw e;
            }
        }
    }
}
