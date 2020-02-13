package JDBCtest;

import java.lang.reflect.Type;
import java.sql.*;
import java.lang.*;
// Проход сета вверх и вниз для вытаскивания записей и изменения и т.п.
public class RowSetJDBC {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String name = "root";
        String connectURL = "jdbc:mysql://localhost:3306/cars";
        String pass = "123";
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        try (Connection conn = DriverManager.getConnection(connectURL, name, pass);
             Statement stat = conn.createStatement()) {
            // CreateStatement в нем два параметра TYPE_SCROLL_INSENSITIVE - пробег вверх вниз без учета изменений
            // CONCUR_READ_ONLY - только для чтения
                Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //или все тоже самое через препермантСтейтмент
              //  PreparedStatement preparedStatement = conn.prepareStatement("", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select  * from carsblob");
        if (resultSet.next())
            System.out.println(resultSet.getString("name_cars"));
        if(resultSet.next())
            System.out.println(resultSet.getString("name_cars"));
        if(resultSet.previous())// предыдыщая
            System.out.println(resultSet.getString("name_cars"));
        if(resultSet.relative(2))// двигаться на две строчки вниз
            System.out.println(resultSet.getString("name_cars"));
        if(resultSet.relative(-2))// двигаться на две строчки ввверх
                System.out.println(resultSet.getString("name_cars"));
        if(resultSet.absolute(8))// абсолютная 8 строка
            System.out.println(resultSet.getString("name_cars"));
       // resultSet.first(); resultSet.last(); первая и последняя соответсвенно

        }
    }
}