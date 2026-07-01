package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class BomLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long parentItemId;
    private Long childItemId;
    private Double quantityRequired;

    // These lines are just to help Java read the data
    public Long getParentItemId() { return parentItemId; }
    public void setParentItemId(Long parentItemId) { this.parentItemId = parentItemId; }
    public Long getChildItemId() { return childItemId; }
    public void setChildItemId(Long childItemId) { this.childItemId = childItemId; }
    public Double getQuantityRequired() { return quantityRequired; }
    public void setQuantityRequired(Double quantityRequired) { this.quantityRequired = quantityRequired; }
}
