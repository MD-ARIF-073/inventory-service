package com.example.inventory_service.core.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDomainModel {

    private Long id;

    private String productId;

    private int quantity;

}
