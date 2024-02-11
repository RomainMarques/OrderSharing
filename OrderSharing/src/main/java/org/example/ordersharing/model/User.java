package org.example.ordersharing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
@Getter
@Setter
@AllArgsConstructor
public class User {
    @Id
    private int id;
    private String name;
    private String role;
}