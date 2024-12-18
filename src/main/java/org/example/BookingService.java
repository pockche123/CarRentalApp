package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class BookingService {
    private int booking_service_id;


    public BookingService(){};
    public BookingService(int booking_service_id){};
    public static Scanner stdin = new Scanner(System.in);



    public static void bookingServiceMenu(){
        System.out.println("Booking Service Menu");
        System.out.println("Options");
        System.out.println("1. Minor Service");
        System.out.println("2. Major Service");
        System.out.println("3. Repair");
        System.out.println("Please select an option no. from above:");

        String service_option = stdin.nextLine();
        while(!service_option.equals("1")&& !service_option.equals("2")&& !service_option.equals("3")){
            System.out.println("Please select a valid option no:");
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




















    }



    public static void  createBookingService(String service_type, String registration_plate, String service_status){
            String insertQuery = "INSERT INTO booking_services (service_type, registration_plate, service_status) VALUES (?, ?, ?)";
            try(Connection conn = Main.establishConnection()){
                PreparedStatement stmt = conn.prepareStatement(insertQuery);
                stmt.setString(1, service_type);
                stmt.setString(2, registration_plate);
                stmt.setString(3, service_status);

               stmt.executeUpdate();


            } catch(SQLException e){
                throw new RuntimeException(e);

            }
    }







}
