package org.example.ordersharing.controller;

import org.example.ordersharing.model.IndividualOrder;
import org.example.ordersharing.model.SharedOrder;
import org.example.ordersharing.model.User;
import org.example.ordersharing.repository.IndividualOrderRepository;
import org.example.ordersharing.repository.SharedOrderRepository;
import org.example.ordersharing.sender.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

public class OrderController {
    public static ResponseEntity<String> placeOrder(
            User customer,
            String parkName,
            IndividualOrder order,
            String alleyNumber,
            IndividualOrderRepository IOR,
            SharedOrderRepository SOR
    ) {
        IndividualOrder indOrder = new IndividualOrder(customer.getEmail(), order.getTotalPrice(), order.getProductList());
        IOR.save(indOrder);

        // Add to SharedOrder
        List<SharedOrder> sharedOrders = SOR.findByParkName(parkName); // Get all sharedOrders of a park
        if(!sharedOrders.isEmpty()) {
            for (SharedOrder sOrder: sharedOrders) { // After getting all sharedOrders of a park, need to add the individualOrder of the correct alley of the park
                if(Objects.equals(sOrder.getAlleyNumber(), alleyNumber)) {
                    sOrder.getIndividualOrders().add(indOrder);

                    // Increase totalPrice and toPay of SharedOrder
                    sOrder.setTotalPrice(sOrder.getTotalPrice() + indOrder.getTotalPrice());
                    sOrder.setToPay(sOrder.getToPay() + indOrder.getTotalPrice());

                    SOR.save(sOrder); // Update the corresponding SharedOrder
                    return ResponseEntity.status(HttpStatus.OK).body("Order of " + customer.getName() + " was added to park " + order.getParkName() + ", alley" + alleyNumber + ".");
                }
            }
        }

        // If we're still here, it means the sharedOrder doesn't exist. So we need to create it
        SharedOrder newSharedOrder = new SharedOrder(
                order.getTotalPrice(),
                order.getTotalPrice(), // toPay = totalPrice when order is created
                order.getParkName(),
                alleyNumber
        );

        newSharedOrder.getIndividualOrders().add(indOrder);
        SOR.save(newSharedOrder);

        return ResponseEntity.status(HttpStatus.OK).body("Order of " + customer.getName() + " was added to park " + order.getParkName() + ". Alley" + alleyNumber + ". \n NEW SHARED ORDER CREATED FOR THE PROVIDED ALLEY AND PARK");
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
