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



    public void createBookingServiceMenu(){
        System.out.println("Options");
        System.out.println("1. Minor Service");
        System.out.println("2. Major Service");
        System.out.println("3. Repair");
        System.out.println("Please pick an option from above:");

        String service_type = stdin.nextLine();
        while(!validOption(service_type.trim(),3)){
            service_type = stdin.nextLine();
        };








    }

    public boolean validOption(String option, int no){
        if(option == null ||!Payment.isNumeric(option)){
            return false;
        }
        return Integer.parseInt(option) <= no && Integer.parseInt(option) >= 0;

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
