package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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



    public static void viewAllLocations(){
        Connection conn = Main.establishConnection();

        try{
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM locations");
            ResultSet rs = pstmt.executeQuery();

            // Print a header for the output
//            System.out.println("Country|\tCity |\t\tAddress");

            while(rs.next()){

                String country = rs.getString("country");
                String city = rs.getString("city");
                String address = rs.getString("address");


                System.out.printf("\t\t%s\t\t\t%s\t\t\t%s\n",
                        country, city, address);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    public static void viewAllAvailableCarsForALocation(){
        Connection conn = Main.establishConnection();

        try{
            System.out.printf("Enter the id of the location you want to view all its available cars: \n");
            int locationId = stdin.nextInt();


            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM cars WHERE pickup_location_id = ? AND car_status = ?");
            pstmt.setInt(1, locationId);
            pstmt.setString(2, "available");
            ResultSet rs = pstmt.executeQuery();

            // Print a header for the output
                System.out.println("Registration Plate |\tCar Type |\t\tMake |\t\t\tModel");

            while(rs.next()){

                String registration = rs.getString("registration_plate");
                String carType = rs.getString("car_type");
                String make = rs.getString("make");
                String model = rs.getString("model");

                System.out.printf("\t\t%s\t\t\t%s\t\t\t%s\t\t\t%s\n",
                        registration, carType, make, model);
            }



        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
