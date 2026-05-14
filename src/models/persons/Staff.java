// ==========================================
// CLASS: Staff extends Person
// ==========================================
// Requirement: Inheritance
// Purpose: Staff IS-A Person, just like Student.
// This demonstrates Inheritance clearly:
// both Student and Staff inherit from Person,
// but each has its own unique properties and behavior.
// Staff has an establishmentId to know which
// cafeteria or food truck they manage.
// ==========================================

package models.persons;

public class Staff extends Person {

    private String establishmentId;
    private String staffRole;

    public Staff(String id, String name, String email,
                 String password, String establishmentId, String staffRole) {
        super(id, name, email, password);
        this.establishmentId = establishmentId;
        this.staffRole = staffRole;
    }

    // Inheritance: implementing the abstract method from Person
    @Override
    public String getRole() { return "Staff"; }

    // Getters
    public String getEstablishmentId() { return establishmentId; }
    public String getStaffRole()       { return staffRole; }
}