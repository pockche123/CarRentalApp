package org.example;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private static Scanner stdin =new Scanner(System.in);
    int customer_id;
    public static void startApp(){


        System.out.println("Please choose one of the following options:");
        System.out.println("1. Customer");
        System.out.println("2. Admin");
        System.out.println("3. Exit");

        String choice = stdin.nextLine();

        switch(choice)
        {
            case "1":
                callCustomer();
                break;
            case "2":
                callAdmin();
                break;
            case "3":
                System.exit(0);
            default:
                System.err.println("Invalid choice");
        }
    }
    public static void callCustomer(){

        System.out.println("Please select one of the following options:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Go Back");
        System.out.println("4. Exit");

        String choice = stdin.nextLine().trim();
        while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3")&& !choice.equals("4")){
            System.err.println("Invalid choice. Please pick an option from the menu");
            choice = stdin.nextLine().trim();
        }

        if (choice.equals("1")){
            goToCustomerLogin();
        }else if(choice.equals("2")){
            goToCustomerRegister();
        } else if(choice.equals("3")){
            startApp();
        } else if(choice.equals("4")){
            System.exit(0);
        }




    }

    private static void goToCustomerRegister() {

        Customer.validateRegistration();
    }


    public static void callAdmin(){
        System.out.println("I am in admin menu");
        
        System.out.println("Please select one of the following options:");
        System.out.println("1. Login");
        System.out.println("2. Go Back");
        System.out.println("3. Exit");
        String choice = stdin.nextLine().trim();
        while(!choice.equals("1")  && !choice.equals("2")){
            System.err.println("Invalid choice. Please pick an option from the menu");
            choice = stdin.nextLine().trim();
        }

        if (choice.equals("1")){
            goToAdminLogin();
        } else if(choice.equals("2")){
            startApp();
        } else if(choice.equals("3")){
            System.exit(0);
        }
    }

    public static void goToAdminLogin() {
        System.out.println("Admin Login: ");
        Admin.login();

    }

    public static  void goToAdminMenu() {
        System.out.println("Please select one of the following options:");

        System.out.println("1. View all Cars");
        System.out.println("2. Add a Car");
        System.out.println("3. Suspend a Car");
        System.out.println("4. UnSuspend a Car");
        System.out.println("5. Book a Car for Servicing");
        System.out.println("6. Go Back");
        System.out.println("7. Exit");
        
        String choice = stdin.nextLine().trim();
        while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3")&& !choice.equals ("4")&& !choice.equals("5")&& !choice.equals("6")&& !choice.equals("7"))
        {
            System.err.println("Invalid choice. Please pick an option from the menu");
            choice = stdin.nextLine().trim();
        }

        if (choice.equals("1")){
            viewAllCarsMenu();
        }else if(choice.equals("2")){
            addCarMenu();
        }else if(choice.equals("3")){
            BookingRental.suspendMenu();
        }else if(choice.equals("4")){
            BookingRental.unSuspendMenu();
        }else if(choice.equals("5")){
            BookingService.bookingServiceMenu();
        } else if(choice.equals("6")){
            startApp();
        } else if(choice.equals("7")){
            System.exit(0);
        }
        
    }

    private static void goToUnsuspendACar() {
    }

    private static void goToBookACarForServiceing() {
    }

    private static void goToSuspendACar() {
    }

    private static void goToAddCar() {
    }

    private static void goToViewAllBooking() {
    }

    public static void goToCustomerLogin(){
        System.out.println("Customer Login :");
        Customer.login();


    }

    public static void gotToCustomerMenu(int customerId){
        System.out.println("Welcome to Customer Menu");
        System.out.println("Please choose one of the following options:");
        System.out.println("1. Create a rental booking");
        System.out.println("2. View a rental booking");
        System.out.println("3. Go Back");
        System.out.println("4. Exit");

        String choice = stdin.nextLine().trim();
        while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4")){
            System.err.println("Invalid choice. Please pick an option from the menu");
            choice = stdin.nextLine().trim();
        }

        if (choice.equals("1")){
            BookingRental.bookingRentalMenu(customerId);
        }else if(choice.equals("2")){
            BookingRental.viewAllBookingRentalsByCustomer(customerId);
        } else if(choice.equals("3")){
            startApp();
        } else if(choice.equals("4")){
            System.exit(0);
        }
    }

    private static void goToCreatRental() {
        System.out.println("Please Pick a Location from the following options:");

    }

    private static void goToViewRentalBooking() {
        System.out.println("Welcome to View  All Rental Booking");
        String choiceSecond = stdin.nextLine().trim();
        while(!choiceSecond.equals("1") && !choiceSecond.equals("2")){
            System.err.println("Invalid choice. Please pick an option from the menu");
            choiceSecond = stdin.nextLine().trim();
        }



    }

    public static void addCarMenu(){
        System.out.println("Add a Car Menu");

        Car.createCar();
        System.out.println("Press '1' to ADD ANOTHER CAR");
        System.out.println("Press 'x' to go back to ADMIN MENU");
        System.out.println();
        String input = stdin.nextLine().trim();
        while (!input.equals("1") && !input.equals("x")){
            System.err.println("Invalid choice. Please pick a valid option from the menu");
            input = stdin.nextLine().trim();
        }
        if(input.equals("1")){
            addCarMenu();
        }
        if(input.equalsIgnoreCase("x")){
            goToAdminMenu();
        }

    }



    public static void viewAllCarsMenu(){
        System.out.println("View all cars menu");
        Car.viewAllCars();
        System.out.println("Press '1' to filter the car by type (or 'x' to CANCEL");


        String input = stdin.nextLine().trim();
        while (!input.equals("1") && !input.equals("x")){
            System.err.println("Invalid choice. Please pick a valid option from the menu");
            input = stdin.nextLine().trim();
        }
        if(input.equals("1")){
            Car.viewCarsByType();
            System.out.println("Press '1' to VIEW ALL CARS ");
            System.out.println("Press 'x' to go back to ADMIN MENU");

            String input2 = stdin.nextLine().trim();
            while (!input.equals("1") && !input.equals("x")){
                System.err.println("Invalid choice. Please pick a valid option from the menu");
                input = stdin.nextLine().trim();
            }
            if(input2.equals("1")){
                viewAllCarsMenu();
            }
            if(input2.equalsIgnoreCase("x")){
                goToAdminMenu();
            }



        }
        if(input.equalsIgnoreCase("x")){
            goToAdminMenu();
        }





    }




}

