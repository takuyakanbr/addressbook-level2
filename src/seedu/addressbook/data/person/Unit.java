package seedu.addressbook.data.person;

/**
 * Represents a unit in a Person's address.
 * Guarantees: immutable.
 */
public class Unit {

    public final String value;

    public Unit(String value) {
        this.value = value;
    }

    public boolean equals(Object other) {
        return other instanceof Unit && this.value.equals(((Unit) other).value);
    }

    public String toString() {
        return value;
    }
}
