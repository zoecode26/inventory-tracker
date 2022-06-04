package com.shopify.inventory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "shipments")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotEmpty
    private Date date;

    @Column
    @NotEmpty
    private String customerName;

    public Shipment() { }

    public Shipment(long id, Date date, String customerName) {
        this.id = id;
        this.date = date;
        this.customerName = customerName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipment shipment = (Shipment) o;
        return id == shipment.id && Objects.equals(date, shipment.date) && Objects.equals(customerName, shipment.customerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, customerName);
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "id=" + id +
                ", date=" + date +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
