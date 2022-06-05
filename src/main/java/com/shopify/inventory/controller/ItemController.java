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

    @GetMapping("")
    public String getItems(Model model) {
        Iterable<Item> items = itemDAO.findAll();
        model.addAttribute("items", items);
        return "items";
    }

    @GetMapping("/{item_id}")
    public Optional<Item> getVegetable(@PathVariable(value = "item_id") Long item_id) {
        return itemDAO.findById(item_id);
    }

    @PostMapping("")
    public String writeItem(Item item, Model model) {
        itemDAO.save(item);
        Iterable<Item> items = itemDAO.findAll();
        model.addAttribute("items", items);
        return "items";
    }

    @GetMapping("/create-item")
    public String createItem(Model model) {
        model.addAttribute("item", new Item());
        return "create-item";
    }

    @GetMapping("/get-id")
    public String getItemById() {
        return "get-id";
    }

    @PostMapping("/get-id")
    public String getItemIdTemplate(@RequestParam String id, Model model) {
        Optional<Item> item = itemDAO.findById(Long.parseLong(id));
        if (item.isPresent()) {
            model.addAttribute("items", item.get());
            return "items";
        }
        return "Item with ID not found";
    }

    @GetMapping("/get-name")
    public String getItemByName() {
        return "get-name";
    }

    @PostMapping("/get-name")
    public String getItemNameTemplate(@RequestParam String name, Model model) {
        List<Item> items = itemDAO.findByName(name);
        if (items.size() > 0) {
            model.addAttribute("items", items);
            return "items";
        }
        return "Items with name not found";
    }

    @GetMapping("/delete-item")
    public String getDeleteItem() {
        return "delete-item";
    }

    @PostMapping("/delete-item")
    public String performDeleteItem(@RequestParam String id, Model model) {
        itemDAO.deleteById(Long.parseLong(id));
        Iterable<Item> items = itemDAO.findAll();
        model.addAttribute("items", items);
        return "items";
    }

    @GetMapping("/update-item")
    public String getUpdateItem() {
        return "update-item";
    }

    @PostMapping("/update-item")
    public String displayUpdateForm(@RequestParam String id, Model model) {
        Optional<Item> item = itemDAO.findById(Long.parseLong(id));
        if (item.isPresent()) {
            model.addAttribute("item", item.get());
            return "update-details";
        }
        return "Item with ID not found";
    }

    @PostMapping("/update-details")
    public String performUpdate(@RequestParam String name, @RequestParam String quantity, @RequestParam String id, Model model) {
        Item item = itemDAO.findById(Long.parseLong(id)).get();
        item.setName(name);
        item.setQuantity(Integer.parseInt(quantity));
        itemDAO.save(item);

        model.addAttribute("items", item);
        return "items";
    }
}
