package JDBCtest;

import java.sql.*;
import java.lang.*;

public class DataJDBC {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        String name = "root";
        String connectURL = "jdbc:mysql://localhost:3306/cars";
        String pass = "123";
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        try (Connection conn = DriverManager.getConnection(connectURL, name, pass);
             Statement stat = conn.createStatement()) {
            //stat.executeUpdate("drop table carsblob");
           // stat.executeUpdate("drop table carsdate");
              stat.executeUpdate("create table if not exists carsDate (id int(5) not null AUTO_INCREMENT, name_cars VARCHAR(30), dt DATE, primary key (id));");
          /*  PreparedStatement preparedStatement = conn.prepareStatement("insert into carsDate (name_cars, dt) values ('Ferrari', ?)");
            preparedStatement.setDate(1, new Date(1581577285479L)); // дата в миллисекундах (now in milliseconds (google search))
            preparedStatement.execute();// выполнить*/
            //System.out.println(preparedStatement); /вывыодит sql запрос
            //или просто через стейтмент
            stat.executeUpdate("insert into carsDate (name_cars, dt) values ('Ferrari', '2020-02-13')");

        }
    }
}