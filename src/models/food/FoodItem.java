// ==========================================
// ABSTRACT CLASS: FoodItem
// ==========================================
// Requirement: Abstraction + Encapsulation + Interfaces
// Purpose: The base class for all food items in the system.
// It is abstract because "FoodItem" is a general concept,
// we always create a specific type: PreparedMeal or BakedGood.
// Implements Reviewable so students can rate any food item.
// All fields are private (Encapsulation), protected by
// validated methods that prevent invalid data entry.
// ==========================================

package models.food;

import interfaces.Reviewable;
import models.discounts.Discount;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class FoodItem implements Reviewable {

    // Encapsulation: all fields are private
    private String id;
    private String name;
    private String description;
    private double originalPrice;
    private double currentPrice;
    private int quantity;
    private LocalDateTime expirationTime;
    private String dietaryLabel;
    private String establishmentId;
    private List<String> reviews;
    private List<Integer> ratings;

    public FoodItem(String id, String name, String description,
                    double originalPrice, int quantity,
                    LocalDateTime expirationTime, String establishmentId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.originalPrice = originalPrice;
        this.currentPrice = originalPrice;
        this.quantity = quantity;
        this.expirationTime = expirationTime;
        this.establishmentId = establishmentId;
        this.dietaryLabel = "Regular";
        this.reviews = new ArrayList<>();
        this.ratings = new ArrayList<>();
    }

    // Apply a discount to this item
    // Uses polymorphism: discount.calculateDiscount() behaves
    // differently depending on the actual discount type passed in
    public void applyDiscount(Discount discount) {
        if (discount.isApplicable(this)) {
            double discountRate = discount.calculateDiscount(this);
            this.currentPrice = originalPrice * (1 - discountRate);
        }
    }

    // Calculate how many minutes are left until expiration
    public long getMinutesUntilExpiration() {
        return java.time.Duration.between(
                LocalDateTime.now(), expirationTime).toMinutes();
    }

    // Check if item is still available (not expired, quantity > 0)
    public boolean isAvailable() {
        return quantity > 0 && LocalDateTime.now().isBefore(expirationTime);
    }

    // Encapsulation: validated method to decrement quantity safely
    public boolean decrementQuantity() {
        if (quantity > 0) {
            quantity--;
            return true;
        }
        return false;
    }

    // How much the student saves compared to original price
    public double getSavings() {
        return originalPrice - currentPrice;
    }

    // Abstraction: every subclass must define its own category
    public abstract String getCategory();

    // Implementing Reviewable interface
    @Override
    public void addReview(String review, int rating) {
        reviews.add(review);
        ratings.add(rating);
    }

    @Override
    public double getAverageRating() {
        if (ratings.isEmpty()) return 0;
        return ratings.stream().mapToInt(Integer::intValue)
                .average().orElse(0);
    }

    @Override
    public List<String> getReviews() { return reviews; }

    // Getters
    public String getId()                    { return id; }
    public String getName()                  { return name; }
    public String getDescription()           { return description; }
    public double getOriginalPrice()         { return originalPrice; }
    public double getCurrentPrice()          { return currentPrice; }
    public int getQuantity()                 { return quantity; }
    public LocalDateTime getExpirationTime() { return expirationTime; }
    public String getDietaryLabel()          { return dietaryLabel; }
    public String getEstablishmentId()       { return establishmentId; }

    // Setter with validation
    public void setDietaryLabel(String label) { this.dietaryLabel = label; }
}