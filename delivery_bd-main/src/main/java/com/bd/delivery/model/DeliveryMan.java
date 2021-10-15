package com.bd.delivery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DeliveryMan extends User{

    private int numberOfSuccessfulOrders;

    private boolean free;

    private Date dateOfAdmission;

    @JsonIgnore
    private List<Order> actualOrders;

    public DeliveryMan(){}

    public DeliveryMan(String name, String email, String username, String password, Date dateOfBirth) {
        super(name, email, username, password, dateOfBirth);
        this.numberOfSuccessfulOrders = 0;
        this.free = true;
        this.dateOfAdmission = Calendar.getInstance().getTime();
        this.actualOrders = new ArrayList<>();
    }

    public int getNumberOfSuccessfulOrders() {
        return numberOfSuccessfulOrders;
    }

    public void setNumberOfSuccessfulOrders(int numberOfSuccessfulOrders) {
        this.numberOfSuccessfulOrders = numberOfSuccessfulOrders;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public Date getDateOfAdmission() {
        return dateOfAdmission;
    }

    public void setDateOfAdmission(Date dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }

    public void addNumberOfSuccessfulOrders(){ this.numberOfSuccessfulOrders++; }

    public List<Order> getActualOrders() {
        return actualOrders;
    }

    public void setActualOrders(List<Order> actualOrders) {
        this.actualOrders = actualOrders;
    }

    public void addOrder(Order order) {
        this.actualOrders.add(order);
    }

    public void deleteOrder(Order order) { this.actualOrders.remove(order); }

    public boolean finishOrder(Order order){
        return this.actualOrders.remove(order);
    }

}
