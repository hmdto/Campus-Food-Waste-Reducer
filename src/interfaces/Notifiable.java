// ==========================================
// INTERFACE: Notifiable
// ==========================================
// Requirement: Interfaces
// Purpose: Defines a contract that any class can implement
// to gain the ability to send and receive notifications.
// Example: Both Student and Staff are Notifiable,
// even though they are completely different classes.
// This shows how interfaces allow unrelated classes
// to share behavior without inheritance.
// ==========================================

package interfaces;

import java.util.List;

public interface Notifiable {

    // Send a notification message to the user
    void sendNotification(String message);

    // Retrieve all past notifications
    List<String> getNotifications();
}