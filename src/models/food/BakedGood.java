// ==========================================
// CLASS: BakedGood extends FoodItem
// ==========================================
// Requirement: Inheritance
// Purpose: BakedGood IS-A FoodItem.
// Represents baked items like muffins, cookies,
// croissants, bread, etc.
// Inherits all FoodItem behavior and adds:
// - bakedType: the specific type of baked good
// - isGlutenFree: dietary information for the item
// ==========================================

package models.food;

import java.time.LocalDateTime;

public class BakedGood extends FoodItem {

    private String bakedType;
    private boolean isGlutenFree;

    public BakedGood(String id, String name, String description,
                     double originalPrice, int quantity,
                     LocalDateTime expirationTime, String establishmentId,
                     String bakedType, boolean isGlutenFree) {
        // Call parent constructor (FoodItem)
        super(id, name, description, originalPrice,
                quantity, expirationTime, establishmentId);
        this.bakedType = bakedType;
        this.isGlutenFree = isGlutenFree;
    }

    // Inheritance: implementing the abstract method from FoodItem
    @Override
    public String getCategory() { return "Baked Good"; }

    // Getters
    public String getBakedType()   { return bakedType; }
    public boolean isGlutenFree()  { return isGlutenFree; }
}