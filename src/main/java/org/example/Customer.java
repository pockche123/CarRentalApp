package org.example;
import java.sql.*;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Customer
{
    private int customer_id;
    private static Scanner stdin = new Scanner(System.in);
    public Customer() {
    }
    public Customer(int customer_id) {
        this.customer_id = customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
    public int getCustomer_id() {
        return customer_id;
    }

    public static Scanner stdin = new Scanner(System.in);

    public static int login() {
        try (Connection conn = Main.establishConnection()){
            System.out.println("Please enter your Login:");
            String userName = stdin.nextLine();
            System.out.println("Please enter your Password:");
            String password = stdin.nextLine();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM customers username = " + userName + " password = " + password);
            String customer_id = stdin.nextLine();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customers username = "+userName+" password = "+password);
            if (rs.next()){
                return rs.getInt("customer_id");
            }else {
                System.err.println("Customer not found. Invalid credentials.");
                return -1;
            }


        } catch (SQLException e) {
        }
    }

 
    public static void validateRegistration(){

        System.out.println("Welcome to customer registration");
        System.out.println("1. Please enter your first name:");
        String first_name = stdin.nextLine();
        System.out.println("2. Please enter your last name:");
        String last_name = stdin.nextLine();
        System.out.println("3. Please enter your address:");
        String address = stdin.nextLine();
        System.out.println("4. Please create a new username:");
        String username = stdin.nextLine();
        System.out.println("5. Please enter your password:");
        String password = stdin.nextLine();

        registerCustomer(first_name, last_name, address, username, password);

    }

    public static boolean  registerCustomer(String first_name, String last_name, String address, String username, String password) {

        String insertQuery = "INSERT INTO customers (first_name, last_name, address, username, password) VALUES (?,?,?,?,?)";
        try(Connection conn = Main.establishConnection()){
            PreparedStatement stmt = conn.prepareStatement(insertQuery);
            stmt.setString(1, first_name);
            stmt.setString(2, last_name);
            stmt.setString(3, address);
            stmt.setString(4, username);
            stmt.setString(5,password);

            int rowsAffected = stmt.executeUpdate();


            return rowsAffected > 0;


        } catch(SQLException e){
            throw new RuntimeException(e);


        }
    }


}