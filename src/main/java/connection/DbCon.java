/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author DELL
 */
public class DbCon {
	private static Connection connection = null;
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
        if(connection == null){
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=shopping-cart","sa","thaithanhdat");
            System.out.print("connected");
        }
        return connection;
    }
        
        
    private static DbCon instance;

    public DbCon() {
    }


    //Get instance of dbms only one time
    public static DbCon getInstance() {
        if (instance == null) {
            instance = new DbCon();
        }
        return instance;
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
