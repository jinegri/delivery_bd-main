package com.bd.delivery.model;

import com.bd.delivery.utils.DeliveryException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Calendar;
import java.util.Date;

public class OrderStatus {

    protected Date startDate;

    @JsonIgnore
    protected Order order;

    public OrderStatus(){}

    public OrderStatus(Order order) {
        this.order = order;
        this.startDate = Calendar.getInstance().getTime();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    protected Order getOrder() {
        return order;
    }

    protected void setOrder(Order order) {
        this.order = order;
    }

    public boolean canAssigned(){
        return false;
    }

    public boolean canRefuse() {
        return false;
    }

    public boolean canDeliver() {
        return false;
    }

    public boolean canFinish() {
        return false;
    }

    public boolean canCancel() {
        return false;
    }

    public void assign(DeliveryMan deliveryMan) throws DeliveryException {
        throw new DeliveryException("The order can't be assigned");
    }

    public void refuse() throws DeliveryException {
        throw new DeliveryException("The order can't be refused");
    }

    public void deliver() throws DeliveryException {
        throw new DeliveryException("The order can't be delivered");
    }

    public void cancel() throws DeliveryException {
        throw new DeliveryException("The order can't be cancelled");
    }

    public void finish() throws DeliveryException {
        throw new DeliveryException("The order can't be finish");
    }
}
