package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ListCommand;

/**
 * Parser class used to parse a list command
 */
public class ListParser extends Parser {

    @Override
    public Command parseCommand(String arguments) {
        arguments = arguments.trim();
        if (!isValidArgs(arguments)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        return new ListCommand(arguments);
    }

    /**
     * Returns true if arguments for clear command are valid
     */
    private boolean isValidArgs(String arguments) {
        return arguments.isEmpty() 
                || arguments.equals(ListCommand.OPTION_LIST_ALL)
                || arguments.equals(ListCommand.OPTION_LIST_COMPLETE)
                || arguments.equals(ListCommand.OPTION_LIST_PENDING);
    }

}
