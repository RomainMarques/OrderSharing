package org.example.ordersharing.controller;

import org.example.ordersharing.model.Order;
import org.example.ordersharing.repository.OrderRepository;

public class OrderController {
    public static boolean isOrderPaid(Order order) {
        return order.getToPay() == 0;
    }
    public static String payOrder(Order order, double amount, OrderRepository orderRepository) {
        if (isOrderPaid(order)) {
            return "Order is already paid";
        }
        if (amount < 0 || amount > order.getToPay()) {
            return "Error in the amount specified";
        }
        double result = order.getToPay() - amount;
        order.setToPay(result);
        orderRepository.save(order);
        if(result == 0) {
            return "Order paid";
        }
        return "Order partially paid, it remains " + result + " to pay";
    }
}
