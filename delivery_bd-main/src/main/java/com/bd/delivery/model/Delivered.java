package com.bd.delivery.model;

public class Delivered extends OrderStatus{

    public Delivered() {}

    public Delivered(Order order){
        super(order);
    }
}
