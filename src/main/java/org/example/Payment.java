package org.example;


import java.sql.SQLOutput;
import java.util.Scanner;

public class Payment {
    private int payment_id;
    private String card_type;
    private String card_number;
    private String expiry_date;
    private String cvv;
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
    public String getCvv() {
        return cvv;
    }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }



    public static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void createPayment() {

        Payment payment = new Payment();

        System.out.println("Please enter a card_type: ");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");

        String card_type = stdin.nextLine();

        while(!card_type.trim().equals("1") && !card_type.trim().equals("2")){
            System.err.println("Please enter a valid card type.");
            card_type = stdin.nextLine();
        }
        System.out.println("Please enter a card_number next: ");
        String card_number = stdin.nextLine();

        while(!isNumeric(card_number) && card_number.trim().length() != 16){

            System.err.println("Card number has to be number with 16 digits. Currently, the length is " + card_number.trim().length());

            card_number = stdin.nextLine();

        }



    }











}