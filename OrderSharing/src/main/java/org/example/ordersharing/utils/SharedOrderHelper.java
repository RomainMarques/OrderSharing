package org.example.ordersharing.utils;

import org.example.ordersharing.model.IndividualOrder;
import org.example.ordersharing.model.SharedOrder;

public class SharedOrderHelper {
    private SharedOrderHelper() {
    }
    public static IndividualOrder getIndividualOrderFromSharedOrder(SharedOrder sharedOrder, String idOrder) {
        for(IndividualOrder individualOrder : sharedOrder.getIndividualOrders()) {
            if(individualOrder.getId().equals(idOrder)) {
                return individualOrder;
            }
        }
        return null;
    }
}
