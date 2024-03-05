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
    private String customerEmail;
    private List<String> productList; // basket
    private double totalPrice;

    public IndividualOrder(String customerEmail, double totalPrice, List<String> productList) {
        this.id = String.valueOf(productList.hashCode());
        this.customerEmail = customerEmail;
        this.productList = productList;
        this.totalPrice = totalPrice;
    }

    // ONLY USED FOR PLACE ORDER REQUEST
    private String parkName;
    private int alleyNumber;
}
