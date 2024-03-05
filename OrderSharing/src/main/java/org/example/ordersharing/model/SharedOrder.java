package org.example.ordersharing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "sharedOrders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SharedOrder {
    @Id
    private String id;
    private double totalPrice;
    private double toPay;
    private String parkName;
    private String alleyNumber;
    @DBRef
    private List<IndividualOrder> individualOrders; // Global id of individual orders

    public SharedOrder(double totalPrice, double toPay, String parkName, String alleyNumber) {
        this.id = String.valueOf(parkName.concat(alleyNumber).hashCode());
        this.totalPrice = totalPrice;
        this.toPay = toPay;
        this.parkName = parkName;
        this.alleyNumber = alleyNumber;
        individualOrders = new ArrayList<>();
    }
}
