package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Car {

    static Scanner stdin = new Scanner(System.in);


    public static void viewAllCars(){
        Connection conn = Main.establishConnection();

        try{
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM cars");
            ResultSet rs = pstmt.executeQuery();

            System.out.printf("%-20s %-15s %-15s %-20s %-10s%n",
                    "REGISTRATION |", "TYPE|", "MAKE|", "MODEL|", "CAR STATUS");
            while(rs.next()){

                String registration = rs.getString("registration_plate");
                String carType = rs.getString("car_type");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String status = rs.getString("car_status");


                System.out.printf("%-20s %-15s %-15s %-20s %-10s%n",
                        registration, carType, make, model, status);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    public static ArrayList viewAllAvailableCars(){
        Connection conn = Main.establishConnection();

        // This will be used to store the registration of all the available cars
        ArrayList<String> carRegistrationList = new ArrayList<>();

        try{

            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM cars WHERE car_status = ?");
            pstmt.setString(1, "available");
            ResultSet rs = pstmt.executeQuery();

            // Print a header for the output
            System.out.printf("%-20s %-15s %-15s %-20s %n",
                    "REGISTRATION |", "TYPE|", "MAKE|", "MODEL|");


            while(rs.next()){

                String registration = rs.getString("registration_plate");
                String carType = rs.getString("car_type");
                String make = rs.getString("make");
                String model = rs.getString("model");

                carRegistrationList.add(registration);

                System.out.printf("%-20s %-15s %-15s %-20s %n",
                        registration, carType, make, model);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return carRegistrationList;
    }




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



    public static void viewCarsByType(){
        Connection conn = Main.establishConnection();

        // This will be used to store the registration of all the cars
        ArrayList<String> carTypeList = new ArrayList<>();

        try{
            PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT LOWER(car_type) AS car_type FROM cars;");
            ResultSet rs = pstmt.executeQuery();

            // Print a header for the output
            System.out.println("|CAR TYPES|\t");
            while(rs.next()){
                String carType = rs.getString("car_type");

                carTypeList.add(carType);

                System.out.printf("\t%s\n", carType);
            }

            // take in the type input from the user
            System.out.println("Enter the car type that you will like to see, from the list above >");
            String carTypeInput = stdin.nextLine();

            // check that the type entered is a valid one in the database
            boolean isFound = false;
            while(!isFound){
                for(int i = 0; i < carTypeList.size(); i++){
                    if(carTypeList.get(i).toLowerCase().equals(carTypeInput.toLowerCase())){
                        isFound = true;
                        break;
                    }
                }
                if(!isFound){
                    System.err.println("The type entered is not on the list! Try again");
                    carTypeInput = stdin.nextLine();
                }
            }

            pstmt = conn.prepareStatement("SELECT * FROM cars WHERE LOWER(car_type) = ?");
            pstmt.setString(1, carTypeInput.toLowerCase().trim());
            ResultSet resultSet = pstmt.executeQuery();

            System.out.printf("%-20s %-15s %-15s %-20s%n",
                    "REGISTRATION", "CAR TYPE", "MAKE", "MODEL");


            while (resultSet.next()) {
                String registration = resultSet.getString("registration_plate");
                String carType = resultSet.getString("car_type");
                String make = resultSet.getString("make");
                String model = resultSet.getString("model");

                System.out.printf("%-20s %-15s %-15s %-20s%n",
                        registration, carType, make, model);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }




    public static String selectCar(ArrayList<String> carRegistrationList){
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


    public static void createCar(){
        Connection conn = Main.establishConnection();

        try{
            System.out.printf("Please enter the registration number: ");
            String registration = "";
            boolean isRegValid = false;
            while(!isRegValid){
                registration = stdin.nextLine();
                if(registration.length() == 7){
                    isRegValid = true;
                }
                else{
                    System.out.println("The vehicle registration must be 7 characters long! try again >");
                }
            }

            System.out.printf("Please enter the car type: ");
            String carType = stdin.nextLine();

            System.out.printf("Please enter the make of the car: ");
            String carMake = stdin.nextLine();

            System.out.printf("Please enter the model of the car: ");
            String carModel = stdin.nextLine();

            System.out.println("Select the pick up location from the available locations below by entering the location ID >");
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM locations");
            ResultSet rs = pstmt.executeQuery();

            //create an array list to store all location ids
            ArrayList<Integer> locationIdList = new ArrayList<>();

            while(rs.next())
            {
                int locationId = rs.getInt("location_id");
                String country = rs.getString("country");
                String city = rs.getString("city");
                String address = rs.getString("address");

                System.out.printf("\t\t%d\t\t%s\t\t\t%s\t\t\t%s\n",
                        locationId, country, city, address);

                locationIdList.add(locationId);
            }


            boolean isFound = false;

            int locationId = -1;
            while(!isFound){

                locationId = validateNumber((stdin.nextLine()));

                //check if the id entered by the user is part of the list
                for(int i = 0; i < locationIdList.size(); i++)
                {
                    if(locationId == locationIdList.get(i))
                    {
                        isFound = true;
                        break;
                    }
                }
                if(!isFound){
                    System.err.println("The ID entered doesn't exist! Try again");
                }

            }
            // At this stage the user entered id has been validated
            pstmt = conn.prepareStatement("INSERT INTO cars (registration_plate, car_type, make, model, car_status, pickup_location_id) VALUES(?,?,?,?,?,?)");
            pstmt.setString(1, registration.toUpperCase());
            pstmt.setString(2, carType);
            pstmt.setString(3, carMake);
            pstmt.setString(4, carModel);
            pstmt.setString(5, "available");
            pstmt.setInt(6, locationId);
            pstmt.execute();

            System.out.println("Car record added successfully\n");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    public static void changeCarStatus(String registration, String status){
        Connection conn = Main.establishConnection();
        try{
            PreparedStatement pstmt = conn.prepareStatement("UPDATE cars SET car_status = ? WHERE registration_plate = ?");
            pstmt.setString(1, status);
            pstmt.setString(2, registration);
            pstmt.execute();

            System.out.println("Car status updated successfully successfully\n");
        }catch (SQLException e){
            e.printStackTrace();
        }
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
