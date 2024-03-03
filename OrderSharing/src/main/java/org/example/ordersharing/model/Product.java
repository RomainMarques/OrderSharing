package org.example.ordersharing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;
    private String name;
    private double price;
    private String parkName;

    public Product (String name, double price, String parkName) {
        this.id = String.valueOf(parkName.concat(name).hashCode());
        this.name = name;
        this.price = price;
        this.parkName = parkName;
    }
}
