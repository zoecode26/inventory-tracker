package com.shopify.inventory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "shipment_items")
public class ShipmentItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shipment_id")
    private Long shipmentId;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "quantity")
    private Integer quantity;

    public ShipmentItem() { }

    public ShipmentItem(Long id, Long shipmentId, Long itemId, Integer quantity) {
        this.id = id;
        this.shipmentId = shipmentId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() { return quantity; }

    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShipmentItem that = (ShipmentItem) o;
        return Objects.equals(id, that.id) && Objects.equals(shipmentId, that.shipmentId) && Objects.equals(itemId, that.itemId) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shipmentId, itemId, quantity);
    }

    @Override
    public String toString() {
        return "ShipmentItem{" +
                "id=" + id +
                ", shipmentId=" + shipmentId +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                '}';
    }
}
