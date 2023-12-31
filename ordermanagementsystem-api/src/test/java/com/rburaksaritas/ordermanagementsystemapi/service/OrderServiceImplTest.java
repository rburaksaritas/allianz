package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.CustomerDTO;
import com.rburaksaritas.ordermanagementsystemapi.dto.OrderDTO;
import com.rburaksaritas.ordermanagementsystemapi.dto.ProductDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.model.Customer;
import com.rburaksaritas.ordermanagementsystemapi.model.Order;
import com.rburaksaritas.ordermanagementsystemapi.model.Product;
import com.rburaksaritas.ordermanagementsystemapi.repository.CustomerRepository;
import com.rburaksaritas.ordermanagementsystemapi.repository.OrderRepository;
import com.rburaksaritas.ordermanagementsystemapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTests {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private ModelMapper modelMapper;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        orderRepository = mock(OrderRepository.class);
        modelMapper = new ModelMapper();
        customerRepository = mock(CustomerRepository.class);
        productRepository = mock(ProductRepository.class);

        orderService = new OrderServiceImpl(orderRepository, modelMapper, customerRepository, productRepository);
    }

    // OrderService Tests
    @Test
    public void OrderService_GetAll_ReturnsAllOrders() {
        // Arrange
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, new Customer(), new Product(), 2, new Date(), null, "Pending"));
        orders.add(new Order(2, new Customer(), new Product(), 1, new Date(), null, "Delivered"));
        when(orderRepository.findAll()).thenReturn(orders);

        // Act
        List<OrderDTO> orderDTOList = orderService.getAllOrders();

        // Assert
        assertNotNull(orderDTOList);
        assertEquals(orders.size(), orderDTOList.size());
    }

    @Test
    public void OrderService_GetById_ValidOrderReturnsOrder() {
        // Arrange
        int orderId = 1;
        Order order = new Order(orderId, new Customer(), new Product(), 2, new Date(), null, "Pending");
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        OrderDTO orderDTO = orderService.getOrderById(orderId);

        // Assert
        assertNotNull(orderDTO);
        assertEquals(order.getId(), orderDTO.getId());
        assertEquals(order.getQuantity(), orderDTO.getQuantity());
    }

    @Test
    public void OrderService_GetById_OrderNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int orderId = 1;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(orderId));
    }

    @Test
    public void OrderService_GetOrdersOfCustomer_ReturnsOrders() {
        // Arrange
        List<Order> ordersOfCustomer = new ArrayList<>();
        Integer customerId = 1;
        Customer customer = new Customer(customerId, "name", "loc", "phone", "mail", "birth", "pass", 500.00, new Date());
        ordersOfCustomer.add(new Order(1, customer, new Product(), 2, new Date(), null, "Pending"));
        ordersOfCustomer.add(new Order(2, customer, new Product(), 1, new Date(), null, "Delivered"));

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(orderRepository.findByCustomer(customer)).thenReturn(ordersOfCustomer);

        // Act
        List<OrderDTO> orderDTOList = orderService.getOrdersOfCustomer(customerId);

        // Assert
        assertNotNull(orderDTOList);
        assertEquals(ordersOfCustomer.size(), orderDTOList.size());
    }

    @Test
    public void OrderService_SaveOrder_ValidOrderDTO_ReturnsSavedOrderDTO() {
        // Arrange
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setQuantity(2);

        // Create and set Customer and Product
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1); // Ensure this id exists in the customerRepository mock
        customerDTO.setWalletBalance(100.0); // Balance enough for the order
        orderDTO.setCustomer(customerDTO);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1); // Ensure this id exists in the productRepository mock
        productDTO.setPrice(20.0); // Price per product
        orderDTO.setProduct(productDTO);

        Order order = modelMapper.map(orderDTO, Order.class);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Mock repository findById methods
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        Product product = modelMapper.map(productDTO, Product.class);
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // Act
        OrderDTO savedOrderDTO = orderService.saveOrder(orderDTO);

        // Assert
        assertNotNull(savedOrderDTO);
        assertEquals(order.getQuantity(), savedOrderDTO.getQuantity());
    }


    @Test
    public void OrderService_UpdateOrder_ValidOrderIdAndData_ReturnsUpdatedOrderDTO() {
        // Arrange
        int orderId = 1;
        Date newDeliveryDate = new Date();
        String newStatus = "Delivered";
        Order order = new Order(orderId, new Customer(), new Product(), 2, new Date(), null, "Pending");
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        OrderDTO updatedOrderDTO = orderService.updateOrder(orderId, newDeliveryDate, newStatus);

        // Assert
        assertNotNull(updatedOrderDTO);
        assertEquals(order.getId(), updatedOrderDTO.getId());
        assertEquals(newDeliveryDate, updatedOrderDTO.getDeliveryDate());
        assertEquals(newStatus, updatedOrderDTO.getStatus());
    }

    @Test
    public void OrderService_UpdateOrder_OrderNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int orderId = 1;
        Date newDeliveryDate = new Date();
        String newStatus = "Delivered";
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> orderService.updateOrder(orderId, newDeliveryDate, newStatus));
    }

    @Test
    public void OrderService_Delete_ValidOrderId_DeletesOrder() {
        // Arrange
        int orderId = 1;
        Order order = new Order(orderId, new Customer(), new Product(), 2, new Date(), null, "Pending");
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        assertDoesNotThrow(() -> orderService.deleteOrder(orderId));

        // Assert
        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    public void OrderService_Delete_OrderNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int orderId = 1;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> orderService.deleteOrder(orderId));
    }
}
