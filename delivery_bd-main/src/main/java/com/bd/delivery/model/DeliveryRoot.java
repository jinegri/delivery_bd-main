package com.bd.delivery.model;

import java.util.ArrayList;
import java.util.List;

public final class DeliveryRoot {

    private static List<Client> clientList = new ArrayList<>();

    private static List<DeliveryMan> deliveryManList = new ArrayList<>();

    private static List<Order> orderList = new ArrayList<>();

    public static List<Client> getClientList() {
        return clientList;
    }

    public static void addClient(Client client) {
        clientList.add(client);
    }

    public static List<DeliveryMan> getDeliveryManList() {
        return deliveryManList;
    }

    public static void addDeliveryMan(DeliveryMan deliveryMan) {
        deliveryManList.add(deliveryMan);
    }

    public static List<Order> getOrderList() { return orderList; }

    public static void addOrder(Order order){ orderList.add(order); }

    public static void reset() {
        clientList = new ArrayList<>();
        deliveryManList = new ArrayList<>();
        orderList = new ArrayList<>();
    }
}
