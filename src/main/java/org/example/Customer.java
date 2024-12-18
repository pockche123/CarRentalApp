package org.example;
import java.sql.*;
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
}