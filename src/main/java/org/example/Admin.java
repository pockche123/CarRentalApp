package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Admin {
    private int admin_id;
    private static Scanner stdin = new Scanner(System.in);


    public Admin(int admin_id) {
        this.admin_id = admin_id;
    }

    public Admin(){

    }

    public int getAdmin_id() {
        return admin_id;
    }
    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public static void  login() {
        try (Connection conn = Main.establishConnection()){
            System.out.println("Please use Username: 'admin01' and Password: 'adminpass321' as an example. ");
            System.out.println("Please enter your Username (or 'x' to cancel) :");
            String username = stdin.nextLine();
            if(username.equalsIgnoreCase("x")){
                Menu.callAdmin();
            }
            System.out.println("Please enter your Password (or 'x' to cancel) :");
            String password = stdin.nextLine();
            if(password.equalsIgnoreCase("x")){
                Menu.callAdmin();
            }
            String query = "SELECT * FROM admins WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username.trim());
            stmt.setString(2, password.trim());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                System.out.println("Login Successful");
                Menu.goToAdminMenu();
            }else {
                System.err.println("Admin not found. Invalid credentials.");
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
                    Menu.callAdmin();
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
