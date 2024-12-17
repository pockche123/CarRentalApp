package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static Scanner stdin = new Scanner(System.in);

    public static void main(String[] args) {

        BookingRental.viewAllBookingRentals(establishConnection());

        Location.viewAllAvailableCarsForALocation();


    }

    public static Connection establishConnection() {
        String connectionString = "jdbc:postgresql://localhost:5432/CarRental";
        Properties props = new Properties();

        try {
            InputStream input = Main.class.getClassLoader().getResourceAsStream("db.properties");
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            Connection conn = DriverManager.getConnection(connectionString, props);
            return conn;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
