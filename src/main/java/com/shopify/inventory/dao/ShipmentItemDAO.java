package com.shopify.inventory.dao;

import com.shopify.inventory.model.ShipmentItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShipmentItemDAO extends CrudRepository<ShipmentItem, Long> {
    ShipmentItem findByShipmentId(Long shipmentId);
    List<ShipmentItem> findAllByShipmentId(Long shipmentId);
}
