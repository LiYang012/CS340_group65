package com.cs340.groupProject.repo;

import com.cs340.groupProject.model.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupProjectRepository {
    private static final String GET_PRODUCTS_QUERY = "SELECT p.product_id, p.name, p.description, p.price, c.category_name\n" +
            "FROM Products p\n" +
            "INNER JOIN Categories c ON c.category_id = p.category_id\n" +
            "ORDER BY p.product_id;";

    private static final String ADD_PRODUCT_QUERY = "INSERT INTO Products (name, description, price, category_id)\n" +
            "VALUES ('%s', '%s', %s, \n" +
            "(SELECT category_id FROM Categories WHERE category_name = '%s'))";

    private static final String UPDATE_PRODUCT_QUERY = "update Products\n" +
            "set price = COALESCE(%s, price), category_id = COALESCE((SELECT category_id FROM Categories WHERE category_name = '%s'), category_id)\n" +
            "where product_id = %s";

    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM Products WHERE product_id = '%s'";

    private static final String GET_CATEGORIES_QUERY = "SELECT category_id, category_name, description, created_at\n" +
            "FROM Categories\n" +
            "ORDER BY category_id;";

    private static final String ADD_CATEGORY_QUERY = "INSERT INTO Categories (category_name, description, created_at)\n" +
            "VALUES ('%s', '%s', NOW());";

    private static final String DELETE_CATEGORY_QUERY = "DELETE FROM Categories WHERE category_id = '%s'";

    private static final String GET_ORDERS_QUERY = "SELECT op.order_id, op.unit_price, op.quantity, p.name AS product_name, o.order_date, u.name AS customer_name\n" +
            "FROM Order_Products op\n" +
            "INNER JOIN Products p ON op.product_id = p.product_id\n" +
            "INNER JOIN Orders o ON op.order_id = o.order_id\n" +
            "INNER JOIN Users u ON o.user_id = u.user_id\n" +
            "ORDER BY o.order_date;";

    private static final String GET_CUSTOMERS_QUERY = "SELECT user_id, name, email, address, phone FROM Users ORDER BY user_id;";

    private static final String ADD_CUSTOMER_QUERY = "insert into Users (name, email, address, phone) values ('%s', '%s', '%s', '%s')\n";

    private static final String RESET_DB_PROCEDURE_CALL = "CALL CreateCompleteDatabase();";

    private static final String CREATE_ORDER_PROCEDURE_CALL = "CALL create_order(%s,%s,%s);"; // input user_id, product_id, quantity

    private static final String DELETE_ORDER_PROCEDURE_CALL = "CALL delete_order(%s);"; // input order_id

    private static final String UPDATE_ORDER_PROCEDURE_CALL = "CALL update_order_item(%s,%s,%s,%s);"; // input order_id, old_product_id, new_product_id, quantity

    private final JdbcTemplate jdbc;

    public GroupProjectRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Category> getCategories(){
        return jdbc.query(GET_CATEGORIES_QUERY, (rs, rowNum) -> new Category(
                rs.getInt("category_id"),
                rs.getString("category_name"),
                rs.getString("description")
        ));
    }

    public void addCategory(String name, String description) {
        jdbc.execute(String.format(ADD_CATEGORY_QUERY, name, description));
    }

    public void deleteCategory(String id) {
        jdbc.execute(String.format(DELETE_CATEGORY_QUERY, id));
    }

    public void resetDB() {
        jdbc.execute(RESET_DB_PROCEDURE_CALL);
    }

    public List<Product> getProducts() {
        return jdbc.query(GET_PRODUCTS_QUERY, (rs, rowNum) -> new Product(
                rs.getInt("product_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("category_name"),
                rs.getDouble("price")
        ));
    }

    public List<Order> getOrders() {

        return jdbc.query(GET_ORDERS_QUERY, (rs, rowNum) -> Order.builder()
                .orderId(rs.getInt("order_id"))
                .customer(rs.getString("customer_name"))
                .date(rs.getDate("order_date"))
                .orderPrice(rs.getDouble("unit_price") * rs.getInt("quantity"))
                .productName(rs.getString("product_name"))
                .quantity(rs.getInt("quantity"))
                .build()
        );
    }

    public List<Customer> getCustomers() {
        return jdbc.query(GET_CUSTOMERS_QUERY, (rs, rowNum) -> new Customer(
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("address"),
                rs.getString("phone")
        ));
    }

    public void addCustomer(Customer customer) {
        jdbc.execute(String.format(ADD_CUSTOMER_QUERY, customer.getName(),
                customer.getEmail(), customer.getAddress(), customer.getPhone()));
    }

    public void addProduct(Product product) {
        jdbc.execute(String.format(ADD_PRODUCT_QUERY, product.getName(), product.getDescription(),
                product.getPrice(), product.getCategory()));
    }

    public void updateProduct(Product product) {
        jdbc.execute(String.format(UPDATE_PRODUCT_QUERY, product.getPrice(), product.getCategory(), product.getId()));
    }

    public void deleteProduct(String id) {
        jdbc.execute(String.format(DELETE_PRODUCT_QUERY, id));
    }

    public void deleteOrder(String id) {
        jdbc.execute(String.format(DELETE_ORDER_PROCEDURE_CALL, id));
    }

    public void createOrder(CreateOrderRequest order) {
        jdbc.execute(String.format(CREATE_ORDER_PROCEDURE_CALL, order.getUserId(), order.getProductId(), order.getQuantity()));
    }

    public void updateOrder(UpdateOrderRequest updateOrderRequest) {
        jdbc.execute(String.format(UPDATE_ORDER_PROCEDURE_CALL, updateOrderRequest.getOrderId(), updateOrderRequest.getOldProductId(),
                updateOrderRequest.getNewProductId(), updateOrderRequest.getQuantity()));
    }
}
