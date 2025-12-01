package com.cs340.groupProject.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Order {
    private int orderId;
    private String customer;
    private Date date;
    private double orderPrice;
    private String productName;

}
