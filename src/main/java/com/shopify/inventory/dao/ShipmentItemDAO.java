package com.shopify.inventory.dao;

import com.shopify.inventory.model.ShipmentItem;
import org.springframework.data.repository.CrudRepository;

public interface ShipmentItemDAO extends CrudRepository<ShipmentItem, Long> {
    ShipmentItem findByShipmentId(Long shipmentId);
}
