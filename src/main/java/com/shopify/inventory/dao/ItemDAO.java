package com.shopify.inventory.dao;

import com.shopify.inventory.model.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemDAO extends CrudRepository<Item, Long> {
    List<Item> findByName(String name);
}
