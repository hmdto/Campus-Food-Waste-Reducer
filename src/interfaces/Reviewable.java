// ==========================================
// INTERFACE: Reviewable
// ==========================================
// Requirement: Interfaces
// Purpose: Defines a contract for anything that
// can be rated and reviewed by users.
// In our project: FoodItem implements Reviewable,
// meaning students can rate meals after reserving them.
// Key insight: FoodItem and FoodEstablishment don't
// inherit from each other, but both can be Reviewable.
// This is the power of interfaces over inheritance.
// ==========================================

package interfaces;

import java.util.List;

public interface Reviewable {

    // Add a review and a rating (1-5)
    void addReview(String review, int rating);

    // Calculate and return the average rating
    double getAverageRating();

    // Return all submitted reviews
    List<String> getReviews();
}