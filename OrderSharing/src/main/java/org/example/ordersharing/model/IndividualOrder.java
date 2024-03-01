package org.example.ordersharing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "individualOrders")
@Getter
@Setter
@AllArgsConstructor
public class IndividualOrder {
    @Id
    private String id;
    private String customerName;
    private List<String> productList; // Basket ?
    private double totalPrice;
}
