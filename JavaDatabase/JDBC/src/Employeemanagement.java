import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Employeemanagement {

    static final String URL = "jdbc:mysql://localhost:3306/company";
    static final String USER = "root";
    static final String PASS = "password123";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            System.out.println("Connected to the database!");

            while (true) {
                System.out.println("\n1. Add Employee");
                System.out.println("2. View All Employees");
                System.out.println("3. Update Employee");
                System.out.println("4. Delete Employee");
                System.out.println("5. Search Employee");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                int choice = -1;
                try {
                    choice = sc.nextInt();
                    sc.nextLine(); // Clear buffer
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    sc.nextLine(); // clear buffer
                    continue;
                }

                switch (choice) {
                    case 1 -> addEmployee(con, sc);
                    case 2 -> viewEmployees(con);
                    case 3 -> updateEmployee(con, sc);
                    case 4 -> deleteEmployee(con, sc);
                    case 5 -> searchEmployee(con, sc);
                    case 6 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice! Try again.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    // Add Employee
    static void addEmployee(Connection con, Scanner sc) {
        try {
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Enter email: ");
            String email = sc.nextLine();
            System.out.print("Enter department: ");
            String dept = sc.nextLine();
            System.out.print("Enter salary: ");
            double salary = sc.nextDouble();
            sc.nextLine();

            String sql = "INSERT INTO employees (name, email, department, salary) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, dept);
            ps.setDouble(4, salary);

            ps.executeUpdate();
            System.out.println("Employee added successfully!");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: Email already exists!");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Salary must be a number.");
            sc.nextLine(); // clear buffer
        }
    }

    // View All Employees
    static void viewEmployees(Connection con) {
        String sql = "SELECT * FROM employees";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\nID\tName\tEmail\tDepartment\tSalary");
            System.out.println("-----------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%d\t%s\t%s\t%s\t%.2f\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("department"),
                        rs.getDouble("salary"));
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // Update Employee
    static void updateEmployee(Connection con, Scanner sc) {
        try {
            System.out.print("Enter employee ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter new department: ");
            String dept = sc.nextLine();
            System.out.print("Enter new salary: ");
            double salary = sc.nextDouble();
            sc.nextLine();

            String sql = "UPDATE employees SET department = ?, salary = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, dept);
            ps.setDouble(2, salary);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Employee updated successfully!");
            else System.out.println("Employee with ID " + id + " not found.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            sc.nextLine(); // clear buffer
        }
    }

    // Delete Employee
    static void deleteEmployee(Connection con, Scanner sc) {
        try {
            System.out.print("Enter employee ID to delete: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = "DELETE FROM employees WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Employee deleted successfully!");
            else System.out.println("Employee with ID " + id + " not found.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            sc.nextLine(); // clear buffer
        }
    }

    // Search Employee
    static void searchEmployee(Connection con, Scanner sc) {
        try {
            System.out.print("Enter employee ID to search: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = "SELECT * FROM employees WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("\nID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Department: " + rs.getString("department"));
                System.out.println("Salary: " + rs.getDouble("salary"));
            } else {
                System.out.println("Employee with ID " + id + " not found.");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            sc.nextLine(); // clear buffer
        }
    }
}
