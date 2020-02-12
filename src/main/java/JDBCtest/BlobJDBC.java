package JDBCtest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.lang.*;


public class BlobJDBC {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        String name = "root";
        String connectURL = "jdbc:mysql://localhost:3306/cars";
        String pass = "123";
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        try (Connection conn = DriverManager.getConnection(connectURL, name, pass);
             Statement stat = conn.createStatement()) {
                //stat.executeUpdate("drop table carsblob");
                stat.executeUpdate("create table if not exists carsblob (id int(5) not null AUTO_INCREMENT, name_cars VARCHAR(30), name_user varchar (30),img BLOB, primary key (id));");

                BufferedImage image = ImageIO.read(new File("C:\\test\\JDBC\\src\\main\\java\\JDBCtest\\smile.jpg"));// считали картинку
                Blob blob = conn.createBlob();// создали блоб из конекшена
            try(OutputStream outputStream = blob.setBinaryStream(1)){//не закрыв тут стрим не запишется в базу
                ImageIO.write(image, "jpg", outputStream);
                }
                PreparedStatement prepStat = conn.prepareStatement("insert into carsBlob (name_cars, name_user, img) values (?, ? ,?)"); // создали запрос
                    prepStat.setString(1, "ferrary1");
                    prepStat.setString(2, "MrBond (James Bond)");
                    prepStat.setBlob(3, blob);
                    prepStat.execute(); // отправили данные

            //для того чтобы вытащить данные из таблицы
            ResultSet result = stat.executeQuery("select * from carsblob");
                int incrementFileName = 1;
                String pathNameFile;
            while (result.next()) {
                pathNameFile = "saved" + incrementFileName +".png" ;
                File outputFile = new File (pathNameFile);
                ++incrementFileName;
                Blob blob1 = result.getBlob("img");//вытаскиваем блом из таблицы
                BufferedImage image1 = ImageIO.read(blob1.getBinaryStream());// создаем стрим
                // создали файл в который будем писать
                ImageIO.write(image1, "png", outputFile);// записали
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}