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


    public static void login() {
        try (Connection conn = Main.establishConnection()){
            System.out.println("Please enter your Login: (or 'x' to cancel): ");
            String userName = stdin.nextLine();
            if(userName.equalsIgnoreCase("x")){
                Menu.callCustomer();
            }

            System.out.println("Please enter your Password (or 'x' to cancel) :");
            String password = stdin.nextLine();
            if(password.equalsIgnoreCase("x")){
                Menu.callCustomer();
            }

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM customers WHERE username = ? AND password = ?");
            stmt.setString(1, userName);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Menu.gotToCustomerMenu(rs.getInt("customer_id"));
            }else {
                System.err.println("Customer not found. Invalid credentials.");
                System.out.println("Press '1' to TRY AGAIN. ");
                System.out.println("Press 'x' to go back to ADMIN HOMEPAGE");
                String input = stdin.nextLine().trim();
                while (!input.equals("1") && !input.equals("x")){
                    System.err.println("Invalid choice. Please pick a valid option from the menu");
                    input = stdin.nextLine().trim();
                }
                if(input.equals("1")){
                    login();
                }
                if(input.equalsIgnoreCase("x")){
                    Menu.callCustomer();
                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
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

        if(registerCustomer(first_name, last_name, address, username, password)){
            System.out.println("Customer successfully registered.");
            System.out.println("Press '1' to LOGIN");
            System.out.println("Press 'x' to go back to CUSTOMER HOMEPAGE");
            String input = stdin.nextLine().trim();
            while (!input.equals("1") && !input.equals("x")){
                System.err.println("Invalid choice. Please pick a valid option from the menu");
                input = stdin.nextLine().trim();
            }
            if(input.equals("1")){
                login();
            }
            if(input.equalsIgnoreCase("x")){
                Menu.callCustomer();
            }
        };

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