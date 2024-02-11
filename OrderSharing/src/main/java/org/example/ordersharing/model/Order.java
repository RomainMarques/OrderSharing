package org.example.ordersharing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "orders")
@Getter
@Setter
@AllArgsConstructor
public class Order {
    @Id
    private String id;
    private String curstomerName;
    private String parkName;
    private List<String> productList;
}
