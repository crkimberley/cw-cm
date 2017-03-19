package impl;

import spec.Contact;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Chris Kimberley
 *
 * @see Contact
 */
public class ContactImpl implements Contact, Serializable {
    private int id;
    private String name, notes;

    /**
     * Constructor including notes about the contact
     *
     * @param id unique id for contact
     * @param name name of contact
     * @param notes notes about contact
     */
    public ContactImpl(int id, String name, String notes) {
        if (name == null || notes == null) {
            throw new NullPointerException();
        }
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.name = name;
        this.notes = notes;
    }

    /**
     * Constructor without notes about the contact
     * @param id unique id for contact
     * @param name name of contact
     */
    public ContactImpl(int id, String name) {
        this(id, name, "");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public void addNotes(String note) {
        notes += note;
    }

    /**
     * Overriding equals() - test for equality based on id and name
     * Required for comparison purposes in some methods
     *
     * @param obj Object used to test for equality
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ContactImpl contact = (ContactImpl) obj;
        return (id == contact.id && Objects.equals(name, contact.name));
    }

    /**
     * Overriding hashCode - based on id and name
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
