package com.shopify.inventory.controller;

import com.shopify.inventory.dao.ItemDAO;
import com.shopify.inventory.model.Item;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/items")
public class ItemController {
    private final ItemDAO itemDAO;

    public ItemController(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    // Mapping to get all items
    @GetMapping("")
    public String getItems(Model model) {
        Iterable<Item> items = itemDAO.findAll();
        model.addAttribute("items", items);
        return "items";
    }

    // Get and post mappings for retrieving an item by its ID
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
        }
        return "Item with ID not found";
    }

    // Get and post mappings for retrieving item/s by their name
    @GetMapping("/get-item-by-name")
    public String getItemByName() {
        return "get-name";
    }

    @PostMapping("/perform-get-by-name")
    public String getItemNameTemplate(@RequestParam String name, Model model) {
        List<Item> items = itemDAO.findByName(name);
        if (items.size() > 0) {
            model.addAttribute("items", items);
            return "items";
        }
        return "Items with name not found";
    }

    // Get and post mappings for creating an item
    @GetMapping("/create-item")
    public String createItem(Model model) {
        model.addAttribute("item", new Item());
        return "create-item";
    }

    @PostMapping("/perform-create")
    public String performCreate(Item item, Model model) {
        itemDAO.save(item);
        Iterable<Item> items = itemDAO.findAll();
        model.addAttribute("items", items);
        return "items";
    }

    // Get and post mappings for updating an item
    // displayUpdate method required to display additional form for user to specify their updates
    @GetMapping("/update-item")
    public String updateItem(Model model) {
        model.addAttribute("action", "display-update");
        return "get-id";
    }

    @PostMapping("/display-update")
    public String displayUpdate(@RequestParam String id, Model model) {
        Optional<Item> item = itemDAO.findById(Long.parseLong(id));
        if (item.isPresent()) {
            model.addAttribute("item", item.get());
            return "update-item";
        }
        return "Item with ID not found";
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
        itemDAO.deleteById(Long.parseLong(id));
        Iterable<Item> items = itemDAO.findAll();
        model.addAttribute("items", items);
        return "items";
    }
}
