package com.bd.delivery.model;

import com.bd.delivery.utils.DeliveryException;

public class Assigned extends OrderStatus{

    public Assigned() {}

    public Assigned(Order order){
        super(order);
    }

    public boolean canRefuse() {
        return true;
    }

    public boolean canDeliver() {
        return true;
    }

    public boolean canCancel() {
        return true;
    }

    public void deliver() throws DeliveryException {
        if(this.canDeliver()) {
            this.order.setOrderStatus(new Sent(this.order));
            this.order.getDeliveryMan().deleteOrder(order);
        } else {
            throw new DeliveryException("The order can't be delivered");
        }
    }

    public void refuse() throws DeliveryException {
        if(this.canRefuse()) {
            this.order.setOrderStatus(new Cancelled(this.order));
            this.order.getDeliveryMan().addScore(-2);
            this.order.getDeliveryMan().deleteOrder(order);
            this.order.getDeliveryMan().setFree(true);
        } else {
            throw new DeliveryException("The order can't be refused");
        }
    }

    public void cancel() throws DeliveryException {
        if(this.canCancel()){
            this.order.setOrderStatus(new Cancelled(this.order));
            this.order.getDeliveryMan().deleteOrder(order);
            this.order.getClient().addScore(-2);
            this.order.getDeliveryMan().setFree(true);
        } else {
            throw new DeliveryException("The order can't be cancelled");
        }
    }
}
