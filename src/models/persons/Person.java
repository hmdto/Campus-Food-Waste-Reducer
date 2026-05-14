// ==========================================
// ABSTRACT CLASS: Person
// ==========================================
// Requirement: Abstraction + Inheritance + Encapsulation
// Purpose: The base class for all users in the system.
// It is abstract because "Person" is a general concept
// and we never create a plain Person object directly.
// We always create a specific type: Student or Staff.
// All fields are private (Encapsulation), meaning
// no outside class can access them directly.
// Implements Notifiable so every user can receive alerts.
// ==========================================

package models.persons;

import interfaces.Notifiable;
import java.util.ArrayList;
import java.util.List;

public abstract class Person implements Notifiable {

    // Encapsulation: all fields are private
    private String id;
    private String name;
    private String email;
    private String password;
    private List<String> notifications;

    public Person(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.notifications = new ArrayList<>();
    }

    // Implementing Notifiable interface: send a notification
    @Override
    public void sendNotification(String message) {
        notifications.add(message);
        System.out.println("📬 Notification for " + name + ": " + message);
    }

    // Implementing Notifiable interface: get all notifications
    @Override
    public List<String> getNotifications() {
        return notifications;
    }

    // Abstraction: abstract method that every subclass MUST implement
    // Because each user type has a different role
    public abstract String getRole();

    // Getters: the only way to access private fields from outside
    public String getId()       { return id; }
    public String getName()     { return name; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
}