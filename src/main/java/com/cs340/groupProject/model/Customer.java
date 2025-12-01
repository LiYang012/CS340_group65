package com.cs340.groupProject.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Customer {
    private int id;
    private String name;
    private String email;
    private String address;
    private String phone;
}
