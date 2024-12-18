package org.example;

import java.util.Scanner;

public class Menu {
    private static Scanner stdin =new Scanner(System.in);
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

    private static  void goToAdminLogin() {
        System.out.println("Please select one of the following options:");

        System.out.println("1. View all Bookings");
        System.out.println("2. Add a Car");
        System.out.println("3. Suspend a Car");
        System.out.println("4. Suspend a Car");
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
            goToViewAllBooking();
        }else if(choice.equals("2")){
            goToAddCar();
        }else if(choice.equals("3")){
            goToSuspendACar();
        }else if(choice.equals("3")){
            goToUnsuspendACar();
        }else if(choice.equals("5")){
            goToBookACarForServiceing();
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
        System.out.println("Welcome to Customer Login");

//        if the login is true
        gotToCustomerMenu();
    }

    public static void gotToCustomerMenu(){
        System.out.println("Welcome to Customer Menu");
        System.out.println("Please choose one of the following options:");
        System.out.println("1. Create a rental booking");
        System.out.println("2. Create a view a rental booking");
        System.out.println("3. Exit");

        String choice = stdin.nextLine().trim();
        while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3")){
            System.err.println("Invalid choice. Please pick an option from the menu");
            choice = stdin.nextLine().trim();
        }

        if (choice.equals("1")){
            goToCreatRental();
        }else if(choice.equals("2")){
            goToViewRentalBooking();
        } else if(choice.equals("3")){
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



}

