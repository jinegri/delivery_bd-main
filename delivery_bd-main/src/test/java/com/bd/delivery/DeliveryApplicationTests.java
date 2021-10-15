package com.bd.delivery;

import com.bd.delivery.model.*;
import com.bd.delivery.services.DeliveryService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@SpringBootTest
class DeliveryApplicationTests {

	@Autowired
	private DeliveryService service;

	@BeforeEach
	void before(){
		DeliveryRoot.reset();
	}

	@Test
	void creationTest() {
		Client client1 = new Client("Cliente Uno", "clienteuno@mail.com", "clienteuno", "1234", new Date(1990, 3, 3));
		this.service.newClient(client1);
		DeliveryMan deliveryMan1 = new DeliveryMan("Dm Uno", "dmuno@mail.com", "dmuno", "1234", new Date(1995, 3, 6));
		this.service.newDeliveryMan(deliveryMan1);

		assertEquals(DeliveryRoot.getClientList().size(), 1);
		assertEquals(DeliveryRoot.getDeliveryManList().size(), 1);
		assertTrue(DeliveryRoot.getClientList().get(0) == client1);
		assertTrue(DeliveryRoot.getDeliveryManList().get(0) == deliveryMan1);
	}

	@Test
	void getTest(){
		Client client1 = new Client("Cliente Uno", "clienteuno@mail.com", "clienteuno", "1234", new Date(1990, 3, 3));
		this.service.newClient(client1);
		DeliveryMan deliveryMan1 = new DeliveryMan("Dm Uno", "dmuno@mail.com", "dmuno", "1234", new Date(1995, 3, 6));
		this.service.newDeliveryMan(deliveryMan1);

		assertTrue(this.service.getClientInfo("clienteuno") == client1);
		assertTrue(this.service.getDeliveryManInfo("dmuno") == deliveryMan1);
	}

	@Test
	void editTest(){
		Client client1 = new Client("Cliente Uno", "clienteuno@mail.com", "clienteuno", "1234", new Date(1990, 3, 3));
		this.service.newClient(client1);
		DeliveryMan deliveryMan1 = new DeliveryMan("Dm Uno", "dmuno@mail.com", "dmuno", "1234", new Date(1995, 3, 6));
		this.service.newDeliveryMan(deliveryMan1);

		client1.setName("Cliente Unooo");
		deliveryMan1.setEmail("dm1@mail.com");
		try{
			this.service.editClient(client1.getUsername(), client1);
			this.service.editDeliveryMan(deliveryMan1.getUsername(), deliveryMan1);
		} catch (Exception e){
			assertTrue(false);
		}

		assertEquals(this.service.getClientInfo("clienteuno").getName(), "Cliente Unooo");
		assertEquals(this.service.getDeliveryManInfo("dmuno").getEmail(), "dm1@mail.com");
	}

	@Test
	void desactiveTest(){
		Client client1 = new Client("Cliente Uno", "clienteuno@mail.com", "clienteuno", "1234", new Date(1990, 3, 3));
		this.service.newClient(client1);
		DeliveryMan deliveryMan1 = new DeliveryMan("Dm Uno", "dmuno@mail.com", "dmuno", "1234", new Date(1995, 3, 6));
		this.service.newDeliveryMan(deliveryMan1);

		this.service.desactiveClient("clienteuno");
		this.service.desactiveDeliveryMan("dmuno");

		assertFalse(this.service.getClientInfo("clienteuno").isActive());
		assertFalse(this.service.getDeliveryManInfo("dmuno").isActive());
	}

	@Test
	void orderTest() {
		Client client1 = new Client("Cliente Uno", "clienteuno@mail.com", "clienteuno", "1234", new Date(1990, 3, 3));
		this.service.newClient(client1);
		Order order = new Order(Calendar.getInstance().getTime(), "Calle 1 n1", "Unos productos", 10,10, 100, client1);
		long numberOrder = order.getNumber();
		this.service.newOrderPending(order);

		assertEquals(order.getOrderStatus().getClass().getName(), "com.bd.delivery.model.Pending");
		assertTrue(this.service.getOrderinfo(numberOrder) == order);

		DeliveryMan deliveryMan1 = new DeliveryMan("Dm Uno", "dmuno@mail.com", "dmuno", "1234", new Date(1995, 3, 6));
		this.service.newDeliveryMan(deliveryMan1);
		this.service.assignOrder(numberOrder);

		assertTrue(this.service.getOrderinfo(numberOrder).getDeliveryMan() != null);
		assertEquals(this.service.getOrderinfo(numberOrder).getDeliveryMan().getUsername(), "dmuno");
		assertFalse(deliveryMan1.isFree());
		assertEquals(order.getOrderStatus().getClass().getName(), "com.bd.delivery.model.Assigned");
		assertEquals(this.service.getAssignedOrders(deliveryMan1.getUsername()).size(), 1);

		try{
			this.service.acceptOrder(numberOrder);
		}catch (Exception e) {
			assertTrue(false);
		}

		assertEquals(order.getOrderStatus().getClass().getName(), "com.bd.delivery.model.Sent");
		assertEquals(this.service.getAssignedOrders(deliveryMan1.getUsername()).size(), 0);

		try{
			this.service.finishOrder(numberOrder);
		} catch (Exception e) {
			assertTrue(false);
		}

		assertEquals(order.getOrderStatus().getClass().getName(), "com.bd.delivery.model.Delivered");
		assertEquals(deliveryMan1.getNumberOfSuccessfulOrders(), 1);
		assertEquals(deliveryMan1.getScore(), 1);
		assertEquals(client1.getScore(), 1);
	}

