package org.example;


import java.sql.*;
import java.util.Scanner;

public class Payment {
    private int payment_id;
    private String card_type;
    private String card_number;
    private String expiry_date;
    private String cvc;
    public static Scanner stdin = new Scanner(System.in);
    public Payment(){};

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }


    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }
    public String getCvc() {
        return cvc;
    }
    public void setCvv(String cvc) {
        this.cvc = cvc;
    }



    public static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validMonth(String month) {
        if (month == null || !isNumeric(month)) {
            return false; // Null or non-numeric input is invalid
        }

        try {
            int monthNumber = Integer.parseInt(month);
            return monthNumber >= 1 && monthNumber <= 12;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validYear(String month) {
        if (month == null || !isNumeric(month)) {
            return false; // Null or non-numeric input is invalid
        }

        try {
            int monthNumber = Integer.parseInt(month);
            return monthNumber >= 1 && monthNumber < 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }




    public static int paymentValidation() {
        System.out.println("Please enter the payment details: ");
        System.out.println("Please enter a card_type: ");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");

        String card_type = stdin.nextLine();

        while(!card_type.trim().equals("1") && !card_type.trim().equals("2")){
            System.err.println("Please enter a valid card type.");
            card_type = stdin.nextLine();
        }
        System.out.println("Please enter a card_number next (16 digits) : ");
        String card_number = stdin.nextLine();

        while(!isNumeric(card_number) || card_number.trim().length() != 16 || card_number.charAt(0) == '0'){
            System.err.println("Card number has to be a valid number with 16 digits. Currently, the length is " + card_number.trim().length());
            card_number = stdin.nextLine();

        }
        System.out.println("Please enter a expiry date in format MMYY: ");
        String expiry_date = stdin.nextLine();


        while(!validMonth(expiry_date.substring(0,2)) || !validYear(expiry_date.substring(2))){
            if(!validMonth(expiry_date.substring(0,2)) && !validYear(expiry_date.substring(2))) {
                System.err.println("Please enter a valid expiry date in format MMYY: ");
            } else if(!validMonth(expiry_date.substring(0,2))) {
                System.err.println("Please enter a valid month");
            }  else if(!validYear(expiry_date.substring(2))) {
                System.err.println("Please enter a valid year");
            }
            expiry_date = stdin.nextLine();


        }

        System.out.println("Please enter a CVC number: ");
        String cvc = stdin.nextLine();

        while(!isNumeric(cvc) || (cvc.length()!= 3 && cvc.length()!= 4)) {
            System.err.println("Invalid CVC number");
            cvc = stdin.nextLine();

        }
        Payment payment = new Payment();
        payment.setCard_type(card_type.equals("1") ? "Credit Card" : "Debit Card");
        payment.setCard_number(card_number);
        payment.setExpiry_date(expiry_date);
        payment.setCvv(cvc);
        return createPayment(payment);
    }

    public static int createPayment(Payment payment) {
        String insertQuery = "INSERT INTO payments (card_type, card_number, expiry_date, cvc) VALUES (?, ?, ?, ?)";
        String selectQuery = "SELECT payment_id FROM payments WHERE card_number = ?";

        try (Connection conn = Main.establishConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {

            // Insert payment details into the database
            insertStmt.setString(1, payment.getCard_type());
            insertStmt.setString(2, payment.getCard_number());
            insertStmt.setString(3, payment.getExpiry_date());
            insertStmt.setString(4, payment.getCvc());
            insertStmt.executeUpdate();

            // Retrieve the generated payment ID
            selectStmt.setString(1, payment.getCard_number());
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    payment.setPayment_id(rs.getInt("payment_id"));
                    return rs.getInt("payment_id");
                } else {
                    throw new RuntimeException("Failed to retrieve payment ID.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }
















}