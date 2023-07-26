package com.rburaksaritas.ordermanagementsystemapi.controller;

import com.rburaksaritas.ordermanagementsystemapi.dto.CustomerDTO;
import com.rburaksaritas.ordermanagementsystemapi.dto.OrderDTO;
import com.rburaksaritas.ordermanagementsystemapi.dto.ProductDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.service.CustomerService;
import com.rburaksaritas.ordermanagementsystemapi.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTests {

    private OrderController orderController;
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderService = mock(OrderService.class);
        orderController = new OrderController(orderService);
    }

    @Test
    void getAllOrders_ReturnsAllOrdersSuccessfully() {
        // Arrange
        List<OrderDTO> expectedOrders = new ArrayList<>();
        expectedOrders.add(new OrderDTO(1, new CustomerDTO(), new ProductDTO(), 5, new Date(), null, "pending"));
        expectedOrders.add(new OrderDTO(2, new CustomerDTO(), new ProductDTO(), 3, new Date(), null, "delivered"));
        when(orderService.getAllOrders()).thenReturn(expectedOrders);

        // Act
        ResponseEntity<List<OrderDTO>> responseEntity = orderController.getAllOrders();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedOrders.size(), responseEntity.getBody().size());
    }

    @Test
    void getOrderById_ValidOrderId_ReturnsOrderSuccessfully() {
        // Arrange
        int orderId = 1;
        OrderDTO expectedOrder = new OrderDTO(1, new CustomerDTO(), new ProductDTO(), 5, new Date(), null, "pending");
        when(orderService.getOrderById(orderId)).thenReturn(expectedOrder);

        // Act
        ResponseEntity<OrderDTO> responseEntity = orderController.getOrderById(orderId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedOrder.getId(), responseEntity.getBody().getId());
        assertEquals(expectedOrder.getStatus(), responseEntity.getBody().getStatus());
    }

    @Test
    void getOrderById_OrderNotFound_ReturnsNotFound() {
        // Arrange
        int orderId = 1;
        when(orderService.getOrderById(orderId)).thenReturn(null);

        // Act
        ResponseEntity<OrderDTO> responseEntity = orderController.getOrderById(orderId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void getOrdersOfCustomer_ReturnsOrdersSuccessfully() {
        // Arrange
        List<OrderDTO> expectedOrders = new ArrayList<>();
        CustomerDTO customer = new CustomerDTO(1, "name", "loc", "phone", "mail", "birth", "pass", 500.00, new Date());
        expectedOrders.add(new OrderDTO(1, customer, new ProductDTO(), 5, new Date(), null, "pending"));
        expectedOrders.add(new OrderDTO(2, customer, new ProductDTO(), 3, new Date(), new Date(), "delivered"));
        when(orderService.getOrdersOfCustomer(1)).thenReturn(expectedOrders);

        // Act
        ResponseEntity<List<OrderDTO>> responseEntity = orderController.getOrderOfCustomer(1);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedOrders.size(), responseEntity.getBody().size());
    }

    @Test
    void addOrder_ValidOrder_ReturnsCreatedOrder() {
        // Arrange
        OrderDTO newOrder = new OrderDTO(null, new CustomerDTO(), new ProductDTO(), 5, new Date(), null, "pending");
        OrderDTO expectedSavedOrder = new OrderDTO(1, new CustomerDTO(), new ProductDTO(), 5, new Date(), null, "pending");
        when(orderService.saveOrder(newOrder)).thenReturn(expectedSavedOrder);

        // Act
        ResponseEntity<OrderDTO> responseEntity = orderController.addOrder(newOrder);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedSavedOrder.getId(), responseEntity.getBody().getId());
        assertEquals(expectedSavedOrder.getStatus(), responseEntity.getBody().getStatus());
    }

    @Test
    void updateOrder_ValidOrder_ReturnsUpdatedOrder() {
        // Arrange
        int orderId = 1;
        Date updatedDeliveryDate = new Date();
        String updatedStatus = "delivered";
        OrderDTO updatedOrder = new OrderDTO(orderId, new CustomerDTO(), new ProductDTO(), 5, new Date(), updatedDeliveryDate, updatedStatus);
        when(orderService.updateOrder(orderId, updatedDeliveryDate, updatedStatus))
                .thenReturn(updatedOrder);

        // Act
        ResponseEntity<OrderDTO> responseEntity = orderController.updateOrder(orderId, updatedDeliveryDate, updatedStatus);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updatedOrder.getId(), responseEntity.getBody().getId());
        assertEquals(updatedOrder.getStatus(), responseEntity.getBody().getStatus());
    }

    @Test
    void updateOrder_OrderNotFound_ReturnsNotFound() {
        // Arrange
        int orderId = 1;
        Date updatedDeliveryDate = new Date();
        String updatedStatus = "delivered";
        when(orderService.updateOrder(orderId, updatedDeliveryDate, updatedStatus))
                .thenThrow(new ResourceNotFoundException("Order", "id", orderId));

        // Act
        ResponseEntity<OrderDTO> responseEntity = orderController.updateOrder(orderId, updatedDeliveryDate, updatedStatus);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void deleteOrder_ValidOrderId_ReturnsNoContent() {
        // Arrange
        int orderId = 1;

        // Act
        ResponseEntity<Void> responseEntity = orderController.deleteOrder(orderId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteOrder_OrderNotFound_ReturnsNotFound() {
        // Arrange
        int orderId = 1;
        doThrow(new ResourceNotFoundException("Order", "id", orderId)).when(orderService).deleteOrder(orderId);

        // Act
        ResponseEntity<Void> responseEntity = orderController.deleteOrder(orderId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
