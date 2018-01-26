package seedu.addressbook.data.person;

/**
 * Represents a postal code in a Person's address.
 * Guarantees: immutable.
 */
public class PostalCode {

    public final String postalCode;

    public PostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
