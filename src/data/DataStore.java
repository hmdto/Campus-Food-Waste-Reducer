// ==========================================
// CLASS: DataStore
// ==========================================
// Requirement: Encapsulation + Singleton Pattern
// Purpose: Acts as the central in-memory database
// for the entire system. Stores all users, food items,
// and orders in one place.
// Uses the Singleton Pattern to ensure only ONE
// instance of DataStore exists throughout the app.
// Pre-loads demo data so the system works immediately
// without needing a real database.
// ==========================================

package data;

import models.discounts.TimeBasedDiscount;
import models.food.BakedGood;
import models.food.FoodItem;
import models.food.PreparedMeal;
import models.orders.Order;
import models.persons.Person;
import models.persons.Staff;
import models.persons.Student;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataStore {

    // Singleton: only one instance allowed
    private static DataStore instance;

    // In-memory storage (acts like a database)
    private List<Person> users;
    private List<FoodItem> foodItems;
    private List<Order> orders;

    // Private constructor: no one can create DataStore directly
    private DataStore() {
        users = new ArrayList<>();
        foodItems = new ArrayList<>();
        orders = new ArrayList<>();
        seedData();
    }

    // Singleton: the only way to get the DataStore instance
    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    // Pre-load demo data so the system works out of the box
    private void seedData() {

        // Demo users
        users.add(new Student("S001", "Ahmed Ali",
                "ahmed@uni.edu", "1234"));
        users.add(new Student("S002", "Sara Mohamed",
                "sara@uni.edu", "1234"));
        users.add(new Staff("ST001", "Manager Hassan",
                "hassan@uni.edu", "admin", "E001", "Manager"));

        // Discount engine
        TimeBasedDiscount discount = new TimeBasedDiscount();

        // Demo food items with different expiration times
        // to demonstrate different discount levels

        PreparedMeal meal1 = new PreparedMeal(
                "F001", "Grilled Chicken Rice",
                "Healthy grilled chicken with steamed rice",
                8.50, 5,
                LocalDateTime.now().plusMinutes(50), // Under 1 hour -> 75% off
                "E001", "Local", true);
        meal1.applyDiscount(discount);

        PreparedMeal meal2 = new PreparedMeal(
                "F002", "Veggie Pasta",
                "Fresh vegetable pasta with tomato sauce",
                6.00, 3,
                LocalDateTime.now().plusMinutes(110), // Under 2 hours -> 50% off
                "E001", "Italian", true);
        meal2.setDietaryLabel("Vegetarian");
        meal2.applyDiscount(discount);

        BakedGood good1 = new BakedGood(
                "F003", "Chocolate Muffin",
                "Freshly baked chocolate muffin",
                3.00, 8,
                LocalDateTime.now().plusMinutes(170), // Under 3 hours -> 25% off
                "E001", "Muffin", false);
        good1.applyDiscount(discount);

        BakedGood good2 = new BakedGood(
                "F004", "Gluten-Free Cookie",
                "Healthy gluten-free oat cookie",
                2.50, 6,
                LocalDateTime.now().plusMinutes(45), // Under 1 hour -> 75% off
                "E002", "Cookie", true);
        good2.setDietaryLabel("Gluten-Free");
        good2.applyDiscount(discount);

        foodItems.add(meal1);
        foodItems.add(meal2);
        foodItems.add(good1);
        foodItems.add(good2);
    }

    // Login: find user by email and password
    public Person login(String email, String password) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email)
                        && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    // Get all food items that are still available
    public List<FoodItem> getAvailableItems() {
        return foodItems.stream()
                .filter(FoodItem::isAvailable)
                .collect(Collectors.toList());
    }

    // Get all food items (including unavailable ones) for vendor view
    public List<FoodItem> getAllItems() {
        return foodItems;
    }

    // Add a new food item (used by vendors)
    public void addFoodItem(FoodItem item) {
        foodItems.add(item);
    }

    // Save a completed order
    public void addOrder(Order order) {
        orders.add(order);
    }

    // Get all orders in the system
    public List<Order> getAllOrders() {
        return orders;
    }

    // Get orders for a specific student
    public List<Order> getOrdersByStudent(String studentId) {
        return orders.stream()
                .filter(o -> o.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }
}