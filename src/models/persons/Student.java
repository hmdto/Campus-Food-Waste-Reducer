// ==========================================
// CLASS: Student extends Person
// ==========================================
// Requirement: Inheritance + Encapsulation
// Purpose: Student IS-A Person, meaning it inherits
// all fields and methods from Person automatically.
// On top of that, Student has its own unique features:
// - Tracking reserved meals
// - Tracking money saved
// - Tracking environmental impact (CO2)
// - Earning badges based on activity level
// ==========================================

package models.persons;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {

    // Student-specific private fields
    private double moneySaved;
    private int mealsSaved;
    private double co2Saved;
    private List<String> orderHistory;

    public Student(String id, String name, String email, String password) {
        // super() calls the Parent Class constructor (Person)
        super(id, name, email, password);
        this.moneySaved = 0;
        this.mealsSaved = 0;
        this.co2Saved = 0;
        this.orderHistory = new ArrayList<>();
    }

    // Called every time the student reserves a meal
    // Updates all impact metrics automatically
    public void addOrder(String itemName, double savings) {
        orderHistory.add(itemName);
        moneySaved += savings;
        mealsSaved++;
        co2Saved += 0.7; // Each saved meal prevents ~0.7kg of CO2
    }

    // Badge is calculated automatically based on meals saved
    public String getBadge() {
        if (mealsSaved >= 100) return "🏆 Platinum Saver";
        if (mealsSaved >= 50)  return "🥇 Gold Saver";
        if (mealsSaved >= 25)  return "🥈 Silver Saver";
        if (mealsSaved >= 10)  return "🥉 Bronze Saver";
        return "🌱 New Saver";
    }

    // Print the student's full environmental impact summary
    public void printImpact() {
        System.out.println("\n=============================");
        System.out.println("      🌍 Your Impact");
        System.out.println("=============================");
        System.out.println("🍱 Meals Saved  : " + mealsSaved);
        System.out.println("💰 Money Saved  : $" + String.format("%.2f", moneySaved));
        System.out.println("🌱 CO2 Prevented: " + String.format("%.1f", co2Saved) + " kg");
        System.out.println("🏆 Badge        : " + getBadge());
        System.out.println("=============================");
    }

    // Inheritance: implementing the abstract method from Person
    @Override
    public String getRole() { return "Student"; }

    // Getters
    public double getMoneySaved()         { return moneySaved; }
    public int getMealsSaved()            { return mealsSaved; }
    public double getCo2Saved()           { return co2Saved; }
    public List<String> getOrderHistory() { return orderHistory; }
}