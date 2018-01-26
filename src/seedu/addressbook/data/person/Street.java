package seedu.addressbook.data.person;

/**
 * Represents a street in a Person's address.
 * Guarantees: immutable.
 */
public class Street {

    public final String value;

    public Street(String value) {
        this.value = value;
    }

    public boolean equals(Object other) {
        return other instanceof Street && this.value.equals(((Street) other).value);
    }

    public String toString() {
        return value;
    }
}
