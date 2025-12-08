package com.cs340.groupProject.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest {

    @NotNull
    private Integer orderId;

    @NotNull
    private Integer oldProductId;

    @NotNull
    private Integer newProductId;

    @Min(1)
    private Integer quantity;
}
