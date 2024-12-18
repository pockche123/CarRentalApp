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

    public static int login() {
        try (Connection conn = Main.establishConnection()){
            System.out.println("Please enter your Username:");
            String username = stdin.nextLine();
            System.out.println("Please enter your Password:");
            String password = stdin.nextLine();
            String query = "SELECT * FROM admins WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username.trim());
            stmt.setString(2, password.trim());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return rs.getInt("admin_id");
            }else {
                System.err.println("Admin not found. Invalid credentials.");
                return -1;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }



}
