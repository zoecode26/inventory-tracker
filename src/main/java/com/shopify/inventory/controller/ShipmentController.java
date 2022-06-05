package com.shopify.inventory.controller;

import com.shopify.inventory.dao.ItemDAO;
import com.shopify.inventory.dao.ShipmentDAO;

import java.util.Map;
import java.util.Optional;

import com.shopify.inventory.dao.ShipmentItemDAO;
import com.shopify.inventory.model.Item;
import com.shopify.inventory.model.Shipment;
import com.shopify.inventory.model.ShipmentItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/shipments", method = { RequestMethod.GET})
public class ShipmentController {
    private final ShipmentDAO shipmentDAO;
    private final ItemDAO itemDAO;
    private final ShipmentItemDAO shipmentItemDAO;

    public ShipmentController(ShipmentDAO shipmentDAO, ItemDAO itemDAO, ShipmentItemDAO shipmentItemDAO) {
        this.shipmentDAO = shipmentDAO;
        this.itemDAO = itemDAO;
        this.shipmentItemDAO = shipmentItemDAO;
    }

    @GetMapping("")
    public String getShipments(Model model) {
        Iterable<Shipment> shipments = shipmentDAO.findAll();
        model.addAttribute("shipments", shipments);
        return "shipments";
    }

    @GetMapping("/{shipment_id}")
    public Optional<Shipment> getShipment(@PathVariable(value = "shipment_id") Long shipment_id) {
        return shipmentDAO.findById(shipment_id);
    }

    @GetMapping("/create-shipment")
    public String getShipmentDetails(Model model) {
        model.addAttribute("shipment", new Shipment());
        return "create-shipment";
    }

    @PostMapping("/create-new-shipment")
    public String createShipment(Shipment shipment, Model model) {
        Shipment addedShipment = shipmentDAO.save(shipment);
        model.addAttribute("shipment", addedShipment);
        Iterable<Item> items = itemDAO.findAll();
        model.addAttribute("items", items);
        model.addAttribute("item", new Item());
        return "populate-shipment";
    }

    @GetMapping("/populate-shipment")
    public String getShipmentTemplate(Model model) {
        Iterable<Item> items = itemDAO.findAll();
        model.addAttribute("items", items);
        model.addAttribute("item", new Item());
        return "create-shipment";
    }

    @PostMapping("/populate-new-shipment")
    public String createShipment(@RequestParam Map<String,String> allParams) {
        allParams.forEach((key, value) -> {
            if (!value.equals("0") && !key.equals("id")) {
                ShipmentItem shipmentItem = new ShipmentItem();
                shipmentItem.setShipmentId(Long.parseLong(allParams.get("id")));
                shipmentItem.setItemId(Long.parseLong(key));
                shipmentItem.setQuantity(Integer.parseInt(value));
                shipmentItemDAO.save(shipmentItem);
            }
        });
        return "items";
    }
}
