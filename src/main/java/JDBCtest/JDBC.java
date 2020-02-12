package JDBCtest;

import java.sql.*;
import java.lang.*;


public class JDBC {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        String name = "root";
        String connectURL = "jdbc:mysql://localhost:3306/cars";
        String pass = "123";
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        try (Connection conn = DriverManager.getConnection(connectURL, name, pass);
             Statement statement = conn.createStatement()) {
            // executeUpdate служит для добавления данных
            //statement.executeUpdate( "drop table cars2;");
            statement.executeUpdate("create table if not exists cars2 (id int(5) not null AUTO_INCREMENT, name_cars VARCHAR(30), name_user varchar (30), primary key (id));");
            statement.executeUpdate("INsert INTO cars2 (name_cars, name_user) VALUES ('Nissan', 'Petr')");
            //execute служит для извлечения данных
            // statement.execute("select table1.username from table1" +
            //       " inner JOIN table2 on table1.user_id=table2.user_id inner JOIN table3 on table2.phone_id=table3.phone_id where room_number='30'");
            ResultSet set = statement.executeQuery("select * from table2;");
            //statement.executeQuery("select table1.username from table1 inner JOIN table2 on table1.user_id=table2.user_id inner JOIN table3 on table2.phone_id=table3.phone_id where room_number='30'");

            while (set.next()) { // Тут сет закроется
                System.out.println(set.getInt("user_id")); //вывод резалтСета через имя или номер колонки
                System.out.println(set.getInt(1));
                System.out.println("------");
            }

            ResultSet set1 = statement.executeQuery("select table1.username from table1 inner JOIN table2 on table1.user_id=table2.user_id inner JOIN table3 on table2.phone_id=table3.phone_id where room_number='30'");
            while (set1.next()) {
                System.out.println(set1.getString(1));
            }
            // работа с SQL Injection
            String userId = "1' or 1 = '1"; // данная иньекция выведет всех пользователей таблицы
            ResultSet set2 = statement.executeQuery("select * from table2 where user_id = '" +userId +"'");
            System.out.println("SQL Injection");
            while (set2.next()) {
                System.out.println("phone_id: " + set2.getInt( "phone_id"));
                System.out.println("phone_number: " + set2.getInt("phone_number"));
            }
//use SQL Prepared Statement используя их мы получаем защиту от SQL injection в отличии от простого стейтмана
           // String userId2 = "2";//                можно забить тут where user_id = ? and phone_number = ?
            PreparedStatement preparedStatement = conn.prepareStatement("select * from table2 where user_id = ?");
                preparedStatement.setString(1, userId);
//     String userId3 = "200"         preparedStatement.setString(2, userId3);
                ResultSet set3 = preparedStatement.executeQuery();
            System.out.println("Prepared Statement");
            while (set3.next()) {
                System.out.println("phone_id: " + set3.getInt( "phone_id"));
                System.out.println("phone_number: " + set3.getInt("phone_number"));
            }
        }

    }
}