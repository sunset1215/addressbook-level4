package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.CompleteCommand;
import seedu.task.logic.commands.IncorrectCommand;

//@@author A0138704E
/**
 * Parser class used to parse a complete command
 */
public class CompleteParser extends Parser {

    /**
     * Parses arguments in the context of the complete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command parseCommand(String arguments) {
        Optional<Integer> index = parseIndex(arguments);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }

        return new CompleteCommand(index.get());
    }

}
