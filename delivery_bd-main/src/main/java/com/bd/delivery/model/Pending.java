package com.bd.delivery.model;

import com.bd.delivery.utils.DeliveryException;

public class Pending extends OrderStatus {

    public Pending() {
    }

    public Pending(Order order) {
        super(order);
    }

    @Override
    public boolean canAssigned() {
        return true;
    }

    @Override
    public boolean canCancel() {
        return true;
    }

    @Override
    public void assign(DeliveryMan deliveryMan) throws DeliveryException {
        if (this.canAssigned()) {
            deliveryMan.addOrder(this.order);
            deliveryMan.setFree(false);
            this.order.setDeliveryMan(deliveryMan);
            this.order.setOrderStatus(new Assigned(this.order));
        } else {
            throw new DeliveryException("The order can't be assigned");
        }
    }

    @Override
    public void cancel() throws DeliveryException {
        if (this.canCancel()) {
            this.order.setOrderStatus(new Cancelled(this.order));
            this.order.getClient().addScore(-1);
        } else {
            throw new DeliveryException("The order can't be cancelled");
        }
    }


}
