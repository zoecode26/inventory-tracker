package com.shopify.inventory.controller;

import com.shopify.inventory.dao.ShipmentDAO;
import java.util.Optional;
import com.shopify.inventory.model.Shipment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/shipments", method = { RequestMethod.GET})
public class ShipmentController {
    private final ShipmentDAO shipmentDAO;

    public ShipmentController(ShipmentDAO shipmentDAO) {
        this.shipmentDAO = shipmentDAO;
    }

    @GetMapping("")
    public Iterable<Shipment> getShipments() {
        return shipmentDAO.findAll();
    }

    @GetMapping("/{shipment_id}")
    public Optional<Shipment> getShipment(@PathVariable(value = "shipment_id") Long shipment_id) {
        return shipmentDAO.findById(shipment_id);
    }
}
