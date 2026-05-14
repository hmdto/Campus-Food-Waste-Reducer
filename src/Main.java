// ==========================================
// CLASS: Main
// ==========================================
// Purpose: Entry point of the application.
// Handles the console menu and user interaction.
// Connects all classes together to form the system.
// Shows login, routing to student or staff menu,
// and all core features of the application.
// ==========================================

import data.DataStore;
import models.discounts.TimeBasedDiscount;
import models.food.BakedGood;
import models.food.FoodItem;
import models.food.PreparedMeal;
import models.orders.Order;
import models.persons.Person;
import models.persons.Staff;
import models.persons.Student;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static DataStore dataStore = DataStore.getInstance();

    public static void main(String[] args) {
        printBanner();

        while (true) {
            Person user = showLogin();
            if (user == null) {
                System.out.println("❌ Invalid email or password. Try again.");
                continue;
            }

            System.out.println("\n✅ Welcome, " + user.getName()
                    + "! [" + user.getRole() + "]");

            if (user instanceof Student) {
                studentMenu((Student) user);
            } else if (user instanceof Staff) {
                staffMenu((Staff) user);
            }
        }
    }

    // ==========================================
    // BANNER
    // ==========================================
    static void printBanner() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   🍱 Campus Food Waste Reducer 🍱    ║");
        System.out.println("║   Reduce Waste. Save Money. 🌍       ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    // ==========================================
    // LOGIN
    // ==========================================
    static Person showLogin() {
        System.out.println("\n--- Login ---");
        System.out.print("Email    : ");
        String email = scanner.nextLine().trim();
        System.out.print("Password : ");
        String password = scanner.nextLine().trim();
        return dataStore.login(email, password);
    }

    // ==========================================
    // STUDENT MENU
    // ==========================================
    static void studentMenu(Student student) {
        while (true) {
            System.out.println("\n=============================");
            System.out.println("       🎓 Student Menu");
            System.out.println("=============================");
            System.out.println("1. Browse Available Food");
            System.out.println("2. Reserve a Meal");
            System.out.println("3. My Orders");
            System.out.println("4. My Impact");
            System.out.println("5. Logout");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> browseFoodItems();
                case "2" -> reserveMeal(student);
                case "3" -> showStudentOrders(student);
                case "4" -> student.printImpact();
                case "5" -> { return; }
                default  -> System.out.println("❌ Invalid choice.");
            }
        }
    }

    // ==========================================
    // STAFF MENU
    // ==========================================
    static void staffMenu(Staff staff) {
        while (true) {
            System.out.println("\n=============================");
            System.out.println("       👨‍💼 Staff Menu");
            System.out.println("=============================");
            System.out.println("1. View All Inventory");
            System.out.println("2. Add New Food Item");
            System.out.println("3. View All Orders");
            System.out.println("4. Logout");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> viewInventory();
                case "2" -> addFoodItem(staff);
                case "3" -> viewAllOrders();
                case "4" -> { return; }
                default  -> System.out.println("❌ Invalid choice.");
            }
        }
    }

    // ==========================================
    // BROWSE AVAILABLE FOOD ITEMS
    // ==========================================
    static void browseFoodItems() {
        List<FoodItem> items = dataStore.getAvailableItems();

        System.out.println("\n=============================");
        System.out.println("    🔥 Available Surplus Food");
        System.out.println("=============================");

        if (items.isEmpty()) {
            System.out.println("😔 No surplus food available right now.");
            return;
        }

        TimeBasedDiscount discount = new TimeBasedDiscount();

        for (int i = 0; i < items.size(); i++) {
            FoodItem item = items.get(i);
            System.out.println("\n[" + (i + 1) + "] " + item.getName());
            System.out.println("    Category : " + item.getCategory());
            System.out.println("    Dietary  : " + item.getDietaryLabel());
            System.out.printf ("    Price    : $%.2f → $%.2f%n",
                    item.getOriginalPrice(), item.getCurrentPrice());
            System.out.println("    Discount : " + discount.getDiscountLabel(item));
            System.out.println("    Quantity : " + item.getQuantity());
            System.out.println("    Expires  : " +
                    item.getMinutesUntilExpiration() + " minutes");
        }
        System.out.println("=============================");
    }

    // ==========================================
    // RESERVE A MEAL
    // ==========================================
    static void reserveMeal(Student student) {
        List<FoodItem> items = dataStore.getAvailableItems();

        if (items.isEmpty()) {
            System.out.println("😔 No items available to reserve.");
            return;
        }

        browseFoodItems();

        System.out.print("\nEnter item number to reserve (0 to cancel): ");
        String input = scanner.nextLine().trim();
        int index;

        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input.");
            return;
        }

        if (index == -1) return;

        if (index < 0 || index >= items.size()) {
            System.out.println("❌ Invalid item number.");
            return;
        }

        FoodItem selectedItem = items.get(index);

        if (!selectedItem.isAvailable()) {
            System.out.println("❌ Sorry, this item is no longer available.");
            return;
        }

        // Create and confirm the order
        Order order = new Order(
                "ORD-" + System.currentTimeMillis(),
                student.getId()
        );
        order.addItem(selectedItem);
        order.confirmOrder();

        // Update student impact
        student.addOrder(selectedItem.getName(), selectedItem.getSavings());

        // Notify student
        student.sendNotification("Reserved: " + selectedItem.getName()
                + " | QR: " + order.getQrCode());

        // Save order
        dataStore.addOrder(order);

        // Print summary
        order.printSummary();
    }

    // ==========================================
    // SHOW STUDENT ORDER HISTORY
    // ==========================================
    static void showStudentOrders(Student student) {
        List<Order> orders = dataStore.getOrdersByStudent(student.getId());

        System.out.println("\n=============================");
        System.out.println("       📋 My Orders");
        System.out.println("=============================");

        if (orders.isEmpty()) {
            System.out.println("You have no orders yet.");
            return;
        }

        for (Order order : orders) {
            System.out.println("Order ID : " + order.getOrderId());
            System.out.println("Status   : " + order.getStatus());
            System.out.printf("Paid     : $%.2f | Saved: $%.2f%n",
                    order.getTotalPrice(), order.getTotalSavings());
            System.out.println("QR Code  : " + order.getQrCode());
            System.out.println("-----------------------------");
        }
    }

    // ==========================================
    // VIEW ALL INVENTORY (STAFF)
    // ==========================================
    static void viewInventory() {
        List<FoodItem> items = dataStore.getAllItems();

        System.out.println("\n=============================");
        System.out.println("       📦 Full Inventory");
        System.out.println("=============================");

        if (items.isEmpty()) {
            System.out.println("No items in inventory.");
            return;
        }

        TimeBasedDiscount discount = new TimeBasedDiscount();

        for (FoodItem item : items) {
            System.out.println("\n• " + item.getName()
                    + " [" + item.getCategory() + "]");
            System.out.printf("  Price    : $%.2f → $%.2f%n",
                    item.getOriginalPrice(), item.getCurrentPrice());
            System.out.println("  Discount : " + discount.getDiscountLabel(item));
            System.out.println("  Quantity : " + item.getQuantity());
            System.out.println("  Status   : " +
                    (item.isAvailable() ? "✅ Available" : "❌ Unavailable"));
            System.out.println("  Expires  : " +
                    item.getMinutesUntilExpiration() + " minutes");
        }
        System.out.println("=============================");
    }

    // ==========================================
    // ADD FOOD ITEM (STAFF)
    // ==========================================
    static void addFoodItem(Staff staff) {
        System.out.println("\n--- ➕ Add New Surplus Item ---");

        System.out.print("Item Type (1 = Prepared Meal, 2 = Baked Good): ");
        String type = scanner.nextLine().trim();

        System.out.print("Name        : ");
        String name = scanner.nextLine().trim();

        System.out.print("Description : ");
        String desc = scanner.nextLine().trim();

        System.out.print("Price ($)   : ");
        double price;
        try {
            price = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid price.");
            return;
        }

        System.out.print("Quantity    : ");
        int qty;
        try {
            qty = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid quantity.");
            return;
        }

        System.out.print("Expires in (minutes): ");
        int mins;
        try {
            mins = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid time.");
            return;
        }

        System.out.print("Dietary Label (Regular/Vegetarian/Vegan/Gluten-Free/Halal): ");
        String dietary = scanner.nextLine().trim();

        String itemId = "F" + System.currentTimeMillis();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(mins);
        TimeBasedDiscount discount = new TimeBasedDiscount();

        FoodItem newItem;

        if (type.equals("1")) {
            newItem = new PreparedMeal(itemId, name, desc, price, qty,
                    expiry, staff.getEstablishmentId(), "Local", true);
        } else {
            newItem = new BakedGood(itemId, name, desc, price, qty,
                    expiry, staff.getEstablishmentId(), "Pastry", false);
        }

        newItem.setDietaryLabel(dietary);
        newItem.applyDiscount(discount);
        dataStore.addFoodItem(newItem);

        System.out.println("\n✅ Item added successfully!");
        System.out.println("   Name     : " + newItem.getName());
        System.out.printf ("   Price    : $%.2f → $%.2f%n",
                newItem.getOriginalPrice(), newItem.getCurrentPrice());
        System.out.println("   Discount : " + discount.getDiscountLabel(newItem));
    }

    // ==========================================
    // VIEW ALL ORDERS (STAFF)
    // ==========================================
    static void viewAllOrders() {
        List<Order> orders = dataStore.getAllOrders();

        System.out.println("\n=============================");
        System.out.println("       📋 All Orders");
        System.out.println("=============================");

        if (orders.isEmpty()) {
            System.out.println("No orders yet.");
            return;
        }

        for (Order order : orders) {
            System.out.println("Order ID : " + order.getOrderId());
            System.out.println("Student  : " + order.getStudentId());
            System.out.println("Status   : " + order.getStatus());
            System.out.printf("Total    : $%.2f | Saved: $%.2f%n",
                    order.getTotalPrice(), order.getTotalSavings());
            System.out.println("-----------------------------");
        }
    }
}