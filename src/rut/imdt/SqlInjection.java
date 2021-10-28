package rut.imdt;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class SqlInjection {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username default (root): ");
        String user = sc.nextLine();
        user = user.equals("") ? "root" : user;

        System.out.println();

        System.out.print("Enter password default (empty): ");
        String password = sc.nextLine().trim();
        System.out.println();

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/new_schema?serverTimezone=UTC", props);
            System.out.println("Enter name: ");

            // потенциальная SQL инъекция!!!
            String name = sc.nextLine();
            String sqlquery = String.format("SELECT * FROM new_table WHERE name = '%s'", name);
            PreparedStatement stmt = connection.prepareStatement(sqlquery);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("name") + " " + rs.getInt("age"));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
