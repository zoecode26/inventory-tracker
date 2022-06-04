package com.shopify.inventory.dao;

import com.shopify.inventory.model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemDAO extends CrudRepository<Item, Long> { }
