package org.example.ordersharing.controller;

import org.example.ordersharing.model.SharedOrder;
import org.example.ordersharing.repository.SharedOrderRepository;
import org.example.ordersharing.sender.Notification;

public class OrderController {
    public static String placeOrder(String customerName, int parkId, SharedOrderRepository orderRepo, Notification notification, int alleyNumber) {

    }
    public static boolean isOrderPaid(SharedOrder order) {
        return order.getToPay() == 0;
    }
    public static String payOrder(SharedOrder order, double amount, SharedOrderRepository sharedOrderRepository, Notification notification) {
        if (isOrderPaid(order)) {
            return "Order is already paid";
        }
        if (amount < 0 || amount > order.getToPay()) {
            return "Error in the amount specified";
        }
        double result = order.getToPay() - amount;
        order.setToPay(result);
        sharedOrderRepository.save(order);
        if(result == 0) {
            notification.sendNotification("Order fully paid");
            return "Order paid";
        }
        notification.sendNotification("Order partially paid, it remains " + result + " to pay");
        return "Order partially paid, it remains " + result + " to pay";
    }
}
