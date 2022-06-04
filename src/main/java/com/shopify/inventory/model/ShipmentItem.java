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

    @Column(name = "vegetable_id")
    private Long vegetableId;

    public ShipmentItem() { }

    public ShipmentItem(Long id, Long shipmentId, Long vegetableId) {
        this.id = id;
        this.shipmentId = shipmentId;
        this.vegetableId = vegetableId;
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

    public Long getVegetableId() {
        return vegetableId;
    }

    public void setVegetableId(Long vegetableId) {
        this.vegetableId = vegetableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShipmentItem that = (ShipmentItem) o;
        return Objects.equals(id, that.id) && Objects.equals(shipmentId, that.shipmentId) && Objects.equals(vegetableId, that.vegetableId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shipmentId, vegetableId);
    }

    @Override
    public String toString() {
        return "ShipmentItem{" +
                "id=" + id +
                ", shipmentId=" + shipmentId +
                ", vegetableId=" + vegetableId +
                '}';
    }
}
