package JDBCtest;

import java.sql.*;
import java.lang.*;

public class ProcedureJDBC {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        String name = "root";
        String connectURL = "jdbc:mysql://localhost:3306/cars";
        String pass = "123";
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        try (Connection conn = DriverManager.getConnection(connectURL, name, pass);
             Statement stat = conn.createStatement()) {
            CallableStatement callableStatement = conn.prepareCall("{call CarsCount(?)}");
            callableStatement.registerOutParameter(1, Types.INTEGER);
            //регестрируем выходные параметры в данном примере у нас будет 1 переменная с типом инт
            callableStatement.execute();// выполняем
            System.out.println(callableStatement.getInt(1));

            // процедуру вызова с условием через сетСтринг
            CallableStatement callableStatement1 = conn.prepareCall("{call getCars(?)}");
            callableStatement1.setString(1, "ferrary1");
            if(callableStatement1.execute()){
                ResultSet resultSet = callableStatement1.getResultSet();
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("id"));
                    System.out.println(resultSet.getString("name_cars"));
                }

            }

        }
    }
}
/* создаем хранимую процедуру в базе данных считающая количество строчек в carsblob
CREATE PROCEDURE `CarsCount` (OUT `cnt` INT(11))
    BEGIN
    select count(*) into cnt from carsblob;
END*/
