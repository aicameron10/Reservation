package com.app.reservation.model;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("customer")
    private String customer;
    @SerializedName("value")
    private Boolean value;



    public Data(String customer, Boolean value) {
        this.customer = customer;
        this.value = value;


    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }


    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

}
