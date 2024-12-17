package org.example;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class BookingRental{

    public static Scanner stdin = new Scanner(System.in);
    public static void viewAllBookingRentals(Connection conn) {

        System.out.println("Please enter the booking ID:");
        try {
            Statement stmt = conn.createStatement();
            String customer_id = stdin.nextLine();
            // Query to select all data from the booking_rentals table
            ResultSet rs = stmt.executeQuery("SELECT * FROM booking_rentals where customer_id = '" + customer_id + "'");

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
}
