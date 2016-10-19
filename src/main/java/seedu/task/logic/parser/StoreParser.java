package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.StoreCommand;

/**
 * Parser class used to parse a store command
 */
public class StoreParser extends Parser {

	@Override
	public Command parseCommand(String args) {
		Command toReturn = null;
		
		toReturn = isGoodPath(args) ? getIncorrectCommand() : getStoreCommand(args);
		return toReturn;
	}
	
	private boolean isGoodPath(String pathToStoreFolder) {
		File f = new File(pathToStoreFolder);
		return f.exists() && f.isDirectory();
	}
	
	/**
	 * returns an incorrect StoreCommand
	 */
	private Command getIncorrectCommand() {
		return new IncorrectCommand(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StoreCommand.MESSAGE_USAGE));
	}
	
	/**
	 * Returns a valid store command given the following arguments
	 */
	private Command getStoreCommand(String args) {
		Path path = Paths.get(args).toAbsolutePath();
		return new StoreCommand(path.toString());
	}
}
