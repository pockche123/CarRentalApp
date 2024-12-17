package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Location {


    int locationId;
    String country;
    String city;
    String address;

    static Scanner stdin = new Scanner(System.in);


//    public Location(){
//
//    }


    public static void createLocation(){
        Connection conn = Main.establishConnection();

        try{
            System.out.printf("Please enter the country for your location: \n");
            String country = stdin.nextLine();

            System.out.printf("Please enter the city for your location: \n");
            String city = stdin.nextLine();

            System.out.printf("Please enter the full address for your location: \n");
            String address = stdin.nextLine();

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO locations (country, city, address) VALUES (?, ?, ?)");
            pstmt.setString(1, country);
            pstmt.setString(2, city);
            pstmt.setString(3, address);
            pstmt.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


//    public static void createLocation(){
//        Connection conn = Main.establishConnection();
//
//        try{
//            System.out.printf("Enter the id of the location you want to view all it's cars: \n");
//            int locationId = stdin.nextInt();
//
//
//            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO locations (country, city, address) VALUES (?, ?, ?)");
//            pstmt.setString(1, country);
//            pstmt.setString(2, city);
//            pstmt.setString(3, address);
//            pstmt.execute();
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }
}
