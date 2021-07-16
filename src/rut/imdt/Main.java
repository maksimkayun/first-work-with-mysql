package rut.imdt;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username default (root): ");
        String user = sc.nextLine();
        user = user.equals("") ? "root" : user;
        System.out.println();

        System.out.print("Enter password default (empty):");
        String password = sc.nextLine().trim();
        System.out.println();

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        try {
            // Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test?serverTimezone=UTC", props);
            PreparedStatement stmt =
                    connection.prepareStatement("SELECT e.name, d.name AS 'department_name', " +
                            "e.salary FROM employees AS e JOIN departments AS d ON e.department_id=d.id " +
                            "WHERE salary > ?");
            System.out.print("Enter salary: ");
            String salary = sc.nextLine();
            stmt.setInt(1, Integer.parseInt(salary));
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                System.out.println(rs.getString("name") + " " + rs.getString("department_name") +
                        " " + rs.getString("salary"));
            }
            connection.close(); //закрываем соединение!
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
