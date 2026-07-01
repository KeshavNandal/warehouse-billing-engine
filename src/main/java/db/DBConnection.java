package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// database connected
public class DBConnection {
    public static Connection getConnection() throws SQLException {

         return   DriverManager.getConnection("jdbc:mysql://localhost:3306/Warehouse_DB", "root", "keshav123450");

    }
}
