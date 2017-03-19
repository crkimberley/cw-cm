package test;

import impl.ContactImpl;
import impl.ContactManagerImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spec.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by chris on 11/04/2016.
 */
public class ContactManagerImplTest {
    private ContactManager cManager1, cManager2;
    private Set<Contact> contactSet1, contactSet2, contactSet3, emptyContactSet;
    private Calendar testTime, pastTime, futureTime;
    private Calendar futureTime01, futureTime02, futureTime03, futureTime04, futureTime05;
    private Calendar pastTime01, pastTime02, pastTime03, pastTime04, pastTime05;

    private Contact contact01, contact02, contact03, contact04, contact05, contact12, contact21, contact22;
    private int id01,id02, id03, id04, id05, id12, id21, id22;
    private String name01, name02, name03, name04, name05, name12, name21, name22;
    private String note01, note02, note03, note04, note05, note12, note21, note22;
    private String text01, text02, text03, text04, textAdded;

    static final String TEXT_FILE_NAME = "contacts.txt";

    @Before
    public void setUp() {

        id01 = 1;
        name01 = "Adam";
        note01 = "Note about Adam";
        id02 = 2;
        name02 = "Bruce";
        note02 = "Note about Bruce";
        id03 = 3;
        name03 = "Claire";
        note03 = "Note about Claire";
        id04 = 4;
        name04 = "Dan";
        note04 = "Note about Dan";
        id05 = 5;
        name05 = "Eve";
        note05 = "Note about Eve";
        id12 = 12;
        name12 = "Lucette";
        note12 = "Note about Lucette";

        id21 = 21;
        name21 = "Uma";
        note21 = "Note about Uma";
        id22 = 22;
        name22 = "Victoria";
        note22 = "Note about Victoria";

        contact01 = new ContactImpl(id01, name01, note01);
        contact02 = new ContactImpl(id02, name02, note02);
        contact03 = new ContactImpl(id03, name03, note03);
        contact04 = new ContactImpl(id04, name04, note04);
        contact05 = new ContactImpl(id05, name05, note05);

        contact12 = new ContactImpl(id12, name12, note12);

        contactSet1 = new HashSet<>();
        contactSet1.add(contact01);
        contactSet1.add(contact02);
        contactSet1.add(contact03);

        contactSet2 = new HashSet<>();
        contactSet2.add(contact04);
        contactSet2.add(contact05);

        /**
         * contactSet3 - unknown contacts
         */
        contactSet3 = new HashSet<>();
        contact21 = new ContactImpl(id21, name21, note21);
        contact22 = new ContactImpl(id22, name22, note22);
        contactSet3.add(contact21);
        contactSet3.add(contact22);

        emptyContactSet = new HashSet<>();

        cManager1 = new ContactManagerImpl();
        cManager1.addNewContact(name01, note01);
        cManager1.addNewContact(name02, note02);
        cManager1.addNewContact(name03, note03);
        cManager1.addNewContact(name04, note04);
        cManager1.addNewContact(name05, note05);

        text01 = "Text about Meeting 1";
        text02 = "Text about Meeting 2";
        text03 = "Text about Meeting 3";
        text04 = "Text about Meeting 4";
        textAdded = "Some added notes";

        cManager2 = new ContactManagerImpl();

        testTime = Calendar.getInstance();
        futureTime = Calendar.getInstance();
        futureTime.add(Calendar.MONTH, 1);
        futureTime01 = Calendar.getInstance();
        futureTime01.add(Calendar.MINUTE, 1);
        futureTime02 = Calendar.getInstance();
        futureTime02.add(Calendar.MINUTE, 2);
        futureTime03 = Calendar.getInstance();
        futureTime03.add(Calendar.MINUTE, 3);
        futureTime04 = Calendar.getInstance();
        futureTime04.add(Calendar.MINUTE, 4);
        futureTime05 = Calendar.getInstance();
        futureTime05.add(Calendar.MINUTE, 5);
        pastTime = Calendar.getInstance();
        pastTime.add(Calendar.MONTH, -1);
        pastTime01 = Calendar.getInstance();
        pastTime01.add(Calendar.MINUTE, -25);
        pastTime02 = Calendar.getInstance();
        pastTime02.add(Calendar.MINUTE, -20);
        pastTime03 = Calendar.getInstance();
        pastTime03.add(Calendar.MINUTE, -15);
        pastTime04 = Calendar.getInstance();
        pastTime04.add(Calendar.MINUTE, -10);
        pastTime05 = Calendar.getInstance();
        pastTime05.add(Calendar.MINUTE, -5);
    }

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(FileSystems.getDefault().getPath(TEXT_FILE_NAME));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test (expected = NullPointerException.class)
    public void testAddFutureMeetingThrowsNullPointerExceptionForNullContacts() {
        cManager1.addFutureMeeting(null, futureTime);
    }

