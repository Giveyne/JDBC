package JDBCtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.lang.*;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBC{

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{

        String name = "root";
        String connectURL = "jdbc:mysql://localhost:3306/cars";
        String pass = "123";
        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
        try (Connection conn = DriverManager.getConnection(connectURL, name, pass);
             Statement statement = conn.createStatement()){
            statement.executeUpdate("INsert INTO cars2 (name_cars, name_user) VALUES ('Nissan', 'Petr')");
        }





    }
}