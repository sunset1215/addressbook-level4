//@@author A0161247J
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.task.commons.util.FileUtil;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.StoreCommand;

/**
 * Parser class used to parse a store command
 */
public class StoreParser extends Parser {

	@Override
	public Command parseCommand(String args) {
	    args = args.trim();
	    if (args.isEmpty()) {
	        return new StoreCommand("");
	    }

	    if (!FileUtil.isDirectory(args)) {
	        return new IncorrectCommand(
	                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StoreCommand.MESSAGE_USAGE));
	    }
	    
	    return new StoreCommand(args);

	}
	
}
