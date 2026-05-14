// ==========================================
// CLASS: TimeBasedDiscount extends Discount
// ==========================================
// Requirement: Polymorphism + Inheritance
// Purpose: Automatically calculates discount based on
// how many minutes are left until the food expires.
// This is the STAR of Polymorphism in our project:
// When calculateDiscount() is called on a Discount reference,
// this specific logic runs if the object is TimeBasedDiscount.
//
// Discount Rules:
// Under 60 minutes  → 75% OFF  🔴
// Under 120 minutes → 50% OFF  🟠
// Under 180 minutes → 25% OFF  🟡
// 180+ minutes      → No discount
// ==========================================

package models.discounts;

import models.food.FoodItem;

public class TimeBasedDiscount extends Discount {

    public TimeBasedDiscount() {
        super("TBD-001", "Time-Based Automatic Discount");
    }

    // Polymorphism: overriding the abstract method from Discount
    // This logic runs automatically based on expiration time
    @Override
    public double calculateDiscount(FoodItem item) {
        long minutes = item.getMinutesUntilExpiration();

        if (minutes <= 60)  return 0.75; // 75% off
        if (minutes <= 120) return 0.50; // 50% off
        if (minutes <= 180) return 0.25; // 25% off
        return 0.0;                      // No discount
    }

    // Only applicable if item expires within 3 hours
    @Override
    public boolean isApplicable(FoodItem item) {
        long minutes = item.getMinutesUntilExpiration();
        return minutes > 0 && minutes <= 180;
    }

    // Returns a human-readable discount label for display
    public String getDiscountLabel(FoodItem item) {
        long minutes = item.getMinutesUntilExpiration();
        if (minutes <= 60)  return "🔴 75% OFF - Expires in under 1 hour!";
        if (minutes <= 120) return "🟠 50% OFF - Expires in under 2 hours!";
        if (minutes <= 180) return "🟡 25% OFF - Expires in under 3 hours!";
        return "✅ Full Price - Fresh item";
    }
}