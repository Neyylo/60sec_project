package fr.l2info.sixtysec.database;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;
    private static MysqlDataSource mysqlDataSource;


    private static String host = "localhost";
    private static int port = 3306;
    private static String dbName = "sixtysec";
    private static String username = "root";
    private static String password = "";


    private DBConnection() {

        mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setPort(port);

        mysqlDataSource.setServerName(host);
        mysqlDataSource.setDatabaseName(dbName);
        mysqlDataSource.setDescription("Data source  for sixtysec project");

        try {
            DataSource dataSource = mysqlDataSource;

            connection = dataSource.getConnection(username, password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } try {
            DataSource dataSource = mysqlDataSource;

            connection = dataSource.getConnection(username, password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getConnection() {
        if (connection == null) {
            new DBConnection();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
