package com.bd.delivery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Client extends User{

    private Date dateOfRegister;

    @JsonIgnore
    private List<Order> orders;

    public Client(){}

    public Client(String name, String email, String username, String password, Date dateOfBirth) {
        super(name, email, username, password, dateOfBirth);
        this.dateOfRegister = Calendar.getInstance().getTime();
        this.orders = new ArrayList<>();
    }

    public Date getDateOfRegister() {
        return dateOfRegister;
    }

    public void setDateOfRegister(Date dateOfRegister) {
        this.dateOfRegister = dateOfRegister;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) { this.orders.add(order); }
}
