package seedu.addressbook.commands;

import org.junit.Before;
import org.junit.Test;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.tag.UniqueTagList;
import seedu.addressbook.ui.TextUi;
import seedu.addressbook.util.TestUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class EditCommandTest {
    private static final Set<String> EMPTY_STRING_LIST = Collections.emptySet();

    private AddressBook emptyAddressBook;
    private AddressBook addressBook;

    private List<ReadOnlyPerson> emptyDisplayList;
    private List<ReadOnlyPerson> listWithEveryone;
    private List<ReadOnlyPerson> listWithSurnameDoe;

    @Before
    public void setUp() throws Exception {
        Person johnDoe = new Person(new Name("John Doe"), new Phone("61234567", false),
                new Email("john@doe.com", false), new Address("395C Ben Road", false),
                new UniqueTagList(new Tag("tag1")));
        Person janeDoe = new Person(new Name("Jane Doe"), new Phone("91234567", false),
                new Email("jane@doe.com", false), new Address("33G Ohm Road", false),
                new UniqueTagList(new Tag("tag2")));
        Person samDoe = new Person(new Name("Sam Doe"), new Phone("63345566", false),
                new Email("sam@doe.com", false), new Address("55G Abc Road", false), new UniqueTagList());
        Person davidGrant = new Person(new Name("David Grant"), new Phone("61121122", false),
                new Email("david@grant.com", false), new Address("44H Define Road", false), new UniqueTagList());

        emptyAddressBook = TestUtil.createAddressBook();
        addressBook = TestUtil.createAddressBook(johnDoe, janeDoe, davidGrant, samDoe);

        emptyDisplayList = TestUtil.createList();

        listWithEveryone = TestUtil.createList(johnDoe, janeDoe, davidGrant, samDoe);
        listWithSurnameDoe = TestUtil.createList(johnDoe, janeDoe, samDoe);
    }

    @Test
    public void editCommand_invalidPhone_throwsException() {
        final String[] invalidNumbers = { "", " ", "1234-5678", "[]\\[;]", "abc", "a123", "+651234" };
        for (String number : invalidNumbers) {
            assertConstructingInvalidEditCmdThrowsException(number, false, Email.EXAMPLE, true,
                    Address.EXAMPLE, false, EMPTY_STRING_LIST);
        }
    }

    @Test
    public void editCommand_invalidEmail_throwsException() {
        final String[] invalidEmails = { "", " ", "def.com", "@", "@def", "@def.com", "abc@",
                                         "@invalid@email", "invalid@email!", "!invalid@email" };
        for (String email : invalidEmails) {
            assertConstructingInvalidEditCmdThrowsException(Phone.EXAMPLE, false, email, false,
                    Address.EXAMPLE, false, EMPTY_STRING_LIST);
        }
    }

    @Test
    public void editCommand_invalidAddress_throwsException() {
        final String[] invalidAddresses = { "", " " };
        for (String address : invalidAddresses) {
            assertConstructingInvalidEditCmdThrowsException(Phone.EXAMPLE, true, Email.EXAMPLE,
                    true, address, true, EMPTY_STRING_LIST);
        }
    }

    @Test
    public void editCommand_invalidTags_throwsException() {
        final String[][] invalidTags = { { "" }, { " " }, { "'" }, { "[]\\[;]" }, { "validTag", "" },
                                         { "", " " } };
        for (String[] tags : invalidTags) {
            Set<String> tagsToAdd = new HashSet<>(Arrays.asList(tags));
            assertConstructingInvalidEditCmdThrowsException(Phone.EXAMPLE, true, Email.EXAMPLE,
                    true, Address.EXAMPLE, false, tagsToAdd);
        }
    }

    /**
     * Asserts that attempting to construct an edit command with the supplied
     * invalid data throws an IllegalValueException
     */
    private void assertConstructingInvalidEditCmdThrowsException(String phone, boolean isPhonePrivate, String email,
                                                                 boolean isEmailPrivate, String address,
                                                                 boolean isAddressPrivate, Set<String> tags) {
        try {
            new EditCommand(1, phone, isPhonePrivate, email, isEmailPrivate, address, isAddressPrivate,
                    tags);
        } catch (IllegalValueException e) {
            return;
        }
        String error = String.format(
                "An edit command was successfully constructed with invalid input: %s %s %s %s %s %s %s %s",
                1, phone, isPhonePrivate, email, isEmailPrivate, address, isAddressPrivate, tags);
        fail(error);
    }

    @Test
    public void editCommand_validData_correctlyConstructed() throws Exception {
        EditCommand command = new EditCommand(1, Phone.EXAMPLE, true, Email.EXAMPLE, false,
                Address.EXAMPLE, true, EMPTY_STRING_LIST);

        assertEquals(Phone.EXAMPLE, command.getPhone().value);
        assertTrue(command.getPhone().isPrivate());
        assertEquals(Email.EXAMPLE, command.getEmail().value);
        assertFalse(command.getEmail().isPrivate());
        assertEquals(Address.EXAMPLE, command.getAddress().value);
        assertTrue(command.getAddress().isPrivate());
        boolean isTagListEmpty = !command.getTags().iterator().hasNext();
        assertTrue(isTagListEmpty);
    }

    @Test
    public void execute_emptyAddressBook_returnsPersonNotFoundMessage() throws Exception {
        assertEditFailsDueToNoSuchPerson(1, emptyAddressBook, listWithEveryone);
    }

    @Test
    public void execute_noPersonDisplayed_returnsInvalidIndexMessage() throws Exception {
        assertEditFailsDueToInvalidIndex(1, addressBook, emptyDisplayList);
    }

    @Test
    public void execute_targetPersonNotInAddressBook_returnsPersonNotFoundMessage() throws Exception {
        Person notInAddressBookPerson = new Person(new Name("Not In Book"), new Phone("63331444", false),
                new Email("notin@book.com", false), new Address("156D Grant Road", false), new UniqueTagList());
        List<ReadOnlyPerson> listWithPersonNotInAddressBook = TestUtil.createList(notInAddressBookPerson);

        assertEditFailsDueToNoSuchPerson(1, addressBook, listWithPersonNotInAddressBook);
    }

    @Test
    public void execute_invalidIndex_returnsInvalidIndexMessage() throws Exception {
        assertEditFailsDueToInvalidIndex(0, addressBook, listWithEveryone);
        assertEditFailsDueToInvalidIndex(-1, addressBook, listWithEveryone);
        assertEditFailsDueToInvalidIndex(listWithEveryone.size() + 1, addressBook, listWithEveryone);
    }

    @Test
    public void execute_validIndex_personIsDeleted() throws Exception {
        assertEditingSuccessful(1, addressBook, listWithSurnameDoe);
        assertEditingSuccessful(listWithSurnameDoe.size(), addressBook, listWithSurnameDoe);

        int middleIndex = (listWithSurnameDoe.size() / 2) + 1;
        assertEditingSuccessful(middleIndex, addressBook, listWithSurnameDoe);
    }

    /**
     * Creates a new edit command.
     *
     * @param targetVisibleIndex of the person that we want to edit.
     */
    private EditCommand createEditCommand(int targetVisibleIndex, AddressBook addressBook,
                                            List<ReadOnlyPerson> displayList) throws IllegalValueException {

        EditCommand command = new EditCommand(targetVisibleIndex, "61234567", false,
                "john@doe.com", false, "395C Ben Road", false, EMPTY_STRING_LIST);
        command.setData(addressBook, displayList);

        return command;
    }

    /**
     * Executes the command, and checks that the execution was what we had expected.
     */
    private void assertCommandBehaviour(EditCommand editCommand, String expectedMessage,
                                        AddressBook expectedAddressBook, AddressBook actualAddressBook) {

        CommandResult result = editCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedAddressBook.getAllPersons(), actualAddressBook.getAllPersons());
    }

    /**
     * Asserts that the index is not valid for the given display list.
     */
    private void assertEditFailsDueToInvalidIndex(int invalidVisibleIndex, AddressBook addressBook,
                                                  List<ReadOnlyPerson> displayList) throws Exception {

        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

        EditCommand command = createEditCommand(invalidVisibleIndex, addressBook, displayList);
        assertCommandBehaviour(command, expectedMessage, addressBook, addressBook);
    }

    /**
     * Asserts that the person at the specified index cannot be edited, because that person
     * is not in the address book.
     */
    private void assertEditFailsDueToNoSuchPerson(int visibleIndex, AddressBook addressBook,
                                                  List<ReadOnlyPerson> displayList) throws Exception {

        String expectedMessage = Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK;

        EditCommand command = createEditCommand(visibleIndex, addressBook, displayList);
        assertCommandBehaviour(command, expectedMessage, addressBook, addressBook);
    }

    /**
     * Asserts that the person at the specified index can be successfully edited.
     *
     * The addressBook passed in will not be modified (no side effects).
     *
     * @throws UniquePersonList.PersonNotFoundException if the selected person is not in the address book
     */
    private void assertEditingSuccessful(int targetVisibleIndex, AddressBook addressBook,
                                         List<ReadOnlyPerson> displayList) throws Exception {

        ReadOnlyPerson targetPerson = displayList.get(targetVisibleIndex - TextUi.DISPLAYED_INDEX_OFFSET);

        AddressBook expectedAddressBook = TestUtil.clone(addressBook);
        expectedAddressBook.editPerson(targetPerson, new Phone("61234567", false),
                new Email("john@doe.com", false), new Address("395C Ben Road", false), new UniqueTagList());
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, targetPerson);

        AddressBook actualAddressBook = TestUtil.clone(addressBook);

        EditCommand command = createEditCommand(targetVisibleIndex, actualAddressBook, displayList);
        assertCommandBehaviour(command, expectedMessage, expectedAddressBook, actualAddressBook);
    }
}
