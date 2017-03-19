package impl;

import spec.Contact;
import spec.Meeting;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * @author Chris Kimberley
 *
 * @see Meeting
 */
public abstract class MeetingImpl implements Meeting, Serializable {
    private int id;
    private Calendar date;
    private Set<Contact> contacts;

    /**
     * @param id unique meeting ID
     * @param date date of meeting
     * @param contacts contacts participating in meeting
     */
    public MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
        if (id <= 0 || contacts.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (date == null || contacts == null) {
            throw new NullPointerException();
        }
        this.id = id;
        this.date = date;
        this.contacts = contacts;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Calendar getDate() {
        return date;
    }

    @Override
    public Set<Contact> getContacts() {
        return contacts;
    }
}
