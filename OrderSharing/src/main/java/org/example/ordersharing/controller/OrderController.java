package org.example.ordersharing.controller;

import org.example.ordersharing.model.Order;
import org.example.ordersharing.repository.OrderRepository;
import org.example.ordersharing.sender.Notification;

public class OrderController {
    public static boolean isOrderPaid(Order order) {
        return order.getToPay() == 0;
    }
    public static String payOrder(Order order, double amount, OrderRepository orderRepository, Notification notification) {
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
            notification.sendNotification("Order fully paid");
            return "Order paid";
        }
        notification.sendNotification("Order partially paid, it remains " + result + " to pay");
        return "Order partially paid, it remains " + result + " to pay";
    }
}
