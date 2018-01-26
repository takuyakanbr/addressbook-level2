package seedu.addressbook.data.person;

/**
 * Represents a postal code in a Person's address.
 * Guarantees: immutable.
 */
public class PostalCode {

    public final String value;

    public PostalCode(String value) {
        this.value = value;
    }

    public boolean equals(Object other) {
        return other instanceof PostalCode && this.value.equals(((PostalCode) other).value);
    }

    public String toString() {
        return value;
    }
}
