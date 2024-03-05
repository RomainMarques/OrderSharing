package org.example.ordersharing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "sharedOrders")
@Getter
@Setter
@AllArgsConstructor
public class SharedOrder {
    @Id
    private String id;
    private double totalPrice;
    private double toPay;
    private String parkName;
    private String alleyNumber;
    @DBRef
    private List<IndividualOrder> individualOrders; // Global id of individual orders
}
