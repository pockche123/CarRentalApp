package org.example;

import java.rmi.ServerError;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class BookingRental{

    public static Scanner stdin = new Scanner(System.in);
    public static void viewAllBookingRentalsByCustomer( int customer_id) {

        System.out.println("Please enter the booking ID:");
        try (Connection conn = Main.establishConnection()){

            Statement stmt = conn.createStatement();

            // query to select all data from the booking_rentals table
            ResultSet rs = stmt.executeQuery("SELECT * FROM booking_rentals where customer_id = " + customer_id);


            System.out.println("Booking Rental ID | \tPayment ID |\tRegistration Plate |\tDropoff Location ID | \tCustomer ID | \tSuspend");

            // process each row in the result set
            while (rs.next()) {
                // retrieve each column value
                int bookingRentalId = rs.getInt("booking_rental_id");
                int paymentId = rs.getInt("payment_id");
                String registrationPlate = rs.getString("registration_plate");
                int dropoffLocationId = rs.getInt("dropoff_location_id");
                int customerId = rs.getInt("customer_id");
                boolean suspend = rs.getBoolean("suspend");


                System.out.printf("\t\t%d\t\t\t\t\t%d\t\t\t\t%s\t\t\t\t\t\t\t%d\t\t\t\t%d\t\t\t%s\n",
                        bookingRentalId, paymentId, registrationPlate, dropoffLocationId, customerId, suspend);
            }

            System.out.println("Press 'x' to go back");
            String input = stdin.nextLine();
            while(!input.equalsIgnoreCase("x")){
                System.err.println("Please pick a valid option!");
                input = stdin.nextLine();
            }
            if(input.equalsIgnoreCase("x")){
                Menu.gotToCustomerMenu(customer_id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



    public static void viewAllBookingRentalsBySuspend(boolean suspendStatus) {

        System.out.println("Please enter the booking ID:");
        try (Connection conn = Main.establishConnection()){

            Statement stmt = conn.createStatement();

            // query to select all data from the booking_rentals table
            ResultSet rs = stmt.executeQuery("SELECT * FROM booking_rentals where suspend = " + suspendStatus);

            // print a header for the output
            System.out.println("Booking Rental ID | \tPayment ID |\tRegistration Plate |\tDropoff Location ID | \tCustomer ID | \tSuspend");

            // process each row in the result set
            while (rs.next()) {
                // retrieve each column value
                int bookingRentalId = rs.getInt("booking_rental_id");
                int paymentId = rs.getInt("payment_id");
                String registrationPlate = rs.getString("registration_plate");
                int dropoffLocationId = rs.getInt("dropoff_location_id");
                int customerId = rs.getInt("customer_id");
                boolean suspend = rs.getBoolean("suspend");


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


    public static boolean checkForValidBookingRental(String id, boolean suspend) {


        if(id == null || !Payment.isNumeric(id)){
            return false;
        }
        int bookingRental_id = Integer.parseInt(id);



        try(Connection conn = Main.establishConnection()){
            String query = "SELECT * FROM booking_rentals WHERE suspend = ? AND booking_rental_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setBoolean(1, suspend);  // Set the value for the 'suspend' column
            stmt.setInt(2, bookingRental_id);  // Set the value for 'booking_rental_id'
            ResultSet rs = stmt.executeQuery(); // Execute the query without passing the query string again

            return rs.next();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }




    public static boolean changeSuspendStatus(int booking_rental_id, boolean suspend) {
                try(Connection conn = Main.establishConnection()) {
                    PreparedStatement stmt = conn.prepareStatement("UPDATE booking_rentals  SET suspend  = ? WHERE booking_rental_id = ?");
                    stmt.setBoolean(1, suspend );
                    stmt.setInt(2, booking_rental_id);
                    stmt.execute();
                    return true;
                } catch(SQLException e){
                    throw new RuntimeException(e);

        }

    }


    public static void suspendMenu() {
        System.out.println("Suspend Booking Rental Menu:");
        viewAllBookingRentalsBySuspend(false);
        System.out.println("Please enter the Booking Rental ID to suspend (or 'x' to CANCEL): ");
        String id = stdin.nextLine();
        if(id.equalsIgnoreCase("x")){
            Menu.goToAdminMenu();
        }
        while (!checkForValidBookingRental(id, false)) {
            System.err.println("Rental ID has to be a number within the table: ");
            id = stdin.nextLine();
        }
        System.out.println("Are you sure you want to suspend this booking rental? (Y/N)");
        String answer = stdin.nextLine();
        while (!answer.trim().equalsIgnoreCase("y") && !answer.trim().equalsIgnoreCase("n")) {
            System.err.println("Please pick a valid option: ");
            answer = stdin.nextLine();
        }

        if (answer.trim().equalsIgnoreCase("y")) {
            if (changeSuspendStatus(Integer.parseInt(id), true)) {
                System.out.println("You have suspended booking rental with id: " + id);
                System.out.println("Press 1 to suspend rental");
                System.out.println("Press 'x' to go to Admin Menu");
                String input = stdin.nextLine();
                if(input.equals("1")){
                    suspendMenu();
                } else if(input.equalsIgnoreCase("x")){
                    Menu.goToAdminMenu();

                }
            } else {
                System.err.println("Error suspending booking rental with id: " + id);
                System.out.println("Press 1 to suspend rental");
                System.out.println("Press 'x' to go to Admin Menu");
                String input = stdin.nextLine();
                if(input.equals("1")){
                    suspendMenu();
                } else if(input.equalsIgnoreCase("x")){
                    Menu.goToAdminMenu();

                }
            }
            ;
        } else {
             Menu.goToAdminMenu();

        }
        ;
    }

        public static void unSuspendMenu(){
            System.out.println("UnSuspend Booking Rental Menu:");
            viewAllBookingRentalsBySuspend(true);
            System.out.println("Please enter the Booking Rental ID to unsuspend (or 'x' to CANCEL): ");
            String id = stdin.nextLine();
            if(id.equalsIgnoreCase("x")){
                Menu.goToAdminMenu();
            }
            while(! checkForValidBookingRental(id,true)){
                System.err.println("Rental ID has to be a number within the table: ");
                id = stdin.nextLine();

            }
            System.out.println("Are you sure you want to unsuspend this booking rental? (Y/N)");
            String answer = stdin.nextLine();
            while(!answer.trim().equalsIgnoreCase("y") && !answer.trim().equalsIgnoreCase("n")){
                System.err.println("Please pick a valid option: ");
                answer = stdin.nextLine();
            }

            if(answer.trim().equalsIgnoreCase("y")){
                if(changeSuspendStatus(Integer.parseInt(id), false)){
                    System.out.println("You have unsuspended booking rental with id: "+ id);
                    System.out.println("Press 1 to unsuspend rental");
                    System.out.println("Press 'x' to go to Admin Menu");
                    String input = stdin.nextLine();
                    if(input.equals("1")){
                        suspendMenu();
                    } else if(input.equalsIgnoreCase("x")){
                        Menu.goToAdminMenu();

                    }
                }else{
                    System.err.println("Error unsuspending booking rental with id: "+ id);
                };
            } else{
                Menu.goToAdminMenu();
//            take me back to main admin menu
            };



    }

    public static void bookingRentalMenu(int customer_id){
        System.out.println("Booking Rental Menu:");
        int location_id = Location.selectPickUpLocation();

        ArrayList<String> cars = Car.viewAllAvailableCarsForALocation(location_id);
        if(cars.isEmpty()){
            System.err.println("Please select from another location other than "+ location_id);
            bookingRentalMenu(customer_id);
        }
        String reg_plate = Car.selectCar(cars);

        System.out.println();
        int dropOffLocation = Location.selectDropOffLocation();

       int payment_id = Payment.paymentValidation();

       if(createBookingRental(payment_id, reg_plate, dropOffLocation, customer_id)){
           Car.changeCarStatus(reg_plate, "rented");
           System.out.println("Booking Rental created successfully.");
           System.out.println("Press 1 to book another rental");
           System.out.println("Press 'x' to go to Customer Menu");
           String input = stdin.nextLine();
           if(input.equals("1")){
               bookingRentalMenu(customer_id);
           } else if(input.equalsIgnoreCase("x")){
               Menu.gotToCustomerMenu(customer_id);

           }
       } else{
           System.out.println("Booking Rental unsuccessful.");
       };

    };








}
