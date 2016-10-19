package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;

/**
 * Parser class used to parse a clear command
 */
public class ClearParser extends Parser {

    @Override
    public Command parseCommand(String arguments) {
        arguments = arguments.trim();
        if (!isValidArgs(arguments)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        }
        return new ClearCommand(arguments);
    }

    /**
     * Returns true if arguments for clear command are valid
     */
    private boolean isValidArgs(String arguments) {
        return arguments.isEmpty() || arguments.equals(ClearCommand.OPTION_CLEAR_ALL);
    }

}
