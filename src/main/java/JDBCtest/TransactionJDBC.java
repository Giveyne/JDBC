package JDBCtest;

import java.sql.*;

public class TransactionJDBC {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        String name = "root";
        String connectURL = "jdbc:mysql://localhost:3306/cars";
        String pass = "123";
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        try (Connection conn = DriverManager.getConnection(connectURL, name, pass);
             Statement stat = conn.createStatement()) {
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);// пока эта не дайдет другие не получают доступ
//Connection.TRANSACTION_REPEATABLE_READ не побходим когда между несколькими чтениями в одной
//транзакции вклинивается апдейт с другой транзакции
//Connection.TRANSACTION_SERIALIZABLE если во время чтений(select) вклинивается добавление записи (insert)
//Connection.TRANSACTION_READ_UNCOMMITTED свободные транзакции любой может в них вклинится
            stat.executeUpdate("create table if not exists carsDate (id int(5) not null AUTO_INCREMENT, name_cars VARCHAR(30), dt DATE, primary key (id));");

            stat.executeUpdate("insert into carsDate (name_cars, dt) values ('Ferrari', '2020-02-13')");

            conn.rollback();// отменить все изменения т.е insert и update set (таблица создасться)
            //Savepoint savepoint = conn.setSavepoint(); установить сейвпоинт в любом месте транзакции
         //conn.rollback(savepoint); откатить до сейвпоинта
         // удобно их делать в catch чтобы если вылетела SQLException можно было откатить транзакции
            conn.commit();// отправить изменения в базу общей транзакцией
// еще есть батч создать вначале общий хтмл запрос а потом разом его отправить
            stat.addBatch("insert into carsDate");
            stat.addBatch("update into carDate set name ");
            if (stat.executeBatch().length == 5){
                conn.commit();
            }
            else conn.rollback();

        }
    }
}