package com.shopify.inventory.controller;

import com.shopify.inventory.dao.ItemDAO;
import com.shopify.inventory.dao.ShipmentDAO;
import com.shopify.inventory.dao.ShipmentItemDAO;
import com.shopify.inventory.model.Item;
import com.shopify.inventory.model.Shipment;
import com.shopify.inventory.model.ShipmentItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/shipments", method = { RequestMethod.GET})
public class ShipmentController {
    private final ShipmentDAO shipmentDAO;
    private static ItemDAO itemDAO = null;
    private final ShipmentItemDAO shipmentItemDAO;

    public ShipmentController(ShipmentDAO shipmentDAO, ItemDAO itemDAO, ShipmentItemDAO shipmentItemDAO) {
        this.shipmentDAO = shipmentDAO;
        ShipmentController.itemDAO = itemDAO;
        this.shipmentItemDAO = shipmentItemDAO;
    }

    @GetMapping("")
    public String getShipments(Model model) {
        Iterable<Shipment> shipments = shipmentDAO.findAll();
        model.addAttribute("shipments", shipments);
        return "shipments";
    }

    @GetMapping("/get-shipment")
    public String getShipment() {
        return "shipment-id";
    }

    @PostMapping("/perform-get-shipment")
    public String performGetShipment(@RequestParam String id, Model model) {
        Optional<Shipment> shipment = shipmentDAO.findById(Long.parseLong(id));
        if (!shipment.isPresent()) {
            throwError(model, "Shipment '" + id + "' not found");
        }

        List<Item> items = new ArrayList<>();
        Iterable<ShipmentItem> shipmentItems = shipmentItemDAO.findAllByShipmentId(Long.parseLong(id));
        StreamSupport.stream(shipmentItems.spliterator(), false).forEach(a -> {
            // Create item object that represents item in shipment
            Item retrievedItem = itemDAO.findById(a.getItemId()).get();
            items.add(new Item(retrievedItem.getId(), retrievedItem.getName(), retrievedItem.getQuantity()));
        });

        model.addAttribute("shipment", shipment.get());
        model.addAttribute("items", items);
        return "get-shipment";
    }

    @GetMapping("/create-shipment")
    public String createShipment(Model model) {
        model.addAttribute("shipment", new Shipment());
        return "create-shipment";
    }

    @PostMapping("/perform-create-shipment")
    public String performCreateShipment(Shipment shipment, Model model) {
        Shipment addedShipment = shipmentDAO.save(shipment);
        Iterable<Item> items = itemDAO.findAll();
        model.addAttribute("shipment", addedShipment);
        model.addAttribute("items", items);
        model.addAttribute("item", new Item());
        return "populate-shipment";
    }

    // Adds specified items to the shipment created in performCreateShipment
    @PostMapping("/perform-populate-shipment")
    public String createShipment(@RequestParam Map<String,String> allParams, Model model) {
        for (Map.Entry<String, String> entry: allParams.entrySet()) {
            Integer value = Integer.parseInt(entry.getValue());
            if (value < 0) {
                return throwError(model, "Cannot populate shipment with negative quantity");
            }
            if (!entry.getValue().equals("0") && !entry.getKey().equals("id")) {
                Long key = Long.parseLong(entry.getKey());
                Item item = itemDAO.findById(key).get();
                if (item.getQuantity() < value) {
                    return throwError(model, "Cannot add " + value + " x " + item.getName() +
                                                        ". Only " + item.getQuantity() + " in stock.");
                }
                item.setQuantity(item.getQuantity() - value);
                itemDAO.save(item);
                shipmentItemDAO.save(new ShipmentItem(0L, Long.parseLong(allParams.get("id")), key, value));
            }
        }
        return "redirect:/";
    }

    private static String throwError(Model model, String message) {
        model.addAttribute("message", message);
        return "error";
    }

    private static String itemsDisplay(Model model) {
        Iterable<Item> items = itemDAO.findAll();
        model.addAttribute("items", items);
        return "items";
    }
}
