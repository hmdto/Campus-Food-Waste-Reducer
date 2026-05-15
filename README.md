```markdown
# 🍱 Campus Food Waste Reducer

> An Object-Oriented Java console application that tackles campus food waste
> by connecting vendors with students through smart dynamic pricing.

---

## 📋 Project Overview

Every day, campus cafeterias and food trucks throw away hundreds of meals
that could have been eaten. This system creates a simple but powerful
marketplace where:

- **Vendors** list surplus food items before they expire
- **The system** automatically calculates discounts based on time remaining
- **Students** browse and reserve discounted meals
- **Everyone wins** — less waste, more savings, better planet

---

## 🎯 OOP Concepts Demonstrated

| Concept | How It's Applied |
|---|---|
| **Abstraction** | `Person`, `FoodItem`, `Discount` are abstract classes hiding complexity |
| **Inheritance** | `Student` and `Staff` extend `Person` — `PreparedMeal` and `BakedGood` extend `FoodItem` |
| **Encapsulation** | All fields are `private` with controlled `getters/setters` |
| **Polymorphism** | `calculateDiscount()` behaves differently per discount type |
| **Interfaces** | `Notifiable` and `Reviewable` define contracts across unrelated classes |

---

## 🏗️ Project Structure

```
src/
├── interfaces/
│   ├── Notifiable.java       # Contract for sending notifications
│   └── Reviewable.java       # Contract for ratings and reviews
├── models/
│   ├── persons/
│   │   ├── Person.java       # Abstract base class for all users
│   │   ├── Student.java      # Student with impact tracking
│   │   └── Staff.java        # Vendor/staff member
│   ├── food/
│   │   ├── FoodItem.java     # Abstract base class for all food
│   │   ├── PreparedMeal.java # Hot prepared meals
│   │   └── BakedGood.java    # Baked goods and pastries
│   ├── discounts/
│   │   ├── Discount.java          # Abstract discount engine
│   │   └── TimeBasedDiscount.java # Auto discount by expiry time
│   └── orders/
│       └── Order.java        # Reservation and order tracking
├── data/
│   └── DataStore.java        # In-memory data management
└── Main.java                 # Console menu and entry point
```

---

## ⚡ Dynamic Pricing Engine

The system automatically applies discounts based on time until expiration:

```
⏰ 3+ hours remaining  →  No discount
🟡 Under 3 hours       →  25% OFF
🟠 Under 2 hours       →  50% OFF
🔴 Under 1 hour        →  75% OFF
```

No manual input needed — the system calculates everything automatically.

---

## 👤 User Roles

### 🧑‍🎓 Student
- Browse all available surplus food
- Filter by category or dietary preference
- Reserve meals at discounted prices
- Track personal impact (meals saved, money saved, CO2 prevented)
- Earn badges based on activity

### 👨‍💼 Staff (Vendor)
- Add surplus food items with expiry time
- View all incoming reservations
- Monitor inventory in real time

---

## 🚀 How to Run

### Requirements
- Java 17 or higher

### Steps
```bash
# Compile
javac -cp src -d out src/Main.java

# Run
java -cp out Main
```

---

## 🔐 Demo Accounts

| Role | Email | Password |
|------|-------|----------|
| Student | ahmed@uni.edu | 1234 |
| Staff | hassan@uni.edu | admin |

---

## 🌍 Environmental Impact Tracking

Every reservation made through the system tracks:
- 🍱 Number of meals saved from waste
- 💰 Total money saved by the student
- 🌱 CO2 emissions prevented (estimated 0.7kg per meal)
- 🏆 Achievement badges (New Saver → Bronze → Silver → Gold → Platinum)

---

## 👥 Team Members

- MUSTAFA ABDUALATIF
- MUSTAFA HISHAM
- MUHAMMET

---

## 📚 Course

Object-Oriented Programming — Java
```
