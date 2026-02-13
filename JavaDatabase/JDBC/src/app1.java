import java.sql.*;

public class app1 {
    public static void main(String[] args) {

        try {

            // 1️⃣ Load Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2️⃣ Connect to DB
            String url = "jdbc:mysql://localhost:3306/testingdb?createDatabaseIfNotExist=true";
            String user = "root";
            String password = "password123";

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connected Successfully!");

            // 3️⃣ Create Table
            Statement stmt = con.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS students (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(50), " +
                    "marks INT)";
            stmt.executeUpdate(createTable);
            System.out.println("Table Created!");

            // =========================
            // 🔵 C - CREATE (INSERT)
            // =========================
            String insertQuery = "INSERT INTO students (name, marks) VALUES (?, ?)";
            PreparedStatement psInsert = con.prepareStatement(insertQuery);
            psInsert.setString(1, "poornima");
            psInsert.setInt(2, 90);
            psInsert.executeUpdate();
            System.out.println("Record Inserted!");

            // =========================
            // 🟢 R - READ (SELECT)
            // =========================
            String selectQuery = "SELECT * FROM students";
            ResultSet rs = stmt.executeQuery(selectQuery);

            System.out.println("---- Students List ----");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getInt("marks")
                );
            }

            // =========================
            // 🟡 U - UPDATE
            // =========================
            String updateQuery = "UPDATE students SET marks=? WHERE id=?";
            PreparedStatement psUpdate = con.prepareStatement(updateQuery);
            psUpdate.setInt(1, 95);
            psUpdate.setInt(2, 1);
            psUpdate.executeUpdate();
            System.out.println("Record Updated!");

            // =========================
            // 🔴 D - DELETE
            // =========================
            String deleteQuery = "DELETE FROM students WHERE id=?";
            PreparedStatement psDelete = con.prepareStatement(deleteQuery);
            psDelete.setInt(1, 1);
            psDelete.executeUpdate();
            System.out.println("Record Deleted!");

            // Close everything
            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}