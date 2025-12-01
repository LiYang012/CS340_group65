package com.cs340.groupProject.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Product {
    private int id;
    private String name;
    private String description;
    private String category;
    private double price;
}
