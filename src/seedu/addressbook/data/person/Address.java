package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final String EXAMPLE = "123, Some Street, #12-345, 123456";
    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Person addresses must be in the following format: Block, Street, Unit, PostalCode";
    public static final String ADDRESS_VALIDATION_REGEX = "(?:([\\w\\d#\\- ]*), ?){3}([\\w\\d]*)";
    public static final String ADDRESS_SPLIT_REGEX = ", ?";

    private static final int ADDRESS_PART_BLOCK_INDEX = 0;
    private static final int ADDRESS_PART_STREET_INDEX = 1;
    private static final int ADDRESS_PART_UNIT_INDEX = 2;
    private static final int ADDRESS_PART_POSTALCODE_INDEX = 3;

    public final Block block;
    public final Street street;
    public final Unit unit;
    public final PostalCode postalCode;
    private boolean isPrivate;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Address(String address, boolean isPrivate) throws IllegalValueException {
        String trimmedAddress = address.trim();
        this.isPrivate = isPrivate;
        if (!isValidAddress(trimmedAddress)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }

        String[] addressParts = trimmedAddress.split(ADDRESS_SPLIT_REGEX);
        block = new Block(addressParts[ADDRESS_PART_BLOCK_INDEX]);
        street = new Street(addressParts[ADDRESS_PART_STREET_INDEX]);
        unit = new Unit(addressParts[ADDRESS_PART_UNIT_INDEX]);
        postalCode = new PostalCode(addressParts[ADDRESS_PART_POSTALCODE_INDEX]);
    }

    /**
     * Returns true if a given string is a valid person address.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s", block, street, unit, postalCode);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Address)) {
            return false;
        }
        Address otherAddress = (Address) other;
        return block.equals(otherAddress.block) && street.equals(otherAddress.street)
                && unit.equals(otherAddress.unit) && postalCode.equals(otherAddress.postalCode);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
