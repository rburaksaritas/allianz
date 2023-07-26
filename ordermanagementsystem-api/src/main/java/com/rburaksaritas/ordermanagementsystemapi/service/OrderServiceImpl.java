package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.CustomerDTO;
import com.rburaksaritas.ordermanagementsystemapi.dto.OrderDTO;
import com.rburaksaritas.ordermanagementsystemapi.dto.ReviewDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.model.Customer;
import com.rburaksaritas.ordermanagementsystemapi.model.Order;
import com.rburaksaritas.ordermanagementsystemapi.model.Product;
import com.rburaksaritas.ordermanagementsystemapi.model.Review;
import com.rburaksaritas.ordermanagementsystemapi.repository.OrderRepository;
import com.rburaksaritas.ordermanagementsystemapi.repository.CustomerRepository;
import com.rburaksaritas.ordermanagementsystemapi.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getOrdersOfCustomer(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("customer", "id", customerId));
        List<Order> orders = orderRepository.findByCustomer(customer);
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        validateOrder(order);
        try {
            withdrawCost(order);
            Order savedOrder = orderRepository.save(order);
            return modelMapper.map(savedOrder, OrderDTO.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to save the order: " + e.getMessage());
        }
    }

    public void validateOrder(Order order) {
        // Validate objects
        int customerId = order.getCustomer().getId();
        int productId = order.getProduct().getId();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("customer", "id", customerId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));

        // Validate balance
        double balance = customer.getWalletBalance();
        double price = product.getPrice();
        int quantity = order.getQuantity();
        if (balance < price*quantity) {
            throw new IllegalArgumentException("Insufficient wallet balance: " + balance + " required: " + price*quantity);
        }
    }

    public void withdrawCost(Order order) {
        Customer customer = order.getCustomer();
        Product product = order.getProduct();
        double balance = customer.getWalletBalance();
        double totalPrice = product.getPrice() + order.getQuantity();

        try {
            customer.setWalletBalance(balance - totalPrice);
            customerRepository.save(customer);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to withdraw cost from customer:" + e.getMessage());
        }
    }

    @Override
    public OrderDTO updateOrder(Integer id, Date deliveryDate, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        try {
            // Update the fields
            order.setDeliveryDate(deliveryDate);
            order.setStatus(status);

            orderRepository.save(order);
            return modelMapper.map(order, OrderDTO.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to update the order: " + e.getMessage());
        }
    }

    @Override
    public void deleteOrder(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        try {
            orderRepository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to delete the order: " + e.getMessage());
        }
    }
}
