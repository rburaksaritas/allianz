package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.OrderDTO;

import java.util.Date;
import java.util.List;

public interface OrderService {
    List<OrderDTO> getAllOrders();
    OrderDTO getOrderById(Integer id);
    List <OrderDTO> getOrdersOfCustomer(Integer customerId);
    OrderDTO saveOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(Integer id, Date deliveryDate, String updatedStatus);
    void deleteOrder(Integer id);
}
