package ClothesOnlineShop.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private final static String serverName = "localhost";
    private final static String dbName = "ConnguoiShopManagement";
    private final static String portNumber = "1433";
    private final static String instance = ""; // LEAVE THIS ONE EMPTY IF YOUR SQL IS A SINGLE INSTANCE
    private final static String userID = "sa";
    private final static String password = "123";

    public static Connection getConnection() {
        String url = instance.isEmpty()
                ? "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName
                : "jdbc:sqlserver://" + serverName + ":" + portNumber + "\\" + instance + ";databaseName=" + dbName;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("JDBC Driver loaded");
            Connection conn = DriverManager.getConnection(url, userID, password);
            System.out.println("Connected to database");
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error in DBUtils: " + e.getMessage());
        }
        return null;
    }
}
