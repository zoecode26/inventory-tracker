package com.shopify.inventory.controller;

import com.shopify.inventory.dao.ItemDAO;
import com.shopify.inventory.model.Item;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/items")
public class ItemController {
    private static ItemDAO itemDAO = null;

    public ItemController(ItemDAO itemDAO) {
        ItemController.itemDAO = itemDAO;
    }

    @GetMapping("")
    public String getItems(Model model) {
        return itemsDisplay(model);
    }

    @GetMapping("/get-item")
    public String getItem(Model model) {
        model.addAttribute("action", "perform-get");
        return "get-id";
    }

    @PostMapping("/perform-get")
    public String performGet(@RequestParam String id, Model model) {
        Optional<Item> item = itemDAO.findById(Long.parseLong(id));
        if (item.isPresent()) {
            model.addAttribute("items", item.get());
            return "items";
        } else {
            return throwError(model, "Item with ID '" + id + "' not found");
        }
    }

    @GetMapping("/create-item")
    public String createItem(Model model) {
        model.addAttribute("item", new Item());
        return "create-item";
    }

    @PostMapping("/perform-create")
    public String performCreate(Item item, Model model) {
        if (item.getQuantity() > 0) {
            itemDAO.save(item);
            return itemsDisplay(model);
        } else {
            return throwError(model, "Cannot add item with negative or zero quantity");
        }
    }

    @GetMapping("/update-item")
    public String updateItem(Model model) {
        model.addAttribute("action", "display-update");
        return "get-id";
    }

    // Required to display additional form for user to specify their updates
    @PostMapping("/display-update")
    public String displayUpdate(@RequestParam String id, Model model) {
        Optional<Item> item = itemDAO.findById(Long.parseLong(id));
        if (!item.isPresent()) {
            return throwError(model, "Item with ID '" + id + "' not found");
        }
        if (item.get().getQuantity() < 0) {
            return throwError(model, "Cannot update item with negative quantity");
        }
        model.addAttribute("item", item.get());
        return "update-item";
    }

    @PostMapping("/perform-update")
    public String performUpdate(@RequestParam String name, @RequestParam String quantity, @RequestParam String id, Model model) {
        Item item = itemDAO.findById(Long.parseLong(id)).get();
        item.setName(name);
        item.setQuantity(Integer.parseInt(quantity));
        itemDAO.save(item);
        model.addAttribute("items", item);
        return "items";
    }

    // Get and post mappings for deleting an item
    @GetMapping("/delete-item")
    public String getDeleteItem(Model model) {
        model.addAttribute("action", "perform-delete");
        return "get-id";
    }

    @PostMapping("/perform-delete")
    public String performDeleteItem(@RequestParam String id, Model model) {
        try {
            itemDAO.deleteById(Long.parseLong(id));
        } catch (Exception err) {
            throwError(model, "Cannot delete item with ID of '" + id + "' as it is part of a shipment.");
        }
        return itemsDisplay(model);
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
