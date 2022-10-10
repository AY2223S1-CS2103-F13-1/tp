package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.client.ClientCommand;
import seedu.address.logic.commands.client.ListClientCommand;
import seedu.address.logic.commands.issue.IssueCommand;
import seedu.address.logic.commands.issue.ListIssueCommand;
import seedu.address.logic.commands.project.ListProjectCommand;
import seedu.address.logic.commands.project.ProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    public static void main(String[] args) {
        try {
            new AddressBookParser().parseCommand("client -a n/name p/98765432 e/email@example.com a/address t/tag");
            new AddressBookParser().parseCommand("client -e 2 n/name p/98765432 e/email@example.com a/address t/tag");
            new AddressBookParser().parseCommand("client -d 1 n/Amy");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\w+)(?<flag>(\\s+-\\w+)?)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String flag = matcher.group("flag").trim();
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case ClientCommand.COMMAND_WORD:
            return new ClientCommandParser().parse(flag, arguments);

        case IssueCommand.COMMAND_WORD:
            return new IssueCommandParser.parse(flag, arguments);

        case ProjectCommand.COMMAND_WORD:
            return new ProjectCommandParser.parse(flag, arguments);

        // TODO: Refactor commands below if necessary
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
