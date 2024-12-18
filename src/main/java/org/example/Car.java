package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Car {

    static Scanner stdin = new Scanner(System.in);


    public static ArrayList viewAllAvailableCarsForALocation(int locationId){
        Connection conn = Main.establishConnection();

        // This will be used to store the registration of all the cars
        ArrayList<String> carRegistrationList = new ArrayList<>();

        try{

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

                carRegistrationList.add(registration);

                System.out.printf("\t\t%s\t\t\t%s\t\t\t%s\t\t\t%s\n",
                        registration, carType, make, model);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return carRegistrationList;
    }


    public static String selectCarForRental(ArrayList<String> carRegistrationList){
        Connection conn = Main.establishConnection();
        String registrationNumber;

        if(carRegistrationList.size() == 0){
            System.out.println("There are no cars available to choose from");
            registrationNumber = "";
            return registrationNumber;
        }


        System.out.println("Select a car that you will like to book from the list by entering the car registration");

        // take in the registration from the user
        registrationNumber = stdin.nextLine();

        boolean isFound = false;
        while(!isFound){
            for(int i = 0; i < carRegistrationList.size(); i++){
                if(carRegistrationList.get(i).equals(registrationNumber.toUpperCase())){
                    isFound = true;
                    break;
                }
            }
            if(!isFound){
                System.err.println("The registration number entered is not on the list! Try again");
                registrationNumber = stdin.nextLine();
            }
        }

        System.out.printf("You have chosen to book the car with registration number %s", registrationNumber.toUpperCase());

        return registrationNumber.toUpperCase();
    }
}
