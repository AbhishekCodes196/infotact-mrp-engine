package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_type")
    private String itemType;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

     public Product() {}

     public Integer getId() { return id; }
    public String getItemName() { return itemName; }
    public String getItemType() { return itemType; }
    public Integer getStockQuantity() { return stockQuantity; }

     public void setId(Integer id) { this.id = id; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setItemType(String itemType) { this.itemType = itemType; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
    
}
