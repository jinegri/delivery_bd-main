package com.bd.delivery.model;

import com.bd.delivery.utils.DeliveryException;

public class Sent extends OrderStatus{

    public Sent() {}

    public Sent(Order order){
        super(order);
    }

    public boolean canFinish() {
        return true;
    }

    public void finish() throws DeliveryException {
        this.order.setOrderStatus(new Delivered(this.order));
        this.order.getDeliveryMan().addScore(1);
        this.order.getDeliveryMan().addNumberOfSuccessfulOrders();
        this.order.getClient().addScore(1);
        this.order.getDeliveryMan().setFree(true);
    }
}
