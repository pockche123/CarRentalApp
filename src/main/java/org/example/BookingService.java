package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookingService {
    private int booking_service_id;


    public BookingService(){};
    public BookingService(int booking_service_id){};
    public static Scanner stdin = new Scanner(System.in);



    public static void bookingServiceMenu(){
        System.out.println("Service Booking Menu");
        System.out.println("Options");
        System.out.println("1. Minor Service");
        System.out.println("2. Major Service");
        System.out.println("3. Repair");
        System.out.println("Please select an option no. from above (or 'x' to CANCEL) :");


        String service_option = stdin.nextLine();
        if(service_option.equalsIgnoreCase("x")){
            Menu.goToAdminMenu();

        }
        while(!service_option.equals("1")&& !service_option.equals("2")&& !service_option.equals("3")){
            System.err.println("Please select a valid option no:");
            service_option = stdin.nextLine();
        };

        String service_type;
        if(service_option.equals("1")){
            service_type = "Minor Service";
        } else if(service_option.equals("2")){
            service_type = "Major Service";
        } else{
            service_type = "Repair";
        }
        Car.viewAllAvailableCars();

        System.out.println("Please enter the registration plate number from above to book for service: (or 'x' to CANCEL) ");
        String reg_plate = stdin.nextLine().toUpperCase();
        if(reg_plate.equalsIgnoreCase("x")){
            Menu.goToAdminMenu();
        }
        while(!checkForValidCar(reg_plate)){
            System.err.println("Please enter a valid plate number from above to book for service: ");
            reg_plate = stdin.nextLine();
        }
        if(createBookingService(service_type, reg_plate.toUpperCase(), "In Progress")){
            Car.changeCarStatus(reg_plate, "In Service");
            System.out.println("Service Booking Created");
            System.out.println("Press '1' to BOOK ANOTHER SERVICE ");
            System.out.println("Press 'x' to go back to ADMIN MENU");
            String input = stdin.nextLine().trim();
            while (!input.equals("1") && !input.equals("x")){
                System.err.println("Invalid choice. Please pick a valid option from the menu");
                input = stdin.nextLine().trim();
            }
            if(input.equals("1")){
                bookingServiceMenu();
            }
            if(input.equalsIgnoreCase("x")){
                Menu.goToAdminMenu();
            }

        } else{
            System.out.println("Service Booking Failed");
            System.out.println("Press '1' to BOOK ANOTHER SERVICE ");
            System.out.println("Press 'x' to go back to ADMIN MENU");
            String input = stdin.nextLine().trim();
            while (!input.equals("1") && !input.equals("x")){
                System.err.println("Invalid choice. Please pick a valid option from the menu");
                input = stdin.nextLine().trim();
            }
            if(input.equals("1")){
                bookingServiceMenu();
            }
            if(input.equalsIgnoreCase("x")){
                Menu.goToAdminMenu();
            }


        }











    }




    public static boolean  createBookingService(String service_type, String registration_plate, String service_status){
            String insertQuery = "INSERT INTO booking_services (service_type, registration_plate, service_status) VALUES (?, ?, ?)";
            try(Connection conn = Main.establishConnection()){
                PreparedStatement stmt = conn.prepareStatement(insertQuery);
                stmt.setString(1, service_type);
                stmt.setString(2, registration_plate);
                stmt.setString(3, service_status);


               return stmt.executeUpdate() > 0;


            } catch(SQLException e){
                throw new RuntimeException(e);

            }
    }

 public static boolean checkForValidCar(String regplate){
        if(regplate == null || regplate.isEmpty() || regplate.length() != 7){
            return false;
        }

        try(Connection conn = Main.establishConnection()){
        String query = "SELECT * FROM cars WHERE  lower(registration_plate) = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, regplate.trim().toLowerCase());

        ResultSet rs = stmt.executeQuery();

        return rs.next();

    }catch(SQLException e){
        throw new RuntimeException(e);
    }

    }







}
