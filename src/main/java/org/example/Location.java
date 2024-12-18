package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Location {


    int locationId;
    String country;
    String city;
    String address;

    static Scanner stdin = new Scanner(System.in);



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



    //This method will display all the available pick up locations, then ask the user to pick one of them and then return the id of what ever location is picked
    public static int selectPickUpLocation(){
        Connection conn = Main.establishConnection();
        int locationId = 0;

        try{
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM locations");
            ResultSet rs = pstmt.executeQuery();

            //create an array list to store all the current location IDs returned
            ArrayList<Integer> locationIdList = new ArrayList<>();

            System.out.println("Pick a location from the list by selecting the location id");
            while(rs.next()){

                int location_id = rs.getInt("location_id");
                String country = rs.getString("country");
                String city = rs.getString("city");
                String address = rs.getString("address");

                System.out.printf("\t\t%d\t\t\t%s\t\t\t%s\t\t\t%s\n",
                        location_id, country, city, address);

                //store all the location id returned so that it can be checked against the id selected by the user.
                locationIdList.add(rs.getInt("location_id"));
            }

            //first check that the user enters a valid number for the id
            locationId = validateNumber(stdin.nextLine());

            boolean isFound = false;
            while(!isFound){
                for(int i = 0; i < locationIdList.size(); i++){
                    if(locationIdList.get(i) == locationId){
                        isFound = true;
                        break;
                    }
                }
                if(!isFound){
                    System.err.println("The location id selected is not part of the list! try again");
                    locationId = validateNumber(stdin.nextLine());
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return locationId;
    }



    public static int validateNumber(String input){
        boolean isValid = false;
        int number = 0;

        while(!isValid) {

            try {
                number = Integer.parseInt(input);
                isValid = true;

            } catch (NumberFormatException e) {

                System.err.println("invalid number entered! Try again");
                input = stdin.nextLine();

            }
        }
        return number;
    }
}
