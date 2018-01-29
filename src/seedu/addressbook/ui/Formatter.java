package seedu.addressbook.ui;

import java.util.List;

import static seedu.addressbook.common.Messages.*;

/**
 * Generates and formats messages for display.
 */
public class Formatter {

    /** A decorative prefix added to the beginning of lines printed by AddressBook */
    private static final String LINE_PREFIX = "|| ";

    /** A platform independent line separator. */
    private static final String LS = System.lineSeparator();

    private static final String DIVIDER = "===================================================";

    /** Format of indexed list item */
    private static final String MESSAGE_INDEXED_LIST_ITEM = "\t%1$d. %2$s";

    /** Offset required to convert between 1-indexing and 0-indexing.  */
    public static final int DISPLAYED_INDEX_OFFSET = 1;

    /** Formats a message before displaying it to the user. */
    String formatMessageForDisplay(String message) {
        return LINE_PREFIX + message.replace("\n", LS + LINE_PREFIX);
    }

    String getUserInputPromptMessage() {
        return LINE_PREFIX + "Enter command: ";
    }

    String getUserInputEchoMessage(String userInput) {
        return "[Command entered:" + userInput + "]";
    }

    String getWelcomeMessage(String version, String storageFilePath) {
        String storageFileInfo = String.format(MESSAGE_USING_STORAGE_FILE, storageFilePath);
        return combineLines(DIVIDER,
                DIVIDER,
                MESSAGE_WELCOME,
                version,
                MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE,
                storageFileInfo,
                DIVIDER);
    }

    String getGoodbyeMessage() {
        return combineLines(MESSAGE_GOODBYE, DIVIDER, DIVIDER);
    }

    String getInitFailedMessage() {
        return combineLines(MESSAGE_INIT_FAILED, DIVIDER, DIVIDER);
    }

    String getCommandFeedbackMessage(String feedback) {
        return combineLines(feedback, DIVIDER);
    }

    /** Combine the specified lines into a single string, with line breaks between them. */
    private String combineLines(String... lines) {
        final StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            if (i > 0) {
                formatted.append("\n");
            }
            formatted.append(lines[i]);
        }
        return formatted.toString();
    }

    /** Formats a list of strings as a viewable indexed list. */
    String getIndexedListForViewing(List<String> listItems) {
        final StringBuilder formatted = new StringBuilder();
        int displayIndex = 0 + DISPLAYED_INDEX_OFFSET;
        for (String listItem : listItems) {
            formatted.append(getIndexedListItemForViewing(displayIndex, listItem)).append("\n");
            displayIndex++;
        }
        return formatted.toString();
    }

    /**
     * Formats a string as a viewable indexed list item.
     *
     * @param visibleIndex visible index for this listing
     */
    private String getIndexedListItemForViewing(int visibleIndex, String listItem) {
        return String.format(MESSAGE_INDEXED_LIST_ITEM, visibleIndex, listItem);
    }

}
