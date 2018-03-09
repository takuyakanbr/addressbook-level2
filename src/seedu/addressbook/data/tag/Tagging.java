package seedu.addressbook.data.tag;

import seedu.addressbook.data.person.Person;

/**
 * Represents an addition or deletion of a tag on a person.
 */
public class Tagging {

    /**
     * Represents the type of a {@code Tagging} object.
     */
    public enum Type {
        ADD, DELETE
    }

    private final Person person;
    private final Tag tag;
    private final Type type;

    /**
     * Creates a new {@code Tagging} that represents the addition or deletion
     * of the specified tag on the specified person.
     */
    public Tagging(Person person, Tag tag, Type type) {
        this.person = person;
        this.tag = tag;
        this.type = type;
    }

    public Person getPerson() {
        return person;
    }

    public Tag getTag() {
        return tag;
    }

    public Type getType() {
        return type;
    }
}
