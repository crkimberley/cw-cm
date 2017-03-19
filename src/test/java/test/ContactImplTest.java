package test;

import impl.ContactImpl;
import org.junit.*;
import spec.Contact;

import static org.junit.Assert.*;

/**
 * Created by Chris Kimberley on 09/03/2016.
 */
public class ContactImplTest {
    private Contact contact0, contact1, contact2, contact1Duplicate;
    private int id1, id2;
    private String name1, name2, notes1, notes2;

    @Before
    public void setUp() {
        id1 = 1;
        name1 = "Joe";
        notes1 = "1st note";
        contact1 = new ContactImpl(id1, name1, notes1);
        contact1Duplicate = new ContactImpl(id1, name1, notes1);
        id2 = 2;
        name2 = "Bill";
        notes2 = "2nd note";
        contact2 = new ContactImpl(id2, name2);
    }

    @Test
    public void testGetIdWith3Parameters() {
        assertEquals(id1, contact1.getId());
    }

    @Test
    public void testGetIdWith2Parameters() {
        assertEquals(id2, contact2.getId());
    }

    @Test
    public void testGetNameWith3Parameters() {
        assertEquals(name1, contact1.getName());
    }

    @Test
    public void testGetNameWith2Parameters() {
        assertEquals(name2, contact2.getName());
    }

    @Test
    public void testGetNotes() {
        assertEquals(notes1, contact1.getNotes());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testZeroIdThrowsIllegalArgumentException() {
        contact0 = new ContactImpl(0, name1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNegativeIdThrowsIllegalArgumentException() {
        contact0 = new ContactImpl(-1, name1);
    }

    @Test (expected = NullPointerException.class)
    public void testNullNameThrowsNullPointerException() {
        contact0 = new ContactImpl(1, null);
    }

    @Test (expected = NullPointerException.class)
    public void testNullNoteThrowsNullPointerException() {
        contact0 = new ContactImpl(1, name1, null);
    }

    @Test
    public void testEmptyStringForName() {
        contact0 = new ContactImpl(1, "", notes1);
        assertEquals("", contact0.getName());
    }

    @Test
    public void testEmptyStringForNote() {
        contact0 = new ContactImpl(1, name1, "");
        assertEquals("", contact0.getNotes());
    }

    @Test
    public void testAddEmptyStringToEmptyNote() {
        contact2.addNotes("");
        assertEquals("", contact2.getNotes());
    }

    @Test
    public void testAddNewNoteToEmptyNote() {
        contact2.addNotes("New note");
        assertEquals("New note", contact2.getNotes());
    }

    @Test
    public void testAddEmptyStringToFirstNote() {
        contact1.addNotes("");
        assertEquals("1st note", contact1.getNotes());
    }

    @Test
    public void testAdd2ndNoteToFirstNote() {
        contact1.addNotes("2nd note");
        assertEquals("1st note2nd note", contact1.getNotes());
    }

    @Test
    public void testEqualityOfContactCheckForDifferentContacts() {
        assertFalse(contact1.equals(contact2));
    }

    @Test
    public void testEqualityOfContactCheckForEqualContacts() {
        assertTrue(contact1.equals(contact1Duplicate));
    }
}