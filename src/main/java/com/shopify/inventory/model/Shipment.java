package com.shopify.inventory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "shipments")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private Date date;

    @Column
    private String customerName;

    @Column
    private String addressLineOne;

    @Column
    private String addressLineTwo;

    @Column
    private String city;

    @Column
    private String county;

    @Column
    private String postcode;

    public Shipment() { }

    public Shipment(long id, Date date, String customerName, String addressLineOne, String addressLineTwo, String city, String county, String postcode) {
        this.id = id;
        this.date = date;
        this.customerName = customerName;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.city = city;
        this.county = county;
        this.postcode = postcode;
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

    public String getAddressLineOne() {
        return addressLineOne;
    }

    public void setAddressLineOne(String addressLineOne) {
        this.addressLineOne = addressLineOne;
    }

    public String getAddressLineTwo() {
        return addressLineTwo;
    }

    public void setAddressLineTwo(String addressLineTwo) {
        this.addressLineTwo = addressLineTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipment shipment = (Shipment) o;
        return id == shipment.id && Objects.equals(date, shipment.date) && Objects.equals(customerName, shipment.customerName) && Objects.equals(addressLineOne, shipment.addressLineOne) && Objects.equals(addressLineTwo, shipment.addressLineTwo) && Objects.equals(city, shipment.city) && Objects.equals(county, shipment.county) && Objects.equals(postcode, shipment.postcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, customerName, addressLineOne, addressLineTwo, city, county, postcode);
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "id=" + id +
                ", date=" + date +
                ", customerName='" + customerName + '\'' +
                ", addressLineOne='" + addressLineOne + '\'' +
                ", addressLineTwo='" + addressLineTwo + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
