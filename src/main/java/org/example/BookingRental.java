package org.example;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class BookingRental{


    private int booking_rental_id;


    public BookingRental(){};
    public BookingRental(int booking_rental_id){};

    public static Scanner stdin = new Scanner(System.in);
    public static void viewAllBookingRentals(Connection conn, int customer_id) {

        System.out.println("Please enter the booking ID:");
        try {
            Statement stmt = conn.createStatement();

            // Query to select all data from the booking_rentals table
            ResultSet rs = stmt.executeQuery("SELECT * FROM booking_rentals");

            // Print a header for the output
            System.out.println("Booking Rental ID | \tPayment ID |\tRegistration Plate |\tDropoff Location ID | \tCustomer ID | \tSuspend");

            // Process each row in the result set
            while (rs.next()) {
                // Retrieve each column value
                int bookingRentalId = rs.getInt("booking_rental_id");
                int paymentId = rs.getInt("payment_id");
                String registrationPlate = rs.getString("registration_plate");
                int dropoffLocationId = rs.getInt("dropoff_location_id");
                int customerId = rs.getInt("customer_id");
                boolean suspend = rs.getBoolean("suspend");

                // Print the values to the console
                System.out.printf("\t\t%d\t\t\t\t\t%d\t\t\t\t%s\t\t\t\t\t\t\t%d\t\t\t\t%d\t\t\t%s\n",
                        bookingRentalId, paymentId, registrationPlate, dropoffLocationId, customerId, suspend);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static boolean createBookingRental(int payment_id, String registration_plate, int dropoff_location_id, int customer_id) {
        String insertQuery = "INSERT INTO booking_rentals (payment_id, registration_plate, dropoff_location_id, customer_id, suspend) VALUES (?, ?, ?, ?, ?)";
        try(Connection conn = Main.establishConnection()){
            PreparedStatement stmt = conn.prepareStatement(insertQuery);
            stmt.setInt(1, payment_id);
            stmt.setString(2, registration_plate);
            stmt.setInt(3, dropoff_location_id);
            stmt.setInt(4, customer_id);
            stmt.setBoolean(5,false);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected >0;


        } catch(SQLException e){
            throw new RuntimeException(e);

        }
    }




}
