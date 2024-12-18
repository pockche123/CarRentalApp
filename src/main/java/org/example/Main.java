package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Properties;
import java.util.Scanner;

public class Main
{

    public static void main (String[] args)
    {
    Scanner stdin =new Scanner(System.in);

    while(true)
    {
        System.out.println("Please choose one of the following options:");
        System.out.println("1. Customer");
        System.out.println("2. Admin");

        String choice = stdin.nextLine();

        switch(choice)
        {
            case "1":
                callCustomer();
                break;
                case "2":
                    callAdmin();
                    break;
                    default:
                        System.out.println("Invalid choice");
        }
    }

    }

    private static void callCustomer() {

        System.out.println("Hi I am in customer page");
    }
    private static void callAdmin() {
        System.out.println("Hi I am admin page");
    }



        public static Connection establishConnection () {
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


