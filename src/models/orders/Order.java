// ==========================================
// CLASS: Order
// ==========================================
// Requirement: Encapsulation
// Purpose: Represents a food reservation made by a student.
// All order data is private and can only be changed
// through controlled methods that enforce valid behavior.
// For example: you cannot cancel a COMPLETED order,
// and you cannot confirm an already CANCELLED order.
// This is Encapsulation protecting the order's state.
// ==========================================

package models.orders;

import models.food.FoodItem;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

    // Enum to represent the possible states of an order
    public enum OrderStatus {
        PENDING,    // Just created, not yet confirmed
        CONFIRMED,  // Student confirmed the reservation
        READY,      // Food is ready for pickup
        COMPLETED,  // Student picked up the food
        CANCELLED   // Order was cancelled
    }

    // Encapsulation: all fields are private
    private String orderId;
    private String studentId;
    private List<FoodItem> items;
    private double totalPrice;
    private double totalSavings;
    private OrderStatus status;
    private LocalDateTime orderTime;
    private String qrCode;

    public Order(String orderId, String studentId) {
        this.orderId = orderId;
        this.studentId = studentId;
        this.items = new ArrayList<>();
        this.totalPrice = 0;
        this.totalSavings = 0;
        this.status = OrderStatus.PENDING;
        this.orderTime = LocalDateTime.now();
        this.qrCode = "QR-" + orderId + "-" + System.currentTimeMillis();
    }

    // Add a food item to this order and update totals
    public void addItem(FoodItem item) {
        items.add(item);
        totalPrice += item.getCurrentPrice();
        totalSavings += item.getSavings();
        item.decrementQuantity();
    }

    // Encapsulation: controlled state transitions
    // Cannot skip states or go backwards
    public void confirmOrder() {
        if (status == OrderStatus.PENDING) {
            status = OrderStatus.CONFIRMED;
            System.out.println("✅ Order " + orderId + " confirmed!");
            System.out.println("📱 Your QR Code: " + qrCode);
        }
    }

    public void markReady() {
        if (status == OrderStatus.CONFIRMED) {
            status = OrderStatus.READY;
            System.out.println("🍱 Order " + orderId + " is ready for pickup!");
        }
    }

    public void cancelOrder() {
        if (status != OrderStatus.COMPLETED) {
            status = OrderStatus.CANCELLED;
            System.out.println("❌ Order " + orderId + " has been cancelled.");
        }
    }

    // Print a full summary of this order
    public void printSummary() {
        System.out.println("\n=============================");
        System.out.println("       📋 Order Summary");
        System.out.println("=============================");
        System.out.println("Order ID  : " + orderId);
        System.out.println("QR Code   : " + qrCode);
        System.out.println("Status    : " + status);
        System.out.println("-----------------------------");
        for (FoodItem item : items) {
            System.out.printf("  %-20s $%.2f%n",
                    item.getName(), item.getCurrentPrice());
        }
        System.out.println("-----------------------------");
        System.out.printf("Total Paid : $%.2f%n", totalPrice);
        System.out.printf("You Saved  : $%.2f%n", totalSavings);
        System.out.println("=============================");
    }

    // Getters
    public String getOrderId()         { return orderId; }
    public String getStudentId()       { return studentId; }
    public List<FoodItem> getItems()   { return items; }
    public double getTotalPrice()      { return totalPrice; }
    public double getTotalSavings()    { return totalSavings; }
    public OrderStatus getStatus()     { return status; }
    public LocalDateTime getOrderTime(){ return orderTime; }
    public String getQrCode()          { return qrCode; }
}