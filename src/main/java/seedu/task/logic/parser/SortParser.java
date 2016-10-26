//@@author A0161247J
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.SortCommand;

/**
 * Parser class used to parse a sort command
 */
public class SortParser extends Parser {

    /**
     * Parses arguments in the context of the sort task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command parseCommand(String arguments) {
        if (!arguments.isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return new SortCommand();
    }

}
