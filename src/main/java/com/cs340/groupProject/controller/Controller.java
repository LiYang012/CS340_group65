package com.cs340.groupProject.controller;

import com.cs340.groupProject.model.Category;
import com.cs340.groupProject.model.Customer;
import com.cs340.groupProject.model.Order;
import com.cs340.groupProject.model.Product;
import com.cs340.groupProject.repo.CategoryRequest;
import com.cs340.groupProject.repo.GroupProjectRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:7788", "http://localhost:9111", "http://127.0.0.1:5500", "http://classwork.engr.oregonstate.edu:8171", "http://classwork.engr.oregonstate.edu:7788", "http://classwork.engr.oregonstate.edu:9111"})
public class Controller {

    private final GroupProjectRepository repo;

    public Controller(GroupProjectRepository repo) {
        this.repo = repo;
    }

    @Operation(description = "Get all categories.")
    @GetMapping("/categories")
    public List<Category> getCategories() {
        return repo.getCategories();
    }

    @Operation(description = "Create a category.")
    @PostMapping("/categories")
    public void createCategory(@RequestBody CategoryRequest categoryRequest) {
        repo.addCategory(categoryRequest.getName(), categoryRequest.getDescription());
    }

    @Operation(description = "Delete a category by id.")
    @DeleteMapping("/categories/{id}")
    public void deleteCategories(@PathVariable String id) {
        repo.deleteCategory(id);
    }

    @Operation(description = "Reset database to its original state.")
    @PostMapping("/procedures/reset-tables")
    public void resetDB() {
        repo.resetDB();
    }

    @Operation(description = "Get all products.")
    @GetMapping("/products")
    public List<Product> getProducts() {
        return repo.getProducts();
    }

    @Operation(description = "Add a product.")
    @PostMapping("/products")
    public void addProducts(@RequestBody Product product) {
        repo.addProduct(product);
    }

    @Operation(description = "Update a product.")
    @PutMapping("/products")
    public void updateProducts(@RequestBody Product product) {
        repo.updateProduct(product);
    }

    @Operation(description = "Delete a product by id.")
    @DeleteMapping("/products/{id}")
    public void deleteProducts(@PathVariable String id) {
        repo.deleteProduct(id);
    }

    @Operation(description = "Get all orders.")
    @GetMapping("/orders")
    public List<Order> getOrders() {
        return repo.getOrders();
    }

    @Operation(description = "Get all customers.")
    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return repo.getCustomers();
    }

    @Operation(description = "Add a customer.")
    @PostMapping("/customers")
    public void createCustomers(@RequestBody Customer customer) {
        repo.addCustomer(customer);
    }

}

