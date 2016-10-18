package seedu.task.logic.commands;

import java.io.IOException;

import seedu.task.commons.core.Config;
import seedu.task.commons.util.ConfigUtil;

/**
 * Stores current task book in the specified location.
 */
public class StoreCommand extends Command{
	
	public static final String COMMAND_WORD = "store";
	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Stores current data in the given location. " 
		    + "\nParameters: [FILE_LOCATION] " 
		    + "\nExample: " + COMMAND_WORD + " C:\\Users\\Jim\\Desktop";
	
	public static final String MESSAGE_SUCCESS = "Storage Location Updated: %1$s";
	public static final String MESSAGE_FAIL = "Unable to save new location into config";
	
	private String newSaveLocation;
	
	public StoreCommand() {
		
	}
	
	public StoreCommand(String fileLocation) {
		newSaveLocation = fileLocation;
	}

	@Override
	public CommandResult execute() {
		try {
			ConfigUtil.saveConfig(new Config(newSaveLocation), Config.USER_CONFIG_FILE);
			model.indicateStorageFilePathChanged(newSaveLocation);
		} catch (IOException e) {
		    return new CommandResult(MESSAGE_FAIL);
		}
		return new CommandResult(String.format(MESSAGE_SUCCESS, newSaveLocation));
	}

}
