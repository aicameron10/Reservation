package com.app.reservation.model;

import com.google.gson.annotations.SerializedName;

public class Customers {

    @SerializedName("id")
    private int id;
    @SerializedName("customerFirstName")
    private String customerFirstName;
    @SerializedName("customerLastName")
    private String customerLastName;


    public Customers(Integer id, String customerFirstName, String customerLastName) {
        this.id = id;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }


}
