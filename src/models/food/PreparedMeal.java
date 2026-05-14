// ==========================================
// CLASS: PreparedMeal extends FoodItem
// ==========================================
// Requirement: Inheritance
// Purpose: PreparedMeal IS-A FoodItem.
// Represents hot prepared meals like grilled chicken,
// pasta, rice dishes, etc.
// Inherits all FoodItem behavior and adds:
// - cuisineType: what type of cuisine it is
// - isHot: whether the meal is served hot
// ==========================================

package models.food;

import java.time.LocalDateTime;

public class PreparedMeal extends FoodItem {

    private String cuisineType;
    private boolean isHot;

    public PreparedMeal(String id, String name, String description,
                        double originalPrice, int quantity,
                        LocalDateTime expirationTime, String establishmentId,
                        String cuisineType, boolean isHot) {
        // Call parent constructor (FoodItem)
        super(id, name, description, originalPrice,
                quantity, expirationTime, establishmentId);
        this.cuisineType = cuisineType;
        this.isHot = isHot;
    }

    // Inheritance: implementing the abstract method from FoodItem
    @Override
    public String getCategory() { return "Prepared Meal"; }

    // Getters
    public String getCuisineType() { return cuisineType; }
    public boolean isHot()         { return isHot; }
}