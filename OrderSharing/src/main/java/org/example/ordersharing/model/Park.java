package org.example.ordersharing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "parks")
@Getter
@Setter
@AllArgsConstructor
public class Park {
    @Id
    private String id;
    private String name;
    private String location;
}