	@Test
	void cancel1Test(){
		Client client1 = new Client("Cliente Uno", "clienteuno@mail.com", "clienteuno", "1234", new Date(1990, 3, 3));
		this.service.newClient(client1);
		Order order = new Order(Calendar.getInstance().getTime(), "Calle 1 n1", "Unos productos", 10,10, 100, client1);
		long numberOrder = order.getNumber();
		this.service.newOrderPending(order);

		assertEquals(order.getOrderStatus().getClass().getName(), "com.bd.delivery.model.Pending");
		assertTrue(this.service.getOrderinfo(numberOrder) == order);

		try{
			this.service.cancelOrder(numberOrder);
		}catch (Exception e) {
			assertTrue(false);
		}

		assertEquals(order.getOrderStatus().getClass().getName(), "com.bd.delivery.model.Cancelled");
		assertEquals(client1.getScore(), -1);
	}

	@Test
	void cancel2Test(){
		Client client1 = new Client("Cliente Uno", "clienteuno@mail.com", "clienteuno", "1234", new Date(1990, 3, 3));
		this.service.newClient(client1);
		Order order = new Order(Calendar.getInstance().getTime(), "Calle 1 n1", "Unos productos", 10,10, 100, client1);
		long numberOrder = order.getNumber();
		this.service.newOrderPending(order);

		assertEquals(order.getOrderStatus().getClass().getName(), "com.bd.delivery.model.Pending");
		assertTrue(this.service.getOrderinfo(numberOrder) == order);

		DeliveryMan deliveryMan1 = new DeliveryMan("Dm Uno", "dmuno@mail.com", "dmuno", "1234", new Date(1995, 3, 6));
		this.service.newDeliveryMan(deliveryMan1);
		this.service.assignOrder(numberOrder);

		assertTrue(this.service.getOrderinfo(numberOrder).getDeliveryMan() != null);
		assertEquals(this.service.getOrderinfo(numberOrder).getDeliveryMan().getUsername(), "dmuno");
		assertFalse(deliveryMan1.isFree());
		assertEquals(order.getOrderStatus().getClass().getName(), "com.bd.delivery.model.Assigned");
		assertEquals(this.service.getAssignedOrders(deliveryMan1.getUsername()).size(), 1);

		try{
			this.service.cancelOrder(numberOrder);
		}catch (Exception e) {
			assertTrue(false);
		}

		assertEquals(order.getOrderStatus().getClass().getName(), "com.bd.delivery.model.Cancelled");
		assertEquals(client1.getScore(), -2);
		assertEquals(deliveryMan1.getNumberOfSuccessfulOrders(), 0);
		assertEquals(deliveryMan1.getActualOrders().size(), 0);
	}

	@Test
	void refuseTest(){
		Client client1 = new Client("Cliente Uno", "clienteuno@mail.com", "clienteuno", "1234", new Date(1990, 3, 3));
		this.service.newClient(client1);
		Order order = new Order(Calendar.getInstance().getTime(), "Calle 1 n1", "Unos productos", 10,10, 100, client1);
		long numberOrder = order.getNumber();
		this.service.newOrderPending(order);

		assertEquals(order.getOrderStatus().getClass().getName(), "com.bd.delivery.model.Pending");
		assertTrue(this.service.getOrderinfo(numberOrder) == order);

		DeliveryMan deliveryMan1 = new DeliveryMan("Dm Uno", "dmuno@mail.com", "dmuno", "1234", new Date(1995, 3, 6));
		this.service.newDeliveryMan(deliveryMan1);
		this.service.assignOrder(numberOrder);

		assertTrue(this.service.getOrderinfo(numberOrder).getDeliveryMan() != null);
		assertEquals(this.service.getOrderinfo(numberOrder).getDeliveryMan().getUsername(), "dmuno");
		assertFalse(deliveryMan1.isFree());
		assertEquals(order.getOrderStatus().getClass().getName(), "com.bd.delivery.model.Assigned");
		assertEquals(this.service.getAssignedOrders(deliveryMan1.getUsername()).size(), 1);

		try{
			this.service.refuseOrder(numberOrder);
		}catch (Exception e) {
			assertTrue(false);
		}

		assertEquals(order.getOrderStatus().getClass().getName(), "com.bd.delivery.model.Cancelled");
		assertEquals(deliveryMan1.getScore(), -2);
		assertEquals(deliveryMan1.getNumberOfSuccessfulOrders(), 0);
		assertEquals(deliveryMan1.getActualOrders().size(), 0);
	}

}
