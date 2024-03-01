package org.example.ordersharing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "alleys")
@Getter
@Setter
@AllArgsConstructor
public class Alley {
    @Id
    private String id;
    private int number;
    private String QRCode;
   // private List<SharedOrder> sharedOrders; // Orders linked to the specified alley
}
