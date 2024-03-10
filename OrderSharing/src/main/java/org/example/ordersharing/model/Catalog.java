package org.example.ordersharing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "catalogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Catalog {
    @Id
    private String id;
    private String QRCode;
    private String parkName;
    private List<Product> products;
}
