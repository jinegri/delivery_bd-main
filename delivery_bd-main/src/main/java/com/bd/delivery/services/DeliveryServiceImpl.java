package com.bd.delivery.services;

import com.bd.delivery.model.*;
import com.bd.delivery.utils.DeliveryException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Override
    public Client newClient(Client client) {
        DeliveryRoot.addClient(client);
        return client;
    }

    @Override
    public Client editClient(String username, Client client) throws DeliveryException {
        Client realClient = this.getClientInfo(username);
        return (Client) this.editUser(realClient, client);
    }

    @Override
    public void desactiveClient(String username) {
        Client realClient = this.getClientInfo(username);
        if (realClient.isActive()) {
            realClient.setActive(false);
        }
    }

    @Override
    public Client getClientInfo(String username) {
        return DeliveryRoot.getClientList().stream().filter(client1 ->
                username.equals(client1.getUsername()) && client1.isActive()).findAny().orElse(null);
    }

    @Override
    public List<Order> getClientOrders(String username) {
        Client client = this.getClientInfo(username);
        return (client != null) ? client.getOrders():null;
    }

    @Override
    public DeliveryMan newDeliveryMan(DeliveryMan deliveryMan) {
        DeliveryRoot.addDeliveryMan(deliveryMan);
        return deliveryMan;
    }

    @Override
    public DeliveryMan editDeliveryMan(String username, DeliveryMan deliveryMan) throws DeliveryException {
        DeliveryMan realDeliveryMan = this.getDeliveryManInfo(username);
        return (DeliveryMan) this.editUser(realDeliveryMan, deliveryMan);
    }

    private User editUser(User realUser, User user) throws DeliveryException {
        if (user != null) {
            realUser.setName(user.getName());
            realUser.setEmail(user.getEmail());
            realUser.setDateOfBirth(user.getDateOfBirth());
            realUser.setPassword(user.getPassword());
        } else {
            throw new DeliveryException("The client with username does not exist");
        }
        return realUser;
    }

    @Override
    public void desactiveDeliveryMan(String username) {
        DeliveryMan deliveryMan = this.getDeliveryManInfo(username);
        if (deliveryMan.isActive()) {
            deliveryMan.setActive(false);
        }
    }

    @Override
    public DeliveryMan getDeliveryManInfo(String username) {
        return DeliveryRoot.getDeliveryManList().stream().filter(dm ->
                username.equals(dm.getUsername()) && dm.isActive()).findAny().orElse(null);
    }

    @Override
    public Order newOrderPending(Order order) {
        order.getClient().addOrder(order);
        DeliveryRoot.addOrder(order);
        return order;
    }

    @Override
    public Order getOrderinfo(long number) {
        return DeliveryRoot.getOrderList().stream().filter(order ->
                order.getNumber() == number).findAny().orElse(null);
    }

    @Override
    public boolean assignOrder(long number) {
        DeliveryMan deliveryMan = DeliveryRoot.getDeliveryManList().stream().filter(dm ->
                dm.isFree() && dm.isActive()).findAny().orElse(null);
        if (deliveryMan != null) {
            try {
                this.getOrderinfo(number).assign(deliveryMan);
                return true;
            } catch (Exception e) {
            }
        }
        return false;
    }

    @Override
    public List<Order> getAssignedOrders(String username) {
        return this.getDeliveryManInfo(username).getActualOrders();
    }

    @Override
    public void acceptOrder(long number) throws DeliveryException {
        this.getOrderinfo(number).deliver();
    }

    @Override
    public void refuseOrder(long number) throws DeliveryException{
            this.getOrderinfo(number).refuse();
    }

    @Override
    public void cancelOrder(long number) throws DeliveryException {
        this.getOrderinfo(number).cancel();
    }

    @Override
    public void finishOrder(long number) throws DeliveryException {
        this.getOrderinfo(number).finish();
    }
}
