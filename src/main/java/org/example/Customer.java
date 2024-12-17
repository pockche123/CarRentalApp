package org.example;

public class Customer {
    private int customer_id;


    public Customer(int customer_id) {}
    public Customer(){};

    public int getCustomer_id() {
        return customer_id;
    };
    public void setCustomer_id(int id){
        this.customer_id = id;
    }
}
