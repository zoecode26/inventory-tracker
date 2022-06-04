package com.shopify.inventory.dao;

import com.shopify.inventory.model.Shipment;
import org.springframework.data.repository.CrudRepository;

public interface ShipmentDAO extends CrudRepository<Shipment, Long> { }
