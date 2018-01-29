package seedu.addressbook.data.person;

/**
 * Provides functionality common to classes representing a Person's contact details.
 */
public abstract class Contact {

    public final String value;
    private final boolean isPrivate;

    public Contact(String value, boolean isPrivate) {
        this.value = value.trim();
        this.isPrivate = isPrivate;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other != null
                && this.getClass() == other.getClass()
                && this.value.equals(((Contact) other).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
