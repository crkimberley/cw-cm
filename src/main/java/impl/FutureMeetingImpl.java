package impl;

import spec.Contact;
import spec.FutureMeeting;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * @author Chris Kimberley
 *
 * @see FutureMeeting
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting, Serializable {
    public FutureMeetingImpl(int id, Calendar date, Set<Contact> contacts){
        super(id, date, contacts);
    }
}