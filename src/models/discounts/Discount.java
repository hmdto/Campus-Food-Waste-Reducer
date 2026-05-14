// ==========================================
// ABSTRACT CLASS: Discount
// ==========================================
// Requirement: Abstraction + Polymorphism
// Purpose: The base class for all discount types.
// It is abstract because "Discount" is a general concept.
// Each subclass implements its own discount calculation logic.
// This is where Polymorphism happens:
// the same method calculateDiscount() behaves completely
// differently depending on which subclass is being used.
// Calling code doesn't need to know which type it is,
// it just calls calculateDiscount() and gets the right result.
// ==========================================

package models.discounts;

import models.food.FoodItem;

public abstract class Discount {

    private String discountId;
    private String description;

    public Discount(String discountId, String description) {
        this.discountId = discountId;
        this.description = description;
    }

    // Polymorphism: each subclass calculates discount differently
    // Returns a value between 0.0 and 1.0 (e.g. 0.75 = 75% off)
    public abstract double calculateDiscount(FoodItem item);

    // Check whether this discount applies to the given item
    public abstract boolean isApplicable(FoodItem item);

    // Getters
    public String getDiscountId()   { return discountId; }
    public String getDescription()  { return description; }
}