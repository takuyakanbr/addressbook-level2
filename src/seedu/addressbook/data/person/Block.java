package seedu.addressbook.data.person;

/**
 * Represents a block in a Person's address.
 * Guarantees: immutable.
 */
public class Block {

    public final String value;

    public Block(String value) {
        this.value = value;
    }

    public boolean equals(Object other) {
        return other instanceof Block && this.value.equals(((Block) other).value);
    }

    public String toString() {
        return value;
    }
}
