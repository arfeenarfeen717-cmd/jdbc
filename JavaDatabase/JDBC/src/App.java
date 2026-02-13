 import java.sql.*;

public class App {

    public static void main(String[] args) {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/testingdb?createDatabaseIfNotExist=true";
            String user = "root";
            String password = "password123";

            Connection con = DriverManager.getConnection(url, user, password);

            System.out.println("Connected Successfully!");

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}