    @Test (expected = NullPointerException.class)
    public void testAddFutureMeetingThrowsNullPointerExceptionForNullDate() {
        cManager1.addFutureMeeting(contactSet1, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddFutureMeetingThrowsIllegalArgumentExceptionForTimeInPastCurrentMinusOneSecond() {
        testTime.add(Calendar.SECOND, -1);
        cManager1.addFutureMeeting(contactSet1, testTime);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddFutureMeetingThrowsIllegalArgumentExceptionForTimeInPastCurrentMinusOneMinute() {
        testTime.add(Calendar.MINUTE, -1);
        cManager1.addFutureMeeting(contactSet1, testTime);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddFutureMeetingThrowsIllegalArgumentExceptionForTimeInPastCurrentMinusOneYear() {
        testTime.add(Calendar.YEAR, -1);
        cManager1.addFutureMeeting(contactSet1, testTime);
    }

    @Test
    public void testAddFutureMeetingReturnsPositiveIdForTimeInFutureCurrentPlusOneYear() {
        testTime.add(Calendar.YEAR, 1);
        assertTrue((cManager1.addFutureMeeting(contactSet1, testTime)) > 0);
    }

    @Test
    public void testAddFutureMeetingReturnsPositiveIdForTimeInFutureCurrentPlusOneSecond() {
        testTime.add(Calendar.SECOND, 1);
        assertTrue((cManager1.addFutureMeeting(contactSet1, testTime)) > 0);
    }

    @Test
    public void testAddFutureMeetingReturnsCorrectIds() {
        assertEquals(1, cManager1.addFutureMeeting(contactSet1, futureTime));
        assertEquals(2, cManager1.addFutureMeeting(contactSet2, futureTime));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddFutureMeetingUnknownContactsThrowsIllegalArgumentException() {
        cManager1.addFutureMeeting(contactSet3, futureTime);
    }

    @Test
    public void testAddFutureMeetingsGetMeetings() {
        cManager1.addFutureMeeting(contactSet1, futureTime);
        cManager1.addFutureMeeting(contactSet2, futureTime);

        Meeting fMeeting1 = cManager1.getMeeting(1);
        assertEquals(1, fMeeting1.getId());
        assertEquals(contactSet1, fMeeting1.getContacts());
        assertEquals(futureTime, fMeeting1.getDate());
        assertTrue(fMeeting1 instanceof FutureMeeting);

        Meeting fMeeting2 = cManager1.getMeeting(2);
        assertEquals(2, fMeeting2.getId());
        assertEquals(contactSet2, fMeeting2.getContacts());
        assertEquals(futureTime, fMeeting2.getDate());
        assertTrue(fMeeting2 instanceof FutureMeeting);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetPastMeetingThrowsIllegalArgumentExceptionForMeetingInFuture() {
        cManager1.addFutureMeeting(contactSet1, futureTime);
        cManager1.getPastMeeting(1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetFutureMeetingThrowsIllegalArgumentExceptionForMeetingInPast() {
        cManager1.addNewPastMeeting(contactSet1, pastTime, text01);
        cManager1.getFutureMeeting(1);
    }

    @Test
    public void testGetMeetingReturnsNullForNoMeetingMatchingId() {
        cManager1.addFutureMeeting(contactSet1, futureTime);
        cManager1.addFutureMeeting(contactSet2, futureTime);
        assertNull(cManager1.getMeeting(3));
    }

    @Test (expected = NullPointerException.class)
    public void testGetFutureMeetingListThrowsNullPointerExceptionForNullContact() {
        cManager1.getFutureMeetingList(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetFutureMeetingListThrowsIllegalArgumentExceptionForNonExistentContact() {
        cManager1.getFutureMeetingList(contact21);
    }

    @Test
    public void testGetFutureMeetingListReturnsEmptyListForContactWithNoScheduledMeetings() {
        cManager1.addFutureMeeting(contactSet1, futureTime);
        assertTrue(cManager1.getFutureMeetingList(contact05).isEmpty());
    }

    @Test
    public void testGetFutureMeetingListReturnsListSize1ForContactWith1ScheduledMeeting() {
        cManager1.addFutureMeeting(contactSet1, futureTime);
        assertEquals(1, cManager1.getFutureMeetingList(contact03).size());
    }

    @Test
    public void testGetFutureMeetingListReturnsListSize2ForContactWith2ScheduledMeetings() {
        cManager1.addFutureMeeting(contactSet1, futureTime);
        futureTime.add(Calendar.MONTH, 1);
        cManager1.addFutureMeeting(contactSet1, futureTime);
        assertEquals(2, cManager1.getFutureMeetingList(contact03).size());
    }

    @Test
    public void testGetFutureMeetingListReturnsSortedList() {
        cManager1.addFutureMeeting(contactSet1, futureTime04);
        cManager1.addFutureMeeting(contactSet1, futureTime02);
        cManager1.addFutureMeeting(contactSet1, futureTime01);
        cManager1.addFutureMeeting(contactSet1, futureTime03);
        List<Meeting> meetingsForSelectedContact = cManager1.getFutureMeetingList(contact03);
        assertEquals(3, meetingsForSelectedContact.get(0).getId());
        assertEquals(2, meetingsForSelectedContact.get(1).getId());
        assertEquals(4, meetingsForSelectedContact.get(2).getId());
        assertEquals(1, meetingsForSelectedContact.get(3).getId());
    }

    @Test (expected = NullPointerException.class)
    public void testGetMeetingListOnThrowsNullPointerExceptionForNullDate() {
        cManager1.getMeetingListOn(null);
    }

    @Test
    public void testGetMeetingListOnReturnsEmptyListForDateWithNoMeetings() {
        cManager1.addFutureMeeting(contactSet1, futureTime);
        cManager1.addFutureMeeting(contactSet2, futureTime);
        assertTrue((cManager1.getMeetingListOn(testTime)).isEmpty());
    }

    @Test
    public void testGetMeetingListOnReturnsCorrectSizeListForDateInFutureWith2Meetings() {
        cManager1.addFutureMeeting(contactSet1, futureTime);
        cManager1.addFutureMeeting(contactSet2, futureTime);
        assertEquals(2, cManager1.getMeetingListOn(futureTime).size());
    }

    @Test
    public void testGetMeetingListOnReturnsCorrectSizeListForDateInFutureWith1Meeting() {
        cManager1.addFutureMeeting(contactSet1, futureTime);
        assertEquals(1, cManager1.getMeetingListOn(futureTime).size());
    }

    @Test
    public void testGetMeetingListOnReturnsSortedList() {
        cManager1.addFutureMeeting(contactSet1, futureTime04);
        cManager1.addFutureMeeting(contactSet1, futureTime02);
        cManager1.addFutureMeeting(contactSet1, futureTime01);
        cManager1.addFutureMeeting(contactSet1, futureTime03);
        List<Meeting> meetingsOnSelectedDate = cManager1.getMeetingListOn(futureTime01);
        assertEquals(3, meetingsOnSelectedDate.get(0).getId());
        assertEquals(2, meetingsOnSelectedDate.get(1).getId());
        assertEquals(4, meetingsOnSelectedDate.get(2).getId());
        assertEquals(1, meetingsOnSelectedDate.get(3).getId());
    }

    @Test (expected = NullPointerException.class)
    public void testGetPastMeetingListForThrowsNullPointerExceptionForNullContact() {
        cManager1.getPastMeetingListFor(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetPastMeetingListForThrowsIllegalArgumentExceptionForNonExistentContact() {
        cManager1.getPastMeetingListFor(contact21);
    }

    @Test
    public void testGetPastMeetingListForReturnsEmptyListForContactWithNoMeetingsParticipatedIn() {
        cManager1.addNewPastMeeting(contactSet1, pastTime, text01);
        assertTrue(cManager1.getPastMeetingListFor(contact05).isEmpty());
    }

    @Test
    public void testGetPastMeetingListForReturnsListSize1ForContactWith1MeetingParticipatedIn() {
        cManager1.addNewPastMeeting(contactSet1, pastTime, text01);
        assertEquals(1, cManager1.getPastMeetingListFor(contact03).size());
    }

    @Test
    public void testGetPastMeetingListForReturnsListSize2ForContactWith2MeetingsParticipatedIn() {
        cManager1.addNewPastMeeting(contactSet1, pastTime, text01);
        pastTime.add(Calendar.DATE, -1);
        cManager1.addNewPastMeeting(contactSet1, pastTime, text01);
        assertEquals(2, cManager1.getPastMeetingListFor(contact03).size());
    }

    @Test
    public void testGetPastMeetingListForReturnsSortedList() {
        cManager1.addNewPastMeeting(contactSet1, pastTime04, text04);
        cManager1.addNewPastMeeting(contactSet1, pastTime02, text02);
        cManager1.addNewPastMeeting(contactSet1, pastTime01, text01);
        cManager1.addNewPastMeeting(contactSet1, pastTime03, text03);
        List<PastMeeting> meetingsForSelectedContact = cManager1.getPastMeetingListFor(contact03);
        assertEquals(3, meetingsForSelectedContact.get(0).getId());
        assertEquals(2, meetingsForSelectedContact.get(1).getId());
        assertEquals(4, meetingsForSelectedContact.get(2).getId());
        assertEquals(1, meetingsForSelectedContact.get(3).getId());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddNewPastMeetingThrowsIllegalArgumentExceptionForEmptyContacts() {
        cManager1.addNewPastMeeting(emptyContactSet, pastTime, text01);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddNewPastMeetingThrowsIllegalArgumentExceptionForNonExistentContacts() {
        cManager1.addNewPastMeeting(contactSet3, pastTime, text01);
    }

    @Test (expected = NullPointerException.class)
    public void testAddNewPastMeetingThrowsNullPointerExceptionForNullContacts() {
        cManager1.addNewPastMeeting(null, pastTime, text01);
    }

    @Test (expected = NullPointerException.class)
    public void testAddNewPastMeetingThrowsNullPointerExceptionForNullDate() {
        cManager1.addNewPastMeeting(contactSet1, null, text01);
    }

    @Test (expected = NullPointerException.class)
    public void testAddNewPastMeetingThrowsNullPointerExceptionForNullText() {
        cManager1.addNewPastMeeting(contactSet1, pastTime, null);
    }

    @Test (expected = NullPointerException.class)
    public void testAddMeetingNotesThrowsNullPointerExceptionForNullNotes() {
        cManager1.addNewPastMeeting(contactSet1, pastTime, text01);
        cManager1.addMeetingNotes(1, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddMeetingNotesThrowsIllegalArgumentExceptionForNonExistentMeeting() {
        cManager1.addNewPastMeeting(contactSet1, pastTime, text01);
        cManager1.addMeetingNotes(2, text02);
    }

    @Test (expected = IllegalStateException.class)
    public void testAddMeetingNotesThrowsIllegalStateExceptionForMeetingDateInFuture() {
        cManager1.addFutureMeeting(contactSet1, futureTime);
        cManager1.addMeetingNotes(1, text01);
    }

    @Test
    public void testAddMeetingNotesAddNotesToPastMeetingThenGetNotesOriginalPlusAdded() {
        cManager1.addNewPastMeeting(contactSet1, pastTime, text01);
        PastMeeting returnedPastMeeting = cManager1.addMeetingNotes(1, textAdded);
        assertEquals(text01 + "\n" + textAdded, returnedPastMeeting.getNotes());
        System.out.println(returnedPastMeeting.getNotes());
    }

    @Test
    public void testAddMeetingNotesAddNotesToPastMeetingWithEmptyNotesThenGetNotesOriginalPlusAdded() {
        cManager1.addNewPastMeeting(contactSet1, pastTime, "");
        PastMeeting returnedPastMeeting = cManager1.addMeetingNotes(1, textAdded);
        assertEquals(textAdded, returnedPastMeeting.getNotes());
        System.out.println(returnedPastMeeting.getNotes());
    }

    /**
     * addNewContact tests
     */
    @Test (expected = IllegalArgumentException.class)
    public void testAddNewContactEmptyStringForNameThrowsIllegalArgumentException() {
        cManager2.addNewContact("", note01);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddNewContactEmptyStringForNotesThrowsIllegalArgumentException() {
        cManager2.addNewContact(name01, "");
    }

    @Test (expected = NullPointerException.class)
    public void testAddNewContactNullNameThrowsNullPointerException() {
        cManager2.addNewContact(null, note01);
    }

    @Test (expected = NullPointerException.class)
    public void testAddNewContactNullNotesThrowsNullPointerException() {
        cManager2.addNewContact(name01, null);
    }

    @Test
    public void testAddNewContactAdd2NewContactsReturnsCorrectIDs() {
        assertEquals(1, cManager2.addNewContact(name01, note01));
        assertEquals(2, cManager2.addNewContact(name02, note02));
        Set<Contact> testContactSet = cManager2.getContacts("");
    }

    /**
     * getContacts(String name) tests
     */
    @Test (expected = NullPointerException.class)
    public void testGetContactsNullNameThrowsNullPointerException() {
        String nullString = null;
        cManager1.getContacts(nullString);
    }

    @Test
    public void testGetContactsEmptyStringReturnsAllCurrentContacts() {
        Set<Contact> testContactSet;
        testContactSet = cManager2.getContacts("");
        assertEquals(testContactSet.size(), 0);

        cManager2.addNewContact(name01, note01);
        testContactSet = cManager2.getContacts("");
        assertEquals(testContactSet.size(), 1);

        cManager2.addNewContact(name02, note02);
        testContactSet = cManager2.getContacts("");
        assertEquals(testContactSet.size(), 2);
    }

    @Test
    public void testGetContactsReturnsContactsWithNamesContainingSpecifiedString() {
        /**
         * Looking for String "uce" in Adam, Bruce & Claire
         */
        cManager2.addNewContact(name01, note01);
        cManager2.addNewContact(name02, note02);
        cManager2.addNewContact(name03, note03);
        Set<Contact> testContactSet = cManager2.getContacts("uce");
        assertEquals(testContactSet.size(), 1);
        /**
         * Adding Lucette and looking again for "uce"
         */
        cManager2.addNewContact(name12, note12);
        testContactSet = cManager2.getContacts("uce");
        assertEquals(testContactSet.size(), 2);
    }

    /**
     * getContacts(int... ids) tests
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGetContactsByIdNoIDsProvidedThrowsIllegalArgumentException() {
        int[] ids = new int[0];
        cManager1.getContacts(ids);
    }

    @Test
    public void testGetContactsByIdReturnsCorrespondingNumberOfContactsForSuppliedIDs() {
        assertEquals(2, cManager1.getContacts(2, 4).size());
        assertEquals(5, cManager1.getContacts(1,2,3,4,5).size());
        assertEquals(5, cManager1.getContacts(5,4,3,2,1).size());
        assertEquals(1, cManager1.getContacts(3).size());
    }

    @Test
    public void testGetContactsByIdIgnoresDuplicateSuppliedIDs() {
        assertEquals(3, cManager1.getContacts(1,2,2,4,4).size());
        assertEquals(1, cManager1.getContacts(3,3,3,3,3,3).size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetContactsByIdThrowsIllegalArgumentExceptionForUnknownIDs() {
        cManager1.getContacts(37);
        cManager1.getContacts(1376, 94, 232);
        cManager1.getContacts(1376, 94, 1376, 232, 94);
    }

    /**
     * Persistence tests
     */
    @Test
    public void testFlushCreatesFile() {
        cManager1.flush();
        Path path = FileSystems.getDefault().getPath(TEXT_FILE_NAME);
        assertTrue(Files.exists(path));
    }

    @Test
    public void testFlushCreatesFileOpenedByNewContactManagerImpl() {
        cManager2.addNewContact(name01, note01);
        cManager2.flush();
        ContactManager cManager3 = new ContactManagerImpl();
        assertEquals(1, cManager3.getContacts("").size());
    }

    @Test
    public void testNumberOfContactsAfterFlushThenStartup() {
        ContactManager cManager3 = new ContactManagerImpl();
        cManager3.addNewContact(name01, note01);
        cManager3.addNewContact(name02, note02);
        cManager3.addNewContact(name03, note03);
        cManager3.flush();
        ContactManager cManager4 = new ContactManagerImpl();
        assertEquals(3, cManager4.getContacts("").size());
    }

    @Test
    public void testGetNotesAfterAddNewPastMeetingFlushThenStartup() {
        cManager1.addNewPastMeeting(contactSet1, pastTime, text01);
        cManager1.addNewPastMeeting(contactSet2, pastTime, text02);
        cManager1.flush();
        ContactManager cManager4 = new ContactManagerImpl();
        assertEquals(text01, cManager4.getPastMeeting(1).getNotes());
        assertEquals(text02, cManager4.getPastMeeting(2).getNotes());
    }
}