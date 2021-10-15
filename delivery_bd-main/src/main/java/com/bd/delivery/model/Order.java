package com.bd.delivery.model;

import com.bd.delivery.utils.DeliveryException;

import java.util.Date;

public class Order {

    private long number;

    private static long nextNumber = 1;

    private Date dateOfOrder;

    private String address;

    private String comments;

    private float coordX;

    private float coordY;

    private float priceProducts;

    private Client client;

    private DeliveryMan deliveryMan;

    private OrderStatus orderStatus;

    public Order(){}

    public Order(Date dateOfOrder, String address, String comments, float coordX, float coordY,  float priceProducts, Client client){
        this.number = nextNumber++;
        this.dateOfOrder = dateOfOrder;
        this.address = address;
        this.comments = comments;
        this.coordX = coordX;
        this.coordY = coordY;
        this.priceProducts = priceProducts;
        this.client = client;
        this.deliveryMan = null;
        this.orderStatus = new Pending(this);
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public float getCoordX() {
        return coordX;
    }

    public void setCoordX(float coordX) {
        this.coordX = coordX;
    }

    public float getCoordY() {
        return coordY;
    }

    public void setCoordY(float coordY) {
        this.coordY = coordY;
    }

    public float getPriceProducts() {
        return priceProducts;
    }

    public void setPriceProducts(float priceProducts) {
        this.priceProducts = priceProducts;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public DeliveryMan getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(DeliveryMan deliveryMan) {
        this.deliveryMan = deliveryMan;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public boolean canAssigned(){
        return this.orderStatus.canAssigned();
    }

    public boolean canRefuse() {
        return this.orderStatus.canRefuse();
    }

    public boolean canDeliver() {
        return this.orderStatus.canDeliver();
    }

    public boolean canFinish() {
        return this.orderStatus.canFinish();
    }

    public boolean canCancel() {
        return this.orderStatus.canCancel();
    }

    public void assign(DeliveryMan deliveryMan) throws DeliveryException {
        this.orderStatus.assign(deliveryMan);
    }

    public void refuse() throws DeliveryException {
        this.orderStatus.refuse();
    }

    public void deliver() throws DeliveryException {
        this.orderStatus.deliver();
    }

    public void cancel() throws DeliveryException {
        this.orderStatus.cancel();
    }

    public void finish() throws DeliveryException {
        this.orderStatus.finish();
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
